package com.github.brick.action.flow.sample;

import com.github.brick.action.flow.method.enums.FLowModel;
import com.github.brick.action.flow.method.execute.FlowExecute;
import com.github.brick.action.flow.method.execute.FlowExecuteImpl;

public class AsyncActionFlowDemo {
    public static void main(String[] args) throws Exception {
        // 两个action异步处理，最终组装数据
        long l = System.currentTimeMillis();
        FlowExecute flowExecute = new FlowExecuteImpl();
        Object execute = flowExecute.execute("async.xml", "1", FLowModel.XML);
        System.out.println(execute);
        System.out.println(System.currentTimeMillis() - l);
    }
}
