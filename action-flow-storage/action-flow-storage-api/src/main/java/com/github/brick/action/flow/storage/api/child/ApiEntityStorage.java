package com.github.brick.action.flow.storage.api.child;

import com.github.brick.action.flow.model.res.Page;
import com.github.brick.action.flow.model.swagger.ApiEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 用于将swagger文件存储到action相关表
 */
public interface ApiEntityStorage {
    boolean save(List<ApiEntity> apiEntities);
    boolean save(ApiEntity api);

    ApiEntity findById(Serializable serializable);

    Page<ApiEntity> page(String apiPath, int page, int size);
}
