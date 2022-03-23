package com.github.brick.action.flow.storage.redis;


import com.github.brick.action.flow.method.entity.ParamEntity;
import com.github.brick.action.flow.method.entity.ParamsEntity;
import com.github.brick.storage.api.ParamStorage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParamRedisStorageTest {
    ParamStorage paramStorage = new ParamRedisStorage();

    @Test
    public void testSave() {
        ParamsEntity params = new ParamsEntity();
        ArrayList<ParamEntity> list = new ArrayList<>();
        ParamEntity e = new ParamEntity();
        e.setGroup("1");
        e.setKey("1");
        e.setValue("1");

        list.add(e);
        params.setList(list);
        paramStorage.save("uid", params);
    }

    @Test
    public void testList() {
        List<ParamEntity> uid = paramStorage.list("uid");
        System.out.println();
    }
}