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

package com.github.brick.action.flow.method.parse.xml;

import com.github.brick.action.flow.method.entity.FlowEntity;
import com.github.brick.action.flow.method.entity.FlowsEntity;
import com.github.brick.action.flow.method.parse.Parse;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class FlowsParse implements Parse<FlowsEntity> {
    FlowParse flowParse = new FlowParse();

    @Override
    public FlowsEntity parse(Element element) throws Exception {
        FlowsEntity flowsEntity = new FlowsEntity();
        ArrayList<FlowEntity> flowEntities = new ArrayList<>();
        flowsEntity.setFlowEntities(flowEntities);
        List<Element> flow = element.elements("flow");
        for (Element element1 : flow) {
            FlowEntity parse = flowParse.parse(element1);
            flowEntities.add(parse);
        }
        return flowsEntity;
    }
}
