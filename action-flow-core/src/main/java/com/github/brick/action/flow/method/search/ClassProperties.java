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

package com.github.brick.action.flow.method.search;

import com.github.brick.action.flow.model.jdk.JavaClassParamEntity;
import com.sun.xml.internal.ws.api.pipe.ServerTubeAssemblerContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Zen Huifer
 */
public class ClassProperties {

	static Class<?>[] defaultClazz = new Class[] { long.class, short.class, double.class,
			float.class, int.class, char.class, boolean.class, byte.class, long[].class,
			short[].class, double[].class, float[].class, int[].class, char[].class,
			boolean[].class, byte[].class, Long.class, Short.class, Double.class,
			Float.class, Integer.class, Character.class, Boolean.class, Byte.class,
			Long[].class, Short[].class, Double[].class, Float[].class, Integer[].class,
			Character[].class, Boolean[].class, Byte[].class, String.class,
			String[].class, Iterable.class, Map.class, Collection.class, List.class,
			Set.class, Queue.class, Deque.class };

	static List<Class<?>> DEFAULT_CLAZZ = Arrays.asList(defaultClazz);

	/**
	 * 计算一个类的所有字段信息
	 * key: 类全路径
	 * value: 字段信息
	 */
	public static Map<String, List<JavaClassParamEntity>> handler(Class<?> aClass) {
		Map<String, List<JavaClassParamEntity>> clazzMap = new HashMap<>(32);
		return handlerOne(aClass, clazzMap);
	}

	public static Map<String, List<JavaClassParamEntity>> handlerOne(Class<?> aClass,
			Map<String, List<JavaClassParamEntity>> clazzMap) {

		Result result = getJavaClassParamEntities(aClass);
		clazzMap.put(aClass.getName(), result.classParamEntities);

		for (Class<?> aClass1 : result.toWork) {
			clazzMap.put(aClass1.getName(),
					getJavaClassParamEntities(aClass1).classParamEntities);
		}

		return clazzMap;
	}

	private static Result getJavaClassParamEntities(Class<?> aClass) {
		Result result = new Result();
		List<JavaClassParamEntity> list = new ArrayList<>();
		Field[] declaredFields = aClass.getDeclaredFields();

		List<Class<?>> toWork = new ArrayList<>(8);
		for (Field declaredField : declaredFields) {
			JavaClassParamEntity javaClassParamEntity = new JavaClassParamEntity();
			String name = declaredField.getName();
			javaClassParamEntity.setName(name);

			Class<?> type = declaredField.getType();

			javaClassParamEntity.setClazz(type.getName());
			if (type.isArray()) {
				Class<?> componentType = type.getComponentType();
				JavaClassParamEntity.ArrayOf classInfo = new JavaClassParamEntity.ArrayOf();
				classInfo.setRelayType(componentType.getName());
				javaClassParamEntity.setClassInfo(classInfo);
			}

			if (type.isInterface()) {
				Type genericType = declaredField.getGenericType();
				if (Iterable.class.isAssignableFrom(type)) {
					if (genericType instanceof ParameterizedType) {
						ParameterizedType pt = (ParameterizedType) genericType;
						//得到泛型里的class类型对象
						Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
						JavaClassParamEntity.ListOf classInfo = new JavaClassParamEntity.ListOf();
						classInfo.setRelayType(genericClazz.getName());

						javaClassParamEntity.setClassInfo(classInfo);
					}
				}
				if (Map.class.isAssignableFrom(type)) {
					if (genericType instanceof ParameterizedType) {
						ParameterizedType pt = (ParameterizedType) genericType;
						//得到泛型里的class类型对象
						Class<?> keyClazz = (Class<?>) pt.getActualTypeArguments()[0];
						Class<?> valueClazz = (Class<?>) pt.getActualTypeArguments()[1];

						JavaClassParamEntity.MapOf classInfo = new JavaClassParamEntity.MapOf();
						classInfo.setKeyType(keyClazz.getName());
						classInfo.setValueType(valueClazz.getName());
						javaClassParamEntity.setClassInfo(classInfo);
					}
				}

			}
			if (!inDefaultClass(type) && !type.isEnum()) {
				toWork.add(type);
			}

			list.add(javaClassParamEntity);
		}
		result.classParamEntities = list;
		result.toWork = toWork;
		return result;
	}

	public static boolean inDefaultClass(Class<?> type) {
		if (type.equals(Object.class)) {
			return true;
		}
		if (type.equals(Object[].class)) {
			return true;
		}
		for (Class<?> clazz : DEFAULT_CLAZZ) {
			if (clazz.isAssignableFrom(type)) {
				return true;
			}
		}
		return false;
	}

	private static class Result {
		List<JavaClassParamEntity> classParamEntities;
		List<Class<?>> toWork;

	}
}
