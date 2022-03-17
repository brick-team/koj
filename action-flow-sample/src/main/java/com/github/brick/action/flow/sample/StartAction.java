package com.github.brick.action.flow.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class StartAction {
    private static final Logger logger = LoggerFactory.getLogger(StartAction.class);

    public Map<String, Object> start() throws InterruptedException {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("age", 18);
        logger.info("start-action");
        Thread.sleep(1000);
        return stringObjectHashMap;
    }
}
