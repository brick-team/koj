package com.github.brick.execute;

import com.github.brick.enums.FLowModel;

public interface FlowExecute {
    Object execute(String file, String flowId, FLowModel model) throws Exception;
}
