package com.github.brick.action.flow.storage.memory.nv;

import com.github.brick.action.flow.model.execute.ResultExecuteEntity;
import com.github.brick.action.flow.storage.api.child.ResultEntityStorage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResultEntityMemoryStorage implements ResultEntityStorage {
    static Map<Serializable, ResultExecuteEntity> map = new HashMap<>(128);

    @Override
    public void save(ResultExecuteEntity result) {
        map.put(result.getId(), result);
    }

    @Override
    public ResultExecuteEntity findById(Serializable id) {
        return map.get(id);
    }
}
