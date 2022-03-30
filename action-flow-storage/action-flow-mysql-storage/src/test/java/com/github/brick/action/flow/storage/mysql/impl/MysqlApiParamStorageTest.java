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

import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MysqlApiParamStorageTest extends CommonTest {
    MysqlApiParamStorage mysqlApiParamStorage;

    @Before
    public void init() {
        mysqlApiParamStorage = context.getBean(MysqlApiParamStorage.class);
    }

    @Test
    public void save() {
    }

    @Test
    public void find() {
        List<ApiParamEntity> ff8080817fd8aa0d017fd8aa12140000 = this.mysqlApiParamStorage.findByAppId("ff8080817fd8aa0d017fd8aa12140000");
        System.out.println();
    }
}