package com.github.brick.action.flow.method.parse;


import org.dom4j.Element;

public interface Parse<T> {
    T parse(Element element) throws Exception;
}
