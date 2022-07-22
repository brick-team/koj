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

import com.github.brick.action.flow.model.entity.JavaMethodParam;
import com.github.brick.action.flow.storage.api.validate.DataSaveAndValidate;
import com.github.brick.action.flow.storage.mysql.mapper.JavaMethodParamMapper;
import com.github.brick.action.flow.storage.mysql.util.MybatisUtil;

/**
 * java参数持久化操作
 *
 * @author xupenggao
 */
public class JavaParamExecuteMysqlStorageDao implements DataSaveAndValidate<JavaMethodParam> {

    @Override
    public void save(JavaMethodParam javaMethodParam) throws Exception {
        MybatisUtil gen = MybatisUtil.gen();

        gen.work(session -> {
            JavaMethodParamMapper mapper = session.getMapper(JavaMethodParamMapper.class);
            mapper.insert(javaMethodParam);
        });
    }

    @Override
    public void saveAndValidate(JavaMethodParam javaMethodParam) throws Exception {
        this.validate(javaMethodParam);
        this.save(javaMethodParam);
    }

    @Override
    public void validate(JavaMethodParam javaMethod) throws IllegalArgumentException, Exception {
        if (javaMethod.getIndex() != null){

            if (javaMethod.getType() == null || "".equals(javaMethod.getType())){
                throw new IllegalArgumentException("JavaMethod参数type不能为空");
            }

            if (javaMethod.getName() == null || "".equals(javaMethod.getName())){
                throw new IllegalArgumentException("JavaMethod参数name不能为空");
            }
        }
    }
}
