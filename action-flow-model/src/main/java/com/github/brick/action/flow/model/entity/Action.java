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
 * Action表(Action)实体类
 *
 * @author xupenggao
 * @since 2022-04-21 10:24:14
 */
@Data
public class Action{

    /**
    * 表主键
    */
    private Integer id;
    /**
    * 1:rest_api,2:java_method
    */
    private Integer type;
    /**
    * type=1时表示HTTP请求地址
    */
    private String url;
    /**
    * type=1时表示HTTP请求方式(POST\\GET\\DELETE\\PUT) type=2时表示java函数名称
    */
    private String method;
    /**
    * type=2时表示需要执行的类全路径
    */
    private String className;
    /**
     * 文件名称
     */
    private String fileName;

}