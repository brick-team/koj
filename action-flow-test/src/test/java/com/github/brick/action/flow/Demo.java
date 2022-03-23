package com.github.brick.action.flow;

import com.alibaba.fastjson.JSON;
import com.github.brick.action.flow.method.entity.*;
import com.github.brick.action.flow.method.format.Format;
import com.github.brick.action.flow.method.format.num.StringToIntegerFormat;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class Demo {
    SpelExpressionParser parser = new SpelExpressionParser();

    @Test
    public void init() throws Exception {
        ParamsEntity paramsEntity = initParams();
        ActionsEntity actionsEntity = initActions();
        WatchersEntity watchersEntity = initWatchers();
        ResultEntity resultEntity = initResult();
        ExtractsEntity extractsEntity = initExtracts();
        FlowEntity flowEntity = flowTag();

        List<WatcherEntity> list = watchersEntity.getList();
        Map<String, WatcherEntity> watcherMap = list.stream().collect(Collectors.toMap(
                WatcherEntity::getId, x -> x
        ));

        Map<String, ActionEntity> actionTagMap =
                actionsEntity.getList().stream()
                        .collect(Collectors.toMap(ActionEntity::getId, s -> s));

        Map<String, ExtractEntity> exMap = extractsEntity.getExtractEntities().stream()
                .collect(Collectors.toMap(ExtractEntity::getId, s -> s));


        Map<String, List<ParamEntity>> paramsMap =
                paramsEntity.getList().stream()
                        .collect(Collectors.groupingBy(ParamEntity::getGroup));


        // 执行
        List<WorkEntity> workEntities = flowEntity.getWorkEntities();

        Map<String, Object> actionResult = new HashMap<>();


        for (WorkEntity workEntity : workEntities) {
            wt(workEntity,
                    watcherMap,
                    exMap,
                    actionTagMap,
                    paramsMap,
                    actionResult);
        }

        Map<String, Object> result = new HashMap<>();
        List<ResultEntity.Key> keys = resultEntity.getKeys();
        for (ResultEntity.Key key : keys) {
            String exId = key.getExId();
            String name = key.getName();
            ExtractEntity extractEntity = exMap.get(exId);

            String el = extractEntity.getEl();
            String fromAction = extractEntity.getFromAction();
            Object rs = actionResult.get(fromAction);

            String s = JSON.toJSONString(rs);
            Map map = JSON.parseObject(s, Map.class);
            Object o = map.get(el);


            result.put(name, o);
        }
        System.out.println(result);
    }

    private void wt(WorkEntity workEntity,
                    Map<String, WatcherEntity> watcherMap,
                    Map<String, ExtractEntity> exMap,
                    Map<String, ActionEntity> actionTagMap,
                    Map<String, List<ParamEntity>> paramsMap,
                    Map<String, Object> actionResult)
            throws Exception {
        String type = workEntity.getType();


        if (type.equalsIgnoreCase("watcher")) {
            WatcherEntity watcherEntity = watcherMap.get(workEntity.getRefId());

            String exId = watcherEntity.getExId();
            ExtractEntity extractEntity = exMap.get(exId);

            String el = extractEntity.getEl();
            String fromAction = extractEntity.getFromAction();
            Object rs = actionResult.get(fromAction);

            String s = JSON.toJSONString(rs);
            Map map = JSON.parseObject(s, Map.class);
            Object o = map.get(el);


            Expression expression = parser.parseExpression(o + watcherEntity.getCondition());
            EvaluationContext context = new StandardEvaluationContext();
            Boolean value = expression.getValue(context, Boolean.class);
            if (value) {
                List<WatcherEntity.Then> thens = watcherEntity.getThens();
                for (WatcherEntity.Then then : thens) {
                    String actionId = then.getActionId();
                    ActionEntity actionEntity = actionTagMap.get(actionId);
                    fillActionTag(paramsMap, actionEntity);
                    runAction(actionEntity);
                }
            }


        } else if (type.equalsIgnoreCase("action")) {
            ActionEntity actionEntity = actionTagMap.get(workEntity.getRefId());
            // 补充反射信息
            fillActionTag(paramsMap, actionEntity);
            // 执行函数
            Object res = runAction(actionEntity);
            actionResult.put(actionEntity.getId(), res);


            List<WorkEntity> then = workEntity.getThen();

            for (WorkEntity tag : then) {
                wt(tag,
                        watcherMap,
                        exMap,
                        actionTagMap,
                        paramsMap, actionResult);
            }

        }
    }

    private Object runAction(ActionEntity actionEntity) throws Exception {
        Class<?> clazz = actionEntity.getClazz();
        Method method = actionEntity.getMethod();
        Map<String, Object> methodArg = actionEntity.getMethodArg();
        Parameter[] parameters = method.getParameters();

        Object[] args = new Object[parameters.length];

        List<ActionEntity.Param> params = actionEntity.getParams();

        params.sort(Comparator.comparing(ActionEntity.Param::getIndex));

        for (int i = 0; i < params.size(); i++) {
            ActionEntity.Param param = params.get(i);
            String argName = param.getArgName();
            Object o = methodArg.get(argName);

            args[i] = o;
        }


        Object o = method.invoke(clazz.newInstance(), args);
        return o;
    }

    private void fillActionTag(Map<String, List<ParamEntity>> paramsMap, ActionEntity actionEntity)
            throws Exception {
        String clazzStr = actionEntity.getClazzStr();
        Class<?> aClass = Class.forName(clazzStr);
        actionEntity.setClazz(aClass);
        String methodStr = actionEntity.getMethodStr();
        Method[] methods = aClass.getMethods();
        Method method = null;
        for (Method m1 : methods) {
            if (m1.getName().equals(methodStr)) {
                method = m1;

            }
        }

        actionEntity.setMethod(method);

        // 解析param节点
        List<ActionEntity.Param> params = actionEntity.getParams();
        Map<String, Object> oo = new HashMap<>();
        for (ActionEntity.Param param : params) {
            String argName = param.getArgName();
            String paramGroup = param.getParamGroup();
            FormatEntity formatEntity = param.getFormatEntity();
            String valueValue = null;
            if (paramGroup != null) {

                String ex = param.getEx();
                List<ParamEntity> paramEntities = paramsMap.get(paramGroup);
                Map<String, ParamEntity> collect =
                        paramEntities.stream()
                                .collect(Collectors.toMap(s -> s.getKey(), s -> s));
                ParamEntity value = collect.get(ex);
                valueValue = value.getValue();

                oo.put(argName, valueValue);
            } else {
                valueValue = param.getValue();

                oo.put(argName, valueValue);
            }


            if (formatEntity != null) {
                String classStr = formatEntity.getClassStr();
                Class<?> aClass1 = Class.forName(classStr);
                if (Format.class.isAssignableFrom(aClass1)) {
                    Object o = aClass1.newInstance();
                    Format format = (Format) o;
                    Object format1 = format.format(valueValue, aClass1);
                    oo.put(argName, format1);

                }

            }


        }

        actionEntity.setMethodArg(oo);
    }

    public FlowEntity flowTag() {
        FlowEntity flowEntity = new FlowEntity();
        ArrayList<WorkEntity> workEntities = new ArrayList<>();
        WorkEntity e1 = new WorkEntity();
        e1.setType("action");
        e1.setRefId("login");
        e1.setId("work1");
        ArrayList<WorkEntity> workTags1 = new ArrayList<>();
        WorkEntity e = new WorkEntity();
        e.setType("watcher");
        e.setRefId("w1");
        e.setId("work2");

        workTags1.add(e);
        e1.setThen(workTags1);

        workEntities.add(e1);
        flowEntity.setWorkEntities(workEntities);

        return flowEntity;
    }

    private ExtractsEntity initExtracts() {
        ExtractsEntity extractsEntity = new ExtractsEntity();
        ArrayList<ExtractEntity> extractEntities = new ArrayList<>();
        ExtractEntity e1 = new ExtractEntity();
        e1.setId("e1");
        e1.setFromAction("login");
        e1.setEl("username");
        extractEntities.add(e1);

        ExtractEntity e2 = new ExtractEntity();
        e2.setId("e2");
        e2.setFromAction("login");
        e2.setEl("login_time");
        extractEntities.add(e2);

        ExtractEntity e3 = new ExtractEntity();
        e3.setId("e3");
        e3.setFromAction("login");
        e3.setEl("age");
        extractEntities.add(e3);


        extractsEntity.setExtractEntities(extractEntities);


        return extractsEntity;
    }

    private ResultEntity initResult() {
        ResultEntity resultEntity = new ResultEntity();
        ArrayList<ResultEntity.Key> keys = new ArrayList<>();
        ResultEntity.Key r1 = new ResultEntity.Key();
        r1.setName("r1");
        r1.setExId("e1");

        keys.add(r1);
        ResultEntity.Key r2 = new ResultEntity.Key();
        r2.setName("r2");
        r2.setExId("e2");

        keys.add(r2);
        resultEntity.setKeys(keys);


        return resultEntity;
    }

    private WatchersEntity initWatchers() {
        WatchersEntity watchersEntity = new WatchersEntity();
        ArrayList<WatcherEntity> list = new ArrayList<>();

        WatcherEntity w1 = new WatcherEntity();
        w1.setId("w1");
        w1.setExId("e3");
        w1.setCondition("> 10");


        ArrayList<WatcherEntity.Then> thens = new ArrayList<>();
        WatcherEntity.Then e = new WatcherEntity.Then();
        e.setActionId("sendPoint");
        thens.add(e);
        w1.setThens(thens);
        list.add(w1);
        watchersEntity.setList(list);


        return watchersEntity;
    }

    private ActionsEntity initActions() {
        ActionsEntity actionsEntity = new ActionsEntity();
        ArrayList<ActionEntity> list1 = new ArrayList<>();


        ActionEntity login = new ActionEntity();
        login.setId("login");
        login.setClazzStr(LoginAction.class.getName());
        login.setMethodStr("login");
        ArrayList<ActionEntity.Param> params = new ArrayList<>();
        ActionEntity.Param e = new ActionEntity.Param();
        e.setArgName("username");
        e.setParamGroup("a");
        e.setEx("username");
        e.setIndex(0);

        params.add(e);
        ActionEntity.Param e1 = new ActionEntity.Param();
        e1.setArgName("password");
        e1.setParamGroup("a");
        e1.setEx("password");
        e1.setIndex(1);
        params.add(e1);

        login.setParams(params);
        list1.add(login);

        ActionEntity sendPoint = new ActionEntity();
        sendPoint.setId("sendPoint");
        sendPoint.setClazzStr(SendPointAction.class.getName());
        sendPoint.setMethodStr("sendPoint");
        ArrayList<ActionEntity.Param> params1 = new ArrayList<>();
        ActionEntity.Param e2 = new ActionEntity.Param();
        e2.setArgName("uid");
        e2.setParamGroup("a");
        e2.setEx("username");
        e2.setIndex(0);

        params1.add(e2);

        ActionEntity.Param e3 = new ActionEntity.Param();
        e3.setArgName("point");
        e3.setValue("10");
        e3.setIndex(1);


        FormatEntity formatEntity = new FormatEntity();
        formatEntity.setId("f1");
        formatEntity.setClassStr(StringToIntegerFormat.class.getName());
        e3.setFormatEntity(formatEntity);

        params1.add(e3);

        sendPoint.setParams(params1);
        actionsEntity.setList(list1);
        list1.add(sendPoint);
        return actionsEntity;
    }

    private ParamsEntity initParams() {
        ParamsEntity paramsEntity = new ParamsEntity();
        ArrayList<ParamEntity> list = new ArrayList<>();
        ParamEntity username = new ParamEntity();
        username.setGroup("a");
        username.setKey("username");
        username.setValue("username");
        list.add(username);

        ParamEntity password = new ParamEntity();
        password.setGroup("a");
        password.setKey("password");
        password.setValue("password");
        list.add(password);
        paramsEntity.setList(list);
        return paramsEntity;
    }
}
