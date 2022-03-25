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

package com.github.brick.action.flow.spring.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Data
public class SearchResult {
    @JsonIgnore
    private Object bean;
    private String beanName;
    private String beanClass;
    @JsonIgnore
    private List<Method> methods;

    /**
     * key : 成员变量 methods 索引
     * value: 函数名称
     */
    private Map<Integer, String> indexMethod;
}
