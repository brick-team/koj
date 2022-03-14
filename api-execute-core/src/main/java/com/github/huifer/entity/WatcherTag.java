package com.github.huifer.entity;

import lombok.Data;

import java.util.List;

@Data
public class WatcherTag {
    private String id;
    private String exId;
    private String condition;
    private List<Then> thens;
    private List<Cache> caches;


    @Data
    public static class Then {
        private String actionId;
    }


    @Data
    public static class Cache {
        private String actionId;
    }
}
