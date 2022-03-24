package com.github.brick.action.flow.method.entity.api;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ApiEntity {
    private String url;
    private String[] method;
    /**
     * key: http method
     * value: http method param
     */
    private Map<String, List<ApiParamEntity>> params;
}
