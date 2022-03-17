package com.github.brick.action.flow.method.entity;

import lombok.Data;

import java.util.List;

@Data
public class WorkEntity {
    private String type;
    private String refId;
    private String id;


    private List<WorkEntity> then;
    private List<WorkEntity> catchs;
}
