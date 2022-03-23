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
    public void save(String uid, ExtractsEntity extracts) {
        StringRedisTemplate stringRedisTemplate = RedisSinglet.redisTemplate();
        List<ExtractEntity> extractEntities = extracts.getExtractEntities();
        for (ExtractEntity extractEntity : extractEntities) {
            stringRedisTemplate.opsForList().rightPush(EXTRACT_KEY_PRE + uid, gson.toJson(extractEntity));
        }

    }

    @Override
    public List<ExtractEntity> list(String uid) {
        StringRedisTemplate stringRedisTemplate = RedisSinglet.redisTemplate();
        List<String> range = stringRedisTemplate.opsForList().range(EXTRACT_KEY_PRE + uid, 0, -1);
        List<ExtractEntity> res = new ArrayList<>();
        for (String s : range) {
            ExtractEntity extractsEntity = gson.fromJson(s, ExtractEntity.class);
            res.add(extractsEntity);
        }
        return res;
    }
}
