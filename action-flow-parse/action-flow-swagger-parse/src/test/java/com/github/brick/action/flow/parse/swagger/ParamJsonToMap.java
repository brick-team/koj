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

package com.github.brick.action.flow.parse.swagger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.github.brick.action.flow.method.entity.api.ParamIn;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ietf.jgss.GSSContext;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamJsonToMap {


    String paramJson;
    Gson gson = new Gson();

    List<ApiParamEntity> apiParamEntities;

    @Before
    public void readJson() throws Exception {
        ClassLoader cl
                = Thread.currentThread().getContextClassLoader();
        URL resource = cl.getResource("api_param_entity.json");
        StringBuffer buffer = new StringBuffer();
        BufferedReader bf = new BufferedReader(new FileReader(resource.getPath()));

        String s = null;
        while ((s = bf.readLine()) != null) {//使用readLine方法，一次读一行
            buffer.append(s.trim());
        }

        paramJson = buffer.toString();
        List<ApiParamEntity> o1 = gson.fromJson(paramJson, new TypeToken<List<ApiParamEntity>>() {
        }.getType());
        apiParamEntities = o1;
    }

    @Test
    public void test() {

        for (ApiParamEntity apiParamEntity : apiParamEntities) {
            ParamIn in = apiParamEntity.getIn();
            if (in == ParamIn.body) {
                List<ApiParamEntity> paramEntities = apiParamEntity.getParamEntities();
                Map<String, Object> ss = extracted(paramEntities);
                System.out.println(gson.toJson(ss));
            }
        }

    }

    private Map<String, Object> extracted(List<ApiParamEntity> paramEntities) {
        Map<String, Object> data = new HashMap<>();

        // 处理body下的数据
        for (ApiParamEntity paramEntity : paramEntities) {
            String type = paramEntity.getType();
            String name = paramEntity.getName();
            List<ApiParamEntity> pp = paramEntity.getParamEntities();
            // 判断是否是 object
            if (type.equals("object")) {

                Map<String, Object> extracted = extracted(pp);
                data.put(name, extracted);

            }
            else if (type.equals("array")) {
                if (pp != null && !pp.isEmpty()) {

                    Map<String, Object> extracted = extracted(pp);
                    data.put(name, extracted.values());
                }
                else {
                    data.put(name, new ArrayList<>());

                }

            }
            else {
                data.put(name, name);
            }
        }
        return data;
    }
}
