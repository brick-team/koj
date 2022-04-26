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

package com.github.brick.action.flow.web.common.ctr.impl;

import com.github.brick.action.flow.method.factory.storage.StorageFactory;
import com.github.brick.action.flow.model.enums.StorageType;
import com.github.brick.action.flow.model.swagger.ApiEntity;
import com.github.brick.action.flow.parse.api.ActionFlowSwaggerParseApi;
import com.github.brick.action.flow.parse.swagger.SwaggerParseImpl;
import com.github.brick.action.flow.storage.api.child.ApiEntityStorage;
import com.github.brick.action.flow.web.common.ctr.SwaggerCtr;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.github.brick.action.flow.web.common.security.ActionFlowSecurityAutoConfiguration.ACTION_FLOW_URL_PRE;

@ResponseBody
@RequestMapping(ACTION_FLOW_URL_PRE)
public class SwaggerCtrImpl implements SwaggerCtr {
    ActionFlowSwaggerParseApi swaggerParseApi = new SwaggerParseImpl();

    private final StorageType storageType;

    private final ApiEntityStorage apiEntityStorage;

    public SwaggerCtrImpl(StorageType storageType) {
        this.storageType = storageType;
        apiEntityStorage = StorageFactory.factory(storageType, ApiEntityStorage.class);

    }

    @Override
    public List<ApiEntity> handlerWithData(String swaggerData) {
        List<ApiEntity> apiEntities = swaggerParseApi.parseData(swaggerData);
        return apiEntities;
    }

    @Override
    public List<ApiEntity> handlerWithFile(MultipartFile file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
        String lineTxt;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            System.out.println(lineTxt);
        }
        List<ApiEntity> apiEntities = handlerWithData(lineTxt);
        return apiEntities;
    }

    @Override
    public List<ApiEntity> handlerWithUrl(String url) {
        List<ApiEntity> apiEntities = swaggerParseApi.parseUrl(url);
        return apiEntities;
    }

    @Override
    public boolean handlerWithDataWithStorage(String swaggerData) {
        List<ApiEntity> apiEntities = handlerWithData(swaggerData);
        return apiEntityStorage.save(apiEntities);
    }

    @Override
    public boolean handlerWithFileWithStorage(MultipartFile file) throws IOException {
        List<ApiEntity> apiEntities = handlerWithFile(file);
        return apiEntityStorage.save(apiEntities);
    }

    @Override
    public boolean handlerWithUrlWithStorage(String url) {
        List<ApiEntity> apiEntities = handlerWithUrl(url);
        return apiEntityStorage.save(apiEntities);
    }
}
