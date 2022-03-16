package com.github.huifer.format;

public interface Format<S> {
    Object format(S s, Class<?> clazz);
}
