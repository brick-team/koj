package com.github.brick.storage.api;

import com.github.brick.action.flow.method.entity.FlowEntity;
import com.github.brick.action.flow.method.entity.FlowsEntity;

import java.util.List;

public interface FlowStorage {
    List<FlowEntity> list(String uid);

    void save(String uid, FlowsEntity flows);
}
