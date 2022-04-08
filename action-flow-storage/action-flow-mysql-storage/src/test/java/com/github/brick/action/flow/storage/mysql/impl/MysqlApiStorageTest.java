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
import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.github.brick.action.flow.method.entity.api.ParamIn;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
        ApiEntity api1 = new ApiEntity();
        api1.setId("api-1");
        api1.setUrl("http://localhost:8080/login");
        api1.setMethod("post");
        ArrayList<ApiParamEntity> params1 = new ArrayList<>();
        ApiParamEntity username = new ApiParamEntity();
        username.setIn(ParamIn.formdata);
        username.setName("username");
        username.setRequire(true);

        params1.add(username);
        ApiParamEntity password = new ApiParamEntity();
        password.setIn(ParamIn.formdata);
        password.setName("password");
        password.setRequire(true);
        params1.add(password);
        api1.setParams(params1);

        mysqlApiStorage.saveForApi(Collections.singletonList(api1), true);


        ApiEntity api2 = new ApiEntity();
        api2.setId("api-2");
        api2.setUrl("http://localhost:8080/user_info");
        api2.setMethod("get");
        ArrayList<ApiParamEntity> params = new ArrayList<>();
        ApiParamEntity apiParamEntity = new ApiParamEntity();
        apiParamEntity.setIn(ParamIn.header);
        apiParamEntity.setName("token");
        apiParamEntity.setRequire(true);
        params.add(apiParamEntity);
        api2.setParams(params);
        mysqlApiStorage.saveForApi(Collections.singletonList(api2), true);


    }

    @Test
    public void saveForApi2() {



    }
}