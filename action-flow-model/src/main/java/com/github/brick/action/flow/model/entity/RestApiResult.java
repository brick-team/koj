package com.github.brick.action.flow.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * rest_api结果表(RestApiResult)实体类
 *
 * @author xupenggao
 * @since 2022-04-21 10:24:16
 */
@Data
public class RestApiResult{

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
    * 字段名
    */
    private String fieldName;
    /**
    * 	
值类型
    */
    private String type;
    /**
    * 字段值
    */
    private String value;

}