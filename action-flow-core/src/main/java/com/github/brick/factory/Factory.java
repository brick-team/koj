package com.github.brick.factory;

public interface Factory<Type extends com.github.brick.enums.Type, T> {


    T gen(Type type);

}
