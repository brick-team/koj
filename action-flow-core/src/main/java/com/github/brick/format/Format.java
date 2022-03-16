package com.github.brick.format;

public interface Format<S> {
    Object format(S s, Class<?> clazz);
}
