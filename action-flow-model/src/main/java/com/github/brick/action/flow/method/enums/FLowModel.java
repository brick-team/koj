package com.github.brick.action.flow.method.enums;

public enum FLowModel implements Type {
    XML,
    ;

    @Override
    public String type() {
        return name();
    }
}
