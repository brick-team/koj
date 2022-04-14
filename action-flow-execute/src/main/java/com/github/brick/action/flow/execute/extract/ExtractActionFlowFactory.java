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

import com.github.brick.action.flow.model.enums.ExtractModel;
import com.github.brick.action.flow.model.ActionFlowFactory;

/**
 * Extract factory.
 * @author Zen Huifer
 */
public class ExtractActionFlowFactory
        implements ActionFlowFactory<ExtractModel, Extract> {
    @Override
    public Extract factory(ExtractModel type) {
        Extract extract = null;
        switch (type) {
            case JSON_PATH:
                extract = new JsonPathExtract();
                break;
            default:
                throw new IllegalArgumentException("参数异常，无法生成数据提取器");


        }
        return extract;
    }
}
