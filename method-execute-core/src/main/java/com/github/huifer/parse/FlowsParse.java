package com.github.huifer.parse;

import com.github.huifer.entity.FlowEntity;
import com.github.huifer.entity.FlowsEntity;
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
