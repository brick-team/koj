package com.github.brick.action.flow.storage.api.child;

import com.github.brick.action.flow.model.execute.ResultExecuteEntity;

import java.io.Serializable;

public interface ResultEntityStorage {
    void save(ResultExecuteEntity result);

    ResultExecuteEntity findById(Serializable id);
}
