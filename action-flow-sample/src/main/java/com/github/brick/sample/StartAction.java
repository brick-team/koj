package com.github.brick.sample;

import java.util.HashMap;
import java.util.Map;

public class StartAction {
    public Map<String, Object> start() {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("age", 18);
        return stringObjectHashMap;
    }
}
