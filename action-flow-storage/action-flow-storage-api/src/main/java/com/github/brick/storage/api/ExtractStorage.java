package com.github.brick.storage.api;

import com.github.brick.action.flow.method.entity.ExtractEntity;
import com.github.brick.action.flow.method.entity.ExtractsEntity;

import java.util.List;

public interface ExtractStorage {

    List<ExtractEntity> list(String uid);

    void save(String uid, ExtractsEntity extracts);
}
