package com.github.huifer.parse;

import com.github.huifer.entity.WorkTag;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class T2 {
    @Test
    public void hh() throws Exception {

        String fileName = this.getClass().getClassLoader().getResource("fl.xml").getPath();//获取文件路径

        SAXReader reader = new SAXReader();
        Document document = reader.read(fileName);

        Element rootElement = document.getRootElement();
        Element flow = rootElement.element("flow");

        List<Element> work = flow.elements("work");
        WorkParse workParse = new WorkParse();

        for (Element element : work) {


            WorkTag workTag = workParse.workTag(element);
            System.out.println();

        }
    }

    public class WorkParse {
        public WorkTag workTag(Element element) {
            WorkTag workTag = new WorkTag();

            String id = element.attributeValue("id");
            String type = element.attributeValue("type");
            String ref_id = element.attributeValue("ref_id");
            System.out.println(id + "\t" + type + "\t" + ref_id);

            workTag.setType(type);
            workTag.setRefId(ref_id);
            workTag.setId(id);


            ArrayList<WorkTag> then1 = new ArrayList<>();
            List<Element> then = element.elements("then");
            for (Element element1 : then) {
                List<Element> work = element1.elements("work");
                for (Element element2 : work) {

                WorkTag workTag1 = workTag(element2);
                then1.add(workTag1);
                }
            }


            workTag.setThen(then1);


            return workTag;
        }
    }

}
