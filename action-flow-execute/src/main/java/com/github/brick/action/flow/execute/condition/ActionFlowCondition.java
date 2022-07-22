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

package com.github.brick.action.flow.execute.condition;

import com.github.brick.action.flow.model.enums.ExtractModel;

/**
 * 条件执行器
 *
 * @author Zen Huifer
 */
public interface ActionFlowCondition {

    /**
     * 条件执行器
     *
     * @param condition 条件文本
     * @param elType    条件中文本表达式
     * @param o         对象
     * @return true:对象符合条件文本，false: 对象不符合条件文本
     */
    boolean condition(String condition, ExtractModel elType, Object o);

    /**
     * 条件执行器
     *
     * @param condition 条件文本
     * @param elType    条件中文本表达式
     * @param json      json字符串
     * @return true:对象符合条件文本，false: 对象不符合条件文本
     */
    boolean condition(String condition, ExtractModel elType, String json);
}
