package com.github.huifer.parse;

import com.github.huifer.entity.ParamTag;
import com.github.huifer.entity.ParamsTag;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class ParamsParse implements Parse<ParamsTag> {
    ParamParse paramParse = new ParamParse();

    @Override
    public ParamsTag parse(Element element) {
        ParamsTag paramsTag = new ParamsTag();
        ArrayList<ParamTag> list = new ArrayList<>();

        // 获取params节点下的子节点
        NodeList param = element.getChildNodes();
        for (int i = 0; i < param.getLength(); i++) {
            Node item = param.item(i);
            if (item instanceof Element) {
                ParamTag paramTag = paramParse.parse((Element) item);
                list.add(paramTag);
            }
        }
        paramsTag.setList(list);
        return paramsTag;
    }

    public class ParamParse implements Parse<ParamTag> {
        @Override
        public ParamTag parse(Element element) {
            ParamTag paramTag = new ParamTag();
            String group = element.getAttribute("group");
            paramTag.setGroup(group);
            NodeList childNodes = element.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item.getNodeName().equals("key")) {
                    String nodeValue = item.getTextContent();
                    paramTag.setKey(nodeValue);
                } else if (item.getNodeName().equals("value")) {
                    String nodeValue = item.getTextContent();
                    paramTag.setValue(nodeValue);

                }
            }



            return paramTag;
        }
    }

}
