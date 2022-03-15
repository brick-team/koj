package com.github.huifer.parse;

import com.github.huifer.entity.ResultTag;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ResultParse implements Parse<ResultTag> {

    ResultKeyParse resultKeyParse = new ResultKeyParse();

    @Override
    public ResultTag parse(Element element) {
        ResultTag resultTag = new ResultTag();

        ArrayList<ResultTag.Key> keys = new ArrayList<>();

        List<Element> key = element.elements("key");
        for (Element keyElement : key) {
            ResultTag.Key parse = resultKeyParse.parse(keyElement);
            keys.add(parse);
        }



        resultTag.setKeys(keys);

        return resultTag;
    }


    public class ResultKeyParse implements Parse<ResultTag.Key> {
        @Override
        public ResultTag.Key parse(Element element) {
            String name = element.attributeValue("name");
            String exid = element.attributeValue("exid");
            ResultTag.Key key = new ResultTag.Key();
            key.setName(name);
            key.setExId(exid);
            return key;
        }
    }

}
