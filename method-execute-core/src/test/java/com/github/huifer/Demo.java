package com.github.huifer;

import com.alibaba.fastjson.JSON;
import com.github.huifer.entity.*;
import com.github.huifer.entity.ActionTag.Param;
import com.github.huifer.entity.ResultTag.Key;
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
        ParamsTag paramsTag = initParams();
        ActionsTag actionsTag = initActions();
        WatchersTag watchersTag = initWatchers();
        ResultTag resultTag = initResult();
        ExtractsTag extractsTag = initExtracts();
        FlowTag flowTag = flowTag();

        List<WatcherTag> list = watchersTag.getList();
        Map<String, WatcherTag> watcherMap = list.stream().collect(Collectors.toMap(
                WatcherTag::getId, x -> x
        ));

        Map<String, ActionTag> actionTagMap =
                actionsTag.getList().stream().collect(Collectors.toMap(ActionTag::getId, s -> s));

        Map<String, ExtractTag> exMap = extractsTag.getExtractTags().stream()
                .collect(Collectors.toMap(ExtractTag::getId, s -> s));


        Map<String, List<ParamTag>> paramsMap =
                paramsTag.getList().stream().collect(Collectors.groupingBy(ParamTag::getGroup));



        // 执行
        List<WorkTag> workTags = flowTag.getWorkTags();

        Map<String, Object> actionResult = new HashMap<>();


        for (WorkTag workTag : workTags) {
            wt(workTag,
                    watcherMap,
                    exMap,
                    actionTagMap,
                    paramsMap,
                    actionResult);
        }

        Map<String, Object> result = new HashMap<>();
        List<Key> keys = resultTag.getKeys();
        for (Key key : keys) {
            String exId = key.getExId();
            String name = key.getName();
            ExtractTag extractTag = exMap.get(exId);

            String el = extractTag.getEl();
            String fromAction = extractTag.getFromAction();
            Object rs = actionResult.get(fromAction);

            String s = JSON.toJSONString(rs);
            Map map = JSON.parseObject(s, Map.class);
            Object o = map.get(el);


            result.put(name, o);
        }
        System.out.println(result);
    }

    private void wt(WorkTag workTag,
                    Map<String, WatcherTag> watcherMap,
                    Map<String, ExtractTag> exMap,
                    Map<String, ActionTag> actionTagMap,
                    Map<String, List<ParamTag>> paramsMap,
                    Map<String, Object> actionResult)
            throws Exception {
        String type = workTag.getType();


        if (type.equalsIgnoreCase("watcher")) {
            WatcherTag watcherTag = watcherMap.get(workTag.getRefId());

            String exId = watcherTag.getExId();
            ExtractTag extractTag = exMap.get(exId);

            String el = extractTag.getEl();
            String fromAction = extractTag.getFromAction();
            Object rs = actionResult.get(fromAction);

            String s = JSON.toJSONString(rs);
            Map map = JSON.parseObject(s, Map.class);
            Object o = map.get(el);


            Expression expression = parser.parseExpression(o + watcherTag.getCondition());
            EvaluationContext context = new StandardEvaluationContext();
            Boolean value = expression.getValue(context, Boolean.class);
            if (value) {
                List<WatcherTag.Then> thens = watcherTag.getThens();
                for (WatcherTag.Then then : thens) {
                    String actionId = then.getActionId();
                    ActionTag actionTag = actionTagMap.get(actionId);
                    fillActionTag(paramsMap, actionTag);
                    runAction(actionTag);
                }
            }


        } else if (type.equalsIgnoreCase("action")) {
            ActionTag actionTag = actionTagMap.get(workTag.getRefId());
            // 补充反射信息
            fillActionTag(paramsMap, actionTag);
            // 执行函数
            Object res = runAction(actionTag);
            actionResult.put(actionTag.getId(), res);


            List<WorkTag> then = workTag.getThen();

            for (WorkTag tag : then) {
                wt(tag,
                        watcherMap,
                        exMap,
                        actionTagMap,
                        paramsMap, actionResult);
            }

        }
    }

    private Object runAction(ActionTag actionTag) throws Exception {
        Class<?> clazz = actionTag.getClazz();
        Method method = actionTag.getMethod();
        Map<String, Object> methodArg = actionTag.getMethodArg();
        Parameter[] parameters = method.getParameters();

        Object[] args = new Object[parameters.length];

        List<Param> params = actionTag.getParams();

        params.sort(Comparator.comparing(Param::getIndex));

        for (int i = 0; i < params.size(); i++) {
            Param param = params.get(i);
            String argName = param.getArgName();
            Object o = methodArg.get(argName);

            args[i] = o;
        }



        Object o = method.invoke(clazz.newInstance(), args);
        return o;
    }

    private void fillActionTag(Map<String, List<ParamTag>> paramsMap, ActionTag actionTag)
            throws Exception {
        String clazzStr = actionTag.getClazzStr();
        Class<?> aClass = Class.forName(clazzStr);
        actionTag.setClazz(aClass);
        String methodStr = actionTag.getMethodStr();
        Method[] methods = aClass.getMethods();
        Method method = null;
        for (Method m1 : methods) {
            if (m1.getName().equals(methodStr)) {
                method = m1;

            }
        }

        actionTag.setMethod(method);

        // 解析param节点
        List<Param> params = actionTag.getParams();
        Map<String, Object> oo = new HashMap<>();
        for (Param param : params) {
            String argName = param.getArgName();
            String paramGroup = param.getParamGroup();
            FormatTag formatTag = param.getFormatTag();
            String valueValue = null;
            if (paramGroup != null) {

                String ex = param.getEx();
                List<ParamTag> paramTags = paramsMap.get(paramGroup);
                Map<String, ParamTag> collect =
                        paramTags.stream()
                                .collect(Collectors.toMap(s -> s.getKey(), s -> s));
                ParamTag value = collect.get(ex);
                valueValue = value.getValue();

                oo.put(argName, valueValue);
            } else {
                valueValue = param.getValue();

                oo.put(argName, valueValue);
            }


            if (formatTag != null) {
                String classStr = formatTag.getClassStr();
                Class<?> aClass1 = Class.forName(classStr);
                if (Format.class.isAssignableFrom(aClass1)) {
                    Object o = aClass1.newInstance();
                    Format format = (Format) o;
                    Object format1 = format.format(valueValue);
                    oo.put(argName, format1);

                }

            }


        }

        actionTag.setMethodArg(oo);
    }

    public FlowTag flowTag() {
        FlowTag flowTag = new FlowTag();
        ArrayList<WorkTag> workTags = new ArrayList<>();
        WorkTag e1 = new WorkTag();
        e1.setType("action");
        e1.setRefId("login");
        e1.setId("work1");
        ArrayList<WorkTag> workTags1 = new ArrayList<>();
        WorkTag e = new WorkTag();
        e.setType("watcher");
        e.setRefId("w1");
        e.setId("work2");

        workTags1.add(e);
        e1.setThen(workTags1);

        workTags.add(e1);
        flowTag.setWorkTags(workTags);

        return flowTag;
    }

    private ExtractsTag initExtracts() {
        ExtractsTag extractsTag = new ExtractsTag();
        ArrayList<ExtractTag> extractTags = new ArrayList<>();
        ExtractTag e1 = new ExtractTag();
        e1.setId("e1");
        e1.setFromAction("login");
        e1.setEl("username");
        extractTags.add(e1);

        ExtractTag e2 = new ExtractTag();
        e2.setId("e2");
        e2.setFromAction("login");
        e2.setEl("login_time");
        extractTags.add(e2);

        ExtractTag e3 = new ExtractTag();
        e3.setId("e3");
        e3.setFromAction("login");
        e3.setEl("age");
        extractTags.add(e3);


        extractsTag.setExtractTags(extractTags);


        return extractsTag;
    }

    private ResultTag initResult() {
        ResultTag resultTag = new ResultTag();
        ArrayList<Key> keys = new ArrayList<>();
        Key r1 = new Key();
        r1.setName("r1");
        r1.setExId("e1");

        keys.add(r1);
        Key r2 = new Key();
        r2.setName("r2");
        r2.setExId("e2");

        keys.add(r2);
        resultTag.setKeys(keys);


        return resultTag;
    }

    private WatchersTag initWatchers() {
        WatchersTag watchersTag = new WatchersTag();
        ArrayList<WatcherTag> list = new ArrayList<>();

        WatcherTag w1 = new WatcherTag();
        w1.setId("w1");
        w1.setExId("e3");
        w1.setCondition("> 10");


        ArrayList<WatcherTag.Then> thens = new ArrayList<>();
        WatcherTag.Then e = new WatcherTag.Then();
        e.setActionId("sendPoint");
        thens.add(e);
        w1.setThens(thens);
        list.add(w1);
        watchersTag.setList(list);


        return watchersTag;
    }

    private ActionsTag initActions() {
        ActionsTag actionsTag = new ActionsTag();
        ArrayList<ActionTag> list1 = new ArrayList<>();


        ActionTag login = new ActionTag();
        login.setId("login");
        login.setClazzStr("com.github.huifer.LoginAction");
        login.setMethodStr("login");
        ArrayList<Param> params = new ArrayList<>();
        Param e = new Param();
        e.setArgName("username");
        e.setParamGroup("a");
        e.setEx("username");
        e.setIndex(0);

        params.add(e);
        Param e1 = new Param();
        e1.setArgName("password");
        e1.setParamGroup("a");
        e1.setEx("password");
        e1.setIndex(1);
        params.add(e1);

        login.setParams(params);
        list1.add(login);

        ActionTag sendPoint = new ActionTag();
        sendPoint.setId("sendPoint");
        sendPoint.setClazzStr("com.github.huifer.SendPointAction");
        sendPoint.setMethodStr("sendPoint");
        ArrayList<Param> params1 = new ArrayList<>();
        Param e2 = new Param();
        e2.setArgName("uid");
        e2.setParamGroup("a");
        e2.setEx("username");
        e2.setIndex(0);

        params1.add(e2);

        Param e3 = new Param();
        e3.setArgName("point");
        e3.setValue("10");
        e3.setIndex(1);


        FormatTag formatTag = new FormatTag();
        formatTag.setId("f1");
        formatTag.setClassStr("com.github.huifer.StringToIntegerFormat");
        e3.setFormatTag(formatTag);

        params1.add(e3);

        sendPoint.setParams(params1);
        actionsTag.setList(list1);
        list1.add(sendPoint);
        return actionsTag;
    }

    private ParamsTag initParams() {
        ParamsTag paramsTag = new ParamsTag();
        ArrayList<ParamTag> list = new ArrayList<>();
        ParamTag username = new ParamTag();
        username.setGroup("a");
        username.setKey("username");
        username.setValue("username");
        list.add(username);

        ParamTag password = new ParamTag();
        password.setGroup("a");
        password.setKey("password");
        password.setValue("password");
        list.add(password);
        paramsTag.setList(list);
        return paramsTag;
    }
}
