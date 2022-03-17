package com.github.brick.action.flow;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintAction {
    private static final Logger logger = LoggerFactory.getLogger(PrintAction.class);

    public void print(Object o) {
        if (logger.isInfoEnabled()) {
            logger.info("print,o = {}", JSON.toJSONString(o));
        }

    }
}
