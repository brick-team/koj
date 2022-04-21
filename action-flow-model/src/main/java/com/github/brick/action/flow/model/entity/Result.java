package com.github.brick.action.flow.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * 流程执行结果表(Result)实体类
 *
 * @author xupenggao
 * @since 2022-04-21 10:24:16
 */
@Data
public class Result{

    /**
    * 表主键
    */
    private Integer id;
    /**
    * flow表主键
    */
    private Integer flowId;
    /**
    * 父级id
    */
    private Integer pid;
    /**
    * 字段名
    */
    private String fieldName;
    /**
    * 值类型
    */
    private String type;
    /**
    * 字段值
    */
    private String value;

}