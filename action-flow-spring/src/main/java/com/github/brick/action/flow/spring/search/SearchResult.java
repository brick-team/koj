package com.github.brick.action.flow.spring.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Data
public class SearchResult {
    @JsonIgnore
    private Object bean;
    private String beanName;
    private String beanClass;
    @JsonIgnore
    private List<Method> methods;

    /**
     * key : 成员变量 methods 索引
     * value: 函数名称
     */
    private Map<Integer, String> indexMethod;
}
