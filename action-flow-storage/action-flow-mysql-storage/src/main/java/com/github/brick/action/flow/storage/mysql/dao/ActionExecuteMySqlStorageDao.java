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

package com.github.brick.action.flow.storage.mysql.dao;

import com.github.brick.action.flow.model.entity.Action;
import com.github.brick.action.flow.storage.mysql.mapper.ActionMapper;
import com.github.brick.action.flow.storage.mysql.util.MybatisUtil;

/**
 * @author xupenggao
 * @version 1.0
 * @description action相关数据持久化操作
 * @date 2022/4/24
 */
public class ActionExecuteMySqlStorageDao {

    public void save(Action actionEntity) {

        MybatisUtil gen = MybatisUtil.gen();

        try {
            gen.work(session -> {
                ActionMapper actionMapper = session.getMapper(ActionMapper.class);

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
