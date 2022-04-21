package com.github.brick.action.flow.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * 条件判断(Watcher)实体类
 *
 * @author xupenggao
 * @since 2022-04-21 10:24:16
 */
@Data
public class Watcher{

    /**
    * 表主键
    */
    private Integer id;
    /**
    * 条件表达式
    */
    private String condition;
    /**
    * 取值表达式类型
    */
    private String elType;

}