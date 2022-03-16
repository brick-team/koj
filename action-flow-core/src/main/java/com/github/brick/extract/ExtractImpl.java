package com.github.brick.extract;

import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

public class ExtractImpl implements Extract {
    @Override
    public Object extract(Object o, String el) {
        String json = JSON.toJSONString(o);
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

        return JsonPath.read(document, el);
    }
}
