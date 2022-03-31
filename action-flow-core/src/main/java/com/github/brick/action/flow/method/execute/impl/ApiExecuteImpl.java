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

import com.github.brick.action.flow.execute.http.HttpWorker;
import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.github.brick.action.flow.method.entity.api.ParamIn;
import com.github.brick.action.flow.method.enums.HttpClientType;
import com.github.brick.action.flow.method.execute.ApiExecute;
import com.github.brick.action.flow.method.extract.Extract;
import com.github.brick.action.flow.method.extract.ExtractImpl;
import com.github.brick.action.flow.method.factory.Factory;
import com.github.brick.action.flow.method.factory.HttpWorkerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiExecuteImpl implements ApiExecute {
    Factory<HttpClientType, HttpWorker> httpWorkerFactory =
            new HttpWorkerFactory();
    Extract extract = new ExtractImpl();

    @Override
    public String execute(String url, String method, Map<String, String> queryParam, Map<String, String> headers, Map<String, String> formatData, String body) throws IOException {
        HttpWorker gen = httpWorkerFactory.gen(HttpClientType.OKHTTP);
        return gen.work(url, method, queryParam, headers, formatData, body);
    }

    @Override
    public String execute(ApiEntity apiEntity, String jsonData) {
        String url = apiEntity.getUrl();
        String method = apiEntity.getMethod();

        Map<String, String> queryParam = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        Map<String, String> formatData = new HashMap<>();
        Map<String, Object> bodyData = new HashMap<>();

        List<ApiParamEntity> params = apiEntity.getParams();

        for (ApiParamEntity param : params) {
            ParamIn in = param.getIn();
            if (in == ParamIn.query) {
                String paramGroup = param.getParamGroup();
                String ex = param.getEx();
                String exId = param.getExId();
                String name = param.getName();
                handlerEx(paramGroup, ex, name, jsonData, queryParam);
                handlerExId();
            }
            else if (in == ParamIn.path) {

            }
            else if (in == ParamIn.body) {

            }
            else if (in == ParamIn.formData) {

            }
            else if (in == ParamIn.header) {

            }
        }


        return null;
    }

    private void handlerExId() {

    }

    private void handlerEx(String paramGroup, String ex, String name, String jsonData, Map<String, String> queryParam) {
        Object value = this.extract.extract(jsonData, ex);
        if (value instanceof String) {
            queryParam.put(name, (String) value);
        }
    }
}
