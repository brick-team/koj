package com.github.huifer.parse;

import com.github.huifer.entity.FormatTag;
import org.w3c.dom.Element;

public class FormatParse implements Parse<FormatTag> {
    @Override
    public FormatTag parse(Element element) {
        String id = element.getAttribute("id");
        String aClass = element.getAttribute("class");
        FormatTag formatTag = new FormatTag();
        formatTag.setId(id);
        formatTag.setClassStr(aClass);


        return formatTag;
    }
}
