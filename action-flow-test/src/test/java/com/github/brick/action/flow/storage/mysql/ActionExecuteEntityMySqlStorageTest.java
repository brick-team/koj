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

import com.github.brick.action.flow.model.enums.ActionType;
import com.github.brick.action.flow.model.execute.ActionExecuteEntity;
import com.github.brick.action.flow.storage.api.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.mysql.mapper.ActionMapper;
import com.github.brick.action.flow.storage.mysql.util.MybatisUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActionExecuteEntityMySqlStorageTest {

    @Before
    public void before() {

        String user = "root";
        String password = "admin123";
        String databasenameURL = "jdbc:h2:~/action-flow";
        String dbDriver = "org.h2.Driver";


        MybatisUtil mybatisUtil = new MybatisUtil(user, password, databasenameURL, dbDriver,
                ActionMapper.class);
    }


    @Test
    public void save() throws Exception{
        MybatisUtil gen = MybatisUtil.gen();
        ActionExecuteEntityStorage storage = new ActionExecuteEntityMySqlStorage();
        List<ActionExecuteEntity> actions = new ArrayList<>();

        ActionExecuteEntity entity = new ActionExecuteEntity() {
            @Override
            public Serializable getId() {
                return 1;
            }
        };
        entity.setType(ActionType.REST_API);

        ActionExecuteEntity.ForRestApi restApi = new ActionExecuteEntity.ForRestApi();
        restApi.setMethod("POST");
        restApi.setUrl("http://127.0.0.1/test");
        entity.setRestApi(restApi);

        actions.add(entity);


        ActionExecuteEntity entity1 = new ActionExecuteEntity() {
            @Override
            public Serializable getId() {
                return 2;
            }
        };

        entity1.setType(ActionType.JAVA_METHOD);
        ActionExecuteEntity.ForJavaMethod javaMethod = new ActionExecuteEntity.ForJavaMethod();
        javaMethod.setMethod("test");
        javaMethod.setClassName("com.github.xxx");
        entity1.setJavaMethod(javaMethod);

        actions.add(entity1);

        gen.work(session -> {
            storage.save("test", actions);
        });

    }

    @Test
    public void getAction() throws Exception{
        MybatisUtil gen = MybatisUtil.gen();
        ActionExecuteEntityStorage storage = new ActionExecuteEntityMySqlStorage();

        gen.work(session -> {
            storage.getAction("test", 1);
        });
    }
}