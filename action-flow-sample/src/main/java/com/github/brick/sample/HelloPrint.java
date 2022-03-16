package com.github.brick.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloPrint {
    private static final Logger logger = LoggerFactory.getLogger(HelloPrint.class);

    public void hello() {
        logger.info("hello");
    }
}
