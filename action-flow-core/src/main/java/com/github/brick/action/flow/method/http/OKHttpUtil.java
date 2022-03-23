package com.github.brick.action.flow.method.http;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * todo: 支持 url path param
 */
public class OKHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(OKHttpUtil.class);

    private static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .retryOnConnectionFailure(false).connectionPool(new ConnectionPool(50, 5, TimeUnit.MINUTES))
            .connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS)
            .build();


    public static String get(String url, Map<String, String> queries) {
        String responseBody = "";
        StringBuffer sb = new StringBuffer(url);
        if (null != queries && !queries.isEmpty()) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        Request request = new Request.Builder().url(sb.toString()).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error("okhttp3 put error >> ex = {}", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    public static String postJsonParams(String url, String jsonParams) {
        String responseBody = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            //            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error("okhttp3 post error >> ex = {}", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

}
