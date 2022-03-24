package com.github.brick.action.flow.parse.swagger;

import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.google.gson.Gson;

import java.util.List;

public class SwaggerFIleParseImplTest {
    SwaggerFIleParse swaggerFIleParse = new SwaggerFIleParseImpl();

    @org.junit.Test
    public void parse() {
        List<ApiEntity> parse = swaggerFIleParse.parse("swagge_example.json");
        Gson gson = new Gson();
        System.out.println(gson.toJson(parse));

    }
}