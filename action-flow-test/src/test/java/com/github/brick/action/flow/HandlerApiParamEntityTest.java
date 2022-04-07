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

package com.github.brick.action.flow;

import com.github.brick.action.flow.method.entity.api.ApiEntity;
import com.github.brick.action.flow.method.entity.api.ApiParamEntity;
import com.github.brick.action.flow.method.execute.ApiExecute;
import com.github.brick.action.flow.method.execute.impl.ApiExecuteImpl;
import com.github.brick.action.flow.method.execute.impl.HandlerApiParamEntity;
import com.github.brick.action.flow.model.Category;
import com.github.brick.action.flow.model.Pet;
import com.github.brick.action.flow.model.Tag;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HandlerApiParamEntityTest {
    HandlerApiParamEntity handlerApiParamEntity = new HandlerApiParamEntity();
    String paramJson;
    Gson gson = new Gson();

    List<ApiParamEntity> apiParamEntities;
    private Map<String, Object> data;

    @Before
    public void readJson() throws Exception {
        ClassLoader cl
                = Thread.currentThread().getContextClassLoader();
        URL resource = cl.getResource("api_param_entity_1.json");
        StringBuffer buffer = new StringBuffer();
        BufferedReader bf = new BufferedReader(new FileReader(resource.getPath()));

        String s = null;
        while ((s = bf.readLine()) != null) {//使用readLine方法，一次读一行
            buffer.append(s.trim());
        }

        paramJson = buffer.toString();
        List<ApiParamEntity> o1 = gson.fromJson(paramJson, new TypeToken<List<ApiParamEntity>>() {
        }.getType());
        apiParamEntities = o1;
    }

    @Before
    public void exData() {


        Pet pet = new Pet();
        pet.setId(1L);
        Category category = new Category();
        category.setId(1l);
        category.setName("category");

        pet.setCategory(category);
        pet.setName("pet");

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add("f1");
        photoUrls.add("f2");
        pet.setPhotoUrls(photoUrls);
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag().name("tag").id(1L));
        pet.setTags(tags);
        pet.setStatus(Pet.StatusEnum.AVAILABLE);

        String s = gson.toJson(pet);
        System.out.println(s    );
        Map map = gson.fromJson(s, Map.class);
        this.data = map;


    }

    @Test
    public void handler() {
        handlerApiParamEntity.handler(apiParamEntities, data);
        ApiEntity apiEntity = new ApiEntity();
        apiEntity.setParams(apiParamEntities);

//        apiExecute.execute(apiEntity, "");
    }

    ApiExecute apiExecute = new ApiExecuteImpl();

}
