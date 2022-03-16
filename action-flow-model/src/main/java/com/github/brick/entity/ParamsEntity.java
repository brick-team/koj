package com.github.brick.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParamsEntity {
    private List<ParamEntity> list = new ArrayList<>();
}
