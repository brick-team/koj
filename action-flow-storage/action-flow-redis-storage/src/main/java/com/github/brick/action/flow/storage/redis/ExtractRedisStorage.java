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

import com.github.brick.action.flow.method.entity.ExtractEntity;
import com.github.brick.action.flow.method.entity.ExtractsEntity;
import com.github.brick.storage.api.ExtractStorage;
import com.google.gson.Gson;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;

public class ExtractRedisStorage implements ExtractStorage {
    public static final String EXTRACT_KEY_PRE = "af:extract:";
    Gson gson = new Gson();

    @Override
    public void save(String groupId, ExtractsEntity extracts) {
        StringRedisTemplate stringRedisTemplate = RedisSinglet.redisTemplate();
        List<ExtractEntity> extractEntities = extracts.getExtractEntities();
        for (ExtractEntity extractEntity : extractEntities) {
            stringRedisTemplate.opsForList().rightPush(EXTRACT_KEY_PRE + groupId, gson.toJson(extractEntity));
        }

    }

    @Override
    public List<ExtractEntity> list(String groupId) {
        StringRedisTemplate stringRedisTemplate = RedisSinglet.redisTemplate();
        List<String> range = stringRedisTemplate.opsForList().range(EXTRACT_KEY_PRE + groupId, 0, -1);
        List<ExtractEntity> res = new ArrayList<>();
        for (String s : range) {
            ExtractEntity extractsEntity = gson.fromJson(s, ExtractEntity.class);
            res.add(extractsEntity);
        }
        return res;
    }
}
