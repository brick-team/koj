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

import com.github.brick.action.flow.method.entity.ExtractEntity;
import org.junit.Before;
import org.junit.Test;

public class MysqlExtractStorageTest extends CommonTest {
    MysqlExtractStorage mysqlExtractStorage;

    @Before
    public void init() {
        mysqlExtractStorage = context.getBean(MysqlExtractStorage.class);
    }

    @Test
    public void save() {
        ExtractEntity extractEntity = new ExtractEntity();
        extractEntity.setId("e1");
        extractEntity.setFromApi("api-1");
        extractEntity.setFromAction("api-1");
        extractEntity.setEl("$.token");

        mysqlExtractStorage.save(extractEntity.getFromAction(), extractEntity.getFromApi(), extractEntity.getEl(), "JSON_PATH");

    }
}