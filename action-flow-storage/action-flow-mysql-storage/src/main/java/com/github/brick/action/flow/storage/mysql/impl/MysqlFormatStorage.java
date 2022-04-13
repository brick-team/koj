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

import com.github.brick.action.flow.storage.api.FormatStorage;
import com.github.brick.action.flow.storage.mysql.entity.AfFormatEntity;
import com.github.brick.action.flow.storage.mysql.repository.AfFormatEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MysqlFormatStorage implements FormatStorage {
    @Autowired(required = false)
    private AfFormatEntityRepository formatEntityRepository;

    @Override
    public Long save(String classStr) {
        AfFormatEntity entity = new AfFormatEntity();
        entity.setClassStr(classStr);
        AfFormatEntity save = formatEntityRepository.save(entity);
        return save.getId();
    }

    public String findById(Long formatId) {
        Optional<AfFormatEntity> byId = formatEntityRepository.findById(formatId);
        if (byId.isPresent()) {
            return byId.get().getClassStr();
        }

        return null;
    }
}
