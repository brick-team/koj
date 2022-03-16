package com.github.brick.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlowsEntity {
    private List<FlowEntity> flowEntities =new ArrayList<>();
}
