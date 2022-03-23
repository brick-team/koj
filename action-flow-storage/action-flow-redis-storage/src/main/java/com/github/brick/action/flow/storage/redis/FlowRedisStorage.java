package com.github.brick.action.flow.storage.redis;

import com.github.brick.action.flow.method.entity.FlowEntity;
import com.github.brick.action.flow.method.entity.FlowsEntity;
import com.github.brick.storage.api.FlowStorage;

import java.util.List;

public class FlowRedisStorage implements FlowStorage {
    @Override
    public void save(String uid, FlowsEntity flows) {

    }

    @Override
    public List<FlowEntity> list(String uid) {
        return null;
    }
}
