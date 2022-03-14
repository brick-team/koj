package com.github.huifer.parse;

import com.github.huifer.entity.*;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ParamsParseTest {
    ParamsParse paramsParse = new ParamsParse();
    ActionsParse actionsParse = new ActionsParse();
    WatchersParse watchersParse = new WatchersParse();
    ResultParse resultParse = new ResultParse();
    ExtractsParse extractsParse = new ExtractsParse();
    FlowParse flowParse = new FlowParse();

    @Test
    public void tt() throws Exception {

        String fileName = this.getClass().getClassLoader().getResource("fl.xml").getPath();//获取文件路径


        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(fileName);


        DocTag docTag = new DocTag();

        NodeList params = document.getElementsByTagName("params");
        for (int i = 0; i < params.getLength(); i++) {
            ParamsTag parse = paramsParse.parse((Element) params.item(i));
            docTag.setParams(parse);
        }

        NodeList actions = document.getElementsByTagName("actions");
        for (int i = 0; i < actions.getLength(); i++) {
            Node item = actions.item(i);
            ActionsTag parse = actionsParse.parse((Element) item);
            docTag.setActions(parse);
        }

        NodeList watchers = document.getElementsByTagName("watchers");
        for (int i = 0; i < watchers.getLength(); i++) {
            Node item = watchers.item(i);
            WatchersTag parse = watchersParse.parse((Element) item);
            docTag.setWatchers(parse);
        }
        NodeList result = document.getElementsByTagName("result");
        for (int i = 0; i < result.getLength(); i++) {
            Node item = result.item(i);
            ResultTag parse = resultParse.parse((Element) item);
            docTag.setResult(parse);

        }
        NodeList extracts = document.getElementsByTagName("extracts");

        for (int i = 0; i < extracts.getLength(); i++) {
            Node item = extracts.item(i);
            ExtractsTag parse = extractsParse.parse((Element) item);
            docTag.setExtracts(parse);

        }
        NodeList flow = document.getElementsByTagName("flow");
        for (int i = 0; i < flow.getLength(); i++) {
            Node item = flow.item(i);
            FlowTag parse = flowParse.parse((Element) item);
            docTag.setFlow(parse);

        }
    }
}