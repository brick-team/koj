package com.github.huifer.execute;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowExecuteImplTest {
    private static final Logger logger = LoggerFactory.getLogger(FlowExecuteImplTest.class);
    FlowExecute flowExecute = new FlowExecuteImpl();

    @Test
    public void execute() throws Exception {
        Object execute = flowExecute.execute("fl.xml", "1");
        logger.info("fl执行结果={}", execute);
    }
}