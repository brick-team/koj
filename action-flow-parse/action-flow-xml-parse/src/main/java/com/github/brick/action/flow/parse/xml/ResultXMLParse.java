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

import com.github.brick.action.flow.model.execute.FieldExecuteEntity;
import com.github.brick.action.flow.model.execute.ResultExecuteEntity;
import com.github.brick.action.flow.model.xml.FieldXML;
import com.github.brick.action.flow.model.xml.ResultXML;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ResultXMLParse implements ParseXML<List<ResultExecuteEntity>> {
    private static final String RESULT_NODE = "result";
    private static final String RESULTS_NODE = "results";
    private static final String FLOW_ID_ATTR = "flowId";
    private static final String FIELDS_NODE = "fields";
    private static final String FIELD_NODE = "field";
    private final FieldXMLParse fieldXMLParse = new FieldXMLParse();

    @Override
    public List<ResultExecuteEntity> parse(Element element) throws Exception {
        Element results = element.element(RESULTS_NODE);
        List<Element> result = results.elements(RESULT_NODE);
        List<ResultExecuteEntity> resultList = new ArrayList<>();
        for (Element element1 : result) {
            String id = element1.attributeValue(FLOW_ID_ATTR);
            ResultXML resultXML = new ResultXML();
            resultXML.setFlowId(id);
            ArrayList<FieldExecuteEntity> fields = new ArrayList<>();
            Element fields1 = element1.element(FIELDS_NODE);
            List<Element> field = fields1.elements(FIELD_NODE);
            for (Element element2 : field) {
                FieldXML parse = this.fieldXMLParse.parse(element2);
                fields.add(parse);
            }

            resultXML.setFields(fields);

            resultList.add(resultXML);
        }
        return resultList;
    }
}
