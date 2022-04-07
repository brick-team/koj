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

import com.github.brick.action.flow.model.execute.ParamExecuteEntity;
import com.github.brick.action.flow.model.xml.ExtractXML;
import com.github.brick.action.flow.model.xml.ParamXML;
import lombok.Data;
import org.dom4j.Element;

@Data
public class ParamXMLForJavaMethodParse implements ParseXML<ParamXML> {
    private static final String NAME_ATTR = "name";
    private static final String INDEX_ATTR = "index";
    private static final String TYPE_ATTR = "type";
    private static final String VALUE_ATTR = "value";
    private static final String EXTRACT_NODE = "extract";
    ExtractXMLParse extractXMLParse = new ExtractXMLParse();

    @Override
    public ParamXML parse(Element element) throws Exception {
        if (element == null) {
            return null;
        }
        ParamXML paramXML = new ParamXML();
        String name = element.attributeValue(NAME_ATTR);
        String value = element.attributeValue(VALUE_ATTR);


        ExtractXML extract = extractXMLParse.parse(element.element(EXTRACT_NODE));


        ParamExecuteEntity.ForJavaMethod javaMethod = new ParamExecuteEntity.ForJavaMethod();
        String index = element.attributeValue(INDEX_ATTR);
        String type = element.attributeValue(TYPE_ATTR);

        javaMethod.setIndex(Integer.parseInt(index));
        javaMethod.setType(type);
        javaMethod.setName(name);
        javaMethod.setValue(value);
        javaMethod.setExtract(extract);

        paramXML.setJavaMethod(javaMethod);


        return paramXML;
    }
}
