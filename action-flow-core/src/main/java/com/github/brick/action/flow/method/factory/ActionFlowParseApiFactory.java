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

package com.github.brick.action.flow.method.factory;

import com.github.brick.action.flow.method.enums.FLowModel;
import com.github.brick.action.flow.method.parse.XMLActionFlowMethodParseApi;
import com.github.brick.action.flow.parse.api.ActionFlowMethodParseApi;

public class ActionFlowParseApiFactory<Type extends com.github.brick.action.flow.method.enums.Type, T>
        implements Factory<FLowModel, ActionFlowMethodParseApi> {


    @Override
    public ActionFlowMethodParseApi gen(FLowModel type) {
        ActionFlowMethodParseApi actionFlowMethodParseApi = null;
        if (type == FLowModel.XML) {
            actionFlowMethodParseApi = new XMLActionFlowMethodParseApi();
        } else {
            throw new IllegalArgumentException("参数异常，无法生成文件解析器");
        }
        return actionFlowMethodParseApi;
    }
}
