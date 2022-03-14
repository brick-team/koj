package com.github.huifer.parse;

import com.github.huifer.entity.ExtractTag;
import com.github.huifer.entity.ExtractsTag;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class ExtractsParse implements Parse<ExtractsTag> {
    ExtractParse extractParse = new ExtractParse();

    @Override
    public ExtractsTag parse(Element element) {
        ExtractsTag extractsTag = new ExtractsTag();
        ArrayList<ExtractTag> extractTags = new ArrayList<>();

        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item instanceof Element) {
                ExtractTag parse = extractParse.parse((Element) item);
                extractTags.add(parse);
            }
        }
        extractsTag.setExtractTags(extractTags);
        return extractsTag;
    }


    public class ExtractParse implements Parse<ExtractTag> {
        @Override
        public ExtractTag parse(Element element) {
            ExtractTag extractTag = new ExtractTag();
            extractTag.setId(element.getAttribute("id"));
            extractTag.setFromAction(element.getAttribute("fromAction"));
            extractTag.setEl(element.getAttribute("el"));
            return extractTag;
        }
    }

}
