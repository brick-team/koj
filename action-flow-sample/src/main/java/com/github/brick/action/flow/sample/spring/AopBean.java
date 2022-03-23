package com.github.brick.action.flow.sample.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
public class AopBean {
    private static final Logger logger = LoggerFactory.getLogger(AopBean.class);

    public void hh() {
        logger.info("hello");
    }
}
