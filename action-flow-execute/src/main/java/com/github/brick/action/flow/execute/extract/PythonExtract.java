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
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.util.Collection;
import java.util.Map;

/**
 * 通过执行 python 代码来获取数据。
 *
 * @author Zen Huifer
 */
public class PythonExtract implements Extract {
    public static final String RUN_METHOD_NAME = "main";
    PythonInterpreter interpreter = new PythonInterpreter();
    Gson gson = new Gson();

    @Override
    public Object extract(Object o, String el) {
        interpreter.exec(el);
        PyFunction function = interpreter.get(RUN_METHOD_NAME, PyFunction.class);

        org.python.core.PyObject po = null;

        // TODO: 2022/4/19 多种类型处理
        if (o instanceof Map) {
            PyDictionary pyDictionary = new PyDictionary();
            ((Map<?, ?>) o).forEach((k, v) -> {
                pyDictionary.put(k, v);
            });
            po = pyDictionary;
        } else if (o instanceof Collection) {
            PyList pyList = new PyList();
            ((Collection<?>) o).forEach(s -> {
                pyList.add(s);
            });
            po = pyList;
        } else if (o instanceof String) {
            po = new PyString((String) o);
        } else if (o instanceof Integer) {
            po = new PyInteger((Integer) o);
        } else if (o instanceof Double) {
            po = new PyFloat((Double) o);
        } else if (o instanceof Float) {
            po = new PyFloat((Float) o);
        } else if (o instanceof Boolean) {
            po = new PyBoolean((Boolean) o);
        }


        PyObject pyRes = function.__call__(po);

        Object o1 = pyRes.__tojava__(Object.class);


        return o1;
    }

    @Override
    public Object extract(String json, String el) {
        Map map = gson.fromJson(json, Map.class);
        return extract(map, el);
    }
}
