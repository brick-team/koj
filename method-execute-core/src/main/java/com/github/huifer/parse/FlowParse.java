package com.github.huifer.parse;

import com.github.huifer.entity.FlowTag;
import com.github.huifer.entity.WorkTag;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class FlowParse implements Parse<FlowTag> {
    WorkParse workParse = new WorkParse();

    @Override
    public FlowTag parse(Element element) throws Exception {

        String id = element.attributeValue("id");
        List<Element> work = element.elements("work");
        FlowTag flowTag = new FlowTag();
        flowTag.setId(id);

        ArrayList<WorkTag> workTags = new ArrayList<>();

        for (Element element1 : work) {


            WorkTag workTag = workParse.parse(element1);
            workTags.add(workTag);
        }
        flowTag.setWorkTags(workTags);

        return flowTag;
    }


    public class WorkParse implements Parse<WorkTag> {
        @Override
        public WorkTag parse(Element element) throws Exception {
            WorkTag workTag = new WorkTag();

            String id = element.attributeValue("id");
            String type = element.attributeValue("type");
            String refId = element.attributeValue("ref_id");

            workTag.setType(type);
            workTag.setRefId(refId);
            workTag.setId(id);


            ArrayList<WorkTag> then1 = new ArrayList<>();
            List<Element> then = element.elements("then");
            for (Element element1 : then) {
                List<Element> work = element1.elements("work");
                for (Element element2 : work) {

                    WorkTag workTag1 = parse(element2);
                    then1.add(workTag1);
                }
            }
            workTag.setThen(then1);


            ArrayList<WorkTag> catchls = new ArrayList<>();
            List<Element> catchs = element.elements("catch");
            for (Element element1 : catchs) {
                List<Element> work = element1.elements("work");
                for (Element element2 : work) {

                    WorkTag workTag1 = parse(element2);
                    catchls.add(workTag1);
                }
            }
            workTag.setCatchs(catchls);
            return workTag;
        }


    }
}
