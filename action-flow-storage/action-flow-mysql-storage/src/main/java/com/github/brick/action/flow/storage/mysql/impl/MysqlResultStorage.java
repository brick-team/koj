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

import com.github.brick.action.flow.storage.api.ResultStorage;
import com.github.brick.action.flow.storage.mysql.entity.AfResultEntity;
import com.github.brick.action.flow.storage.mysql.repository.AfResultEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MysqlResultStorage implements ResultStorage {

    @Autowired(required = false)

    private AfResultEntityRepository afResultEntityRepository;

    @Override
    public Long save(Long flowId, String name, String exId) {
        AfResultEntity entity = new AfResultEntity();
        entity.setName(name);
        entity.setExId(exId);
        entity.setFlowId(flowId);
        AfResultEntity save = afResultEntityRepository.save(entity);
        return save.getId();
    }
}
