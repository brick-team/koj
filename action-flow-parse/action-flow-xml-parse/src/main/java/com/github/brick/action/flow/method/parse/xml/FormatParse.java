package com.github.brick.action.flow.method.parse.xml;

import com.github.brick.action.flow.method.entity.FormatEntity;
import com.github.brick.action.flow.method.parse.Parse;
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
