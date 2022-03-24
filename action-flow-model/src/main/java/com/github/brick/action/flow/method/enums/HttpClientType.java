package com.github.brick.action.flow.method.enums;

public enum HttpClientType implements Type {
    OKHTTP,
    ;

    @Override
    public String type() {
        return name();
    }
}
