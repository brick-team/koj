package com.github.huifer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class LoginAction {
    public Map<String, Object> login(String username, String password) {
//        if ("username".equals(username) && "password".equals(password)) {
//            throw new RuntimeException("error");
//        }
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("username", "zhangsan");
        stringStringHashMap.put("age", "18");
        stringStringHashMap.put("login_time", LocalDate.now());
        System.out.println(stringStringHashMap);
        return stringStringHashMap;
    }
}
