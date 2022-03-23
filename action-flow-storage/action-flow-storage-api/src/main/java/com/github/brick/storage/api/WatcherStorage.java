package com.github.brick.storage.api;

import com.github.brick.action.flow.method.entity.WatcherEntity;
import com.github.brick.action.flow.method.entity.WatchersEntity;

import java.util.List;

public interface WatcherStorage {
    List<WatcherEntity> list(String uid);

    void save(String uid, WatchersEntity watchers);
}
