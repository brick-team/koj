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

import com.github.brick.action.flow.model.entity.Flow;
import com.github.brick.action.flow.storage.api.validate.DataSaveAndValidate;
import com.github.brick.action.flow.storage.mysql.mapper.FlowMapper;
import com.github.brick.action.flow.storage.mysql.util.MybatisUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * flow数据持久化处理
 *
 * @author xupenggao
 */
public class FlowExecuteMysqlStorageDao implements DataSaveAndValidate<Flow> {

    @Override
    public void save(Flow flow) throws Exception {
        MybatisUtil gen = MybatisUtil.gen();

        gen.work(session -> {
            FlowMapper mapper = session.getMapper(FlowMapper.class);
            mapper.insert(flow);
        });
    }

    @Override
    public void saveAndValidate(Flow flow) throws Exception {
        this.validate(flow);
        this.save(flow);
    }

    @Override
    public void validate(Flow flow) throws IllegalArgumentException, Exception {
        MybatisUtil gen = MybatisUtil.gen();

        String fileName = flow.getFileName();
        Integer id = flow.getId();

        gen.work(session -> {
            FlowMapper mapper = session.getMapper(FlowMapper.class);
            List<Flow> flowList = mapper.queryByFileName(fileName);

            List<Integer> ids = new ArrayList<>();
            flowList.forEach(t -> ids.add(t.getId()));

            if(ids.contains(id)){
                throw new IllegalArgumentException("当前flowId = " + id + " 已存在");
            }
        });
    }
}
