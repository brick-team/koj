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

package com.github.brick.action.flow.execute;

import com.github.brick.action.flow.execute.condition.ActionFlowCondition;
import com.github.brick.action.flow.execute.condition.ActionFlowConditionImpl;
import com.github.brick.action.flow.execute.extract.Extract;
import com.github.brick.action.flow.execute.extract.ExtractActionFlowFactory;
import com.github.brick.action.flow.execute.http.HttpWorker;
import com.github.brick.action.flow.execute.http.OkHttpWorkerImpl;
import com.github.brick.action.flow.execute.jdk.JDKExecuteService;
import com.github.brick.action.flow.execute.jdk.JDKExecuteServiceImpl;
import com.github.brick.action.flow.metrics.ActionMetricsImpl;
import com.github.brick.action.flow.model.ActionFlowFactory;
import com.github.brick.action.flow.model.enums.ActionType;
import com.github.brick.action.flow.model.enums.ExtractModel;
import com.github.brick.action.flow.model.enums.FieldType;
import com.github.brick.action.flow.model.enums.ParamIn;
import com.github.brick.action.flow.model.execute.*;
import com.github.brick.action.flow.storage.api.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.FlowExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.ResultExecuteEntityStorage;
import com.google.gson.Gson;
import lombok.Data;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * action flow execute.
 *
 * @author Zen Huifer
 */
public class ActionFlowExecute {
    private static final Logger logger = LoggerFactory.getLogger(ActionFlowExecute.class);
    private static final String[] ops =
            new String[] {">", ">=", "==", "<", "<=", "&&", "||",};
    private final ActionExecuteEntityStorage actionExecuteEntityStorage;
    private final FlowExecuteEntityStorage flowExecuteEntityStorage;
    private final ResultExecuteEntityStorage resultExecuteEntityStorage;

    private boolean objectSearchFromSpring = false;


    private ApplicationContext context;
    private String fileName;

    public ActionFlowExecute(ActionExecuteEntityStorage actionExecuteEntityStorage,
                             FlowExecuteEntityStorage flowExecuteEntityStorage,
                             ResultExecuteEntityStorage resultExecuteEntityStorage,
                             boolean objectSearchFromSpring) {
        this.actionExecuteEntityStorage = actionExecuteEntityStorage;
        this.flowExecuteEntityStorage = flowExecuteEntityStorage;
        this.resultExecuteEntityStorage = resultExecuteEntityStorage;
        this.objectSearchFromSpring = objectSearchFromSpring;
        this.extractFactory = new ExtractActionFlowFactory();
    }

    public ActionFlowExecute(String fileName,
                             ActionExecuteEntityStorage actionExecuteEntityStorage,
                             FlowExecuteEntityStorage flowExecuteEntityStorage,
                             ResultExecuteEntityStorage resultExecuteEntityStorage) {
        this.fileName = fileName;
        this.actionExecuteEntityStorage = actionExecuteEntityStorage;
        this.flowExecuteEntityStorage = flowExecuteEntityStorage;
        this.resultExecuteEntityStorage = resultExecuteEntityStorage;
        extractFactory = new ExtractActionFlowFactory();
    }

