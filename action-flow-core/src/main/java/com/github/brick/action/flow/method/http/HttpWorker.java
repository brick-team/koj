package com.github.brick.action.flow.method.http;

import java.io.IOException;
import java.util.Map;

public interface HttpWorker {
    String work(String url, String method, Map<String, String> headers, Map<String, String> formatData, Map<String, String> body) throws IOException;

}
