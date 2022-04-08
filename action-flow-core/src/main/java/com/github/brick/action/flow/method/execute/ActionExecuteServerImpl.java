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

package com.github.brick.action.flow.method.execute;

import com.github.brick.action.flow.execute.action.ActionExecuteServer;
import com.github.brick.action.flow.method.entity.api.ParamIn;
import com.github.brick.action.flow.method.enums.ActionType;
import com.github.brick.action.flow.model.execute.ActionExecuteEntity;
import com.github.brick.action.flow.model.execute.ExtractExecuteEntity;
import com.github.brick.action.flow.model.execute.ParamExecuteEntity;

import java.util.List;

public class ActionExecuteServerImpl implements ActionExecuteServer {

    @Override
    public String execute(ActionExecuteEntity execute) {
        ActionType type = execute.getType();
        if (type == ActionType.REST_API) {
            ActionExecuteEntity.ForRestApi restApi = execute.getRestApi();

            String url = restApi.getUrl();
            String method = restApi.getMethod();
            List<ParamExecuteEntity> param = restApi.getParam();

            for (ParamExecuteEntity paramExecuteEntity : param) {
                ParamExecuteEntity.ForRestApi restApi1 = paramExecuteEntity.getRestApi();
                ParamIn in = restApi1.getIn();
                ExtractExecuteEntity extract = restApi1.getExtract();
                String name = restApi1.getName();
                String value = restApi1.getValue();

            }

        }
        else if (type == ActionType.JAVA_METHOD) {

        }
        return null;
    }
}
