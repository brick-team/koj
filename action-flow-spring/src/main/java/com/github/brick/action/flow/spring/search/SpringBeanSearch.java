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

import cn.hutool.core.util.ReflectUtil;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SpringBeanSearch implements ApplicationContextAware, BeanPostProcessor {
    private final List<SearchResult> searchResultAll = new ArrayList<>();
    Map<Class, Object> classMapObj = new HashMap<>();
    private ApplicationContext applicationContext;
    private ConfigurableApplicationContext cac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.cac = (ConfigurableApplicationContext) applicationContext;
        }
    }

    public List<SearchResult> searchAll() {
        if (searchResultAll.isEmpty()) {
            String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

            if (cac != null) {
                ConfigurableListableBeanFactory beanFactory = cac.getBeanFactory();


                for (String beanDefinitionName : beanDefinitionNames) {
                    Object bean = applicationContext.getBean(beanDefinitionName);
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);

                    SearchResult searchResult = new SearchResult();
                    searchResult.setBean(bean);
                    searchResult.setBeanName(beanDefinitionName);
                    searchResult.setBeanClass(beanDefinition.getBeanClassName());
                    Method[] methods = null;
                    // 判断是否是 aop 代理，如果是则取最小函数范围
                    if (AopUtils.isAopProxy(bean)) {
                        Class<?> targetClass = AopUtils.getTargetClass(bean);
                        methods = ReflectUtil.getMethods(targetClass);
                    } else {
                        methods = ReflectUtil.getMethods(bean.getClass());
                    }

                    // 只保留 public 修饰
                    List<Method> collect = Arrays.stream(methods)
                            .filter(method -> {
                                return (method.getModifiers() & 1) == 1;
                            })
                            .collect(Collectors.toList());
                    searchResult.setMethods(collect);
                    Map<Integer, String> collect1 = collect.stream().collect(Collectors.toMap(collect::indexOf, Method::getName));
                    searchResult.setIndexMethod(collect1);

                    searchResultAll.add(searchResult);
                }
            } else {
                for (String beanDefinitionName : beanDefinitionNames) {
                    Object bean = applicationContext.getBean(beanDefinitionName);

                    SearchResult searchResult = new SearchResult();
                    searchResult.setBean(bean);
                    searchResult.setBeanName(beanDefinitionName);


                    Method[] methods = ReflectUtil.getMethods(bean.getClass());
                    List<Method> collect = Arrays.stream(methods)
                            .filter(method -> {
                                return (method.getModifiers() & 1) == 1;
                            })
                            .collect(Collectors.toList());
                    searchResult.setMethods(collect);

                    Map<Integer, String> collect1 = collect.stream().collect(Collectors.toMap(collect::indexOf, Method::getName));
                    searchResult.setIndexMethod(collect1);

                    searchResultAll.add(searchResult);
                }
            }

        }

        return searchResultAll;
    }
}
