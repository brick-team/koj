package com.github.huifer.parse;

import org.w3c.dom.Element;

public interface Parse<T> {
    T parse(Element element);
}
