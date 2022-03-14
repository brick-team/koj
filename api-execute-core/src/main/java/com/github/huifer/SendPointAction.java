package com.github.huifer;

import java.util.HashMap;
import java.util.Map;

public class SendPointAction {
    Map<String, Object> points = new HashMap<>();

    public Map<String, Object > sendPoint(String uid, Integer point) {

        points.put(uid, point);
        System.out.println(points);
        return points;
    }
}
