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

package com.github.brick.action.flow.method.parse.xml;

import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.github.brick.action.flow.method.entity.api.ApisEntity;
import com.github.brick.action.flow.method.entity.api.ParamIn;
import com.github.brick.action.flow.method.parse.Parse;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ApisParse implements Parse<ApisEntity> {
    ApiParse apiParse = new ApiParse();
    ApiParamParse apiParamParse = new ApiParamParse();

    @Override
    public ApisEntity parse(Element element) throws Exception {
        ApisEntity apisEntity = new ApisEntity();
        ArrayList<ApiEntity> list = new ArrayList<>();


        List<Element> action = element.elements("api");
        for (Element element1 : action) {
            ApiEntity parse = apiParse.parse(element1);
            list.add(parse);
        }
        apisEntity.setList(list);
        return apisEntity;
    }

    public class ApiParse implements Parse<ApiEntity> {
        @Override
        public ApiEntity parse(Element element) throws Exception {
            String id = element.attributeValue("id");
            String url = element.attributeValue("url");
            String method = element.attributeValue("method");
            String desc = element.attributeValue("desc");

            ApiEntity apiEntity = new ApiEntity();
            apiEntity.setId(id);
            apiEntity.setUrl(url);
            apiEntity.setMethod(method);
            apiEntity.setDesc(desc);
            ArrayList<ApiParamEntity> params = new ArrayList<>();

            List<Element> action = element.elements("params");

            for (Element element1 : action) {

                List<Element> param = element1.elements("param");
                for (Element element2 : param) {
                    ApiParamEntity parse = apiParamParse.parse(element2);
                    params.add(parse);
                }
            }
            apiEntity.setParams(params);
            return apiEntity;
        }
    }

    public class ApiParamParse implements Parse<ApiParamEntity> {
        @Override
        public ApiParamEntity parse(Element element) throws Exception {

            String in = element.attributeValue("in");
            String name = element.attributeValue("name");
            String require = element.attributeValue("require");
            String param_group = element.attributeValue("param_group");
            String ex = element.attributeValue("ex");

            String   ex_id= element.attributeValue("ex_id");


            ApiParamEntity apiParamEntity = new ApiParamEntity();
            apiParamEntity.setIn(ParamIn.valueOf(in));
            apiParamEntity.setName(name);
            apiParamEntity.setRequire(Boolean.valueOf(require));
            apiParamEntity.setParamGroup(param_group);
            apiParamEntity.setExId(ex_id);
            apiParamEntity.setEx(ex);

            List<Element> action = element.elements("param");
            List<ApiParamEntity> inner = new ArrayList<>();
            for (Element element1 : action) {
                ApiParamEntity parse = this.parse(element1);
                inner.add(parse);
            }
            apiParamEntity.setParamEntities(inner);


            return apiParamEntity;
        }
    }


}
