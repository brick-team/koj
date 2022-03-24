package com.github.brick.action.flow.method.entity.api;

import lombok.Data;

@Data
public class ParamEntity {
    private ParamIn in;
    private String name;
    private boolean require;
}
