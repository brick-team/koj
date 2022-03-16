package com.github.huifer.parse;

import com.github.huifer.entity.FlowEntity;
import com.github.huifer.entity.WorkEntity;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class FlowParse implements Parse<FlowEntity> {
    WorkParse workParse = new WorkParse();

    @Override
    public FlowEntity parse(Element element) throws Exception {

        String id = element.attributeValue("id");
        List<Element> work = element.elements("work");
        FlowEntity flowEntity = new FlowEntity();
        flowEntity.setId(id);

        ArrayList<WorkEntity> workEntities = new ArrayList<>();

        for (Element element1 : work) {


            WorkEntity workEntity = workParse.parse(element1);
            workEntities.add(workEntity);
        }
        flowEntity.setWorkEntities(workEntities);

        return flowEntity;
    }


    public class WorkParse implements Parse<WorkEntity> {
        @Override
        public WorkEntity parse(Element element) throws Exception {
            WorkEntity workEntity = new WorkEntity();

            String id = element.attributeValue("id");
            String type = element.attributeValue("type");
            String refId = element.attributeValue("ref_id");

            workEntity.setType(type);
            workEntity.setRefId(refId);
            workEntity.setId(id);


            ArrayList<WorkEntity> then1 = new ArrayList<>();
            List<Element> then = element.elements("then");
            for (Element element1 : then) {
                List<Element> work = element1.elements("work");
                for (Element element2 : work) {

                    WorkEntity workEntity1 = parse(element2);
                    then1.add(workEntity1);
                }
            }
            workEntity.setThen(then1);


            ArrayList<WorkEntity> catchls = new ArrayList<>();
            List<Element> catchs = element.elements("catch");
            for (Element element1 : catchs) {
                List<Element> work = element1.elements("work");
                for (Element element2 : work) {

                    WorkEntity workEntity1 = parse(element2);
                    catchls.add(workEntity1);
                }
            }
            workEntity.setCatchs(catchls);
            return workEntity;
        }


    }
}
