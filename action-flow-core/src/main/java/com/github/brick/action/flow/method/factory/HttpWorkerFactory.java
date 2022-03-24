package com.github.brick.action.flow.method.factory;

import com.github.brick.action.flow.method.enums.HttpClientType;
import com.github.brick.action.flow.method.http.HttpWorker;
import com.github.brick.action.flow.method.http.OkHttpWorkerImpl;

public class HttpWorkerFactory implements Factory<HttpClientType, HttpWorker> {
    @Override
    public HttpWorker gen(HttpClientType type) {
        if (type == HttpClientType.OKHTTP) {
            return new OkHttpWorkerImpl();
        }
        return null;
    }
}
