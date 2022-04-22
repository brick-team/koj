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