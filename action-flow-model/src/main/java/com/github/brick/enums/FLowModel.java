package com.github.brick.enums;

public enum FLowModel implements Type {
    XML,
    ;

    @Override
    public String type() {
        return name();
    }
}
