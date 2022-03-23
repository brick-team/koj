package com.github.brick.action.flow.spring.search;


import cn.hutool.core.util.ReflectUtil;
import org.junit.Test;

import java.lang.reflect.Method;

public class SpringBeanSearchTest {

    @Test
    public void test() {
        Method[] methods = ReflectUtil.getMethods(SpringBeanSearch.class);
        System.out.println();
    }
}