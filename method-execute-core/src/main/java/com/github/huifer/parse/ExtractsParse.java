package com.github.huifer.parse;

import com.github.huifer.entity.ExtractEntity;
import com.github.huifer.entity.ExtractsEntity;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ExtractsParse implements Parse<ExtractsEntity> {
    ExtractParse extractParse = new ExtractParse();

    @Override
    public ExtractsEntity parse(Element element) {
        ExtractsEntity extractsEntity = new ExtractsEntity();
        ArrayList<ExtractEntity> extractEntities = new ArrayList<>();

        List<Element> extract = element.elements("extract");
        for (Element element1 : extract) {
            ExtractEntity parse = extractParse.parse(element1);
            extractEntities.add(parse);
        }
        extractsEntity.setExtractEntities(extractEntities);
        return extractsEntity;
    }


    public class ExtractParse implements Parse<ExtractEntity> {
        @Override
        public ExtractEntity parse(Element element) {
            ExtractEntity extractEntity = new ExtractEntity();
            extractEntity.setId(element.attributeValue("id"));
            extractEntity.setFromAction(element.attributeValue("fromAction"));
            extractEntity.setEl(element.attributeValue("el"));
            return extractEntity;
        }
    }

}
