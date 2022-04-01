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

package com.github.brick.action.flow.web.common.ctr;

import java.util.Map;

/**
 * 函数控制器，功能:
 * 1. 函数数据CRUD
 * 2. 函数模拟执行（模拟执行函数必须在本地）
 */
public interface MethodCtr {


    /**
     * 处理java函数
     * @param clazz 全类名
     * @param method 函数名称
     * @param types 参数信息，key: 索引，value：参数类型（类全名)
     */
    boolean handler(String clazz, String method, Map<Integer, String> types);

}
