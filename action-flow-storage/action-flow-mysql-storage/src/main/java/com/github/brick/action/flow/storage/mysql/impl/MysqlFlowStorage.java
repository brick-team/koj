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

import com.github.brick.action.flow.method.entity.FlowEntity;
import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.github.brick.action.flow.storage.api.FlowStorage;
import com.github.brick.action.flow.storage.mysql.entity.AfApiParamExEntity;
import com.github.brick.action.flow.storage.mysql.entity.AfFlowEntity;
import com.github.brick.action.flow.storage.mysql.repository.AfApiParamExEntityRepository;
import com.github.brick.action.flow.storage.mysql.repository.AfFlowEntityRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MysqlFlowStorage implements FlowStorage {
    @Autowired
    private AfFlowEntityRepository afFlowEntityRepository;
    @Autowired
    private AfApiParamExEntityRepository afApiParamExEntityRepository;
    @Autowired
    private MysqlWorkStorage workStorage;

    @Override
    public Long save(String name, List<Long> workIds) {
        AfFlowEntity entity = new AfFlowEntity();
        entity.setName(name);
        entity.setWorks(StringUtils.join(workIds, ","));
        AfFlowEntity save = afFlowEntityRepository.save(entity);
        return save.getId();
    }

    public void saveForApi(String name, List<Long> workIds, List<ApiParamEntity> list) {
        Long save = save(name, workIds);

        for (ApiParamEntity apiParamEntity : list) {
            AfApiParamExEntity entity = new AfApiParamExEntity();
            entity.setParamGroup(apiParamEntity.getParamGroup());
            entity.setEx(apiParamEntity.getEx());
            entity.setExId(apiParamEntity.getExId());
            entity.setEl(apiParamEntity.getEl());
            entity.setFlowId(save);
            entity.setApiParamId(apiParamEntity.getId());
            afApiParamExEntityRepository.save(entity);
        }
    }

    @Override
    public FlowEntity findById(Long flowId) {
        Optional<AfFlowEntity> byId = this.afFlowEntityRepository.findById(flowId);
        if (byId.isPresent()) {

            AfFlowEntity afFlowEntity = byId.get();
            String works = afFlowEntity.getWorks();


            FlowEntity flow = new FlowEntity();
            flow.setId(flowId.toString());
// todo: workEntity 计算
            return flow;
        }
        else {
            return null;
        }
    }
}
