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

package com.github.brick.action.flow.extract;

import com.github.brick.action.flow.execute.extract.JsExtract;
import com.google.gson.Gson;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zen Huifer
 */
public class JsExtractTest {
    Map<Object, Object> map = new HashMap<>();
    JsExtract jsExtract = new JsExtract();
    Gson gson = new Gson();

    @Before
    public void before() {
        map.put("a", "字符串");
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        map.put("b", list);
        map.put("c", 1);

        Map<String, String> sampleData = new HashMap<>();
        sampleData.put("a", "b");
        map.put("d", sampleData);
    }

    @org.junit.Test
    public void extract() {
        String js = getString();
        System.out.println(js);
        System.out.println(gson.toJson(map));
        Object extract = jsExtract.extract(map, js);
        System.out.println(extract);
    }

    private String getString() {
        String js = "function main(o){"
                + "var a =  o['b'];"
                + "print(a);"
                + "print(sum(o['b']));"
                + "return a;"
                + "};"
                + "function sum(arr) {\n"
                + "  var s = 0;\n"
                + "  for (var i=arr.length-1; i>=0; i--) {\n"
                + "    s += arr[i];\n"
                + "  }\n"
                + "  return s;\n"
                + "}";
        return js;
    }

    @org.junit.Test
    public void testExtract() {
        Object extract = jsExtract.extract(gson.toJson(map), getString());
        System.out.println(extract);
    }
}