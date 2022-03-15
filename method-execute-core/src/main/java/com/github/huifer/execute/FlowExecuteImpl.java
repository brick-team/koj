package com.github.huifer.execute;

import com.github.huifer.Format;
import com.github.huifer.entity.*;
import com.github.huifer.extract.Extract;
import com.github.huifer.extract.ExtractImpl;
import com.github.huifer.parse.DocParse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlowExecuteImpl implements FlowExecute {
    private static final Logger logger = LoggerFactory.getLogger(FlowExecuteImpl.class);
    DocParse docParse = new DocParse();
    SpelExpressionParser parser = new SpelExpressionParser();
    Extract extract = new ExtractImpl();

    @Override
    public Object execute(String file) throws Exception {
        DocTag parse = null;
        try {
            parse = docParse.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        ParamsTag paramsTag = parse.getParams();
        FlowTag flowTag = parse.getFlow();
        ActionsTag actionsTag = parse.getActions();
        ExtractsTag extractsTag = parse.getExtracts();
        WatchersTag watchersTag = parse.getWatchers();

        ResultTag resultTag = parse.getResult();

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

        // 用于存储执行器执行结果
        // key: 执行器id
        // value: 执行器处理结果
        Map<String, Object> actionResult = new HashMap<>();


        for (WorkTag workTag : workTags) {
            wt(workTag,
                    watcherMap,
                    exMap,
                    actionTagMap,
                    paramsMap,
                    actionResult);
        }


        // 组装结果信息
        Map<String, Object> result = new HashMap<>();
        List<ResultTag.Key> keys = resultTag.getKeys();
        for (ResultTag.Key key : keys) {
            String exId = key.getExId();
            String name = key.getName();
            ExtractTag extractTag = exMap.get(exId);
            String el = extractTag.getEl();
            String fromAction = extractTag.getFromAction();
            Object rs = actionResult.get(fromAction);
            Object extract = null;
            if (rs instanceof Throwable) {
                extract = rs;
            } else {
                 extract = this.extract.extract(rs, el);
            }
            result.put(name, extract);
        }
        return result;
    }

    private Object runAction(ActionTag actionTag) throws Exception {
        Class<?> clazz = actionTag.getClazz();
        Method method = actionTag.getMethod();
        Map<String, Object> methodArg = actionTag.getMethodArg();
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        List<ActionTag.Param> params = actionTag.getParams();
        params.sort(Comparator.comparing(ActionTag.Param::getIndex));

        // fixme: 值提取抽象
        for (int i = 0; i < params.size(); i++) {
            ActionTag.Param param = params.get(i);


            String argName = param.getArgName();
            Object o = methodArg.get(argName);
            args[i] = o;
        }
        return method.invoke(clazz.newInstance(), args);
    }

    private void fillActionTag(Map<String, List<ParamTag>> paramsMap, ActionTag actionTag)
            throws Exception {
        // 解析param
        List<ActionTag.Param> params = actionTag.getParams();
        //
        Map<String, Object> methodArgs = new HashMap<>();
        for (ActionTag.Param param : params) {
            String argName = param.getArgName();
            String paramGroup = param.getParamGroup();
            FormatTag formatTag = param.getFormatTag();
            String valueValue = null;
            if (paramGroup != null) {
                String ex = param.getEx();
                List<ParamTag> paramTags = paramsMap.get(paramGroup);
                Map<String, ParamTag> collect =
                        paramTags.stream()
                                .collect(Collectors.toMap(ParamTag::getKey, s -> s));
                ParamTag value = collect.get(ex);
                valueValue = value.getValue();
                methodArgs.put(argName, valueValue);
            } else {
                valueValue = param.getValue();
                methodArgs.put(argName, valueValue);
            }

            if (formatTag != null) {
                String classStr = formatTag.getClassStr();
                Class<?> aClass1 = Class.forName(classStr);
                if (Format.class.isAssignableFrom(aClass1)) {
                    Object o = aClass1.newInstance();
                    Format format = (Format) o;
                    Object format1 = format.format(valueValue);
                    methodArgs.put(argName, format1);

                }

            }


        }

        actionTag.setMethodArg(methodArgs);
    }

    private void wt(WorkTag workTag,
                    Map<String, WatcherTag> watcherMap,
                    Map<String, ExtractTag> exMap,
                    Map<String, ActionTag> actionTagMap,
                    Map<String, List<ParamTag>> paramsMap,
                    Map<String, Object> actionResult)
            throws Exception {
        // 获取类型做出不同操作
        String type = workTag.getType();

        // 数据监控类型
        if ("watcher".equalsIgnoreCase(type)) {
            runWithWatcher(workTag, watcherMap, exMap, actionTagMap, paramsMap, actionResult);
        }
        // 执行器类型
        else if ("action".equalsIgnoreCase(type)) {
            runWithAction(workTag, watcherMap, exMap, actionTagMap, paramsMap, actionResult);
        }


    }

    private void runWithAction(WorkTag workTag,
                               Map<String, WatcherTag> watcherMap,
                               Map<String, ExtractTag> exMap,
                               Map<String, ActionTag> actionTagMap,
                               Map<String, List<ParamTag>> paramsMap,
                               Map<String, Object> actionResult) throws Exception {
        ActionTag actionTag = actionTagMap.get(workTag.getRefId());
        // 补充反射信息
        fillActionTag(paramsMap, actionTag);
        // 执行函数
        try {

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
        } catch (Exception e) {
            actionResult.put(actionTag.getId(), e);

            List<WorkTag> catchs = workTag.getCatchs();

            for (WorkTag tag : catchs) {
                wt(tag,
                        watcherMap,
                        exMap,
                        actionTagMap,
                        paramsMap, actionResult);
            }
        }
    }

    private void runWithWatcher(WorkTag workTag,
                                Map<String, WatcherTag> watcherMap,
                                Map<String, ExtractTag> exMap,
                                Map<String, ActionTag> actionTagMap,
                                Map<String, List<ParamTag>> paramsMap,
                                Map<String, Object> actionResult) throws Exception {
        WatcherTag watcherTag = watcherMap.get(workTag.getRefId());

        String exId = watcherTag.getExId();
        ExtractTag extractTag = exMap.get(exId);

        String el = extractTag.getEl();
        String fromAction = extractTag.getFromAction();


        Object rs = actionResult.get(fromAction);
        Object extract = null;
        if (rs instanceof Throwable) {
            List<WatcherTag.Catch> catchs = watcherTag.getCatchs();
            for (WatcherTag.Catch aCatch : catchs) {
                String actionId = aCatch.getActionId();
                ActionTag actionTag = actionTagMap.get(actionId);
                fillActionTag(paramsMap, actionTag);
                runAction(actionTag);
            }
            return;

        }
        extract = this.extract.extract(rs, el);



        Expression expression = parser.parseExpression(extract + watcherTag.getCondition());
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
            List<WatcherTag.Catch> catchs = watcherTag.getCatchs();
            for (WatcherTag.Catch aCatch : catchs) {
                String actionId = aCatch.getActionId();
                ActionTag actionTag = actionTagMap.get(actionId);
                fillActionTag(paramsMap, actionTag);
                runAction(actionTag);
            }
        }
    }

}
