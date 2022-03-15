package com.github.huifer.entity;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Data
public class ActionTag {
    private String id;
    private String clazzStr;
    private Class<?> clazz;

    private String methodStr;
    private Method method;

    private List<Param> params;


    private Map<String, Object> methodArg;


    @Data
    public static class Param {
        private String argName;
        private String value;
        private String paramGroup;
        private String ex;
        private Integer index;
        private String type;
        private Class<?> typeClass;

        private FormatTag formatTag;
    }
}
