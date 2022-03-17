package com.github.brick.action.flow.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamAction {
    private static final Logger logger = LoggerFactory.getLogger(ParamAction.class);


    public void ac(Object data) {
        logger.info("data = {}", data);
    }
}
