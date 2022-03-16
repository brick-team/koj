package com.github.brick.enums;

public enum ExtractModel implements Type {
    JSON_PATH,
    ;

    @Override
    public String type() {
        return name();
    }
}
