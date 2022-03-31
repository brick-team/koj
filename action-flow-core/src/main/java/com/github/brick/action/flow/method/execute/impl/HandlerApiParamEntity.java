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

package com.github.brick.action.flow.method.execute.impl;

import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.github.brick.action.flow.method.entity.api.ParamIn;
import com.github.brick.action.flow.method.extract.Extract;
import com.github.brick.action.flow.method.extract.ExtractImpl;
import com.google.gson.Gson;
import net.minidev.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 核心作用: 根据api参数列表和map结构组装实际请求对象
 */
public class HandlerApiParamEntity {
    Gson gson = new Gson();
    Extract extract = new ExtractImpl();

    public void handler(List<ApiParamEntity> apiParamEntities, Map data) {
        for (ApiParamEntity apiParamEntity : apiParamEntities) {
            ParamIn in = apiParamEntity.getIn();
            if (in == ParamIn.body) {
                List<ApiParamEntity> paramEntities = apiParamEntity.getParamEntities();
                Map<String, Object> body = extracted(paramEntities, data);
                System.out.println(gson.toJson(body));
            }
        }

    }

    private Map<String, Object> extracted(List<ApiParamEntity> paramEntities, Object input) {
        Map<String, Object> data = new HashMap<>();

        // 处理body下的数据
        for (ApiParamEntity paramEntity : paramEntities) {
            String type = paramEntity.getType();
            String name = paramEntity.getName();
            String el = paramEntity.getEl();
            List<ApiParamEntity> pp = paramEntity.getParamEntities();
            // 判断是否是 object
            if (type.equals("object")) {
                Map<String, Object> extracted = extracted(pp, input);
                data.put(name, extracted);
            }
            else if (type.equals("array")) {
                if (pp != null && !pp.isEmpty()) {
                    Object extract = this.extract.extract(input, el);
                    Map<String, Object> extracted = extracted(pp, extract);
                    data.put(name, extracted.values());
                }
                else {
                    Object extract = this.extract.extract(input, el);
                    data.put(name, extract);
                }
            }
            else {
                Object extract = this.extract.extract(input, el);
                // 解决 list 取值
                // 1. 判断是否有 [*]
                // 2. 有直接取最后一段
                if (el.contains("[*]")) {
                    // todo: 如何与 gson 中的序列化解耦
                    JSONArray jsonArray = (JSONArray) input;
                    Object extract1 = null;
                    for (Object o : jsonArray) {
                        String[] split = el.split("\\[\\*]");

                        extract1 = this.extract.extract(o, "$" + split[split.length - 1]);
                    }
                    data.put(name, extract1);

                }
                else {
                    data.put(name, extract);

                }


            }
        }
        return data;
    }
}
