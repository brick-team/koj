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

import com.github.brick.action.flow.method.entity.ActionEntity.Param;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.ArrayList;

import com.github.brick.action.flow.method.entity.ActionEntity;
import com.github.brick.action.flow.method.entity.ActionsEntity;
import com.github.brick.storage.api.ActionParamStorage;
import com.github.brick.storage.api.ActionStorage;
import com.google.gson.Gson;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Set;

public class ActionRedisStorage implements ActionStorage {
    public static final String ACTION_KEY_PRE = "af:action:";
    Gson gson = new Gson();

    @Override
    public void save(String groupId, ActionsEntity actions) {
        StringRedisTemplate stringRedisTemplate = RedisSinglet.redisTemplate();

        List<ActionEntity> list = actions.getList();
        for (ActionEntity actionEntity : list) {
            stringRedisTemplate.opsForHash().put(ACTION_KEY_PRE + groupId, actionEntity.getId(), gson.toJson(actionEntity));
        }

    }

    @Override
    public List<ActionEntity> list(String groupId) {
        StringRedisTemplate stringRedisTemplate = RedisSinglet.redisTemplate();

        Set<Object> keys = stringRedisTemplate.opsForHash().keys(ACTION_KEY_PRE + groupId);
        List<ActionEntity> res = new ArrayList<>();
        for (Object key : keys) {

            String o = (String) stringRedisTemplate.opsForHash().get(ACTION_KEY_PRE + groupId, key);
            res.add(gson.fromJson(o, ActionEntity.class));

        }

        return res;
    }
}
