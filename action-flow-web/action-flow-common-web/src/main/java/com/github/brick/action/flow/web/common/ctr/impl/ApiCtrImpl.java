package com.github.brick.action.flow.web.common.ctr.impl;

import com.github.brick.action.flow.method.factory.storage.StorageFactory;
import com.github.brick.action.flow.model.enums.StorageType;
import com.github.brick.action.flow.model.swagger.ApiEntity;
import com.github.brick.action.flow.storage.api.child.ApiEntityStorage;
import com.github.brick.action.flow.web.common.ctr.ApiCtr;

import java.io.Serializable;
import java.util.Map;

public class ApiCtrImpl implements ApiCtr {

    private final StorageType storageType;

    private final ApiEntityStorage apiEntityStorage;

    public ApiCtrImpl(StorageType storageType) {
        this.storageType = storageType;
        apiEntityStorage = StorageFactory.factory(storageType, ApiEntityStorage.class);

    }

    @Override
    public void save(ApiEntity api) {
        apiEntityStorage.save(api);
    }

    @Override
    public ApiEntity findById(Serializable id) {
        return apiEntityStorage.findById(id);
    }

    @Override
    public Object execute(Serializable id, Map<String, Object> jsonData) {
        ApiEntity byId = apiEntityStorage.findById(id);

        return null;
    }
}
