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
import com.github.brick.action.flow.model.enums.ParamIn;
import com.github.brick.action.flow.model.execute.ActionExecuteEntity;
import com.github.brick.action.flow.model.execute.ParamExecuteEntity;
import com.github.brick.action.flow.model.xml.ActionXML;
import com.github.brick.action.flow.storage.api.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.mysql.mapper.ActionMapper;
import com.github.brick.action.flow.storage.mysql.mapper.JavaMethodParamMapper;
import com.github.brick.action.flow.storage.mysql.mapper.RestApiParamMapper;
import com.github.brick.action.flow.storage.mysql.util.MybatisUtil;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ActionExecuteEntityMySqlStorageTest {

    @Before public void before() throws Exception {

        String user = "sa";
        String password = "sa";
        String databasenameURL = "jdbc:h2:~/action-flow;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
        String dbDriver = "org.h2.Driver";
        /*String user = "root";
        String password = "admin123";
        String databasenameURL = "jdbc:mysql://localhost:3306/action-flow";
        String dbDriver = Driver.class.getName();

        MybatisUtil mybatisUtil = new MybatisUtil(user, password, databasenameURL, dbDriver,
                ActionMapper.class, JavaMethodParamMapper.class, RestApiParamMapper.class);*/

        MybatisUtil mybatisUtil = new MybatisUtil(user, password, databasenameURL, dbDriver,
                ActionMapper.class, JavaMethodParamMapper.class, RestApiParamMapper.class);
        mybatisUtil.work(session -> {
            Connection connection = session.getConnection();
            Statement statement = connection.createStatement();

            List<String> allLines = Files.readAllLines(Paths.get("src/test/resources/h2database.sql"), StandardCharsets.UTF_8);

            StringBuilder buffer = new StringBuilder();
            allLines.forEach(buffer::append);

            statement.execute(buffer.toString());
            connection.commit();
        });
    }


    @Test
    public void save() throws Exception{
        ActionExecuteEntityStorage storage = new ActionExecuteEntityMySqlStorage();
        List<ActionExecuteEntity> actions = new ArrayList<>();

        ActionExecuteEntity entity = new ActionXML();
        entity.setType(ActionType.REST_API);

        ActionExecuteEntity.ForRestApi restApi = new ActionExecuteEntity.ForRestApi();
        restApi.setMethod("POST");
        restApi.setUrl("http://127.0.0.1/test");

        List<ParamExecuteEntity> a1ParamList = new ArrayList<>();
        ParamExecuteEntity a1ParamEntity = new ParamExecuteEntity();
        ParamExecuteEntity.ForRestApi restApi1 = new ParamExecuteEntity.ForRestApi();
        restApi1.setName("name1");
        restApi1.setIn(ParamIn.body);
        restApi1.setValue("rest1");
        restApi1.setRequire(true);

        a1ParamEntity.setRestApi(restApi1);
        a1ParamList.add(a1ParamEntity);

        restApi.setParam(a1ParamList);
        entity.setRestApi(restApi);

        actions.add(entity);


        ActionExecuteEntity entity1 = new ActionXML();

        entity1.setType(ActionType.JAVA_METHOD);
        ActionExecuteEntity.ForJavaMethod javaMethod = new ActionExecuteEntity.ForJavaMethod();
        javaMethod.setMethod("test");
        javaMethod.setClassName("com.github.xxx");

        List<ParamExecuteEntity> a2ParamList = new ArrayList<>();
        ParamExecuteEntity a2ParamEntity = new ParamExecuteEntity();
        ParamExecuteEntity.ForJavaMethod javaParam = new ParamExecuteEntity.ForJavaMethod();
        javaParam.setIndex(0);
        javaParam.setType("java.lang.String");
        javaParam.setName("test111");
        javaParam.setValue("aaa");

        a2ParamEntity.setJavaMethod(javaParam);
        a2ParamList.add(a2ParamEntity);

        ParamExecuteEntity.ForJavaMethod javaParam2 = new ParamExecuteEntity.ForJavaMethod();
        ParamExecuteEntity a2ParamEntity2 = new ParamExecuteEntity();
        javaParam2.setIndex(1);
        javaParam2.setType("java.lang.String");
        javaParam2.setName("test222");
        javaParam2.setValue("bbb");

        a2ParamEntity2.setJavaMethod(javaParam2);

        a2ParamList.add(a2ParamEntity2);
        javaMethod.setParam(a2ParamList);

        entity1.setJavaMethod(javaMethod);

        actions.add(entity1);

        storage.save("test", actions);

    }

    @Test public void dd() throws Exception {
        MybatisUtil.gen().work(session -> {
            ActionMapper mapper = session.getMapper(ActionMapper.class);
            ArrayList<Action> list = new ArrayList<>();
            Action e = new Action();
            e.setClassName("11");
            list.add(e);
            mapper.insert(e);
        });

    }
}