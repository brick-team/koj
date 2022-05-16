package com.github.brick.action.flow.storage.memory.nv;

import com.github.brick.action.flow.model.res.Page;
import com.github.brick.action.flow.model.swagger.ApiEntity;
import com.github.brick.action.flow.storage.api.child.ApiEntityStorage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApiEntityMemoryStorage implements ApiEntityStorage {
    private final List<ApiEntity> storage = new ArrayList<>(128);

    @Override
    public boolean save(List<ApiEntity> apiEntities) {
        // todo: 验证： id 重复
        return storage.addAll(apiEntities);
    }

    @Override
    public boolean save(ApiEntity api) {
        // todo: 验证： id 重复
        return storage.add(api);
    }

    @Override
    public ApiEntity findById(Serializable serializable) {
        ApiEntity res = new ApiEntity();
        for (ApiEntity apiEntity : storage) {
            if (apiEntity.getId().equals(serializable)) {
                res = apiEntity;
                break;
            }
        }
        return res;
    }

    @Override
    public Page<ApiEntity> page(String apiPath, int page, int size) {
        List<ApiEntity> collect = storage.stream().skip((page - 1) * size).limit(size).
                collect(Collectors.toList());
        Page<ApiEntity> apiEntityPage = new Page<>();
        apiEntityPage.setPage(page);
        apiEntityPage.setSize(size);

        int total = storage.size();
        int pageSum = total % size == 0 ? total / size : total / size + 1;
        apiEntityPage.setPageSum(pageSum);

        apiEntityPage.setTotal(total);
        apiEntityPage.setList(collect);
        return apiEntityPage;
    }
}
