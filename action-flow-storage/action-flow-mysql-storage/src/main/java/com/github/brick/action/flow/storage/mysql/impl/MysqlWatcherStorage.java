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

import com.github.brick.action.flow.storage.api.WatcherStorage;
import com.github.brick.action.flow.storage.mysql.entity.AfWatcherEntity;
import com.github.brick.action.flow.storage.mysql.entity.AfWatcherRsEntity;
import com.github.brick.action.flow.storage.mysql.repository.AfWatcherEntityRepository;
import com.github.brick.action.flow.storage.mysql.repository.AfWatcherRsEntityRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MysqlWatcherStorage implements WatcherStorage {
    @Autowired
    private AfWatcherEntityRepository afWatcherEntityRepository;

    @Autowired
    private AfWatcherRsEntityRepository afWatcherRsEntityRepository;

    @Transactional
    @Override
    public String save(String exId, String condition, List<String> thenActions, List<String> catchActions, List<String> thenApis, List<String> catchApis) {
        AfWatcherEntity entity = new AfWatcherEntity();
        entity.setCondition(condition);
        entity.setExId(exId);

        AfWatcherEntity save = afWatcherEntityRepository.save(entity);
        String id = save.getId();


        AfWatcherRsEntity entity1 = new AfWatcherRsEntity();
        entity1.setCatchApiIds(StringUtils.join(thenActions, ","));
        entity1.setCatchActionIds(StringUtils.join(catchActions, ","));
        entity1.setThenApiIds(StringUtils.join(thenApis, ","));
        entity1.setThenActionIds(StringUtils.join(catchApis, ","));
        entity1.setWatcherId(id);

        afWatcherRsEntityRepository.save(entity1);

        return id;
    }


}
