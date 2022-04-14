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

import com.github.brick.action.flow.model.swagger.ApiEntity;
import com.github.brick.action.flow.parse.api.ActionFlowSwaggerParseApi;

import java.util.List;

/**
 * swagger parse
 */
public interface SwaggerParse extends ActionFlowSwaggerParseApi {
    /**
     * 解析本地 swagger 文件
     * @param filePath swagger 文件地址
     * @return 解析结果
     */
    List<ApiEntity> parseFile(String filePath);

    /**
     * 解析 swagger url
     * @param url swagger url
     * @return 解析结果
     */
    List<ApiEntity> parseUrl(String url);

}
