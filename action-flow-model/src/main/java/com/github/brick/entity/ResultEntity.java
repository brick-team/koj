package com.github.brick.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResultEntity {
    private List<Key> keys = new ArrayList<>();


    @Data
    public static class Key {
        private String name;
        private String exId;
    }

}
