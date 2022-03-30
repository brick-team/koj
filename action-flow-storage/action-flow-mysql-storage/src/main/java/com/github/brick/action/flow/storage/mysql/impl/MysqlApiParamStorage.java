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

import com.github.brick.action.flow.storage.api.ApiParamStorage;
import com.github.brick.action.flow.storage.mysql.entity.AfApiParamEntity;
import com.github.brick.action.flow.storage.mysql.repository.AfApiParamEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MysqlApiParamStorage implements ApiParamStorage {
    @Autowired
    private AfApiParamEntityRepository afApiParamEntityRepository;

    @Override
    public Long save(String apiId, Long pid, String in, String name, String require) {
        AfApiParamEntity entity = new AfApiParamEntity();
        entity.setApiId(apiId);
        entity.setPid(pid);
        entity.setIn(in);
        entity.setName(name);
        entity.setRequire(require);

        AfApiParamEntity save = afApiParamEntityRepository.save(entity);
        return save.getId();
    }
}
