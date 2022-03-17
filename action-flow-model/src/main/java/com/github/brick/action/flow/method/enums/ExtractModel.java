package com.github.brick.action.flow.method.enums;

public enum ExtractModel implements Type {
    JSON_PATH,
    ;

    @Override
    public String type() {
        return name();
    }
}
