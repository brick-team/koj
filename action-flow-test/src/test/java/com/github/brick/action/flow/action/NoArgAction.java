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

package com.github.brick.action.flow.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zen Huifer
 */
public class NoArgAction {
    public Map<String, Object> data() {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("age", 19);
        stringObjectHashMap.put("array", new String[]{"a", "2", "3"});

        HashMap<Object, Object> value = new HashMap<>();
        value.put("a", 1);
        stringObjectHashMap.put("obj", value);


        ArrayList<Object> value1 = new ArrayList<>();
        value1.add(value);
        value1.add(value);
        stringObjectHashMap.put("arrayObj", value1);

        return stringObjectHashMap;
    }
}
