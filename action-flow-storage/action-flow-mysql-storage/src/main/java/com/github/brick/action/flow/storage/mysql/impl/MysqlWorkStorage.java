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

import com.github.brick.action.flow.method.entity.WorkEntity;
import com.github.brick.action.flow.storage.api.WorkStorage;
import com.github.brick.action.flow.storage.mysql.entity.AfWorkEntity;
import com.github.brick.action.flow.storage.mysql.repository.AfWorkEntityRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MysqlWorkStorage implements WorkStorage {
    @Autowired
    private AfWorkEntityRepository afWorkEntityRepository;

    @Override
    public String save(String type, String refId, List<String> thenWorkIds, List<String> catchWorkIds) {
        AfWorkEntity entity = new AfWorkEntity();
        entity.setType(type);
        entity.setRefId(refId);
        entity.setThens(StringUtils.join(thenWorkIds, ","));
        entity.setCatchs(StringUtils.join(catchWorkIds, ","));

        AfWorkEntity save = afWorkEntityRepository.save(entity);
        return save.getId();
    }

    public List<WorkEntity> findByIds(String works) {
        List<AfWorkEntity> allById = afWorkEntityRepository.findAllById(Arrays.stream(works.split(",")).collect(Collectors.toList()));
        List<WorkEntity> workEntities = new ArrayList<>();


        for (AfWorkEntity afWorkEntity : allById) {
            WorkEntity workEntity = new WorkEntity();
            workEntity.setType(afWorkEntity.getType());
            workEntity.setRefId(afWorkEntity.getRefId());


            workEntity.setThen(findByIds(afWorkEntity.getThens()));
            workEntity.setCatchs(findByIds(afWorkEntity.getCatchs()));
            workEntities.add(workEntity);

        }
        return workEntities;
    }
}
