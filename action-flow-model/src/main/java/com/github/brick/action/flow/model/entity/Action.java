package com.github.brick.action.flow.model.entity;

import lombok.Data;

/**
 * Action表(Action)实体类
 *
 * @author xupenggao
 * @since 2022-04-21 10:24:14
 */
@Data
public class Action{

    /**
    * 表主键
    */
    private Integer id;
    /**
    * 1:rest_api,2:java_method
    */
    private Integer type;
    /**
    * type=1时表示HTTP请求地址
    */
    private String url;
    /**
    * type=1时表示HTTP请求方式(POST\\GET\\DELETE\\PUT) type=2时表示java函数名称
    */
    private String method;
    /**
    * type=2时表示需要执行的类全路径
    */
    private String className;

}