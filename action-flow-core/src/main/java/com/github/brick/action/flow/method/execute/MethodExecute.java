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

package com.github.brick.action.flow.method.execute;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public interface MethodExecute {
    /**
     * 字面量执行某个函数
     *
     * @param clazzName  全类名
     * @param methodName 函数名称
     * @param types      函数参数类型表
     * @param args       执行参数
     * @return 执行结果
     */
    Object execute(String clazzName, String methodName, String[] types, List<Object> args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;


    Object execute(Class<?> clazz, Method method, List<Object> args) throws InstantiationException, IllegalAccessException, InvocationTargetException;
}
