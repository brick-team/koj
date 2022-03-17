package com.github.brick.action.flow.sample;

import com.github.brick.action.flow.method.execute.FlowExecute;
import com.github.brick.action.flow.method.enums.FLowModel;
import com.github.brick.action.flow.method.execute.FlowExecuteImpl;

public class ActionFlowDemo {

    public static void main(String[] args) throws Exception {
        FlowExecute flowExecute = new FlowExecuteImpl();
        flowExecute.execute("paramFromAction.xml", "1", FLowModel.XML);
    }
}
