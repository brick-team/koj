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
 * 取值表达式表(Extract)实体类
 *
 * @author xupenggao
 * @since 2022-04-21 10:24:15
 */
@Data
public class Extract{

    /**
    * 表主键
    */
    private Integer id;
    /**
    * flow表主键
    */
    private Integer flowId;
    /**
    * flow_work表主键
    */
    private Integer flowWorkId;
    /**
    * type=1:关联rest_api_param, type=2:关联ava_method_param表,type=3:关联result表
    */
    private Integer refId;
    /**
    * type=1:关联rest_api_param, type=2:关联ava_method_param表, type=3:关联result表
    */
    private Integer type;
    /**
    * 步骤标记
    */
    private String step;
    /**
    * el取值表达式类型
    */
    private String elType;
    /**
    * 取值表达式
    */
    private String el;

}