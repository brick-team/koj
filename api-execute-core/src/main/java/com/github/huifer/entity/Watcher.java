package com.github.huifer.entity;

import lombok.Data;

import java.util.List;

@Data
public class Watcher {
    private String id;
    private String exId;
    private String type;
    private String condition;
    private List<Then> thens;


    @Data
    public static class Then {
        private String actionId;

    }
}
