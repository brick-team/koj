package com.github.brick.action.flow.method.entity.api;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ApiEntity {
    private String url;
    private String method;
    private String desc;

    private List<ApiParamEntity> params;
}
