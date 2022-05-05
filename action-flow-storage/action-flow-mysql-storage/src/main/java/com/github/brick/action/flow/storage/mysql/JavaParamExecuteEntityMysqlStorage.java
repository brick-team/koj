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

import com.github.brick.action.flow.model.entity.JavaMethodParam;
import com.github.brick.action.flow.model.execute.ParamExecuteEntity;
import com.github.brick.action.flow.storage.api.child.ParamExecuteEntityStorage;
import com.github.brick.action.flow.storage.mysql.dao.JavaParamExecuteMysqlStorageDao;

import java.util.List;

/**
 * java参数处理
 *
 * @author xupenggao
 */
public class JavaParamExecuteEntityMysqlStorage implements ParamExecuteEntityStorage {

    private final JavaParamExecuteMysqlStorageDao javaParamExecuteMysqlStorageDao;

    public JavaParamExecuteEntityMysqlStorage() {
        javaParamExecuteMysqlStorageDao = new JavaParamExecuteMysqlStorageDao();
    }

    /**
     * 保存javaParam数据
     *
     * @param javaParams java参数
     * @param actionId   actionId
     * @throws Exception 异常
     */
    @Override
    public void save(List<ParamExecuteEntity> javaParams, Integer actionId) throws Exception {

        for (ParamExecuteEntity entity : javaParams) {

            ParamExecuteEntity.ForJavaMethod javaMethod = entity.getJavaMethod();

            JavaMethodParam param = saveJavaParam(javaMethod, actionId, null);

            List<ParamExecuteEntity.ForJavaMethod> javaMethodList = javaMethod.getRestApis();

            if (javaMethodList != null && !javaMethodList.isEmpty()) {
                recursiveParam(actionId, param.getId(), javaMethodList);
            }
        }

    }

    private void recursiveParam(Integer actionId, Integer pid, List<ParamExecuteEntity.ForJavaMethod> javaMethodList) throws Exception {

        for (ParamExecuteEntity.ForJavaMethod javaMethod : javaMethodList) {
            JavaMethodParam javaMethodParam = saveJavaParam(javaMethod, actionId, pid);

            if (!javaMethod.getRestApis().isEmpty()) {
                recursiveParam(actionId, javaMethodParam.getId(), javaMethod.getRestApis());
            }
        }
    }

    private JavaMethodParam saveJavaParam(ParamExecuteEntity.ForJavaMethod javaMethod, Integer actionId, Integer pid) throws Exception{

        JavaMethodParam javaMethodParam = new JavaMethodParam();
        javaMethodParam.setActionId(actionId);
        javaMethodParam.setPid(pid);
        javaMethodParam.setName(javaMethod.getName());
        javaMethodParam.setValue(javaMethod.getValue());
        javaMethodParam.setType(javaMethod.getType());
        javaMethodParam.setIndex(javaMethod.getIndex());

        javaParamExecuteMysqlStorageDao.saveAndValidate(javaMethodParam);

        return javaMethodParam;
    }
}
