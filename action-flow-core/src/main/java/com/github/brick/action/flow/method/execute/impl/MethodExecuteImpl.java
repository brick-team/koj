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

package com.github.brick.action.flow.method.execute.impl;

import com.github.brick.action.flow.method.execute.MethodExecute;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MethodExecuteImpl implements MethodExecute {

    @Override
    public Object execute(String clazzName, String methodName, String[] types, List<Object> args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Class<?> aClass = contextClassLoader.loadClass(clazzName);

        Method[] declaredMethods = aClass.getDeclaredMethods();

        Method searchResult = null;

        for (Method declaredMethod : declaredMethods) {
            Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
            boolean f = true;

            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                if (!parameterType.getName().equals(types[i])) {
                    f = false;
                }
            }

            if (f) {
                searchResult = declaredMethod;
                break;
            }
        }
        if (searchResult == null) {
            throw new IllegalArgumentException("需要执行的方法未找到");
        }

        return execute(aClass, searchResult, args);
    }

    @Override
    public Object execute(Class<?> clazz, Method method, List<Object> args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Object[] objects = new Object[args.size()];
        for (int i = 0; i < args.size(); i++) {
            objects[i] = args.get(i);
        }

        return method.invoke(clazz.newInstance(), objects);
    }
}
