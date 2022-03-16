package com.github.brick;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SendPointAction {
    private static final Logger logger = LoggerFactory.getLogger(SendPointAction.class);
    Map<String, Object> points = new HashMap<>();

    public Map<String, Object> sendPoint(String uid, Integer point) {
        if (logger.isInfoEnabled()) {
            logger.info("sendPoint,uid = {}, point = {}", uid, point);
        }

        points.put(uid, point);
        return points;
    }
}
