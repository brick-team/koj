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
//package com.github.brick.action.flow.storage.mysql.impl;
//
//import com.github.brick.action.flow.method.entity.api.ParamIn;
//import com.github.brick.action.flow.method.enums.ExtractModel;
//import com.github.brick.action.flow.method.req.WorkNode;
//import com.github.brick.action.flow.storage.api.*;
//import com.github.brick.action.flow.storage.mysql.ExecuteForMysql;
//import com.github.brick.action.flow.storage.mysql.entity.AfApiParamExEntity;
//import com.github.brick.action.flow.storage.mysql.repository.AfApiParamExEntityRepository;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//public class ApiDataStorage extends CommonTest {
//
//    ApiStorage apiStorage;
//    ApiParamStorage apiParamStorage;
//    ExtractStorage extractStorage;
//    FlowStorage flowStorage;
//    WorkStorage workStorage;
//    AfApiParamExEntityRepository apiParamExEntityRepository;
//    ExecuteForMysql executeForMysql;
//
//    @Before
//    public void init() {
//        apiStorage = context.getBean(ApiStorage.class);
//        apiParamStorage = context.getBean(ApiParamStorage.class);
//        extractStorage = context.getBean(ExtractStorage.class);
//        flowStorage = context.getBean(FlowStorage.class);
//        workStorage = context.getBean(WorkStorage.class);
//        apiParamExEntityRepository = context.getBean(AfApiParamExEntityRepository.class);
//        executeForMysql = context.getBean(ExecuteForMysql.class);
//    }
//
//    @Test
//    public void step1() {
//        Long loginId = apiStorage.save("http://localhost:8080/login", "post", "登陆");
//        apiParamStorage.save(loginId, null, ParamIn.formdata.name(), "username", true);
//        apiParamStorage.save(loginId, null, ParamIn.formdata.name(), "password", true);
//        Long userInfoId = apiStorage.save("http://localhost:8080/user_info", "get", "获取用户信息");
//        apiParamStorage.save(userInfoId, null, ParamIn.header.name(), "token", true);
//        extractStorage.save(null, loginId, "$.token", ExtractModel.JSON_PATH.name());
//
//
//        Long getUserInfoFlow = flowStorage.save("获取用户信息", new ArrayList<>());
//
//        WorkNode workNode = new WorkNode();
//        workNode.setType("api");
//        workNode.setRefId(userInfoId);
//        workStorage.saveWorkNode(workNode, getUserInfoFlow);
//
//    }
//
//
//    @Test
//    public void step2() {
//        AfApiParamExEntity entity1 = new AfApiParamExEntity();
//        entity1.setApiParamId(4L);
//        entity1.setParamGroup("a");
//        entity1.setEx("username");
//        entity1.setFlowId(9L);
//
//        apiParamExEntityRepository.save(entity1);
//
//        AfApiParamExEntity entity2 = new AfApiParamExEntity();
//        entity2.setApiParamId(5L);
//        entity2.setParamGroup("a");
//        entity2.setEx("password");
//        entity2.setFlowId(9L);
//        apiParamExEntityRepository.save(entity2);
//
//        AfApiParamExEntity entity3 = new AfApiParamExEntity();
//        entity3.setApiParamId(6L);
//        entity3.setExId(5L);
//        entity3.setFlowId(9L);
//        apiParamExEntityRepository.save(entity3);
//
//    }
//
//    @Test
//    public void step3() throws Exception {
//        executeForMysql.execute(9L, "");
//
//    }
//
//
//
//}
