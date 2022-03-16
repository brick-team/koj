package com.github.brick.sample;

import com.github.brick.enums.FLowModel;
import com.github.brick.execute.FlowExecute;
import com.github.brick.execute.FlowExecuteImpl;

public class ActionFlowDemo {

    public static void main(String[] args) throws Exception {
        FlowExecute flowExecute = new FlowExecuteImpl();
        flowExecute.execute("base.xml", "1", FLowModel.XML);
    }
}
