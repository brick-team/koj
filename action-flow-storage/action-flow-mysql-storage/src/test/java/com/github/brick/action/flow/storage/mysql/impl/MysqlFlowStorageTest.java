/*
 *    Copyright [2022] [brick-team]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.brick.action.flow.storage.mysql.impl;

import com.github.brick.action.flow.method.entity.*;
import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.github.brick.action.flow.method.entity.api.ParamIn;
import com.github.brick.action.flow.method.enums.FLowModel;
import com.github.brick.action.flow.method.enums.HttpClientType;
import com.github.brick.action.flow.method.execute.impl.FlowExecuteImpl;
import com.github.brick.action.flow.storage.mysql.entity.AfApiParamExEntity;
import com.github.brick.action.flow.storage.mysql.repository.AfApiParamExEntityRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class MysqlFlowStorageTest extends CommonTest {
    MysqlFlowStorage mysqlFlowStorage;
    MysqlWatcherStorage mysqlWatcherStorage;
    MysqlActionStorage actionStorage;
    MysqlApiStorage apiStorage;
    AfApiParamExEntityRepository paramExEntityRepository;
    MysqlExtractStorage mysqlExtractStorage;
    FlowExecuteImpl flowExecute;

    @Before
    public void init() {
        mysqlFlowStorage = context.getBean(MysqlFlowStorage.class);
        mysqlWatcherStorage = context.getBean(MysqlWatcherStorage.class);
        actionStorage = context.getBean(MysqlActionStorage.class);
        apiStorage = context.getBean(MysqlApiStorage.class);
        paramExEntityRepository = context.getBean(AfApiParamExEntityRepository.class);
        mysqlExtractStorage = context.getBean(MysqlExtractStorage.class);
        flowExecute = new FlowExecuteImpl();
        flowExecute.init(FLowModel.XML, HttpClientType.OKHTTP);

    }

    @Test
    public void save() {


        // 登陆接口 ff8080817fd8d4fa017fd8d4ff870000

        ApiParamEntity username = new ApiParamEntity();
        username.setId(17L);
        username.setIn(ParamIn.formdata);
        username.setName("username");
        username.setRequire(true);
        username.setParamGroup("a");
        username.setEx("username");


        ApiParamEntity password = new ApiParamEntity();
        password.setId(18L);
        password.setIn(ParamIn.formdata);
        password.setName("password");
        password.setRequire(true);
        password.setParamGroup("a");
        password.setEx("password");

        // 获取用户信息接口 ff8080817fd8d4fa017fd8d4ffe30001
        ApiParamEntity token = new ApiParamEntity();
        token.setId(19L);
        token.setIn(ParamIn.header);
        token.setName("token");
        token.setRequire(true);
        token.setExId("ff8080817fd8d20f017fd8d214b30000");

        List<ApiParamEntity> list = new ArrayList<>();
        list.add(token);
        list.add(username);
        list.add(password);
        mysqlFlowStorage.saveForApi("获取用户信息", new ArrayList<>(), list);


    }


    @Test
    public void runner() throws Exception {
        AllEntity allEntity = new AllEntity();
        String flowID = "1";
        FlowEntity getUserInfo = this.mysqlFlowStorage.findById(Long.valueOf(flowID));


        Map<String, WatcherEntity> watcherMap = new HashMap<>();

        Map<String, ActionEntity> actionTagMap = new HashMap<>();

        Map<String, ExtractEntity> exMap = new HashMap<>();

        Map<String, List<ParamEntity>> paramsMap = new HashMap<>();

        Map<String, ApiEntity> apiEntityMap = new HashMap<>();

        // 这段内容相当于前端调用参数组装
        ArrayList<ParamEntity> value = new ArrayList<>();
        ParamEntity username = new ParamEntity();
        username.setGroup("a");
        username.setKey("username");
        username.setValue("username");

        value.add(username);
        ParamEntity password = new ParamEntity();
        password.setGroup("a");
        password.setKey("password");
        password.setValue("password");

        value.add(password);
        paramsMap.put("a", value);


        List<WorkEntity> workEntities = getUserInfo.getWorkEntities();

        for (WorkEntity workEntity : workEntities) {
            String type = workEntity.getType();
            if (type.equals("watcher")) {
                WatcherEntity watcherEntity = mysqlWatcherStorage.findById(Long.valueOf(workEntity.getRefId()));
                watcherMap.put(workEntity.getRefId(), watcherEntity);
            }
            else if (type.equals("action")) {
                ActionEntity actionEntity = actionStorage.findById(Long.valueOf(workEntity.getRefId()));
                actionTagMap.put(workEntity.getRefId(), actionEntity);
            }
            else if (type.equals("api")) {
                ApiEntity apiEntity = apiStorage.findById(Long.valueOf(workEntity.getRefId()));
                List<ApiParamEntity> params = apiEntity.getParams();
                // 处理参数信息
                handlerApiParamValue(flowID, exMap, params, apiEntityMap);
                apiEntityMap.put(workEntity.getRefId(), apiEntity);
            }
        }


        Map<String, Object> stringObjectMap = flowExecute.getStringObjectMap(new ResultEntity(), watcherMap, actionTagMap,
                exMap,
                paramsMap,
                apiEntityMap,
                workEntities);


    }

    private void handlerApiParamValue(String flowID, Map<String, ExtractEntity> exMap, List<ApiParamEntity> params, Map<String, ApiEntity> apiEntityMap) {
        for (ApiParamEntity param : params) {
            handlerOneApiValue(flowID, exMap, param, apiEntityMap);
        }
    }

    private void handlerOneApiValue(String flowID, Map<String, ExtractEntity> exMap, ApiParamEntity param, Map<String, ApiEntity> apiEntityMap) {
        Long id = param.getId();
        AfApiParamExEntity byApiParamIdAndFlowId = paramExEntityRepository.findByApiParamIdAndFlowId(id, Long.valueOf(flowID));
        Long exId = byApiParamIdAndFlowId.getExId();
        if (exId != null) {

            ExtractEntity extractEntity = mysqlExtractStorage.findById(Long.valueOf(exId));
            String fromApi = extractEntity.getFromApi();
            if (fromApi != null) {
                ApiEntity byId = apiStorage.findById(Long.valueOf(fromApi));
                List<ApiParamEntity> params = byId.getParams();
                handlerApiParamValue(flowID, exMap, params, apiEntityMap);
                apiEntityMap.put(fromApi, byId);

            }


            exMap.put(extractEntity.getId(), extractEntity);
            param.setExId(extractEntity.getId());
        }
        else {
            String paramGroup = byApiParamIdAndFlowId.getParamGroup();
            String ex = byApiParamIdAndFlowId.getEx();
            param.setParamGroup(paramGroup);
            param.setEx(ex);

        }

        List<ApiParamEntity> paramEntities = param.getParamEntities();
        for (ApiParamEntity paramEntity : paramEntities) {
            handlerOneApiValue(flowID, exMap, paramEntity, apiEntityMap);
        }

    }


}