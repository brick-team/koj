package com.github.huifer.parse;

import com.github.huifer.entity.ParamTag;
import com.github.huifer.entity.ParamsTag;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ParamsParse implements Parse<ParamsTag> {
    ParamParse paramParse = new ParamParse();

    @Override
    public ParamsTag parse(Element element) throws Exception {
        ParamsTag paramsTag = new ParamsTag();
        ArrayList<ParamTag> list = new ArrayList<>();


        List<Element> param = element.elements("param");
        for (Element element1 : param) {
            list.add(paramParse.parse(element1));
        }
        paramsTag.setList(list);

        return paramsTag;
    }


    public class ParamParse implements Parse<ParamTag> {
        @Override
        public ParamTag parse(Element element) throws Exception {
            String group = element.attributeValue("group");

            String key = element.elementText("key");
            String value = element.elementText("value");
            ParamTag paramTag = new ParamTag();
            paramTag.setGroup(group);
            paramTag.setKey(key);
            paramTag.setValue(value);


            return paramTag;
        }
    }

}
