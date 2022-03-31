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

package com.github.brick.action.flow.method.entity;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ActionEntity implements ExecuteObject {
    private String id;
    private String clazzStr;
    private Class<?> clazz;

    private String methodStr;
    private Method method;

    private boolean async = false;
    private List<Param> params = new ArrayList<>();


    private Map<String, Object> methodArg = new HashMap<>();


    @Data
    public static class Param {
        private String id;
        private String argName;
        private String value;
        private String paramGroup;
        private String ex;
        private String exId;
        private Integer index;
        private String type;
        private Class<?> typeClass;

        private FormatEntity formatEntity;
    }
}
