package com.github.brick.action.flow.parse.swagger;

import com.github.brick.action.flow.method.entity.api.ApiEntity;

import java.util.List;

import static org.junit.Assert.*;

public class SwaggerFIleParseImplTest {
    SwaggerFIleParse swaggerFIleParse = new SwaggerFIleParseImpl();
    @org.junit.Test
    public void parse() {
        List<ApiEntity> parse = swaggerFIleParse.parse("swagge_example.json");
        System.out.println();

    }
}