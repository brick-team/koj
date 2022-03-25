package com.github.brick.action.flow.parse.swagger;

import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.google.gson.Gson;

import java.util.List;

public class SwaggerFIleParseImplTest {
    SwaggerFIleParse swaggerFIleParse = new SwaggerFIleParseImpl();
    Gson gson = new Gson();

    @org.junit.Test
    public void parse() {
        List<ApiEntity> parse = swaggerFIleParse.parse("swagge_example.json");
        for (ApiEntity apiEntity : parse) {
            List<ApiParamEntity> params = apiEntity.getParams();
            System.out.println(gson.toJson(params));
        }

    }
}