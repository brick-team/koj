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

package com.github.brick.action.flow.method.execute;

import com.github.brick.action.flow.method.entity.api.ApiEntity;

import java.io.IOException;
import java.util.Map;

public interface ApiExecute {
    /**
     * http 请求处理器
     *
     * @param url        请求地址
     * @param method     请求方法
     * @param queryParam 查询参数
     * @param headers    头信息
     * @param formatData 表单信息
     * @param body       请求体
     * @return http 响应结果
     * @throws IOException 连接建立异常
     */
    String execute(String url, String method,
                   Map<String, String> queryParam,
                   Map<String, String> headers,
                   Map<String, String> formatData,
                   String body
    ) throws IOException;


    /**
     * http 请求处理器
     *
     * @param apiEntity api 实体
     * @return http 响应结果
     */
    String execute(ApiEntity apiEntity, String jsonData);

}