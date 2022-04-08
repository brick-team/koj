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

import com.github.brick.action.flow.model.execute.ResultExecuteEntity;
import com.github.brick.action.flow.storage.api.nv.ResultExecuteEntityStorage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultExecuteEntityMemoryStorage implements ResultExecuteEntityStorage {
    Map<String, Map<Serializable, ResultExecuteEntity>> map = new HashMap<>(32);

    @Override
    public ResultExecuteEntity getResult(String fileName, Serializable flowId) {
        Map<Serializable, ResultExecuteEntity> serializableResultExecuteEntityMap = map.get(fileName);
        if (serializableResultExecuteEntityMap == null) {
            throw new IllegalArgumentException("当前文件名称未解析,文件名称 = " + fileName);
        }
        ResultExecuteEntity resultExecuteEntity = serializableResultExecuteEntityMap.get(flowId);
        if (resultExecuteEntity == null) {
            throw new IllegalArgumentException("当前文件名称 " + fileName + "中不存在 flowId 为" + flowId + "的 result 数据");
        }
        return resultExecuteEntity;
    }

    @Override
    public void save(String fileName, List<ResultExecuteEntity> results) {
        Map<Serializable, ResultExecuteEntity> collect = results.stream().collect(Collectors.toMap(ResultExecuteEntity::getFlowId, s -> s));
        this.map.put(fileName, collect);
    }
}
