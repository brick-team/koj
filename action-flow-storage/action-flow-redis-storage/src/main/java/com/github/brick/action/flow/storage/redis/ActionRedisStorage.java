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
    public void save(String uid, ActionsEntity actions) {
        StringRedisTemplate stringRedisTemplate = RedisSinglet.redisTemplate();

        List<ActionEntity> list = actions.getList();
        for (ActionEntity actionEntity : list) {
            stringRedisTemplate.opsForHash().put(ACTION_KEY_PRE + uid, actionEntity.getId(), gson.toJson(actionEntity));
        }

    }

    @Override
    public List<ActionEntity> list(String uid) {
        StringRedisTemplate stringRedisTemplate = RedisSinglet.redisTemplate();

        Set<Object> keys = stringRedisTemplate.opsForHash().keys(ACTION_KEY_PRE + uid);
        List<ActionEntity> res = new ArrayList<>();
        for (Object key : keys) {

            String o = (String) stringRedisTemplate.opsForHash().get(ACTION_KEY_PRE + uid, key);
            res.add(gson.fromJson(o, ActionEntity.class));

        }

        return res;
    }
}
