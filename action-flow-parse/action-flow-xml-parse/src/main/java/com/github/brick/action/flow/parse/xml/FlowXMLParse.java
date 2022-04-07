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

import com.github.brick.action.flow.model.xml.FlowXML;
import com.github.brick.action.flow.model.xml.WorkXML;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class FlowXMLParse implements ParseXML<List<FlowXML>> {
    private static final String FLOWS_NODE = "flows";
    private static final String FLOW_NODE = "flow";
    private static final String WORK_NODE = "work";
    private static final String ID_ATTR = "id";
    private final WorkXMLParse workXMLParse = new WorkXMLParse();

    @Override
    public List<FlowXML> parse(Element element) throws Exception {
        Element flows = element.element(FLOWS_NODE);
        List<Element> flow = flows.elements(FLOW_NODE);
        List<FlowXML> res = new ArrayList<>();

        for (Element element1 : flow) {
            FlowXML flowXML = new FlowXML();
            String id = element1.attributeValue(ID_ATTR);
            flowXML.setId(id);

            List<Element> work = element1.elements(WORK_NODE);

            ArrayList<WorkXML> works = new ArrayList<>();
            for (Element element2 : work) {
                works.add(workXMLParse.parse(element2));
            }
            flowXML.setWorks(works);
            res.add(flowXML);
        }
        return res;
    }
}
