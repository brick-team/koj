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

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

/**
 * 通过执行 JS 代码来获取数据。
 *
 * @author Zen Huifer
 */
public class JsExtract implements Extract {

    public static final String RUN_METHOD_NAME = "main";

    public static Invocable invocable;
    public static ScriptEngine scriptEngine;


    static {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        scriptEngine = engine;
        if (!(engine instanceof Invocable)) {
            throw new RuntimeException("不支持调用方法");
        }
        invocable = (Invocable) engine;

    }

    @Override
    public Object extract(Object o, String el) {
        try {
            scriptEngine.eval(el);
            return invocable.invokeFunction(RUN_METHOD_NAME, o);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

    Gson gson = new Gson();


    @Override
    public Object extract(String json, String el) {
        try {
            scriptEngine.eval(el);
            return invocable.invokeFunction(RUN_METHOD_NAME, gson.fromJson(json, Map.class));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
