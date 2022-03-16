package com.github.brick.parse.xml;

import com.github.brick.entity.FormatEntity;
import com.github.brick.parse.Parse;
import org.dom4j.Element;

public class FormatParse implements Parse<FormatEntity> {
    @Override
    public FormatEntity parse(Element element) throws Exception {
        String id = element.attributeValue("id");

        String clazz = element.attributeValue("class");

        FormatEntity formatEntity = new FormatEntity();
        formatEntity.setId(id);
        formatEntity.setClassStr(clazz);
        formatEntity.setClazz(Class.forName(clazz));



        return formatEntity;
    }
}
