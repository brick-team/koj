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

package com.github.brick.action.flow.web.common.ctr;

import com.github.brick.action.flow.model.swagger.ApiEntity;

import java.io.Serializable;
import java.util.Map;

/**
 * api 接口控制器，功能
 * 1. 设置 api 接口 和 接口参数
 * 2. 查看 api 接口 和 接口参数
 * 3. 模拟调用 api 接口
 */
public interface ApiCtr {
    /**
     * 保存单个，id 不允许重复
     *
     * @param api
     */
    void save(ApiEntity api);

    /**
     * 查找单个
     *
     * @param id
     * @return
     */
    ApiEntity findById(Serializable id);


    /**
     * 根据id执行
     *
     * @param id
     * @param jsonData todo: jsonData 数据结构
     * @return
     */
    Object execute(Serializable id, Map<String, Object> jsonData);
}
