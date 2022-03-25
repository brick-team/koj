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