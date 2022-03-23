package com.github.brick.action.flow.spring.search;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

@Data
public class SearchResult {
    private Object bean;
    private String beanName;
    private String beanClass;
    private List<Method> methods;
}
