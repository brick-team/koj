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

package com.github.brick.action.flow.storage.mysql;

import com.github.brick.action.flow.method.entity.ActionEntity;
import com.github.brick.action.flow.method.entity.ExtractEntity;
import com.github.brick.action.flow.method.entity.FormatEntity;
import com.github.brick.action.flow.method.entity.WatcherEntity;
import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.github.brick.action.flow.method.enums.WorkTypeModel;
import com.github.brick.action.flow.method.execute.MethodExecute;
import com.github.brick.action.flow.method.execute.impl.MethodExecuteImpl;
import com.github.brick.action.flow.method.extract.Extract;
import com.github.brick.action.flow.method.extract.ExtractImpl;
import com.github.brick.action.flow.method.format.Format;
import com.github.brick.action.flow.method.req.WorkNode;
import com.github.brick.action.flow.storage.api.*;
import com.github.brick.action.flow.storage.mysql.entity.AfActionParamExEntity;
import com.github.brick.action.flow.storage.mysql.entity.AfParamEntity;
import com.github.brick.action.flow.storage.mysql.repository.AfActionParamExEntityRepository;
import com.github.brick.action.flow.storage.mysql.repository.AfApiParamExEntityRepository;
import com.github.brick.action.flow.storage.mysql.repository.AfParamEntityRepository;
import com.github.brick.action.flow.storage.mysql.repository.AfWorkCzRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExecuteForMysql {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteForMysql.class);
    @Autowired
    FlowStorage mysqlFlowStorage;
    @Autowired
    WatcherStorage mysqlWatcherStorage;
    @Autowired
    ActionStorage actionStorage;
    @Autowired
    ActionParamStorage actionParamStorage;
    @Autowired
    ApiStorage apiStorage;
    @Autowired
    AfActionParamExEntityRepository afActionParamExEntityRepository;
    @Autowired
    AfApiParamExEntityRepository paramExEntityRepository;
    @Autowired
    AfParamEntityRepository afParamEntityRepository;
    @Autowired
    ExtractStorage mysqlExtractStorage;
    @Autowired
    WorkStorage mysqlWorkStorage;
    @Autowired
    ResultStorage mysqlResultStorage;
    @Autowired
    AfWorkCzRepository afWorkCzRepository;
    @Autowired
    WorkStorage workStorage;
    @Autowired
    FormatStorage mysqlFormatStorage;
    MethodExecute methodExecute = new MethodExecuteImpl();
    Extract extract = new ExtractImpl();
    SpelExpressionParser parser = new SpelExpressionParser();
    Gson gson = new Gson();

    /**
     * todo: 核心数据结构需要抽象
     *  1. 数据库结构确定
     *  2. allEntity 实体修正
     *      2.1 字面量和取值量抽离
     *
     * @param flowId
     * @param jsonData
     * @return
     */
    public Object execute(long flowId, String jsonData) throws Exception {
        List<WorkNode> byFlowId = workStorage.findByFlowId(flowId);
        List<AfParamEntity> allByFlowId = afParamEntityRepository.findAllByFlowId(flowId);
        Map<String, Map<String, String>> paramsFromDB = new HashMap<>();

        for (AfParamEntity afParamEntity : allByFlowId) {
            Map<String, String> map = paramsFromDB.get(afParamEntity.getGroup());
            if (map == null) {
                map = new HashMap<>();
                map.put(afParamEntity.getKey(), afParamEntity.getValue());
            }
            else {
                map.put(afParamEntity.getKey(), afParamEntity.getValue());
            }
            paramsFromDB.put(afParamEntity.getGroup(), map);
        }

        // 执行器执行结果
        // fixme:
        //  key : action \ api 的id ，如果重复请求怎么处理
        //  value: action \ api 执行结果
        Map<Long, Object> resultData = new HashMap<>();

        for (WorkNode workNode : byFlowId) {
            // 搜索 action 、api 、watcher
            handlerWorkNode(flowId, jsonData, paramsFromDB, workNode, resultData);
        }
        System.out.println(gson.toJson(resultData));

        return null;
    }

    private void handlerWorkNode(long flowId, String jsonData, Map<String, Map<String, String>> paramsFromDB, WorkNode workNode, Map<Long, Object> resultData) throws Exception {

        String type = workNode.getType();
        Long refId = workNode.getRefId();


        WorkTypeModel workTypeModel = WorkTypeModel.valueOf(type.toUpperCase());

        if (workTypeModel == WorkTypeModel.ACTION) {


            Object execute;
            try {
                execute = executeAction(flowId, jsonData, paramsFromDB, resultData, refId, workNode);
                List<WorkNode> then = workNode.getThen();
                for (WorkNode node : then) {
                    handlerWorkNode(flowId, jsonData, paramsFromDB, node, resultData);
                }
            } catch (Exception e) {
                List<WorkNode> cat = workNode.getCat();
                for (WorkNode node : cat) {
                    handlerWorkNode(flowId, jsonData, paramsFromDB, node, resultData);
                }
            }
        }
        else if (workTypeModel == WorkTypeModel.WATCHER) {
            try {
                handlerWatcher(flowId, jsonData, paramsFromDB, resultData, refId, workNode);
                List<WorkNode> then = workNode.getThen();
                for (WorkNode node : then) {
                    handlerWorkNode(flowId, jsonData, paramsFromDB, node, resultData);
                }
            } catch (Exception e) {
                List<WorkNode> cat = workNode.getCat();
                for (WorkNode node : cat) {
                    handlerWorkNode(flowId, jsonData, paramsFromDB, node, resultData);
                }
            }
        }
        else if (workTypeModel == WorkTypeModel.API) {
            ApiEntity byId = this.apiStorage.findById(refId);

            System.out.println();

        }
    }


    private void handlerWatcher(long flowId, String jsonData, Map<String, Map<String, String>> paramsFromDB, Map<Long, Object> resultData, Long refId, WorkNode workNode) throws Exception {
        WatcherEntity byId = this.mysqlWatcherStorage.findById(refId);
        String condition = byId.getCondition();
        String exId = byId.getExId();

        // 条件通过
        List<WatcherEntity.Then> thens = byId.getThens();
        // 条件不通过
        List<WatcherEntity.Catch> catchs = byId.getCatchs();

        ExtractEntity extractEntity = this.mysqlExtractStorage.findById(Long.valueOf(exId));
        String el = extractEntity.getEl();
        String fromAction = extractEntity.getFromAction();
        Object execute = null;
        if (fromAction != null) {
            Object o = resultData.get(Long.valueOf(fromAction));
            if (o == null) {

                execute = executeAction(flowId, jsonData, paramsFromDB, resultData, Long.valueOf(fromAction), workNode);
            }
            else {
                execute = o;
            }
        }
        // TODO: 2022/3/31 api接口分装
        String fromApi = extractEntity.getFromApi();

        // 如果是返回值是异常类执行 条件不通过
        if (execute instanceof Throwable) {
            for (WatcherEntity.Catch aCatch : catchs) {
                String actionId = aCatch.getActionId();
                executeAction(flowId, jsonData, paramsFromDB, resultData, Long.valueOf(actionId), workNode);
                String apiId = aCatch.getApiId();
            }
        }


        // 执行取值操作
        Object preData = this.extract.extract(execute, el);
        Expression expression = parser.parseExpression(preData + condition);
        EvaluationContext context = new StandardEvaluationContext();
        Boolean value = expression.getValue(context, Boolean.class);
//        logger.info("当前条件表达式 = [{}] , 判断结果 = [{}]", preData + condition, value);
        if (value) {
            for (WatcherEntity.Then then1 : thens) {
                String actionId = then1.getActionId();
                executeAction(flowId, jsonData, paramsFromDB, resultData, Long.valueOf(actionId), workNode);
                String apiId = then1.getApiId();
            }
        }
        else {
            for (WatcherEntity.Catch aCatch : catchs) {
                String actionId = aCatch.getActionId();
                executeAction(flowId, jsonData, paramsFromDB, resultData, Long.valueOf(actionId), workNode);
                String apiId = aCatch.getApiId();
            }
        }
    }

    private Object executeAction(long flowId, String jsonData, Map<String, Map<String, String>> paramsFromDB,
                                 Map<Long, Object> resultData, Long refId, WorkNode workNode) throws Exception {
        ActionEntity byId = this.actionStorage.findById(refId);

        List<String> types = new ArrayList<>();
        List<ArgData> argDataList = new ArrayList<>();
        for (ActionEntity.Param param : byId.getParams()) {
            AfActionParamExEntity byFlowIdAndId = this.afActionParamExEntityRepository.findByFlowIdAndActionParamId(flowId, Long.valueOf(param.getId()));

            types.add(param.getType());
            // 静态值直接用于参数
            String value = byFlowIdAndId.getValue();
            ArgData argData = new ArgData();
            argData.index = param.getIndex();
            Object data = null;
            data = value;
            // 通过参数组获取数据
            String paramGroupId = byFlowIdAndId.getParamGroupId();
            if (paramGroupId != null) {
                param.setParamGroup(paramGroupId);
                String ex = byFlowIdAndId.getEx();
                param.setEx(ex);

                data = exData(paramGroupId, ex, paramsFromDB, jsonData);
            }
            // 通过提取式进行数据获取
            Long exId = byFlowIdAndId.getExId();
            if (exId != null) {
                param.setExId(exId.toString());
                // todo: 根据exId提取数据
                data = exID();

            }
            Long formatId = byFlowIdAndId.getFormatId();
            if (formatId != null) {
                FormatEntity formatEntity = new FormatEntity();
                formatEntity.setId(formatId.toString());
                formatEntity.setClassStr(this.mysqlFormatStorage.findById(formatId));
                param.setFormatEntity(formatEntity);
                String type = param.getType();
                Class<?> typeClass = Class.forName(type);

                Class<?> formatClass = Class.forName(formatEntity.getClassStr());
                if (Format.class.isAssignableFrom(formatClass)) {
                    Object o = formatClass.newInstance();
                    Format format = (Format) o;
                    Object format1 = format.format(data, typeClass);
                    data = format1;
                }

            }
            argData.data = data;
            argDataList.add(argData);
        }

        argDataList.sort(Comparator.comparing(o -> o.index));
        List<Object> collect = argDataList.stream().map(s -> s.data).collect(Collectors.toList());

        // 执行 action

        // todo: 确认是否有办法知道异常是项目内的，

        Object execute = methodExecute.execute(byId.getClazzStr(), byId.getMethodStr(), types.toArray(new String[0]), collect);
        logger.info("执行 {} {} 结果 {}", byId.getClazzStr(), byId.getMethodStr(), execute);
        resultData.put(workNode.getId(), execute);
        return execute;

    }

    private Object exID() {

        return null;
    }

    private Object exData(String paramGroupId, String ex, Map<String, Map<String, String>> paramsFromDB, String jsonData) {
        // 直接从数据库参数表中获取
        // todo: jsonData 数据提取
        return paramsFromDB.get(paramGroupId).get(ex);
    }

    private static class ArgData {
        public Integer index;
        public Object data;
    }
}
