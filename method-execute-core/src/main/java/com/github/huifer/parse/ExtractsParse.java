package com.github.huifer.parse;

import com.github.huifer.entity.ExtractTag;
import com.github.huifer.entity.ExtractsTag;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ExtractsParse implements Parse<ExtractsTag> {
    ExtractParse extractParse = new ExtractParse();

    @Override
    public ExtractsTag parse(Element element) {
        ExtractsTag extractsTag = new ExtractsTag();
        ArrayList<ExtractTag> extractTags = new ArrayList<>();

        List<Element> extract = element.elements("extract");
        for (Element element1 : extract) {
            ExtractTag parse = extractParse.parse(element1);
            extractTags.add(parse);
        }
        extractsTag.setExtractTags(extractTags);
        return extractsTag;
    }


    public class ExtractParse implements Parse<ExtractTag> {
        @Override
        public ExtractTag parse(Element element) {
            ExtractTag extractTag = new ExtractTag();
            extractTag.setId(element.attributeValue("id"));
            extractTag.setFromAction(element.attributeValue("fromAction"));
            extractTag.setEl(element.attributeValue("el"));
            return extractTag;
        }
    }

}
