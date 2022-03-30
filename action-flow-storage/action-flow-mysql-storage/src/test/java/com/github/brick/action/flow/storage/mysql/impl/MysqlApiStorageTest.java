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

import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class MysqlApiStorageTest extends CommonTest {
    public static final String JSON_TT = "{\"url\":\"/pet\",\"method\":\"post\",\"desc\":\"Add a new pet to the store\",\"params\":[{\"in\":\"body\",\"name\":\"body\",\"require\":true,\"paramEntities\":[{\"flag\":\"Pet.id\",\"name\":\"id\",\"require\":false,\"type\":\"integer\"},{\"flag\":\"Pet.category\",\"name\":\"category\",\"require\":false,\"type\":\"object\",\"paramEntities\":[{\"flag\":\"Category.id\",\"name\":\"id\",\"require\":false,\"type\":\"integer\"},{\"flag\":\"Category.name\",\"name\":\"name\",\"require\":false,\"type\":\"string\"}]},{\"flag\":\"Pet.name\",\"name\":\"name\",\"require\":true,\"type\":\"string\"},{\"flag\":\"Pet.photoUrls\",\"name\":\"photoUrls\",\"require\":true,\"type\":\"array\"},{\"flag\":\"Pet.tags\",\"name\":\"tags\",\"require\":false,\"type\":\"array\",\"paramEntities\":[{\"flag\":\"Pet.tags\",\"name\":\"tags\",\"require\":false,\"type\":\"object\",\"paramEntities\":[{\"flag\":\"Tag.id\",\"name\":\"id\",\"require\":false,\"type\":\"integer\"},{\"flag\":\"Tag.name\",\"name\":\"name\",\"require\":false,\"type\":\"string\"}]}]},{\"flag\":\"Pet.status\",\"name\":\"status\",\"require\":false,\"type\":\"string\"}]}]}\n";
    MysqlApiStorage mysqlApiStorage;
    Gson gson = new Gson();

    @Before
    public void init() {
        mysqlApiStorage = context.getBean(MysqlApiStorage.class);
    }

    @Test
    public void save() {
    }

    @Test
    public void saveForApi() {
        ApiEntity apiEntity = gson.fromJson(JSON_TT, ApiEntity.class);

        mysqlApiStorage.saveForApi(Collections.singletonList(apiEntity));

    }
}