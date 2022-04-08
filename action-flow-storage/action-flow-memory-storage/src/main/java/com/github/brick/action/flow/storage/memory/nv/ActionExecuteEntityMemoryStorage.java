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

import com.github.brick.action.flow.model.execute.ActionExecuteEntity;
import com.github.brick.action.flow.storage.api.nv.ActionExecuteEntityStorage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActionExecuteEntityMemoryStorage implements ActionExecuteEntityStorage {
    Map<String, Map<Serializable, ActionExecuteEntity>> map = new HashMap<>(32);

    @Override
    public void save(String fileName, List<ActionExecuteEntity> actions) {
        Map<Serializable, ActionExecuteEntity> collect = actions.stream().collect(Collectors.toMap(ActionExecuteEntity::getId, s -> s));
        map.put(fileName, collect);
    }

    @Override
    public ActionExecuteEntity getAction(String fileName, Serializable refId) {
        Map<Serializable, ActionExecuteEntity> serializableActionExecuteEntityMap = map.get(fileName);
        if (serializableActionExecuteEntityMap == null) {
            throw new IllegalArgumentException("当前文件名称未解析,文件名称 = " + fileName);
        }

        ActionExecuteEntity actionExecuteEntity = serializableActionExecuteEntityMap.get(refId);
        if (actionExecuteEntity == null) {
            throw new IllegalArgumentException("当前文件名称 " + fileName + "中不存在 action_id 为" + refId + "的数据");
        }

        return actionExecuteEntity;
    }
}
