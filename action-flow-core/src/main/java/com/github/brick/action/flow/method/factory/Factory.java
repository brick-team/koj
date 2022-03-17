package com.github.brick.action.flow.method.factory;

public interface Factory<Type extends com.github.brick.action.flow.method.enums.Type, T> {


    T gen(Type type);

}
