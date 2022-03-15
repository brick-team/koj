package com.github.huifer.parse;

import com.github.huifer.entity.FormatTag;
import org.dom4j.Element;

public class FormatParse implements Parse<FormatTag> {
    @Override
    public FormatTag parse(Element element) throws Exception {
        String id = element.attributeValue("id");

        String clazz = element.attributeValue("class");

        FormatTag formatTag = new FormatTag();
        formatTag.setId(id);
        formatTag.setClassStr(clazz);
        formatTag.setClazz(Class.forName(clazz));



        return formatTag;
    }
}
