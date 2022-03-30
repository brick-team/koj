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

import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.google.gson.Gson;

import java.util.List;

public class SwaggerFIleParseImplTest {
    SwaggerFIleParse swaggerFIleParse = new SwaggerFIleParseImpl();
    Gson gson = new Gson();

    @org.junit.Test
    public void parse() {
        List<ApiEntity> parse = swaggerFIleParse.parse("swagge_example.json");
        for (ApiEntity apiEntity : parse) {
            List<ApiParamEntity> params = apiEntity.getParams();
            System.out.println(gson.toJson(apiEntity));
        }


    }
}