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

package com.github.brick.action.flow.search;

import com.github.brick.action.flow.method.search.ClassProperties;
import com.github.brick.action.flow.model.enums.ActionType;
import com.github.brick.action.flow.model.jdk.JavaClassParamEntity;
import com.google.gson.Gson;
import lombok.Data;
import org.junit.Test;

import javax.xml.bind.annotation.XmlType;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Zen Huifer
 */
public class ClassPropertiesTest {
    Gson gson = new Gson();
    @Test
    public void properties() {
        Class<?> aClass = A.class;

        Map<String, List<JavaClassParamEntity>> handler1 = ClassProperties.handler(
                A.class);
        System.out.println(gson.toJson(handler1));

    }

    @Data
    static class A {
        private long aLong;
        private ActionType actionType;
        private String[] array;
        private String name;
        private List<Object> list;
        private Map<String, Object> map;
        private B b;
    }

    @Data
    static class B {
        private String name;
        private A a;
    }
}