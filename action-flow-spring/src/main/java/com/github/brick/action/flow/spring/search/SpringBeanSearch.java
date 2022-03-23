package com.github.brick.action.flow.spring.search;

import cn.hutool.core.util.ReflectUtil;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpringBeanSearch implements ApplicationContextAware, BeanPostProcessor {
    private final List<SearchResult> searchResultAll = new ArrayList<>();
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

                    Method[] methods = ReflectUtil.getMethods(bean.getClass());
                    List<Method> collect = Arrays.stream(methods).collect(Collectors.toList());
                    searchResult.setMethods(collect);

                    searchResultAll.add(searchResult);
                }
            } else {
                for (String beanDefinitionName : beanDefinitionNames) {
                    Object bean = applicationContext.getBean(beanDefinitionName);

                    SearchResult searchResult = new SearchResult();
                    searchResult.setBean(bean);
                    searchResult.setBeanName(beanDefinitionName);


                    Method[] methods = ReflectUtil.getMethods(bean.getClass());
                    List<Method> collect = Arrays.stream(methods).collect(Collectors.toList());
                    searchResult.setMethods(collect);
                    searchResultAll.add(searchResult);
                }
            }

        }

        return searchResultAll;
    }
}
