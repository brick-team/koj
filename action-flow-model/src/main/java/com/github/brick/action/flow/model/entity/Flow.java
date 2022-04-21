package com.github.brick.action.flow.model.entity;

import lombok.Data;

/**
 * flow流程配置表(Flow)实体类
 *
 * @author xupenggao
 * @since 2022-04-21 10:24:16
 */
@Data
public class Flow{

    /**
    * 表主键
    */
    private Integer id;
    /**
    * 工作流名称
    */
    private String name;
    /**
    * 与spring 进行整合使用
    */
    private String qualifier;

}