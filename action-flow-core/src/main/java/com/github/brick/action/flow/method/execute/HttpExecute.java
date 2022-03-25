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
import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.github.brick.action.flow.method.entity.api.ParamIn;
import com.github.brick.action.flow.method.enums.HttpClientType;
import com.github.brick.action.flow.method.factory.HttpWorkerFactory;
import com.github.brick.action.flow.method.http.HttpWorker;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpExecute {
    public static final String URL_PATH_PARAM_FORMAT = "{%s}";
    HttpWorker httpWorker = null;

    @PostConstruct
    public void init() {
        // TODO: 2022/3/24 从配置文件中确认http客户端类型
        HttpWorkerFactory httpWorkerFactory = new HttpWorkerFactory();
        httpWorker = httpWorkerFactory.gen(HttpClientType.OKHTTP);
    }

    public String execute(ApiEntity api, Map<String, String> data) throws IOException {

        String method = api.getMethod();
        String url = api.getUrl();
        List<ApiParamEntity> params = api.getParams();

        StringBuffer urlQuery = new StringBuffer("?");

        Map<String, String> headers = new HashMap<>(8);
        Map<String, String> formData = new HashMap<>(8);
        Map<String, String> body = new HashMap<>();

        for (ApiParamEntity param : params) {
            ParamIn in = param.getIn();
            String name = param.getName();
            String value = data.get(name);
            if (in == ParamIn.formData) {
                handlerFormDataParam(formData, name, value);
            }
            // 请求地址中的参数处理
            else if (in == ParamIn.path) {
                url = handlerPathParam(url, name, value);
            }
            // 处理请求体
            // todo: 多级对象处理
            else if (in == ParamIn.body) {
                handlerBodyParam(body, name, value);
            }
            // 处理url查询参数
            else if (in == ParamIn.query) {
                handlerQueryParam(urlQuery, name, value);

            } else if (in == ParamIn.header) {
                handlerHeaderParam(headers, name, value);
            } else {
                throw new IllegalStateException("Unexpected value: " + in);
            }
        }
        // 移除最后一个 & 符号
        String urlQueryStr = urlQuery.substring(0, urlQuery.length() - 1);

        // 发送消息
        return httpWorker.work(url + urlQueryStr, method, headers, formData, body);
    }

    private void handlerQueryParam(StringBuffer urlQuery, String name, String value) {
        urlQuery.append(name).append("=").append(value).append("&");
    }

    private void handlerHeaderParam(Map<String, String> headers, String name, String value) {
        headers.put(name, value);
    }

    private void handlerFormDataParam(Map<String, String> formData, String name, String value) {
        formData.put(name, value);
    }

    private void handlerBodyParam(Map<String, String> body, String name, String value) {
        body.put(name, value);
    }

    private String handlerPathParam(String url, String name, String value) {
        String oVal = String.format(URL_PATH_PARAM_FORMAT, name);
        url = url.replace(oVal, value);
        return url;
    }
}
