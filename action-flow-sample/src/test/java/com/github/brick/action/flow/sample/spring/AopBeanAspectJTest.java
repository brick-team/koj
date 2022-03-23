package com.github.brick.action.flow.sample.spring;

import com.github.brick.action.flow.spring.search.SearchResult;
import com.github.brick.action.flow.spring.search.SpringBeanSearch;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class AopBeanAspectJTest {

    @org.junit.Test
    public void aspect() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        AopBean bean = ctx.getBean(AopBean.class);
        bean.hh();

        SpringBeanSearch bean1 = ctx.getBean(SpringBeanSearch.class);
        List<SearchResult> searchResults = bean1.searchAll();

        System.out.println();

    }
}