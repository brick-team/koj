package com.github.brick.action.flow.method.entity;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ActionEntity {
    private String id;
    private String clazzStr;
    private Class<?> clazz;

    private String methodStr;
    private Method method;

    private boolean async = false;
    private List<Param> params = new ArrayList<>();


    private Map<String, Object> methodArg = new HashMap<>();


    @Data
    public static class Param {
        private String argName;
        private String value;
        private String paramGroup;
        private String ex;
        private String exId;
        private Integer index;
        private String type;
        private Class<?> typeClass;

        private FormatEntity formatEntity;
    }
}