    public ApplicationContext getContext() {
        return context;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
    private final ActionFlowFactory<ExtractModel, Extract> extractFactory;
    Gson gson = new Gson();
    JDKExecuteService jdkExecuteService = new JDKExecuteServiceImpl();
    ActionMetricsImpl actionMetrics = new ActionMetricsImpl();

    public boolean isObjectSearchFromSpring() {
        return objectSearchFromSpring;
    }

    public void setObjectSearchFromSpring(boolean objectSearchFromSpring) {
        this.objectSearchFromSpring = objectSearchFromSpring;
    }





    public String execute(Serializable flowId, String jsonData) {
        Map<String, Object> res = getStringObjectMap(flowId, jsonData);
        return gson.toJson(res);
    }

    private Map<String, Object> getStringObjectMap(Serializable flowId, String jsonData) {
        FlowExecuteEntity flowExecuteEntity = flowExecuteEntityStorage.getFlow(fileName, flowId);

        ResultExecuteEntity resultExecuteEntity = resultExecuteEntityStorage.getResult(fileName, flowId);

        List<WorkExecuteEntity> works = flowExecuteEntity.getWorks();

        Map<String, Object> stepWorkResult = new HashMap<>(32);
        for (WorkExecuteEntity work : works) {
            executeWork(jsonData, stepWorkResult, work);
        }

        Map<String, Object> res = handlerResult(resultExecuteEntity, stepWorkResult);
        return res;
    }

    protected Map<String, Object> handlerResult(ResultExecuteEntity resultExecuteEntity, Map<String, Object> stepWorkResult) {
        List<FieldExecuteEntity> fields = resultExecuteEntity.getFields();

        Map<String, Object> res = new HashMap<>(32);

        for (FieldExecuteEntity field : fields) {
            res.putAll(handlerFieldExecuteEntity(stepWorkResult, field));

        }
        return res;
    }

    private Map<String, Object> handlerFieldExecuteEntity(Map<String, Object> stepWorkResult, FieldExecuteEntity field) {
        Map<String, Object> res = new HashMap<>();
        FieldType type = field.getType();

        String fieldName = field.getFieldName();
        ExtractExecuteEntity extract = field.getExtract();
        ExtractModel elType = extract.getElType();
        Extract factory = this.extractFactory.factory(elType);

        List<FieldExecuteEntity> properties = field.getProperties();
        Object objDataFrom = factory.extract(stepWorkResult.get(extract.getStep()), extract.getEl());
        if (type == FieldType.OBJECT) {
            res.put(fieldName, handlerResultObject(properties, objDataFrom));
        }
        else if (type == FieldType.ARRAY) {
            res.put(fieldName, handlerResultArray(properties, objDataFrom));
        }
        else if (type == FieldType.ARRAY_OBJECT) {
            res.put(fieldName, handlerResultArrayObject(properties, objDataFrom,stepWorkResult));

        }
        else {
            res.put(fieldName, objDataFrom);
        }
        return res;
    }

    private Object handlerResultArrayObject(List<FieldExecuteEntity> properties, Object objDataFrom, Map<String, Object> stepWorkResult) {
        List<Map> res = new ArrayList<>();
        // todo:  objDataFrom 不同数据类型
        if (objDataFrom instanceof LinkedHashMap) {

            Map<String, Object> data = new HashMap<>();

            for (FieldExecuteEntity field : properties) {
                String fieldName = field.getFieldName();
                ExtractExecuteEntity extract = field.getExtract();
                ExtractModel elType = extract.getElType();
                Extract factory = this.extractFactory.factory(elType);
                Object elValue = factory.extract(objDataFrom, extract.getEl());
                data.put(fieldName, elValue);
            }
            res.add(data);
        }
        if (objDataFrom instanceof JSONArray) {
            for (Object k : ((JSONArray) objDataFrom)) {
                Map<String, Object> data = new HashMap<>();

                for (FieldExecuteEntity field : properties) {
                    String fieldName = field.getFieldName();
                    ExtractExecuteEntity extract = field.getExtract();
                    ExtractModel elType = extract.getElType();
                    Extract factory = this.extractFactory.factory(elType);
                    Object elValue = factory.extract(k, extract.getEl());
                    data.put(fieldName, elValue);
                }
                res.add(data);
            }
        }


        return res;
    }

    private Object handlerResultArray(List<FieldExecuteEntity> properties, Object objDataFrom) {
        List<Object> res = new ArrayList<>();
        if (null == properties || properties.isEmpty()) {
            return objDataFrom;
        }

        for (FieldExecuteEntity property : properties) {
            ExtractExecuteEntity extract = property.getExtract();
            ExtractModel elType = extract.getElType();
            Extract factory = this.extractFactory.factory(elType);

            if (objDataFrom instanceof JSONArray) {
                for (Object o : ((JSONArray) objDataFrom)) {
                    Object extract1 = factory.extract(o, extract.getEl());
                    res.add(extract1);
                }
            }

        }
        return res;
    }

    private Object handlerResultObject(List<FieldExecuteEntity> properties, Object objDataFrom) {
        Map<String, Object> map = new HashMap<>(32);
        if (null == properties || properties.isEmpty()) {
            return objDataFrom;
        }
        for (FieldExecuteEntity property : properties) {
            ExtractExecuteEntity extract = property.getExtract();
            ExtractModel elType = extract.getElType();
            Extract factory = this.extractFactory.factory(elType);
            Object extract1 = factory.extract(objDataFrom, extract.getEl());
            map.put(property.getFieldName(), extract1);
        }
        return map;
    }

    ActionFlowCondition actionFlowCondition = new ActionFlowConditionImpl();

    private Object executeWork(String jsonData,
                               Map<String, Object> stepWorkResult,
                               WorkExecuteEntity work) {
        String refId = work.getRefId();
        // 执行action
        Object o = executeAction(fileName, jsonData, refId);
        // 放入步骤结果容器
        stepWorkResult.put(work.getStep(), o);
        // 处理watcher标签
        List<WatcherExecuteEntity> watchers = work.getWatchers();
        for (WatcherExecuteEntity watcher : watchers) {
            ExtractModel elType = watcher.getElType();
            String condition = watcher.getCondition();
            boolean aBoolean = actionFlowCondition.condition(condition, elType, o);
            if (aBoolean) {
                List<WorkExecuteEntity> then = watcher.getThen();
                for (WorkExecuteEntity workExecuteEntity : then) {
                    executeWork(jsonData, stepWorkResult, workExecuteEntity);
                }
            } else {
                List<WorkExecuteEntity> cat = watcher.getCat();
                for (WorkExecuteEntity workExecuteEntity : cat) {
                    executeWork(jsonData, stepWorkResult, workExecuteEntity);
                }
            }
        }
        return o;
    }

    private Object executeAction(String fileName, String jsonData, Serializable refId) {
        ActionExecuteEntity actionExecuteEntity = this.actionExecuteEntityStorage.getAction(fileName, refId);
        ActionType type = actionExecuteEntity.getType();
        Object o = null;
        if (type == ActionType.JAVA_METHOD) {
            try {
                o = handlerJavaMethod(actionExecuteEntity, jsonData);
            } catch (Exception e) {
                logger.error("执行action异常", e);
            }
        }
        else if (type == ActionType.REST_API) {
            try {

                o = handlerRestApi(actionExecuteEntity, jsonData);
            } catch (Exception e) {
                logger.error("执行action异常", e);
            }
        }
        return o;
    }

    private Object handlerRestApi(ActionExecuteEntity actionExecuteEntity, String jsonData) throws IOException {
        ActionExecuteEntity.ForRestApi restApi = actionExecuteEntity.getRestApi();

        String url = restApi.getUrl();
        String method = restApi.getMethod();


        List<ParamExecuteEntity> param = restApi.getParam();
        // todo: 支持HTTPCLIENT选择
        HttpWorker httpWorker = new OkHttpWorkerImpl();


        Map<String, String> queryParam = new HashMap<>(32);
        Map<String, String> pathParam = new HashMap<>(32);
        Map<String, String> headers = new HashMap<>(32);
        Map<String, String> formatData = new HashMap<>(32);
        Map<String, String> body = new HashMap<>(32);

        for (ParamExecuteEntity paramExecuteEntity : param) {
            ParamExecuteEntity.ForRestApi restApi1 = paramExecuteEntity.getRestApi();
            handlerRestApiParam(jsonData, queryParam, pathParam, headers, formatData, body, restApi1);

        }


        String work = httpWorker.work(url, method, pathParam, queryParam, headers, formatData, body);
        return work;
    }

    private void handlerRestApiParam(String jsonData, Map<String, String> queryParam, Map<String, String> pathParam, Map<String, String> headers, Map<String, String> formatData, Map<String, String> body, ParamExecuteEntity.ForRestApi restApi1) {
        ParamIn in = restApi1.getIn();
        ExtractExecuteEntity extract = restApi1.getExtract();
        String name = restApi1.getName();
        String value = restApi1.getValue();


        if (in == ParamIn.header) {
            handlerDataMap(value, extract, jsonData, headers, name);
        }
        else if (in == ParamIn.formdata) {
            handlerDataMap(value, extract, jsonData, formatData, name);
        }
        else if (in == ParamIn.body) {
            handlerDataMap(value, extract, jsonData, body, name);
        }
        else if (in == ParamIn.path) {
            handlerDataMap(value, extract, jsonData, pathParam, name);
        }
        else if (in == ParamIn.query) {
            handlerDataMap(value, extract, jsonData, queryParam, name);
        }

        // todo: 子集参数处理
        List<ParamExecuteEntity.ForRestApi> restApis = restApi1.getRestApis();

        if (restApis != null) {

            for (ParamExecuteEntity.ForRestApi api : restApis) {
                List<ParamExecuteEntity.ForRestApi> restApis1 = api.getRestApis();
                for (ParamExecuteEntity.ForRestApi forRestApi : restApis1) {
                    handlerRestApiParam(jsonData, queryParam, pathParam, headers, formatData, body, forRestApi);
                }
            }
        }
    }

    private void handlerDataMap(String value, ExtractExecuteEntity extract, String jsonData, Map<String, String> dataMap, String name) {
        if (strValueIsNull(value)) {
            Serializable fromAction = extract.getFromAction();
            String el = extract.getEl();
            Extract factory = extractFactory.factory(extract.getElType());
            Object o = executeAction(fileName, jsonData, fromAction);
            Object exData = factory.extract(o, el);
            dataMap.put(name, gson.toJson(exData));
        }
        else {
            dataMap.put(name, value);
        }
    }

    private boolean strValueIsNull(String value) {
        return "".equals(value) || value == null;
    }

    private Object handlerJavaMethod(ActionExecuteEntity actionExecuteEntity, String jsonData) throws Exception {

        ActionExecuteEntity.ForJavaMethod javaMethod = actionExecuteEntity.getJavaMethod();
        String className = javaMethod.getClassName();
        String method = javaMethod.getMethod();
        List<ParamExecuteEntity> param = javaMethod.getParam();


        List<IndexAndType> indexAndTypes = new ArrayList<>(8);
        Object[] args = new Object[param.size()];
        for (int i = 0; i < param.size(); i++) {
            ParamExecuteEntity paramExecuteEntity = param.get(i);
            ParamExecuteEntity.ForJavaMethod javaMethod1 = paramExecuteEntity.getJavaMethod();

            IndexAndType e = new IndexAndType();
            e.setIndex(javaMethod1.getIndex());
            e.setTyp(javaMethod1.getType());

            indexAndTypes.add(e);
            String value = javaMethod1.getValue();
            if (strValueIsNull(value)) {
                ExtractExecuteEntity extract = javaMethod1.getExtract();
                Serializable fromAction = extract.getFromAction();
                String el = extract.getEl();
                Extract factory = extractFactory.factory(extract.getElType());
                Object o = executeAction(fileName, jsonData, fromAction);
                Object exData = factory.extract(o, el);
                args[i] = exData;
            }
            else {
                args[i] = value;
            }


        }

        indexAndTypes.sort(Comparator.comparing(IndexAndType::getIndex));

        List<String> paramTypes = indexAndTypes.stream().map(IndexAndType::getTyp)
                .collect(Collectors.toList());

        if (!this.objectSearchFromSpring) {
            Object execute = jdkExecuteService.execute(className, method,
                    paramTypes.toArray(new String[] {}), args);
            return execute;
        } else {
            Object bean;
            String qualifier = javaMethod.getQualifier();
            if ("".equals(qualifier) || qualifier == null) {
                bean = this.context.getBean(Thread.currentThread().getContextClassLoader()
                        .loadClass(className));
            } else {
                bean = this.context.getBean(qualifier);
            }
            return jdkExecuteService.execute(bean, method,
                    paramTypes.toArray(new String[] {}), args);


        }
    }

    @Data
    private static class IndexAndType {
        private Integer index;
        private String typ;
    }

}
