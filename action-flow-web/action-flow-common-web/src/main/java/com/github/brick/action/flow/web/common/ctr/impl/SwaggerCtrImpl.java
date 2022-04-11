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

import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.github.brick.action.flow.parse.api.ActionFlowSwaggerParseApi;
import com.github.brick.action.flow.parse.swagger.SwaggerParseImpl;
import com.github.brick.action.flow.web.common.ctr.SwaggerCtr;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.github.brick.action.flow.web.common.security.ActionFlowSecurityAutoConfiguration.ACTION_FLOW_URL_PRE;

@ResponseBody
@RequestMapping(ACTION_FLOW_URL_PRE)
public class SwaggerCtrImpl implements SwaggerCtr {
    ActionFlowSwaggerParseApi swaggerParseApi = new SwaggerParseImpl();

    @Override
    public List<ApiEntity> handlerWithData(String swaggerData) {
        List<ApiEntity> apiEntities = swaggerParseApi.parseData(swaggerData);
        return apiEntities;
    }

    @Override
    public List<ApiEntity> handlerWithFile(MultipartFile file) {
        return null;
    }

    @Override
    public List<ApiEntity> handlerWithUrl(String url) {
        List<ApiEntity> apiEntities = swaggerParseApi.parseUrl(url);
        return apiEntities;
    }

    @Override
    public boolean handlerWithDataWithStorage(String swaggerData) {
        return false;
    }

    @Override
    public boolean handlerWithFileWithStorage(MultipartFile file) {
        return false;
    }

    @Override
    public boolean handlerWithUrlWithStorage(String url) {
        return false;
    }
}
