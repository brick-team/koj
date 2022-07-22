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

import com.github.brick.action.flow.model.ActionFlowFactory;
import com.github.brick.action.flow.model.enums.ExtractModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Extract factory.
 *
 * @author Zen Huifer
 */
public class ExtractActionFlowFactory
        implements ActionFlowFactory<ExtractModel, Extract> {

    Map<ExtractModel, Extract> map = new HashMap<>(8);

    @Override
    public Extract factory(ExtractModel type) {

        if (map.containsKey(type)) {
            return map.get(type);
        }
        Extract extract = null;
        switch (type) {
            case JSON_PATH:
                extract = new JsonPathExtract();
                break;
            case XPATH:
                return null;
            case JAVA_SCRIPT:
                extract = new JsExtract();
                break;
            case PYTHON:
                extract = new PythonExtract();
                break;
            case GROOVY:
                extract = new GroovyExtract();
                break;
            default:
                throw new IllegalArgumentException("参数异常，无法生成数据提取器");


        }
        this.map.put(type, extract);
        return extract;
    }
}
