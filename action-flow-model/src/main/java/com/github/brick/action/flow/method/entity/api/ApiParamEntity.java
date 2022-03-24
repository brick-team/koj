package com.github.brick.action.flow.method.entity.api;

import lombok.Data;

import java.util.List;

@Data
public class ApiParamEntity {
    private String flag;
    private ParamIn in;
    private String name;
    private boolean require;
    private String type;
    private List<ApiParamEntity> paramEntities;
}
