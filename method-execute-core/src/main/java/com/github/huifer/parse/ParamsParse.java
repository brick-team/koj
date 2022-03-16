package com.github.huifer.parse;

import com.github.huifer.entity.ParamEntity;
import com.github.huifer.entity.ParamsEntity;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ParamsParse implements Parse<ParamsEntity> {
    ParamParse paramParse = new ParamParse();

    @Override
    public ParamsEntity parse(Element element) throws Exception {
        ParamsEntity paramsEntity = new ParamsEntity();
        ArrayList<ParamEntity> list = new ArrayList<>();


        List<Element> param = element.elements("param");
        for (Element element1 : param) {
            list.add(paramParse.parse(element1));
        }
        paramsEntity.setList(list);

        return paramsEntity;
    }


    public class ParamParse implements Parse<ParamEntity> {
        @Override
        public ParamEntity parse(Element element) throws Exception {
            String group = element.attributeValue("group");

            String key = element.elementText("key");
            String value = element.elementText("value");
            ParamEntity paramEntity = new ParamEntity();
            paramEntity.setGroup(group);
            paramEntity.setKey(key);
            paramEntity.setValue(value);


            return paramEntity;
        }
    }

}
