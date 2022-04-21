package com.github.brick.action.flow.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * java_method参数表(JavaMethodParam)实体类
 *
 * @author xupenggao
 * @since 2022-04-21 10:24:16
 */
@Data
public class JavaMethodParam{

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
    * 参数索引，从0开始计数
    */
    private Integer index;
    /**
    * 参数类型，类全路径
    */
    private String type;
    /**
    * 参数名称
    */
    private String name;
    /**
    * 默认值
    */
    private String value;

}