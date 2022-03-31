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

import com.github.brick.action.flow.method.entity.ActionEntity;
import com.github.brick.action.flow.storage.api.ActionStorage;
import com.github.brick.action.flow.storage.mysql.entity.AfActionEntity;
import com.github.brick.action.flow.storage.mysql.entity.AfActionParamEntity;
import com.github.brick.action.flow.storage.mysql.repository.AfActionEntityRepository;
import com.github.brick.action.flow.storage.mysql.repository.AfActionParamEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MysqlActionStorage implements ActionStorage {
    @Autowired
    private AfActionEntityRepository actionEntityRepository;

    @Override
    public Long save(String classStr, String methodStr, boolean async) {

        AfActionEntity entity = new AfActionEntity();
        entity.setClazzStr(classStr);
        entity.setMethodStr(methodStr);
        entity.setAsync(async);
        AfActionEntity save = actionEntityRepository.save(entity);
        return save.getId();
    }

    @Autowired
    private AfActionParamEntityRepository actionParamEntityRepository;

    @Override
    public ActionEntity findById(Long refId) {
        AfActionEntity byId = actionEntityRepository.getById(refId);
        ActionEntity actionEntity = new ActionEntity();
        actionEntity.setId(refId.toString());
        actionEntity.setClazzStr(byId.getClazzStr());
        actionEntity.setMethodStr(byId.getMethodStr());
        actionEntity.setAsync(byId.isAsync());

        List<AfActionParamEntity> byActionId = actionParamEntityRepository.findByActionId(refId);

        ArrayList<ActionEntity.Param> params = new ArrayList<>();
        for (AfActionParamEntity afActionParamEntity : byActionId) {
            ActionEntity.Param param = new ActionEntity.Param();
            param.setArgName(afActionParamEntity.getArgName());
            param.setIndex(afActionParamEntity.getIndex());
            param.setType(afActionParamEntity.getType());
        }
        actionEntity.setParams(params);

        return actionEntity;
    }

}
