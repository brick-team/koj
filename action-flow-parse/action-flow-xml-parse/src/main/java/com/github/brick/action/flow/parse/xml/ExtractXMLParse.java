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

import com.github.brick.action.flow.model.enums.ExtractModel;
import com.github.brick.action.flow.model.xml.ExtractXML;
import org.dom4j.Element;

public class ExtractXMLParse extends CommonParseAndValidateImpl<ExtractXML> implements ValidateXMLParseData<ExtractXML> {
    private static final String EL_ATTR = "el";
    private static final String EL_TYPE_ATTR = "elType";
    private static final String STEP_ATTR = "step";
    private static final String FROM_ACTION_ATTR = "fromAction";

    @Override
    public ExtractXML parse(Element element) throws Exception {
        if (element == null) {
            return null;
        }
        ExtractXML extractXML = new ExtractXML();
        String el = element.attributeValue(EL_ATTR);
        extractXML.setEl(el);
        String elType = element.attributeValue(EL_TYPE_ATTR);
        if ("".equals(elType) || elType == null) {
            extractXML.setElType(ExtractModel.JSON_PATH);
        } else {
            extractXML.setElType(ExtractModel.valueOf(elType.toUpperCase()));
        }
        String step = element.attributeValue(STEP_ATTR);
        extractXML.setStep(step);
        String fromAction = element.attributeValue(FROM_ACTION_ATTR);
        extractXML.setFromAction(fromAction);

        return extractXML;
    }

    @Override
    public void validate(ExtractXML extractXml) throws IllegalArgumentException {
        String el = extractXml.getEl();
        if (el == null || "".equals(el)) {
            throw new IllegalArgumentException("extract中el表达式不能为空");
        }
    }
}
