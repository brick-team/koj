package com.github.huifer.execute;

import org.junit.Test;

public class FlowExecuteImplTest {
    FlowExecute flowExecute = new FlowExecuteImpl();

    @Test
    public void execute() throws Exception {
        System.out.println(flowExecute.execute("fl.xml"));
    }
}