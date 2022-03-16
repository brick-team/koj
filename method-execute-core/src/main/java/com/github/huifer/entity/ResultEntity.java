package com.github.huifer.entity;

import lombok.Data;

import java.util.List;

@Data
public class ResultEntity {
    private List<Key> keys;


    @Data
    public static class Key {
        private String name;
        private String exId;
    }

}
