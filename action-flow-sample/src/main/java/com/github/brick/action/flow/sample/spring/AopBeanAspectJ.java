package com.github.brick.action.flow.sample.spring;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopBeanAspectJ {
    private static final Logger logger = LoggerFactory.getLogger(AopBeanAspectJ.class);

    @Before("execution(* com.github.brick.action.flow.sample.spring.AopBean.hh(..))")
    public void aspect() {
        logger.info("aspect");
    }
}
