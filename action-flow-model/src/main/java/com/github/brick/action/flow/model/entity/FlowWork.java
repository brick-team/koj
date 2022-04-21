package com.github.brick.action.flow.model.entity;

import lombok.Data;

/**
 * flow、work表中间关联表(FlowWork)实体类
 *
 * @author xupenggao
 * @since 2022-04-21 10:24:16
 */
@Data
public class FlowWork{

    /**
    * 表主键
    */
    private Integer id;
    /**
    * flow表关联id
    */
    private Integer flowId;
    /**
    * 关联work表id
    */
    private Integer workId;
    /**
    * 步骤值
    */
    private String step;
    /**
    * 是否是顶层
    */
    private Integer type;
    /**
    * 是否异步(异步：后续所有流程都与之脱离)
    */
    private Integer async;
    /**
    * 排序字段（只有顶层具备）
    */
    private Integer sort;

}