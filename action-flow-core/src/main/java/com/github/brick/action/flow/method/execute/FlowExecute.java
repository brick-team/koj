package com.github.brick.action.flow.method.execute;

import com.github.brick.action.flow.method.enums.FLowModel;

public interface FlowExecute {
    Object execute(String file, String flowId, FLowModel model) throws Exception;
}
