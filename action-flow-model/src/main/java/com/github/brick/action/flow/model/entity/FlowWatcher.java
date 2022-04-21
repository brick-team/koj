package com.github.brick.action.flow.model.entity;

import lombok.Data;

/**
 * flow、watcher、flow_work表中间关联表(FlowWatcher)实体类
 *
 * @author xupenggao
 * @since 2022-04-21 10:24:16
 */
@Data
public class FlowWatcher{

    /**
    * 表主键
    */
    private Integer id;
    /**
    * flow_work表关联id
    */
    private Integer flowWorkId;
    /**
    * 1:then节点   2:cat节点
    */
    private Integer thenOrCat;
    /**
    * 指向flow_work表的id
    */
    private Integer nextFlowWorkId;
    /**
    * 指向watcher表的id
    */
    private Integer watcherId;
    /**
    * 排序字段
    */
    private Integer sort;

}