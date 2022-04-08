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

package com.github.brick.action.flow.execute.extract;

import com.google.gson.Gson;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

public class JsonPathExtract implements Extract {
    Gson gson = new Gson();
    @Override
    public Object extract(Object o, String el) {
        String json = gson.toJson(o);
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        try {
            return JsonPath.read(document, el);
        } catch (Exception e) {
            return null;

        }
    }

    @Override
    public Object extract(String json, String el) {
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        try {
            return JsonPath.read(document, el);
        } catch (Exception e) {
            return null;
        }
    }
}
