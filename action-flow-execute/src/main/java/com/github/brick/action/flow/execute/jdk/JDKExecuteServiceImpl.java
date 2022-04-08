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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JDKExecuteServiceImpl implements JDKExecuteService {
    @Override
    public Object execute(String className, String method, String[] methodParamType, Object... args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?>[] methodParamType1 = new Class<?>[methodParamType.length];

        for (int i = 0; i < methodParamType.length; i++) {
            String s = methodParamType[i];
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            Class<?> aClass = contextClassLoader.loadClass(s);
            methodParamType1[i] = aClass;
        }
        return execute(className, method, methodParamType1, args);

    }

    @Override
    public Object execute(String className, String method, Class<?>[] methodParamType, Object... args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Class<?> aClass = contextClassLoader.loadClass(className);
        return execute(aClass, method, methodParamType, args);
    }

    @Override
    public Object execute(Class<?> clazz, String method, Class<?>[] methodParamType, Object... args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method declaredMethod = clazz.getDeclaredMethod(method, methodParamType);
        Constructor<?> constructor = clazz.getConstructor();
        Object o = constructor.newInstance();
        return execute(o, declaredMethod, args);
    }


    @Override
    public Object execute(Object object, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(object, args);
    }
}
