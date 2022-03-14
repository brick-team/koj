package com.github.huifer.entity;

import lombok.Data;

import java.util.List;

@Data
public class WorkTag {
    private String type;
    private String refId;
    private String id;


    private List<WorkTag> then;
}
