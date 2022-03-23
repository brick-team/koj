package com.github.brick.action.flow.storage.redis;

import com.github.brick.action.flow.method.entity.WatcherEntity;
import com.github.brick.action.flow.method.entity.WatchersEntity;
import com.github.brick.storage.api.WatcherStorage;

import java.util.List;

public class WatcherRedisStorage implements WatcherStorage {
    @Override
    public List<WatcherEntity> list(String uid) {
        return null;
    }

    @Override
    public void save(String uid, WatchersEntity watchers) {

    }
}
