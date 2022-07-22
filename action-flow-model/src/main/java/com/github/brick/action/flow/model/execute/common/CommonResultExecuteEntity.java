package com.github.brick.action.flow.model.execute.common;

import com.github.brick.action.flow.model.execute.ResultExecuteEntity;

import java.io.Serializable;

public class CommonResultExecuteEntity extends ResultExecuteEntity {
    private String id;

    @Override
    public Serializable getId() {
        return id;
    }

    @Override
    public Serializable getFlowId() {
        return null;
    }
}
