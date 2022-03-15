package com.github.huifer.parse;


import org.dom4j.Element;

public interface Parse<T> {
    T parse(Element element)throws Exception;
}
