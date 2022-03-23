package com.github.brick.storage.api;

import com.github.brick.action.flow.method.entity.ParamEntity;
import com.github.brick.action.flow.method.entity.ParamsEntity;

import java.util.List;

public interface ParamStorage {
    List<ParamEntity> list(String uid);

    void save(String uid, ParamsEntity params);
}
