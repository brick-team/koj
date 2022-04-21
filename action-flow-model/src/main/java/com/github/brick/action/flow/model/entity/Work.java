package com.github.brick.action.flow.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * 执行工作表(Work)实体类
 *
 * @author xupenggao
 * @since 2022-04-21 10:24:16
 */
@Data
public class Work{

    /**
    * 表主键
    */
    private Integer id;
    /**
    * action表关联id
    */
    private Integer actionId;

}