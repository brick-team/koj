///*
// *    Copyright [2022] [brick-team]
// *
// *    Licensed under the Apache License, Version 2.0 (the "License");
// *    you may not use this file except in compliance with the License.
// *    You may obtain a copy of the License at
// *
// *        http://www.apache.org/licenses/LICENSE-2.0
// *
// *    Unless required by applicable law or agreed to in writing, software
// *    distributed under the License is distributed on an "AS IS" BASIS,
// *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *    See the License for the specific language governing permissions and
// *    limitations under the License.
// */
//
//package com.github.brick.action.flow.storage.mysql;
//
//import com.github.brick.action.flow.storage.mysql.impl.CommonTest;
//import com.google.gson.Gson;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ExecuteForMysqlTest extends CommonTest {
//    ExecuteForMysql executeForMysql;
//
//    @Before
//    public void init() {
//        executeForMysql = context.getBean(ExecuteForMysql.class);
//
//    }
//
//    Gson gson = new Gson();
//    @Test
//    public void execute() throws Exception {
//
//        Map<String, Map> data = new HashMap<>();
//        Map<String, String> map = new HashMap<>();
//        map.put("username", "zhangsan");
//        map.put("password", "123");
//        data.put("a", map);
//
//
//        executeForMysql.execute(3L, gson.toJson(data));
//
//    }
//}