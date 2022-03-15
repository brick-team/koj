package com.github.huifer.format;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class StringToClass implements Format<String, JSONObject> {
    @Override
    public JSONObject format(String s) {
        return JSON.parseObject(s,JSONObject.class);
    }
}
