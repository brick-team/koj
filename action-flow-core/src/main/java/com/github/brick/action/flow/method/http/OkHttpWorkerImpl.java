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

package com.github.brick.action.flow.method.http;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpWorkerImpl implements HttpWorker {

    private static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .retryOnConnectionFailure(false).connectionPool(new ConnectionPool(50, 5, TimeUnit.MINUTES))
            .connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS)
            .build();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Gson gson = new Gson();

    @Override
    public String work(String url, String method, Map<String, String> headers, Map<String, String> formatData, Map<String, String> body) throws IOException {
        FormBody formBody = buildFormBody(formatData);

        Headers reqHead = buildHeaders(headers);


        if ("get".equalsIgnoreCase(method)) {
            Request.Builder builder = new Request.Builder().url(url).headers(reqHead).get();
            Request request = builder.build();
            return getHttpResponse(request);

        } else if ("post".equalsIgnoreCase(method)) {
            if (!formatData.isEmpty()) {
                Request request = new Request.Builder().url(url).post(formBody).headers(reqHead).build();
                return getHttpResponse(request);
            }

            if (!body.isEmpty()) {
                RequestBody requestBody = RequestBody.create(JSON, gson.toJson(body));
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                return getHttpResponse(request);
            }
            throw new IllegalArgumentException("post 请求异常");

        } else if ("put".equalsIgnoreCase(method)) {
            if (!formatData.isEmpty()) {
                Request request = new Request.Builder().url(url).put(formBody).headers(reqHead).build();
                return getHttpResponse(request);
            }

            if (!body.isEmpty()) {
                RequestBody requestBody = RequestBody.create(JSON, gson.toJson(body));
                Request request = new Request.Builder()
                        .url(url)
                        .put(requestBody)
                        .build();
                return getHttpResponse(request);
            }
            throw new IllegalArgumentException("put 请求异常");

        } else if ("delete".equalsIgnoreCase(method)) {
            if (!formatData.isEmpty()) {
                Request request = new Request.Builder().url(url).delete(formBody).headers(reqHead).build();
                return getHttpResponse(request);
            }

            if (!body.isEmpty()) {
                RequestBody requestBody = RequestBody.create(JSON, gson.toJson(body));
                Request request = new Request.Builder()
                        .url(url)
                        .delete(requestBody)
                        .build();
                return getHttpResponse(request);
            }
            throw new IllegalArgumentException("put 请求异常");
        } else {
            throw new IllegalArgumentException("无法发送非 get post put delete 以外的请求");
        }

    }

    private String getHttpResponse(Request request) throws IOException {
        Call call = okHttpClient.newCall(request);
        Response execute = call.execute();
        assert execute.body() != null;
        return execute.body().string();
    }

    private FormBody buildFormBody(Map<String, String> formatData) {
        FormBody.Builder builder = new FormBody.Builder();
        if (formatData.isEmpty()) {
            return builder.build();
        }
        formatData.forEach(builder::add);
        return builder.build();
    }

    private Headers buildHeaders(Map<String, String> headers) {
        Headers.Builder builder = new Headers.Builder();
        if (headers.isEmpty()) {
            return builder.build();
        }

        headers.forEach(builder::add);
        return builder.build();
    }
}
