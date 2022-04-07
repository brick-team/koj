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

package com.github.brick.action.flow.parse.xml;

import com.github.brick.action.flow.method.enums.ActionType;
import com.github.brick.action.flow.model.xml.ActionXML;
import com.github.brick.action.flow.model.xml.ParamXML;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ActionXMLParse implements ParseXML<List<ActionXML>> {
    private static final String ACTIONS_NODE = "actions";
    private static final String ACTION_NODE = "action";
    private static final String ID_ATTR = "id";
    private static final String TYPE_ATTR = "type";
    private static final String METHOD_ATTR = "method";
    private static final String CLASS_NAME_ATTR = "className";
    private static final String PARAM_NODE = "param";
    private static final String URL_ATTR = "url";
    ParamXMLForRestApiParse paramXMLForRestApiParse = new ParamXMLForRestApiParse();
    ParamXMLForJavaMethodParse paramXMLForJavaMethodParse = new ParamXMLForJavaMethodParse();

    @Override
    public List<ActionXML> parse(Element element) throws Exception {
        Element actions = element.element(ACTIONS_NODE);

        List<Element> action = actions.elements(ACTION_NODE);
        List<ActionXML> result = new ArrayList<>();

        for (Element elm : action) {
            handlerAction(result, elm);
        }
        return result;
    }

    private void handlerAction(List<ActionXML> result, Element elm) throws Exception {
        ActionXML actionXML = new ActionXML();
        String id = elm.attributeValue(ID_ATTR);
        actionXML.setId(id);
        String type = elm.attributeValue(TYPE_ATTR);
        ActionType actionType = ActionType.valueOf(type.toUpperCase());
        actionXML.setType(actionType);

        if (actionType == ActionType.REST_API) {
            handlerRestApi(elm, actionXML);
        }
        else if (actionType == ActionType.JAVA_METHOD) {
            handlerJavaMethod(elm, actionXML);
        }
        result.add(actionXML);
    }

    private void handlerJavaMethod(Element elm, ActionXML actionXML) throws Exception {
        ActionXML.ForJavaMethod javaMethod = new ActionXML.ForJavaMethod();
        String method = elm.attributeValue(METHOD_ATTR);
        javaMethod.setMethod(method);

        String className = elm.attributeValue(CLASS_NAME_ATTR);
        javaMethod.setClassName(className);
        actionXML.setJavaMethod(javaMethod);
        ArrayList<ParamXML> methodParam = new ArrayList<>();
        List<Element> param = elm.elements(PARAM_NODE);

        for (Element paramElement : param) {
            ParamXML parse = paramXMLForJavaMethodParse.parse(paramElement);
            methodParam.add(parse);
        }
        javaMethod.setParam(methodParam);
    }

    private void handlerRestApi(Element elm, ActionXML actionXML) throws Exception {
        ActionXML.ForRestApi restApi = new ActionXML.ForRestApi();
        String method = elm.attributeValue(METHOD_ATTR);
        // TODO: 2022/4/7 get post delete put 校验
        restApi.setMethod(method);
        String url = elm.attributeValue(URL_ATTR);
        restApi.setUrl(url);
        ArrayList<ParamXML> apiParam = new ArrayList<>();

        List<Element> param = elm.elements(PARAM_NODE);

        for (Element paramElement : param) {
            ParamXML parse = paramXMLForRestApiParse.parse(paramElement);
            apiParam.add(parse);
        }
        restApi.setParam(apiParam);
        actionXML.setRestApi(restApi);
    }
}
