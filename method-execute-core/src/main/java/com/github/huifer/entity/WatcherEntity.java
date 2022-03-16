package com.github.huifer.entity;

import lombok.Data;

import java.util.List;

@Data
public class WatcherEntity {
    private String id;
    private String exId;
    private String condition;
    private List<Then> thens;
    private List<Catch> catchs;


    @Data
    public static class Then {
        private String actionId;
    }


    @Data
    public static class Catch {
        private String actionId;
    }
}
