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

import com.github.brick.action.flow.method.entity.ActionEntity;
import com.github.brick.action.flow.method.entity.ExtractEntity;
import com.github.brick.action.flow.method.entity.FormatEntity;
import com.github.brick.action.flow.method.entity.WatcherEntity;
import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.github.brick.action.flow.method.enums.FLowModel;
import com.github.brick.action.flow.method.enums.HttpClientType;
import com.github.brick.action.flow.method.enums.WorkNodeType;
import com.github.brick.action.flow.method.enums.WorkTypeModel;
import com.github.brick.action.flow.method.execute.impl.FlowExecuteImpl;
import com.github.brick.action.flow.method.req.WorkNode;
import com.github.brick.action.flow.storage.mysql.entity.AfActionParamExEntity;
import com.github.brick.action.flow.storage.mysql.entity.AfParamEntity;
import com.github.brick.action.flow.storage.mysql.entity.AfWorkCz;
import com.github.brick.action.flow.storage.mysql.repository.AfActionParamExEntityRepository;
import com.github.brick.action.flow.storage.mysql.repository.AfApiParamExEntityRepository;
import com.github.brick.action.flow.storage.mysql.repository.AfParamEntityRepository;
import com.github.brick.action.flow.storage.mysql.repository.AfWorkCzRepository;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class MysqlStorageFlow extends CommonTest {

    MysqlFlowStorage mysqlFlowStorage;
    MysqlWatcherStorage mysqlWatcherStorage;
    MysqlActionStorage actionStorage;
    MysqlActionParamStorage actionParamStorage;
    MysqlApiStorage apiStorage;
    AfActionParamExEntityRepository afActionParamExEntityRepository;
    AfApiParamExEntityRepository paramExEntityRepository;
    AfParamEntityRepository afParamEntityRepository;
    MysqlExtractStorage mysqlExtractStorage;
    FlowExecuteImpl flowExecute;
    MysqlWorkStorage mysqlWorkStorage;
    MysqlResultStorage mysqlResultStorage;
    Gson gson = new Gson();

    AfWorkCzRepository afWorkCzRepository;
    MysqlWorkStorage workStorage;
    MysqlFormatStorage mysqlFormatStorage;

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
        actionParamStorage = context.getBean(MysqlActionParamStorage.class);
        mysqlWorkStorage = context.getBean(MysqlWorkStorage.class);
        mysqlResultStorage = context.getBean(MysqlResultStorage.class);

        afWorkCzRepository = context.getBean(AfWorkCzRepository.class);
        workStorage = context.getBean(MysqlWorkStorage.class);
        afActionParamExEntityRepository = context.getBean(AfActionParamExEntityRepository.class);
        afParamEntityRepository = context.getBean(AfParamEntityRepository.class);
        mysqlFormatStorage = context.getBean(MysqlFormatStorage.class);
    }

    @Test
    public void step1() {
        ActionEntity login = new ActionEntity();
        login.setClazzStr("com.github.brick.action.flow.LoginAction");
        login.setMethodStr("login");
        Long loginId = actionStorage.save(login.getClazzStr(), login.getMethodStr(), login.isAsync());


        ActionEntity.Param loginParam1 = new ActionEntity.Param();
        loginParam1.setIndex(0);
        loginParam1.setArgName("username");
        loginParam1.setParamGroup("a");
        loginParam1.setEx("username");
        loginParam1.setType(String.class.getName());
        ActionEntity.Param loginParam2 = new ActionEntity.Param();
        loginParam2.setIndex(1);
        loginParam2.setArgName("password");
        loginParam2.setParamGroup("a");
        loginParam2.setEx("password");
        loginParam2.setType(String.class.getName());


        Long usernameParamId = actionParamStorage.save(loginId, loginParam1.getArgName(), loginParam1.getIndex(), loginParam1.getType());
        Long passwordParamId = actionParamStorage.save(loginId, loginParam2.getArgName(), loginParam2.getIndex(), loginParam2.getType());


        ActionEntity send = new ActionEntity();
        send.setClazzStr("com.github.brick.action.flow.SendPointAction");
        send.setMethodStr("sendPoint");
        Long sendId = actionStorage.save(send.getClazzStr(), send.getMethodStr(), send.isAsync());


        ActionEntity.Param sendParam1 = new ActionEntity.Param();
        sendParam1.setIndex(0);
        sendParam1.setArgName("uid");
        sendParam1.setParamGroup("a");
        sendParam1.setEx("username");
        sendParam1.setType(String.class.getName());
        ActionEntity.Param sendParam2 = new ActionEntity.Param();
        sendParam2.setIndex(1);
        sendParam2.setArgName("point");
        sendParam2.setValue("10");
        sendParam2.setType(Integer.class.getName());

        actionParamStorage.save(sendId, sendParam1.getArgName(), sendParam1.getIndex(), sendParam1.getType());
        actionParamStorage.save(sendId, sendParam2.getArgName(), sendParam2.getIndex(), sendParam2.getType());


        ExtractEntity e1 = new ExtractEntity();
        e1.setFromAction(loginId.toString());
        e1.setEl("$.username");
        Long e1Id = mysqlExtractStorage.save(Long.valueOf(e1.getFromAction()), null, e1.getEl(), "JSON_PATH");

        ExtractEntity e2 = new ExtractEntity();
        e2.setFromAction(loginId.toString());
        e2.setEl("$.login_time");
        Long e2Id = mysqlExtractStorage.save(Long.valueOf(e2.getFromAction()), null, e2.getEl(), "JSON_PATH");

        ExtractEntity e3 = new ExtractEntity();
        e3.setFromAction(loginId.toString());
        e3.setEl("$.age");
        Long e3Id = mysqlExtractStorage.save(Long.valueOf(e3.getFromAction()), null, e3.getEl(), "JSON_PATH");


        WatcherEntity watcherEntity = new WatcherEntity();
        watcherEntity.setExId(e3Id.toString());
        watcherEntity.setCondition(">10");
        ArrayList<WatcherEntity.Then> thens = new ArrayList<>();
        WatcherEntity.Then e = new WatcherEntity.Then();
        e.setActionId(String.valueOf(sendId));
        thens.add(e);
        thens.add(e);
        watcherEntity.setThens(thens);
        ArrayList<WatcherEntity.Catch> catchs = new ArrayList<>();
        WatcherEntity.Catch e4 = new WatcherEntity.Catch();
        e4.setActionId(String.valueOf(sendId));
        catchs.add(e4);
        watcherEntity.setCatchs(catchs);
        this.mysqlWatcherStorage.save(Long.valueOf(watcherEntity.getExId()), watcherEntity.getCondition(), Arrays.stream(new String[]{
                        String.valueOf(sendId), String.valueOf(sendId)
                }).collect(Collectors.toList()),
                Arrays.stream(new String[]{
                        String.valueOf(sendId)
                }).collect(Collectors.toList()), new ArrayList<>(), new ArrayList<>());

        mysqlFormatStorage.save("com.github.brick.action.flow.method.format.num.StringToIntegerFormat");

    }


    @Test
    public void step2() {

        String s = "{\n" +
                "  \"type\": \"action\",\n" +
                "  \"refId\": 1,\n" +
                "  \"cat\": [\n" +
                "    {\n" +
                "      \"type\": \"watcher\",\n" +
                "      \"refId\": 1\n" +
                "    }\n" +
                "  ],\n" +
                "  \"then\": [\n" +
                "    {\n" +
                "      \"type\": \"watcher\",\n" +
                "      \"refId\": 1,\n" +
                "      \"then\": [\n" +
                "        {\n" +
                "          \"type\": \"watcher\",\n" +
                "          \"refId\": 1,\n" +
                "          \"then\": [\n" +
                "            {\n" +
                "              \"type\": \"watcher\",\n" +
                "              \"refId\": 1,\n" +
                "              \"then\": [\n" +
                "                {\n" +
                "                  \"type\": \"watcher\",\n" +
                "                  \"refId\": 1\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        WorkNode workNode = gson.fromJson(s, WorkNode.class);


        Long action1 = this.mysqlFlowStorage.save("tttt", null);
        this.workStorage.saveWorkNode(workNode, action1);


        AfParamEntity entity1 = new AfParamEntity();
        entity1.setGroup("a");
        entity1.setKey("username");
        entity1.setValue("username");

        this.afParamEntityRepository.save(entity1);

        AfParamEntity entity2 = new AfParamEntity();
        entity2.setGroup("a");
        entity2.setKey("password");
        entity2.setValue("password");

        this.afParamEntityRepository.save(entity2);
    }

    @Test
    public void step3() {
        long flowId = 3L;
        AfActionParamExEntity afActionParamExEntity1 = new AfActionParamExEntity();
        afActionParamExEntity1.setActionParamId(1L);
        afActionParamExEntity1.setEx("username");
        afActionParamExEntity1.setParamGroupId("a");
        afActionParamExEntity1.setFlowId(flowId);
        afActionParamExEntityRepository.save(afActionParamExEntity1);

        AfActionParamExEntity afActionParamExEntity2 = new AfActionParamExEntity();
        afActionParamExEntity2.setActionParamId(2L);
        afActionParamExEntity2.setEx("password");
        afActionParamExEntity2.setParamGroupId("a");
        afActionParamExEntity2.setFlowId(flowId);
        afActionParamExEntityRepository.save(afActionParamExEntity2);


        AfActionParamExEntity afActionParamExEntity4 = new AfActionParamExEntity();
        afActionParamExEntity4.setActionParamId(3L);
        afActionParamExEntity4.setParamGroupId("a");
        afActionParamExEntity4.setEx("username");
        afActionParamExEntity4.setFlowId(flowId);
        afActionParamExEntityRepository.save(afActionParamExEntity4);

        AfActionParamExEntity afActionParamExEntity5 = new AfActionParamExEntity();
        afActionParamExEntity5.setActionParamId(4l);
        afActionParamExEntity5.setFormatId(1L);
        afActionParamExEntity5.setValue("10");
        afActionParamExEntity5.setFlowId(flowId);
        afActionParamExEntityRepository.save(afActionParamExEntity5);


    }

    @Test
    public void step4() {
        long flowId = 3L;
        List<WorkNode> byFlowId = workStorage.findByFlowId(flowId);
        List<AfParamEntity> allByFlowId = afParamEntityRepository.findAllByFlowId(flowId);


        for (WorkNode workNode : byFlowId) {
            // 搜索 action 、api 、watcher
            String type = workNode.getType();
            Long refId = workNode.getRefId();

            WorkTypeModel workTypeModel = WorkTypeModel.valueOf(type.toUpperCase());

            if (workTypeModel == WorkTypeModel.ACTION) {
                ActionEntity byId = this.actionStorage.findById(refId);

                for (ActionEntity.Param param : byId.getParams()) {
                    AfActionParamExEntity byFlowIdAndId = this.afActionParamExEntityRepository.findByFlowIdAndActionParamId(flowId, Long.valueOf(param.getId()));

                    // 静态值直接用于参数
                    String value = byFlowIdAndId.getValue();
                    param.setValue(value);

                    String paramGroupId = byFlowIdAndId.getParamGroupId();
                    param.setParamGroup(paramGroupId);

                    String ex = byFlowIdAndId.getEx();
                    param.setEx(ex);

                    Long exId = byFlowIdAndId.getExId();
                    if (exId != null) {

                        param.setExId(exId.toString());
                    }
                    Long formatId = byFlowIdAndId.getFormatId();
                    if (formatId != null) {

                        FormatEntity formatEntity = new FormatEntity();
                        formatEntity.setId(formatId.toString());
                        formatEntity.setClassStr(this.mysqlFormatStorage.findById(formatId));
                        param.setFormatEntity(formatEntity);
                    }
                }

                System.out.println();
            }
            else if (workTypeModel == WorkTypeModel.WATCHER) {
                WatcherEntity byId = this.mysqlWatcherStorage.findById(refId);
                System.out.println();

            }
            else if (workTypeModel == WorkTypeModel.API) {
                ApiEntity byId = this.apiStorage.findById(refId);

                System.out.println();

            }
        }
    }


    @Test
    public void testWorkRead() {
        List<AfWorkCz> list = afWorkCzRepository.findByFlowId(2L);
        System.out.println(gson.toJson(list));
//        String JSON = "[{\"id\":1,\"refId\":1,\"type\":\"action\",\"workType\":1,\"flowId\":1},{\"id\":2,\"refId\":11,\"type\":\"watcher\",\"pid\":1,\"workType\":3,\"flowId\":1},{\"id\":3,\"refId\":2,\"type\":\"watcher\",\"pid\":1,\"workType\":2,\"flowId\":1},{\"id\":4,\"refId\":21,\"type\":\"watcher\",\"pid\":3,\"workType\":2,\"flowId\":1},{\"id\":5,\"refId\":221,\"type\":\"watcher\",\"pid\":4,\"workType\":2,\"flowId\":1},{\"id\":6,\"refId\":2221,\"type\":\"watcher\",\"pid\":5,\"workType\":2,\"flowId\":1},{\"id\":7,\"refId\":222,\"type\":\"watcher\",\"pid\":4,\"workType\":2,\"flowId\":1},{\"id\":8,\"refId\":2221,\"type\":\"watcher\",\"pid\":7,\"workType\":2,\"flowId\":1},{\"id\":9,\"refId\":2222,\"type\":\"watcher\",\"pid\":7,\"workType\":2,\"flowId\":1},{\"id\":10,\"refId\":2232,\"type\":\"watcher\",\"pid\":7,\"workType\":3,\"flowId\":1},{\"id\":11,\"refId\":22321,\"type\":\"watcher\",\"pid\":10,\"workType\":2,\"flowId\":1},{\"id\":12,\"refId\":22322,\"type\":\"watcher\",\"pid\":10,\"workType\":3,\"flowId\":1}]\n";
//        List<AfWorkCz> list = gson.fromJson(JSON, new TypeToken<List<AfWorkCz>>() {
//        }.getType());

        List<WorkNode> res = new ArrayList<>();


        Map<Long, List<AfWorkCz>> pidMap = new HashMap<>();

        for (AfWorkCz afWorkCz : list) {
            Long pid = afWorkCz.getPid();
            if (pid != null) {
                List<AfWorkCz> afWorkCzs = pidMap.get(pid);
                if (afWorkCzs == null) {
                    afWorkCzs = new ArrayList<>();
                }
                afWorkCzs.add(afWorkCz);
                pidMap.put(pid, afWorkCzs);
            }
        }

        List<AfWorkCz> starts = list.stream().filter(s -> s.getWorkType().equals(WorkNodeType.START.getId())).collect(Collectors.toList());

        for (AfWorkCz start : starts) {
            WorkNode WorkNode = new WorkNode();
            WorkNode.setType(start.getType());
            WorkNode.setRefId(start.getRefId());
            WorkNode.setThen(workCzToThens(start, pidMap));
            WorkNode.setCat(workCzToCat(start, pidMap));

            res.add(WorkNode);
        }


        System.out.println(gson.toJson(res));

    }

    private List<WorkNode> workCzToCat(AfWorkCz start, Map<Long, List<AfWorkCz>> pidMap) {
        List<WorkNode> res = new ArrayList<>();
        List<AfWorkCz> afWorkCzs = pidMap.get(start.getId());
        if (afWorkCzs != null) {
            List<AfWorkCz> collect = afWorkCzs.stream().filter(s -> {
                return s.getWorkType().equals(WorkNodeType.CAT.getId());
            }).collect(Collectors.toList());

            for (AfWorkCz afWorkCz : collect) {
                WorkNode WorkNode = new WorkNode();
                WorkNode.setType(afWorkCz.getType());
                WorkNode.setRefId(afWorkCz.getRefId());
                WorkNode.setThen(workCzToThens(afWorkCz, pidMap));
                WorkNode.setCat(workCzToCat(afWorkCz, pidMap));
                res.add(WorkNode);
            }
        }

        return res;
    }

    private List<WorkNode> workCzToThens(AfWorkCz start, Map<Long, List<AfWorkCz>> pidMap) {
        List<AfWorkCz> afWorkCzs = pidMap.get(start.getId());
        List<WorkNode> res = new ArrayList<>();
        if (afWorkCzs != null) {
            List<AfWorkCz> collect = afWorkCzs.stream().filter(s -> {
                return s.getWorkType().equals(WorkNodeType.THEN.getId());
            }).collect(Collectors.toList());

            for (AfWorkCz afWorkCz : collect) {
                WorkNode WorkNode = new WorkNode();
                WorkNode.setType(afWorkCz.getType());
                WorkNode.setRefId(afWorkCz.getRefId());
                WorkNode.setThen(workCzToThens(afWorkCz, pidMap));
                WorkNode.setCat(workCzToCat(afWorkCz, pidMap));
                res.add(WorkNode);
            }
        }

        return res;
    }

    private void handlerCat(List<WorkNode> data, Long pid, long workFlowId) {
        for (WorkNode WorkNode : data) {
            AfWorkCz entity1 = new AfWorkCz();
            entity1.setWorkType(WorkNodeType.CAT.getId());
            entity1.setFlowId(workFlowId);

            entity1.setPid(pid);
            entity1.setType(WorkNode.getType());
            entity1.setRefId(WorkNode.getRefId());

            AfWorkCz save = afWorkCzRepository.save(entity1);
            List<WorkNode> then = WorkNode.getThen();
            handlerThe(then, save.getId(), workFlowId);
            List<WorkNode> cat = WorkNode.getCat();
            handlerCat(cat, save.getId(), workFlowId);

        }
    }

    private void handlerThe(List<WorkNode> data, Long pid, long workFlowId) {
        for (WorkNode WorkNode : data) {
            AfWorkCz entity1 = new AfWorkCz();
            entity1.setWorkType(WorkNodeType.THEN.getId());
            entity1.setFlowId(workFlowId);
            entity1.setPid(pid);
            entity1.setType(WorkNode.getType());
            entity1.setRefId(WorkNode.getRefId());

            AfWorkCz save = afWorkCzRepository.save(entity1);
            List<WorkNode> then = WorkNode.getThen();
            handlerThe(then, save.getId(), workFlowId);
            List<WorkNode> cat = WorkNode.getCat();

            handlerCat(cat, save.getId(), workFlowId);

        }


    }


}
