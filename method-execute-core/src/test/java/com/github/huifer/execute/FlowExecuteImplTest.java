package com.github.huifer.execute;

import com.alibaba.fastjson.JSON;
import com.github.huifer.ClassAction;
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
    @Test
    public void execute2() throws Exception {
         flowExecute.execute("f2.xml", "1");
    }

    public static void main(String[] args) {
        String s = "{\"name\":\"b\"}";
        ClassAction.Mc mc = JSON.parseObject(s, ClassAction.Mc.class);
        System.out.println();
    }
}