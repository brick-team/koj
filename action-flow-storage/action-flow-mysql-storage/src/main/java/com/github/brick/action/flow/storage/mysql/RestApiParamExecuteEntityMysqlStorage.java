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

import com.github.brick.action.flow.model.entity.RestApiParam;
import com.github.brick.action.flow.model.execute.ParamExecuteEntity;
import com.github.brick.action.flow.storage.api.child.ParamExecuteEntityStorage;
import com.github.brick.action.flow.storage.mysql.dao.RestApiParamExecuteMysqlStorageDao;

import java.util.List;

/**
 * restApi参数处理
 *
 * @author xupenggao
 */
public class RestApiParamExecuteEntityMysqlStorage implements ParamExecuteEntityStorage {

    private final RestApiParamExecuteMysqlStorageDao restApiParamExecuteMysqlStorageDao;

    public RestApiParamExecuteEntityMysqlStorage() {
        restApiParamExecuteMysqlStorageDao = new RestApiParamExecuteMysqlStorageDao();
    }

    /**
     * 保存RestApiParam数据
     *
     * @param restApiParam restApi参数
     * @param actionId     actionId
     * @throws Exception 异常
     */
    @Override
    public void save(List<ParamExecuteEntity> restApiParam, Integer actionId) throws Exception {

        for (ParamExecuteEntity entity : restApiParam) {

            ParamExecuteEntity.ForRestApi restApi = entity.getRestApi();

            RestApiParam param = saveRestApiParam(restApi, actionId, null);

            List<ParamExecuteEntity.ForRestApi> restApis = restApi.getRestApiParams();

            if (restApis != null && !restApis.isEmpty()) {
                recursiveParam(actionId, param.getId(), restApis);
            }
        }
    }

    private void recursiveParam(Integer actionId, Integer pid, List<ParamExecuteEntity.ForRestApi> restApis) throws Exception {

        for (ParamExecuteEntity.ForRestApi restApi : restApis) {
            RestApiParam restApiParam = saveRestApiParam(restApi, actionId, pid);

            if (!restApi.getRestApiParams().isEmpty()) {
                recursiveParam(actionId, restApiParam.getId(), restApi.getRestApiParams());
            }
        }
    }

    private RestApiParam saveRestApiParam(ParamExecuteEntity.ForRestApi restApi, Integer actionId, Integer pid) throws Exception{

        RestApiParam restApiParam = new RestApiParam();
        restApiParam.setName(restApi.getName());
        restApiParam.setActionId(actionId);
        restApiParam.setRequire(restApi.isRequire() ? 1 : 0);
        restApiParam.setIn(restApi.getIn().name());
        restApiParam.setValue(restApi.getValue());
        restApiParam.setPid(pid);

        restApiParamExecuteMysqlStorageDao.saveAndValidate(restApiParam);

        return restApiParam;
    }
}
