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

import com.github.brick.action.flow.model.enums.FieldType;
import com.github.brick.action.flow.model.execute.FieldExecuteEntity;
import com.github.brick.action.flow.model.xml.ExtractXML;
import com.github.brick.action.flow.model.xml.FieldXML;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class FieldXMLParse extends CommonParseAndValidateImpl<FieldXML>
        implements ParseXML<FieldXML>, ValidateXMLParseData<FieldXML> {
    private static final String FIELD_NAME_NODE = "fieldName";
    private static final String TYPE_NODE = "type";
    private static final String EXTRACT_NODE = "extract";
    private static final String PROPERTIES_NODE = "properties";
    private static final String FIELDS_NODE = "fields";
    private static final String FIELD_NODE = "field";
    ExtractXMLParse extractXMLParse = new ExtractXMLParse();

    @Override
    public FieldXML parse(Element element) throws Exception {
        FieldXML fieldXML = new FieldXML();


        Element filedName = element.element(FIELD_NAME_NODE);
        if (filedName != null) {

            String filedNameStr = filedName.getTextTrim();
            fieldXML.setFieldName(filedNameStr);
        }

        Element type = element.element(TYPE_NODE);
        String typeStr = type.getTextTrim();
        fieldXML.setType(FieldType.valueOf(typeStr.toUpperCase()));
        Element extract = element.element(EXTRACT_NODE);
        ExtractXML parse = extractXMLParse.parse(extract);
        fieldXML.setExtract(parse);

        Element properties = element.element(PROPERTIES_NODE);
        if (properties != null) {

            Element fields = properties.element(FIELDS_NODE);
            List<Element> field = fields.elements(FIELD_NODE);
            List<FieldExecuteEntity> prop = new ArrayList<>();
            for (Element element1 : field) {
                Element fieldName = element1.element(FIELD_NAME_NODE);
                FieldXML parse1 = parse(element1);
                prop.add(parse1);
            }
            fieldXML.setProperties(prop);
        }


        return fieldXML;
    }

    /**
     * 验证flow结果集
     *
     * @param fieldXml 参数xml
     * @throws IllegalArgumentException 非法参数异常
     */
    @Override
    public void validate(FieldXML fieldXml) throws IllegalArgumentException {

        if (fieldXml.getFieldName() == null || "".equals(fieldXml.getFieldName())){
            throw new IllegalArgumentException("流程结果 fieldName 不能为空");
        }

        if (fieldXml.getType() == null){
            throw new IllegalArgumentException("流程结果 type 不能为空");
        }
    }
}
