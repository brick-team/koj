package com.github.brick.storage.api;

import com.github.brick.action.flow.method.entity.ActionEntity;
import com.github.brick.action.flow.method.entity.ActionsEntity;

import java.util.List;

public interface ActionStorage {

    List<ActionEntity> list(String uid);

    void save(String uid, ActionsEntity actions);
}
