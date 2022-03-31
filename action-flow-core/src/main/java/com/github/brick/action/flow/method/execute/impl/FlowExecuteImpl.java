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

package com.github.brick.action.flow.method.execute.impl;

import com.github.brick.action.flow.execute.http.HttpWorker;
import com.github.brick.action.flow.method.entity.*;
import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.github.brick.action.flow.method.entity.api.ApisEntity;
import com.github.brick.action.flow.method.entity.api.ParamIn;
import com.github.brick.action.flow.method.enums.FLowModel;
import com.github.brick.action.flow.method.enums.HttpClientType;
import com.github.brick.action.flow.method.execute.FlowExecute;
import com.github.brick.action.flow.method.extract.Extract;
import com.github.brick.action.flow.method.extract.ExtractImpl;
import com.github.brick.action.flow.method.factory.ActionFlowParseApiFactory;
import com.github.brick.action.flow.method.factory.Factory;
import com.github.brick.action.flow.method.factory.HttpWorkerFactory;
import com.github.brick.action.flow.method.format.Format;
import com.github.brick.action.flow.parse.api.ActionFlowMethodParseApi;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class FlowExecuteImpl implements FlowExecute {
    private static final Logger logger = LoggerFactory.getLogger(FlowExecuteImpl.class);
    ActionFlowMethodParseApi actionFlowMethodParseApi;

    SpelExpressionParser parser = new SpelExpressionParser();
    Extract extract = new ExtractImpl();
    /**
     * ActionFlowParseApi factory
     */
    Factory<FLowModel, ActionFlowMethodParseApi> actionFlowParseApiFactory =
            new ActionFlowParseApiFactory<>();


    Factory<HttpClientType, HttpWorker> httpWorkerFactory =
            new HttpWorkerFactory();
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    HttpWorker httpWorker = null;
    Gson gson = new Gson();


    @Override
    public Object execute(String file, String flowId, FLowModel module) throws Exception {
        // FIXME: 2022/3/28 第二个参数隐藏
        init(module, HttpClientType.OKHTTP);

        AllEntity parse = null;
        try {
            parse = actionFlowMethodParseApi.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Map<String, Object> result = getStringObjectMap(flowId, parse);
        fixedThreadPool.shutdown();
        return result;
    }

    public void init(FLowModel module, HttpClientType type) {
        this.actionFlowMethodParseApi = actionFlowParseApiFactory.gen(module);
        this.httpWorker = httpWorkerFactory.gen(type);
    }

    /**
     * 核心方法 用于执行具体流程
     */
    private Map<String, Object> getStringObjectMap(String flowId, AllEntity parse)
            throws Exception {
        ParamsEntity paramsEntity = parse.getParams();
        FlowsEntity flows = parse.getFlows();
        ActionsEntity actionsEntity = parse.getActions();
        ExtractsEntity extractsEntity = parse.getExtracts();
        WatchersEntity watchersEntity = parse.getWatchers();

        ResultEntity resultEntity = parse.getResult();
        ApisEntity apisEntity = parse.getApisEntity();

        Map<String, FlowEntity> collect = flows.getFlowEntities().stream().collect(Collectors.toMap(
                FlowEntity::getId, x -> x));
        FlowEntity flowEntity = collect.get(flowId);
        List<WatcherEntity> list = watchersEntity.getList();
        Map<String, WatcherEntity> watcherMap = list.stream().collect(Collectors.toMap(
                WatcherEntity::getId, x -> x));

        Map<String, ActionEntity> actionTagMap =
                actionsEntity.getList().stream().collect(Collectors.toMap(
                        ActionEntity::getId, s -> s));

        Map<String, ExtractEntity> exMap =
                extractsEntity.getExtractEntities().stream().collect(Collectors.toMap(
                        ExtractEntity::getId, s -> s));


        Map<String, List<ParamEntity>> paramsMap =
                paramsEntity.getList().stream().collect(Collectors.groupingBy(
                        ParamEntity::getGroup));


        Map<String, ApiEntity> apiEntityMap = apisEntity.getList().stream().collect(Collectors.toMap(
                ApiEntity::getId, s -> s));


        // 执行
        List<WorkEntity> workEntities = flowEntity.getWorkEntities();

        // 用于存储执行器执行结果
        // key: 执行器id
        // value: 执行器处理结果


        Map<String, Object> result = getStringObjectMap(resultEntity, watcherMap, actionTagMap, exMap, paramsMap, apiEntityMap, workEntities);

        return result;
    }

    public Map<String, Object> getStringObjectMap(ResultEntity resultEntity, Map<String, WatcherEntity> watcherMap, Map<String, ActionEntity> actionTagMap, Map<String, ExtractEntity> exMap, Map<String, List<ParamEntity>> paramsMap, Map<String, ApiEntity> apiEntityMap, List<WorkEntity> workEntities) throws Exception {
        Map<String, Object> actionResult = new HashMap<>();
        for (WorkEntity workEntity : workEntities) {
            run(workEntity, watcherMap, exMap, actionTagMap, paramsMap, actionResult, apiEntityMap);
        }

        logger.info("action/api execute result = {}", actionResult);

        Map<String, Object> result = handlerResult(resultEntity, exMap, actionResult);
        return result;
    }

    /**
     * 组装响应结果
     *
     * @param resultEntity 相应结果组装依据
     * @param exMap        提取器集合
     * @param actionResult action执行结果集合
     * @return
     */
    private Map<String, Object> handlerResult(ResultEntity resultEntity,
                                              Map<String, ExtractEntity> exMap,
                                              Map<String, Object> actionResult) throws ExecutionException, InterruptedException {
        // 组装结果信息
        Map<String, Object> result = new HashMap<>();
        List<ResultEntity.Key> keys = resultEntity.getKeys();
        for (ResultEntity.Key key : keys) {
            String exId = key.getExId();
            String name = key.getName();
            ExtractEntity extractEntity = exMap.get(exId);
            String el = extractEntity.getEl();
            String fromAction = extractEntity.getFromAction();
            Object rs = actionResult.get(fromAction);
            Object extract;
            // 异常组装异常信息
            if (rs instanceof Throwable) {
                extract = ((Throwable) rs).getMessage();
            }
            else if (rs instanceof Future) {
                rs = ((Future<?>) rs).get();
                extract = this.extract.extract(rs, el);
            }
            // 正常情况下走提取策略
            else {
                extract = this.extract.extract(rs, el);
            }
            result.put(name, extract);
        }
        return result;
    }

    private Object runAction(Map<String, ActionEntity> actionTagMap, Map<String, List<ParamEntity>> paramsMap, ActionEntity actionEntity, Map<String, ExtractEntity> exMap, Map<String, Object> actionResult) throws Exception {

        Class<?> clazz = actionEntity.getClazz();
        Method method = actionEntity.getMethod();
        Map<String, Object> methodArg = actionEntity.getMethodArg();
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        if (methodArg != null) {
            List<ActionEntity.Param> params = actionEntity.getParams();
            params.sort(Comparator.comparing(ActionEntity.Param::getIndex));

            // fixme: 值提取抽象，多线程使用
            for (int i = 0; i < params.size(); i++) {

                ActionEntity.Param param = params.get(i);
                // 支持提取器转换为参数
                if (param.getExId() != null) {
                    if (exMap != null) {
                        ExtractEntity extractEntity = exMap.get(param.getExId());
                        String el = extractEntity.getEl();
                        String fromAction = extractEntity.getFromAction();
                        Object rs = actionResult.get(fromAction);
                        Object extract = null;


                        if (rs instanceof Throwable) {
                            extract = ((Throwable) rs).getMessage();
                        }
                        else if (rs instanceof Future) {
                            rs = ((Future<?>) rs).get();
                            extract = this.extract.extract(rs, el);
                        }
                        else if (rs == null) {
                            Object o = commonRunAction(actionTagMap,
                                    fromAction,
                                    paramsMap,
                                    exMap,
                                    actionResult);
                            if (o instanceof Future) {
                                extract = ((Future<?>) o).get();
                            }
                            else {
                                extract = o;
                            }

                        }
                        // 正常情况下走提取策略
                        else {
                            extract = this.extract.extract(rs, el);
                        }
                        args[i] = extract;
                    }


                }
                else {
                    String argName = param.getArgName();
                    Object o = methodArg.get(argName);
                    args[i] = o;
                }
            }
        }


        if (actionEntity.isAsync()) {
            Future<Object> submit = this.fixedThreadPool.submit(() -> {
                Object invoke = method.invoke(clazz.newInstance(), args);
                return invoke;
            });
//            return submit.get();
            return submit;
        }
        else {
            return method.invoke(clazz.newInstance(), args);
        }

    }

    /**
     * 填充ActionEntity缺少的数据
     */
    private void fillActionTag(Map<String, List<ParamEntity>> paramsMap, ActionEntity actionEntity)
            throws Exception {
        // 解析param
        List<ActionEntity.Param> params = actionEntity.getParams();

        if (params != null) {

            Map<String, Object> methodArgs = new HashMap<>();
            for (ActionEntity.Param param : params) {
                String argName = param.getArgName();
                String paramGroup = param.getParamGroup();
                FormatEntity formatEntity = param.getFormatEntity();
                String valueValue = null;
                if (paramGroup != null) {
                    String ex = param.getEx();
                    List<ParamEntity> paramEntities = paramsMap.get(paramGroup);
                    Map<String, ParamEntity> collect =
                            paramEntities.stream().collect(Collectors.toMap(
                                    ParamEntity::getKey, s -> s));
                    ParamEntity value = collect.get(ex);
                    if (value != null) {

                        valueValue = value.getValue();
                        methodArgs.put(argName, valueValue);
                    }
                }
                else {
                    valueValue = param.getValue();
                    methodArgs.put(argName, valueValue);
                }
                Class<?> typeClass = param.getTypeClass();


                if (formatEntity != null) {
                    // FIXME: 2022/3/15 编写Format搜索器
                    String classStr = formatEntity.getClassStr();
                    Class<?> formatClass = Class.forName(classStr);
                    if (Format.class.isAssignableFrom(formatClass)) {
                        Object o = formatClass.newInstance();
                        Format format = (Format) o;
                        Object format1 = format.format(valueValue, typeClass);
                        methodArgs.put(argName, format1);

                    }

                }


            }

            actionEntity.setMethodArg(methodArgs);
        }
        else {

        }
    }

    private void run(WorkEntity workEntity,
                     Map<String, WatcherEntity> watcherMap,
                     Map<String, ExtractEntity> exMap,
                     Map<String, ActionEntity> actionTagMap,
                     Map<String, List<ParamEntity>> paramsMap,
                     Map<String, Object> actionResult, Map<String, ApiEntity> apisEntity) throws Exception {
        // 获取类型做出不同操作
        String type = workEntity.getType();
        // 嵌套执行存在问题
        // 数据监控类型
        if ("watcher".equalsIgnoreCase(type)) {
            wrapperRunWatcher(workEntity, watcherMap, exMap, actionTagMap, paramsMap, actionResult, apisEntity);
        }
        // 执行器类型
        else if ("action".equalsIgnoreCase(type)) {
            runWithAction(workEntity, watcherMap, exMap, actionTagMap, paramsMap, actionResult, apisEntity);
        }
        else if ("api".equalsIgnoreCase(type)) {
            runWithApi(workEntity, watcherMap, exMap, apisEntity, paramsMap, actionResult);
        }


    }

    /**
     * 包装的watcher执行器
     */
    private void wrapperRunWatcher(WorkEntity workEntity,
                                   Map<String, WatcherEntity> watcherMap,
                                   Map<String, ExtractEntity> exMap,
                                   Map<String, ActionEntity> actionTagMap,
                                   Map<String, List<ParamEntity>> paramsMap,
                                   Map<String, Object> actionResult,
                                   Map<String, ApiEntity> apisEntity
    ) throws Exception {
        try {
            runWithWatcher(workEntity, watcherMap, exMap, actionTagMap, paramsMap, actionResult);
            List<WorkEntity> then = workEntity.getThen();
            // 监控执行器正常的情况下执行
            for (WorkEntity tag : then) {
                run(tag, watcherMap, exMap, actionTagMap, paramsMap, actionResult, apisEntity);
            }
        } catch (Exception e) {
            logger.error("e", e);
            // 监控执行器执行异常的情况下执行
            List<WorkEntity> catchs = workEntity.getCatchs();
            for (WorkEntity aCatch : catchs) {
                run(aCatch, watcherMap, exMap, actionTagMap, paramsMap, actionResult, apisEntity);
            }
        }
    }

    /**
     * 基础action执行函数
     */
    private void runWithAction(WorkEntity workEntity,
                               Map<String, WatcherEntity> watcherMap,
                               Map<String, ExtractEntity> exMap,
                               Map<String, ActionEntity> actionTagMap,
                               Map<String, List<ParamEntity>> paramsMap,
                               Map<String, Object> actionResult,
                               Map<String, ApiEntity> apisEntity
    ) throws Exception {
        ActionEntity actionEntity = actionTagMap.get(workEntity.getRefId());
        // 补充反射信息
        fillActionTag(paramsMap, actionEntity);
        // 执行函数
        try {
            Object res = runAction(actionTagMap, paramsMap, actionEntity, exMap, actionResult);
            actionResult.put(actionEntity.getId(), res);
            List<WorkEntity> then = workEntity.getThen();

            for (WorkEntity tag : then) {
                run(tag, watcherMap, exMap, actionTagMap, paramsMap, actionResult, apisEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            actionResult.put(actionEntity.getId(), e);
            List<WorkEntity> catchs = workEntity.getCatchs();
            for (WorkEntity tag : catchs) {
                run(tag, watcherMap, exMap, actionTagMap, paramsMap, actionResult, apisEntity);
            }
        }
    }

    // 执行 类型为 api的调用器
    private String runWithApi(WorkEntity workEntity,
                              Map<String, WatcherEntity> watcherMap,
                              Map<String, ExtractEntity> exMap,
                              Map<String, ApiEntity> apisEntity,
                              Map<String, List<ParamEntity>> paramsMap,
                              Map<String, Object> actionResult
    ) throws IOException {
        String s = runApi(workEntity, exMap, apisEntity, actionResult, paramsMap);

        return s;
    }

    private String runApi(WorkEntity workEntity, Map<String, ExtractEntity> exMap, Map<String, ApiEntity> apisEntity, Map<String, Object> actionResult, Map<String, List<ParamEntity>> paramsMap) throws IOException {
        String refId = workEntity.getRefId();
        ApiEntity apiEntity = apisEntity.get(refId);

        return handlerOneApiEntity(exMap, apisEntity, actionResult, refId, apiEntity, paramsMap);

    }

    private String handlerOneApiEntity(Map<String, ExtractEntity> exMap, Map<String, ApiEntity> apisEntity, Map<String, Object> actionResult, String refId, ApiEntity apiEntity, Map<String, List<ParamEntity>> paramsMap) throws IOException {
        String url = apiEntity.getUrl();
        String method = apiEntity.getMethod();
        List<ApiParamEntity> params = apiEntity.getParams();


        Map<String, String> queryParam = new HashMap<>(8);
        Map<String, String> headers = new HashMap<>(8);
        Map<String, String> formatData = new HashMap<>(8);
        Map<String, String> body = new HashMap<>(8);
        Map<String, String> path = new HashMap<>(8);

        for (ApiParamEntity param : params) {
            String paramGroup = param.getParamGroup();
            String ex = param.getEx();
            String name = param.getName();
            String exId = param.getExId();
            ParamIn in = param.getIn();
            if (in == ParamIn.body) {
                handlerExId(exMap, apisEntity, actionResult, body, name, exId, paramsMap);
                handlerEx(paramsMap, body, paramGroup, ex, name);
            }
            else if (in == ParamIn.path) {
                handlerExId(exMap, apisEntity, actionResult, path, name, exId, paramsMap);
                handlerEx(paramsMap, path, paramGroup, ex, name);
            }
            else if (in == ParamIn.formData) {
                handlerExId(exMap, apisEntity, actionResult, formatData, name, exId, paramsMap);
                handlerEx(paramsMap, formatData, paramGroup, ex, name);
            }
            else if (in == ParamIn.query) {
                handlerExId(exMap, apisEntity, actionResult, queryParam, name, exId, paramsMap);
                handlerEx(paramsMap, queryParam, paramGroup, ex, name);
            }
            else if (in == ParamIn.header) {
                handlerExId(exMap, apisEntity, actionResult, headers, name, exId, paramsMap);
                handlerEx(paramsMap, headers, paramGroup, ex, name);
            }

        }

        String work = this.httpWorker.work(url, method, queryParam, headers, formatData, body);
        // 设置结果
        actionResult.put(refId, work);
        return work;
    }

    private void handlerEx(Map<String, List<ParamEntity>> paramsMap, Map<String, String> formatData, String paramGroup, String ex, String name) {
        if (ex != null && !"".equals(ex)) {
            List<ParamEntity> paramEntities = paramsMap.get(paramGroup);
            for (ParamEntity paramEntity : paramEntities) {
                String key = paramEntity.getKey();
                if (key.equals(ex)) {
                    String value = paramEntity.getValue();
                    formatData.put(name, value);
                }
            }
        }
    }


    private void handlerExId(Map<String, ExtractEntity> exMap, Map<String, ApiEntity> apisEntity, Map<String, Object> actionResult, Map<String, String> formatData, String name, String exId, Map<String, List<ParamEntity>> paramsMap) throws IOException {
        if (exId != null && !"".equals(exId)) {
            ExtractEntity extractEntity = exMap.get(exId);
            // todo: 增加 fromAction 处理
            String fromApi = extractEntity.getFromApi();

            String el = extractEntity.getEl();


            ApiEntity apiEntity1 = apisEntity.get(fromApi);
            String fromApiResult = handlerOneApiEntity(
                    exMap,
                    apisEntity,
                    actionResult,
                    fromApi, apiEntity1,
                    paramsMap);
            // 解析处理
            Object extract = this.extract.extract(fromApiResult, el);
            if (extract instanceof String) {
                formatData.put(name, (String) extract);
            }


        }
    }

    /**
     * 执行watcher相关节点
     */
    private void runWithWatcher(WorkEntity workEntity,
                                Map<String, WatcherEntity> watcherMap,
                                Map<String, ExtractEntity> exMap,
                                Map<String, ActionEntity> actionTagMap,
                                Map<String, List<ParamEntity>> paramsMap,
                                Map<String, Object> actionResult) throws Exception {
        // 从监控集合中找到对应的详细数据
        WatcherEntity watcherEntity = watcherMap.get(workEntity.getRefId());
        // 从action执行结果集合中获取数据

        String exId = watcherEntity.getExId();
        ExtractEntity extractEntity = exMap.get(exId);
        String el = extractEntity.getEl();
        // todo: 增加fromApi处理
        String fromAction = extractEntity.getFromAction();
        Object rs = actionResult.get(fromAction);
        // 提取结果如果是异常
        if (rs instanceof Throwable) {
            //            // todo: 结果如果是异常建议增加error节点
            //            List<WatcherEntity.Error> errors = watcherEntity.getErrors();
            //            for (WatcherEntity.Error error : errors) {
            //                String actionId = error.getActionId();
            //                commonRunAction(actionTagMap, actionId, paramsMap);
            //            }
            return;

        }
        // 提取结果不是异常
        else {
            Object extract = null;

            extract = this.extract.extract(rs, el);


            Expression expression = parser.parseExpression(extract + watcherEntity.getCondition());
            EvaluationContext context = new StandardEvaluationContext();
            Boolean value = expression.getValue(context, Boolean.class);
            if (value) {
                // 如果符合条件表达式执行then相关内容
                List<WatcherEntity.Then> thens = watcherEntity.getThens();
                for (WatcherEntity.Then then : thens) {
                    String actionId = then.getActionId();
                    commonRunAction(actionTagMap, actionId, paramsMap, exMap, actionResult);
                }
            }
            else {
                // 如果不符合条件表达式执行catch相关内容
                List<WatcherEntity.Catch> catchs = watcherEntity.getCatchs();
                for (WatcherEntity.Catch aCatch : catchs) {
                    String actionId = aCatch.getActionId();
                    commonRunAction(actionTagMap, actionId, paramsMap, exMap, actionResult);
                }
            }
        }

    }

    /**
     * 通用的action执行器
     *
     * @param actionTagMap action实体集合
     * @param actionId     actionId
     * @param paramsMap    参数集合
     * @param exMap
     * @param actionResult
     * @see FlowExecuteImpl#fillActionTag(java.util.Map, ActionEntity)}
     * @see FlowExecuteImpl#runAction(Map, Map, ActionEntity, Map, Map) }
     */
    private Object commonRunAction(Map<String, ActionEntity> actionTagMap,
                                   String actionId,
                                   Map<String, List<ParamEntity>> paramsMap, Map<String, ExtractEntity> exMap, Map<String, Object> actionResult) throws Exception {
        ActionEntity actionEntity = actionTagMap.get(actionId);
        fillActionTag(paramsMap, actionEntity);
        Object o = runAction(
                actionTagMap,
                paramsMap,
                actionEntity, exMap, actionResult);
        return o;
    }

}
