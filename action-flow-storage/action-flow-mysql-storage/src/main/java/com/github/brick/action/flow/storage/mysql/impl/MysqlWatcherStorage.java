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

import com.github.brick.action.flow.method.entity.WatcherEntity;
import com.github.brick.action.flow.storage.api.WatcherStorage;
import com.github.brick.action.flow.storage.mysql.entity.AfWatcherEntity;
import com.github.brick.action.flow.storage.mysql.entity.AfWatcherRsEntity;
import com.github.brick.action.flow.storage.mysql.repository.AfWatcherEntityRepository;
import com.github.brick.action.flow.storage.mysql.repository.AfWatcherRsEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MysqlWatcherStorage implements WatcherStorage {
    @Autowired
    private AfWatcherEntityRepository afWatcherEntityRepository;

    @Autowired
    private AfWatcherRsEntityRepository afWatcherRsEntityRepository;

    @Transactional
    @Override
    public Long save(Long exId, String condition, List<String> thenActions, List<String> catchActions, List<String> thenApis, List<String> catchApis) {
        AfWatcherEntity entity = new AfWatcherEntity();
        entity.setCondition(condition);
        entity.setExId(exId);

        AfWatcherEntity save = afWatcherEntityRepository.save(entity);
        Long id = save.getId();

        List<AfWatcherRsEntity> toSave = new ArrayList<>();
        for (String thenAction : thenActions) {

            AfWatcherRsEntity entity1 = new AfWatcherRsEntity();
            entity1.setType(1);
            entity1.setRefId(Long.valueOf(thenAction));
            entity1.setRefType("action");
            entity1.setWatcherId(id);
            toSave.add(entity1);
        }


        for (String thenAction : catchActions) {

            AfWatcherRsEntity entity1 = new AfWatcherRsEntity();
            entity1.setType(2);
            entity1.setRefId(Long.valueOf(thenAction));
            entity1.setRefType("action");
            entity1.setWatcherId(id);
            toSave.add(entity1);
        }


        for (String thenAction : thenApis) {

            AfWatcherRsEntity entity1 = new AfWatcherRsEntity();
            entity1.setType(1);
            entity1.setRefId(Long.valueOf(thenAction));
            entity1.setRefType("api");
            entity1.setWatcherId(id);
            toSave.add(entity1);
        }


        for (String thenAction : catchApis) {

            AfWatcherRsEntity entity1 = new AfWatcherRsEntity();
            entity1.setType(2);
            entity1.setRefId(Long.valueOf(thenAction));
            entity1.setRefType("api");
            entity1.setWatcherId(id);
            toSave.add(entity1);
        }

        afWatcherRsEntityRepository.saveAll(toSave);

        return id;
    }

    @Override
    public WatcherEntity findById(Long refId) {
        AfWatcherEntity byId = afWatcherEntityRepository.getById(refId);
        WatcherEntity watcherEntity = new WatcherEntity();
        watcherEntity.setId(refId.toString());
        watcherEntity.setExId(byId.getExId().toString());
        watcherEntity.setCondition(byId.getCondition());

        AfWatcherRsEntity byWatcherId = afWatcherRsEntityRepository.findByWatcherId(refId);

        // todo: 处理then、catch


        return watcherEntity;
    }
}
