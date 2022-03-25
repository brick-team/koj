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

package com.github.brick.action.flow.storage.redis;

import com.github.brick.action.flow.method.entity.FormatEntity;

import com.github.brick.action.flow.method.entity.ActionEntity;
import com.github.brick.action.flow.method.entity.ActionsEntity;
import com.github.brick.storage.api.ActionStorage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ActionRedisStorageTest {
    ActionStorage actionStorage = new ActionRedisStorage();

    @Test
    public void save() {
        ActionsEntity actions = new ActionsEntity();
        ArrayList<ActionEntity> list = new ArrayList<>();
        ActionEntity e = new ActionEntity();
        e.setId("1");
        e.setClazzStr("ActionsEntity");
        e.setMethodStr("ActionsEntity");
        e.setAsync(false);
        ArrayList<ActionEntity.Param> params = new ArrayList<>();
        ActionEntity.Param e1 = new ActionEntity.Param();
        e1.setArgName("name");
        e1.setValue("name");
        e1.setParamGroup("1");
        e1.setEx("1");
        e1.setExId("1");
        e1.setIndex(0);
        e1.setType("1");
        FormatEntity formatEntity = new FormatEntity();
        formatEntity.setId("1");
        formatEntity.setClassStr("1");

        e1.setFormatEntity(formatEntity);

        params.add(e1);
        e.setParams(params);


        list.add(e);
        list.add(e);

        actions.setList(list);

        actionStorage.save("uid", actions);
    }

    @Test
    public void list() {
        List<ActionEntity> uid = actionStorage.list("uid");

    }
}