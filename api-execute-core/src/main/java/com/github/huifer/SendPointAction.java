package com.github.huifer;

import java.util.HashMap;
import java.util.Map;

public class SendPointAction {
    Map<String, String> points = new HashMap<>();

    public Map<String, String > sendPoint(String uid, String point) {

        points.put(uid, point);
        System.out.println(points);
        return points;
    }
}
