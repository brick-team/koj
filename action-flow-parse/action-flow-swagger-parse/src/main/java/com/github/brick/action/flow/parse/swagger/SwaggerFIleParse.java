package com.github.brick.action.flow.parse.swagger;

import com.github.brick.action.flow.method.entity.api.ApiEntity;

import java.util.List;

public interface SwaggerFIleParse {
    List<ApiEntity> parse(String file);
}
