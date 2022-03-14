package com.github.huifer.parse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

public class T2 {
    @Test
    public void hh() throws Exception {

        String fileName = this.getClass().getClassLoader().getResource("fl.xml").getPath();//获取文件路径

        SAXReader reader = new SAXReader();
        Document document = reader.read(fileName);

        Element rootElement = document.getRootElement();
        Element flow = rootElement.element("flow");

        System.out.println(flow.elements("work"));
    }
}
