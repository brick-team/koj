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

import com.github.brick.action.flow.method.entity.WorkEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MysqlWorkStorageTest extends CommonTest {
    MysqlWorkStorage mysqlWorkStorage;

    @Before
    public void init() {
        mysqlWorkStorage = context.getBean(MysqlWorkStorage.class);
    }

    @Test
    public void save() {
        WorkEntity workEntity = new WorkEntity();
        workEntity.setId("w1");
        workEntity.setType("api");
        workEntity.setRefId("api-2");


        this.mysqlWorkStorage.save("api", "ff8080817fd8aa0d017fd8aa12140000", new ArrayList<>(), new ArrayList<>());

    }
}