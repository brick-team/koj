package com.github.brick.action.flow.method.format;

public interface Format<S> {
    Object format(S s, Class<?> clazz);
}
