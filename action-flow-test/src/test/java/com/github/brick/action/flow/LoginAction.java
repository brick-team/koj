package com.github.brick.action.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class LoginAction {
    private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);

    public Map<String, Object> login(String username, String password) {
        if (logger.isInfoEnabled()) {
            logger.info("login,username = {}, password = {}", username, password);
        }
//        if (true) {
//            throw new RuntimeException("aaa");
//
//        }

        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("username", "zhangsan");
        stringStringHashMap.put("age", "18");
        stringStringHashMap.put("login_time", LocalDate.now());

        return stringStringHashMap;
    }
}
