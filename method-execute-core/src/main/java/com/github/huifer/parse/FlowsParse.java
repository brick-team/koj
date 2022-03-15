package com.github.huifer.parse;

import com.github.huifer.entity.FlowTag;
import com.github.huifer.entity.FlowsTag;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class FlowsParse implements Parse<FlowsTag> {
    FlowParse flowParse = new FlowParse();

    @Override
    public FlowsTag parse(Element element) throws Exception {
        FlowsTag flowsTag = new FlowsTag();
        ArrayList<FlowTag> flowTags = new ArrayList<>();
        flowsTag.setFlowTags(flowTags);
        List<Element> flow = element.elements("flow");
        for (Element element1 : flow) {
            FlowTag parse = flowParse.parse(element1);
            flowTags.add(parse);
        }
        return flowsTag;
    }
}
