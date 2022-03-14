package com.github.huifer.parse;

import com.github.huifer.entity.FlowTag;
import com.github.huifer.entity.WorkTag;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class FlowParse implements Parse<FlowTag> {
    FlowWorkParse flowWorkParse = new FlowWorkParse();

    @Override
    public FlowTag parse(Element element) {

        FlowTag flowTag = new FlowTag();
        ArrayList<WorkTag> workTags = new ArrayList<>();


        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item instanceof Element) {
                WorkTag parse = flowWorkParse.parse((Element) item);
                workTags.add(parse);
            }
        }
        flowTag.setWorkTags(workTags);
        return flowTag;
    }


    public class FlowWorkParse implements Parse<WorkTag> {
        @Override
        public WorkTag parse(Element element) {
            WorkTag workTag = new WorkTag();
            workTag.setType(element.getAttribute("type"));
            workTag.setRefId(element.getAttribute("ref_id"));
            workTag.setId(element.getAttribute("id"));





            NodeList then = element.getElementsByTagName("then");



            List<WorkTag> thens = new ArrayList<>();


            for (int i = 0; i < then.getLength(); i++) {
                // fixme: then 节点解析
                Node item = then.item(i);
                if (item.getNodeName().equals("then")) {

                    Element item2 = (Element) item;
                    NodeList work = item2.getElementsByTagName("work");

                    NodeList childNodes = item.getChildNodes();
                    for (int i1 = 0; i1 < childNodes.getLength(); i1++) {

                        Node item1 = childNodes.item(i1);
                        if (item1.getNodeName().equals("work")) {

                            WorkTag parse = parse((Element) item1);

                            thens.add(parse);
                        }
                    }
                }
            }
            workTag.setThen(thens);
            NodeList cache = element.getElementsByTagName("cache");
            List<WorkTag> caches = new ArrayList<>();

            for (int i = 0; i < cache.getLength(); i++) {
                Node item = cache.item(i);

                if (item.getNodeName().equals("cache")) {

                    // fixme: 节点解析
                    NodeList childNodes = item.getChildNodes();
                    for (int i1 = 0; i1 < childNodes.getLength(); i1++) {

                        Node item1 = childNodes.item(i1);
                        if (item1.getNodeName().equals("work")) {

                            WorkTag parse = parse((Element) item1);

                            caches.add(parse);
                        }
                    }
                }

            }

            workTag.setCache(caches);

            return workTag;
        }
    }

}
