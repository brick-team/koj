package com.github.brick.action.flow.storage.redis;

import com.github.brick.action.flow.method.entity.ResultEntity;
import com.github.brick.storage.api.ResultStorage;

import java.util.List;

public class ResultResultStorage implements ResultStorage {
    @Override
    public List<ResultEntity.Key> list(String uid) {
        return null;
    }

    @Override
    public void save(String uid, ResultEntity result) {

    }
}
