package com.github.huifer.parse;

import com.github.huifer.entity.ResultTag;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class ResultParse implements Parse<ResultTag> {
    ResultKeyParse resultKeyParse = new ResultKeyParse();

    @Override
    public ResultTag parse(Element element) {
        ResultTag resultTag = new ResultTag();

        ArrayList<ResultTag.Key> keys = new ArrayList<>();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item instanceof Element) {
                if (item.getNodeName().equals("key")) {

                    ResultTag.Key parse = resultKeyParse.parse((Element) item);
                    keys.add(parse);
                }
            }
        }
        resultTag.setKeys(keys);

        return resultTag;
    }


    public class ResultKeyParse implements Parse<ResultTag.Key> {
        @Override
        public ResultTag.Key parse(Element element) {
            String name = element.getAttribute("name");
            String exid = element.getAttribute("exid");
            ResultTag.Key key = new ResultTag.Key();
            key.setName(name);
            key.setExId(exid);

            return key;
        }
    }
}
