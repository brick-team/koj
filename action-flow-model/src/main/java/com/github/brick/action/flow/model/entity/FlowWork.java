/*
 *    Copyright [2022] [brick-team]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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