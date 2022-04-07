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
import com.github.brick.action.flow.method.enums.HttpClientType;
import com.github.brick.action.flow.method.execute.ApiExecute;
import com.github.brick.action.flow.method.extract.Extract;
import com.github.brick.action.flow.method.extract.JsonPathExtract;
import com.github.brick.action.flow.method.factory.Factory;
import com.github.brick.action.flow.method.factory.HttpWorkerFactory;

import java.io.IOException;
import java.util.Map;

public class ApiExecuteImpl implements ApiExecute {
    Factory<HttpClientType, HttpWorker> httpWorkerFactory =
            new HttpWorkerFactory();
    Extract extract = new JsonPathExtract();
    HttpWorker gen = httpWorkerFactory.gen(HttpClientType.OKHTTP);

    @Override
    public String execute(String url, String method, Map<String, String> queryParam, Map<String, String> headers, Map<String, String> formatData, String body) throws IOException {
        return gen.work(url, method, queryParam, headers, formatData, body);
    }


}
