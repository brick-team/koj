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
    /**
     * 文件名称
     */
    private String fileName;

}