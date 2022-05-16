package com.github.brick.action.flow.storage.mysql.child;

import com.github.brick.action.flow.model.res.Page;
import com.github.brick.action.flow.model.swagger.ApiEntity;
import com.github.brick.action.flow.storage.api.child.ApiEntityStorage;
import com.github.brick.action.flow.storage.mysql.mapper.ActionMapper;
import com.github.brick.action.flow.storage.mysql.util.MybatisUtil;

import java.io.Serializable;
import java.util.List;

public class ApiEntityMysqlStorage implements ApiEntityStorage {
    @Override
    public boolean save(List<ApiEntity> apiEntities) {
        return false;
    }

    @Override
    public boolean save(ApiEntity api) {





        return false;
    }

    @Override
    public ApiEntity findById(Serializable serializable) {
        return null;
    }

    @Override
    public Page<ApiEntity> page(String apiPath, int page, int size) {
        return null;
    }
}
