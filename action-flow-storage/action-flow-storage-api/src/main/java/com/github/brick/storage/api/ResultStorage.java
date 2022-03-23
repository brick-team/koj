package com.github.brick.storage.api;

import com.github.brick.action.flow.method.entity.ResultEntity;

import java.util.List;

public interface ResultStorage {
    List<ResultEntity.Key> list(String uid);

    void save(String uid, ResultEntity result);
}
