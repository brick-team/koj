package com.github.brick.parse.xml;

import com.github.brick.entity.ResultEntity;
import com.github.brick.parse.Parse;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ResultParse implements Parse<ResultEntity> {

    ResultKeyParse resultKeyParse = new ResultKeyParse();

    @Override
    public ResultEntity parse(Element element) {
        ResultEntity resultEntity = new ResultEntity();

        ArrayList<ResultEntity.Key> keys = new ArrayList<>();

        List<Element> key = element.elements("key");
        for (Element keyElement : key) {
            ResultEntity.Key parse = resultKeyParse.parse(keyElement);
            keys.add(parse);
        }



        resultEntity.setKeys(keys);

        return resultEntity;
    }


    public class ResultKeyParse implements Parse<ResultEntity.Key> {
        @Override
        public ResultEntity.Key parse(Element element) {
            String name = element.attributeValue("name");
            String exid = element.attributeValue("exid");
            ResultEntity.Key key = new ResultEntity.Key();
            key.setName(name);
            key.setExId(exid);
            return key;
        }
    }

}
