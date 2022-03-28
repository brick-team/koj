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

package com.github.brick.action.flow.execute.jdk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */
public interface JDKExecuteService {
    Object execute(String className, String method, Class<?>[] methodParamType, Object... args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    Object execute(Class<?> clazz, String method, Class<?>[] methodParamType, Object... args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;


    Object execute(Object object, Method method, Object... args) throws InvocationTargetException, IllegalAccessException;
}
