package com.github.huifer.entity;

import lombok.Data;

import java.util.List;

@Data
public class FlowTag {
    private List<WorkTag> workTags;
}
