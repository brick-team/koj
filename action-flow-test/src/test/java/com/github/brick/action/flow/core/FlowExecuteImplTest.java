package com.github.brick.action.flow.core;

import com.github.brick.action.flow.method.enums.FLowModel;
import com.github.brick.action.flow.method.execute.FlowExecute;
import com.github.brick.action.flow.method.execute.FlowExecuteImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowExecuteImplTest {
    private static final Logger logger = LoggerFactory.getLogger(FlowExecuteImplTest.class);
    FlowExecute flowExecute = new FlowExecuteImpl();


    @Test
    public void execute() throws Exception {
        Object execute = flowExecute.execute("fl.xml", "1", FLowModel.XML);
        logger.info("fl执行结果={}", execute);
    }

    @Test
    public void execute2() throws Exception {
        flowExecute.execute("f2.xml", "1", FLowModel.XML);
    }
}