package com.github.brick.action.flow.model.res;

import lombok.Data;

import java.io.PrintWriter;
import java.util.List;

@Data

public class Page<T> {
    private Integer page;
    private Integer size;
    private Integer total;
    private Integer pageSum;
    private List<T> list;
}
