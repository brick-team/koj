/*
 *    Copyright [2022] [brick-team]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.brick.action.flow.storage.mysql.impl;

import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.github.brick.action.flow.method.entity.api.ParamIn;
import com.github.brick.action.flow.storage.api.ApiParamStorage;
import com.github.brick.action.flow.storage.mysql.entity.AfApiParamEntity;
import com.github.brick.action.flow.storage.mysql.repository.AfApiParamEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class MysqlApiParamStorage implements ApiParamStorage {
    @Autowired(required = false)
    private AfApiParamEntityRepository afApiParamEntityRepository;

    @Override
    public Long save(Long apiId, Long pid, String in, String name, Boolean require) {
        AfApiParamEntity entity = new AfApiParamEntity();
        entity.setApiId(apiId);
        entity.setPid(pid);
        entity.setIn(in);
        entity.setName(name);
        entity.setRequire(require ? 1 : 0);

        AfApiParamEntity save = afApiParamEntityRepository.save(entity);
        return save.getId();
    }

    public List<ApiParamEntity> findByAppId(Long apiId) {

        List<ApiParamEntity> res = new ArrayList<>();

        // step1: 先查询所有
        List<AfApiParamEntity> allByApiId = this.afApiParamEntityRepository.findAllByApiId(apiId);

        // step2: 第一级别
        for (AfApiParamEntity afApiParamEntity : allByApiId) {
            ApiParamEntity apiParamEntity = new ApiParamEntity();
            if (afApiParamEntity.getPid() == null) {
                apiParamEntity.setId(afApiParamEntity.getId());
                String in = afApiParamEntity.getIn();
                if (in != null) {
                    apiParamEntity.setIn(ParamIn.valueOf(in));
                }
                apiParamEntity.setName(afApiParamEntity.getName());
                apiParamEntity.setRequire(afApiParamEntity.getRequire() == 1);
                res.add(apiParamEntity);
            }
        }


        for (ApiParamEntity re : res) {
            re.setParamEntities(handlerChild(allByApiId, re.getId()));
        }

        return res;
    }

    private List<ApiParamEntity> handlerChild(List<AfApiParamEntity> allByApiId, Long id) {
        List<ApiParamEntity> res = new ArrayList<>();
        for (AfApiParamEntity afApiParamEntity : allByApiId) {
            try {

                if (id.equals(afApiParamEntity.getPid())) {
                    ApiParamEntity apiParamEntity = new ApiParamEntity();
                    apiParamEntity.setId(afApiParamEntity.getId());
                    String in = afApiParamEntity.getIn();
                    if (in != null) {
                        apiParamEntity.setIn(ParamIn.valueOf(in));
                    }
                    apiParamEntity.setName(afApiParamEntity.getName());
                    apiParamEntity.setRequire(afApiParamEntity.getRequire() == 1);
                    res.add(apiParamEntity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (ApiParamEntity re : res) {
            re.setParamEntities(handlerChild((allByApiId), re.getId()));

        }
        if (res.size() == 0) {
            return res;
        }

        return res;
    }

}
