package com.github.brick.action.flow.web.common.ctr.impl;

import com.github.brick.action.flow.method.factory.storage.StorageFactory;
import com.github.brick.action.flow.model.enums.ExtractModel;
import com.github.brick.action.flow.model.enums.StorageType;
import com.github.brick.action.flow.model.execute.ResultExecuteEntity;
import com.github.brick.action.flow.storage.api.child.ResultEntityStorage;
import com.github.brick.action.flow.web.common.ctr.ResultCtr;

import java.io.Serializable;

public class ResultCtrImpl implements ResultCtr {

    private final StorageType storageType;

    private final ResultEntityStorage storage;

    public ResultCtrImpl(StorageType storageType) {
        this.storageType = storageType;
        storage = StorageFactory.factory(storageType, ResultEntityStorage.class);

    }

    @Override
    public void save(ResultExecuteEntity result) {
        storage.save(result);
    }

    @Override
    public Object execute(Serializable id, String jsonData, ExtractModel extractModel) {
        ResultExecuteEntity entity = storage.findById(id);
        // TODO: 2022/4/26 提取器
        return null;
    }

}
