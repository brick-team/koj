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

package com.github.brick.action.flow.storage.memory.nv;

import com.github.brick.action.flow.model.execute.FlowExecuteEntity;
import com.github.brick.action.flow.model.res.Page;
import com.github.brick.action.flow.storage.api.FlowExecuteEntityStorage;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class FlowExecuteEntityMemoryStorage implements FlowExecuteEntityStorage {
    Map<String, Map<Serializable, FlowExecuteEntity>> map = new HashMap<>(32);

    @Override
    public void save(String fileName, List<FlowExecuteEntity> flows) {
        Map<Serializable, FlowExecuteEntity> collect = flows.stream().collect(Collectors.toMap(FlowExecuteEntity::getId, s -> s));
        map.put(fileName, collect);
    }

    @Override
    public FlowExecuteEntity getFlow(String fileName, Serializable flowId) {
        Map<Serializable, FlowExecuteEntity> serializableFlowExecuteEntityMap = map.get(fileName);
        if (serializableFlowExecuteEntityMap == null) {
            throw new IllegalArgumentException("当前文件名称未解析,文件名称 = " + fileName);
        }

        FlowExecuteEntity flowExecuteEntity = serializableFlowExecuteEntityMap.get(flowId);
        if (flowExecuteEntity == null) {
            throw new IllegalArgumentException("当前文件名称 " + fileName + "中不存在 flowId 为" + flowId + "的数据");
        }
        return flowExecuteEntity;
    }

    @Override
    public Page page(int size, int page) {
        Page<FlowExecuteEntity> objectPage = new Page<>();
        List<FlowExecuteEntity> res = new ArrayList<>();
        Collection<Map<Serializable, FlowExecuteEntity>> values = map.values();
        for (Map<Serializable, FlowExecuteEntity> value : values) {
            Collection<FlowExecuteEntity> values1 = value.values();
            res.addAll(values1);
        }
        objectPage.setList(res);

        return objectPage;
    }
}
