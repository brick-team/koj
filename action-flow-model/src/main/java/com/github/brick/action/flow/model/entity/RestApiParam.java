package com.github.brick.action.flow.model.entity;

import lombok.Data;

/**
 * rest_api参数表(RestApiParam)实体类
 *
 * @author xupenggao
 * @since 2022-04-21 10:24:16
 */
@Data
public class RestApiParam{

    /**
    * 表主键
    */
    private Integer id;
    /**
    * action表关联id
    */
    private Integer actionId;
    /**
    * 父级id
    */
    private Integer pid;
    /**
    * 参数所在位置
    */
    private String in;
    /**
    * 参数名称
    */
    private String name;
    /**
    * 是否必填
    */
    private Integer require;
    /**
    * 默认值
    */
    private String value;

}