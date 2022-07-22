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

import com.github.brick.action.flow.execute.extract.PythonExtract;
import com.google.gson.Gson;
import org.junit.Before;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zen Huifer
 */
public class PythonExtractTest {
    Map<Object, Object> map = new HashMap<>();
    PythonExtract jsExtract = new PythonExtract();
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

        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec(getString());
        // python脚本中的方法名为main
        PyFunction function = interpreter.get("main", PyFunction.class);

        // 将参数代入并执行python脚本
        PyDictionary pyDictionary = new PyDictionary();
        map.forEach(
                (k,v)->{
                    pyDictionary.put(k, v);
                }
        );
        PyList list = new PyList();

        PyInteger pyInteger = new PyInteger(1);
        PyObject o = function.__call__(pyDictionary);

        Object o1 = o.__tojava__(Object.class);
        System.out.println(o1);

    }

    private String getString() {
        String python = "def main(o):\n"
                + "    a =  o.get(\"b\")\n"
                + "    return a;";

        return python;
    }


}