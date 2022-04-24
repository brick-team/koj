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

import com.github.brick.action.flow.model.entity.Action;
import com.github.brick.action.flow.model.enums.ActionType;
import com.github.brick.action.flow.model.execute.ActionExecuteEntity;
import com.github.brick.action.flow.model.execute.ParamExecuteEntity;
import com.github.brick.action.flow.storage.api.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.mysql.dao.ActionExecuteMySqlStorageDao;
import com.github.brick.action.flow.storage.mysql.mapper.ActionMapper;
import com.github.brick.action.flow.storage.mysql.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActionExecuteEntityMySqlStorage implements ActionExecuteEntityStorage {

    private final ActionExecuteMySqlStorageDao actionExecuteMySqlStorageDao;

    /**
     * 构造的时候设置基础依赖
     */
    public ActionExecuteEntityMySqlStorage() {
        actionExecuteMySqlStorageDao = new ActionExecuteMySqlStorageDao();
    }

    @Override
    public void save(String fileName, List<ActionExecuteEntity> actions) {

        for (ActionExecuteEntity action : actions) {

            Action actionEntity = new Action();
            ActionType actionType = action.getType();
            actionEntity.setType(actionType.getCode());
            actionEntity.setFileName(fileName);

            List<ParamExecuteEntity> param = new ArrayList<>();

            if (actionType == ActionType.JAVA_METHOD) {
                actionEntity.setClassName(action.getJavaMethod().getClassName());
                actionEntity.setMethod(action.getJavaMethod().getMethod());

                param = action.getJavaMethod().getParam();

            } else if (actionType == ActionType.REST_API) {
                actionEntity.setUrl(action.getRestApi().getUrl());
                actionEntity.setMethod(action.getRestApi().getMethod());

                param = action.getRestApi().getParam();
            }

            actionExecuteMySqlStorageDao.save(actionEntity);

            System.out.println("[保存成功]  id = " + actionEntity.getId());

        }
    }

    @Override
    public ActionExecuteEntity getAction(String fileName, Serializable refId) {
        SqlSession sqlSession = MybatisUtil.getThreadLocalSqlSession();
        ActionMapper actionMapper = sqlSession.getMapper(ActionMapper.class);

        List<Action> actionList = actionMapper.queryById(refId);
        // TODO ActionExecuteEntity组装

        return null;

    }
}
