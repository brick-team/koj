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

import com.github.brick.action.flow.model.enums.ParamIn;
import com.github.brick.action.flow.model.execute.ParamExecuteEntity;
import com.github.brick.action.flow.model.xml.ExtractXML;
import com.github.brick.action.flow.model.xml.ParamXML;
import org.dom4j.Element;

public class ParamXMLForRestApiParse extends CommonParseAndValidateImpl<ParamXML>
        implements ParseXML<ParamXML>, ValidateXMLParseData<ParamXML> {
    private static final String IN_ATTR = "in";
    private static final String NAME_ATTR = "name";
    private static final String REQUIRE_ATTR = "require";
    private static final String VALUE_ATTR = "value";
    private static final String EXTRACT_NODE = "extract";
    private final ExtractXMLParse extractXMLParse = new ExtractXMLParse();

    @Override
    public ParamXML parse(Element element) throws Exception {
        String in = element.attributeValue(IN_ATTR);
        String name = element.attributeValue(NAME_ATTR);
        String require = element.attributeValue(REQUIRE_ATTR);
        String value = element.attributeValue(VALUE_ATTR);

        ParamXML paramXML = new ParamXML();

        ParamExecuteEntity.ForRestApi restApi = new ParamExecuteEntity.ForRestApi();
        // todo: 参数所在位置枚举转换修正
        restApi.setIn(ParamIn.valueOf(in.toLowerCase()));
        restApi.setName(name);
        restApi.setRequire(Boolean.parseBoolean(require));

        restApi.setValue(value);

        ExtractXML extract = extractXMLParse.parasAndValidate(element.element(EXTRACT_NODE));
        restApi.setExtract(extract);


        paramXML.setRestApi(restApi);


        return paramXML;
    }

    /**
     * 验证RestApi参数
     *
     * @param paramXml 参数xml
     * @throws IllegalArgumentException 非法参数异常
     */
    @Override
    public void validate(ParamXML paramXml) throws IllegalArgumentException {
        if (paramXml.getRestApi().getIn() == null){
            throw new IllegalArgumentException("restApi参数所在位置不能为空");
        }
    }
}
