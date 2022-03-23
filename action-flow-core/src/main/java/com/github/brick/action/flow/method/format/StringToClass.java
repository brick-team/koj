package com.github.brick.action.flow.method.format;

import com.google.gson.Gson;

public class StringToClass implements Format<String> {
    Gson gson = new Gson();

    @Override
    public Object format(String s, Class<?> clazz) {
        return gson.fromJson(s, clazz);
    }
}
