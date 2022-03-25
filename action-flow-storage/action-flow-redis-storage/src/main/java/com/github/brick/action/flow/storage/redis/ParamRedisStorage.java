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

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.brick.action.flow.method.entity.ParamEntity;
import com.github.brick.action.flow.method.entity.ParamsEntity;
import com.github.brick.storage.api.ParamStorage;
import com.google.gson.Gson;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;

public class ParamRedisStorage implements ParamStorage {
    public static final String PARAM_KEY_PRE = "af:params:";
    Gson gson = new Gson();

    @Override
    public void save(String uid, ParamsEntity params) {
        StringRedisTemplate redisTemplate = RedisSinglet.redisTemplate();
        List<ParamEntity> list = params.getList();
        for (ParamEntity paramEntity : list) {
            redisTemplate.opsForList().rightPushAll(PARAM_KEY_PRE + uid, gson.toJson(paramEntity));

        }
    }

    @Override
    public List<ParamEntity> list(String uid) {
        StringRedisTemplate redisTemplate = RedisSinglet.redisTemplate();
        List<String> range = redisTemplate.opsForList().range(PARAM_KEY_PRE + uid, 0, -1);
        List<ParamEntity> res = new ArrayList<>();
        for (String s : range) {
            ParamEntity paramEntity = gson.fromJson(s, ParamEntity.class);
            res.add(paramEntity);
        }
        return res;
    }
}
