package com.github.huifer.entity;

import lombok.Data;

import java.util.List;

@Data
public class FlowEntity {
    private String id;
    private List<WorkEntity> workEntities;
}
