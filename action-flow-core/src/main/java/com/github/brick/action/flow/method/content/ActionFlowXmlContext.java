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

package com.github.brick.action.flow.method.content;

import com.github.brick.action.flow.execute.ActionFlowExecute;
import com.github.brick.action.flow.method.enums.StorageType;

public class ActionFlowXmlContext extends ActionFlowContent {
    public ActionFlowXmlContext(StorageType storageType, String... actionFiles) {
        super(storageType, actionFiles);
    }

    public ActionFlowXmlContext(String... actionFlowFiles) {
        super(actionFlowFiles);
    }
    /**
     * 执行
     *
     * @param fileName action flow xml 文件名称
     * @param flowId   action flow xml 文件中flow标签的id
     * @param jsonData 执行flow所需参数
     * @return 执行结果
     */
    public String execute(String fileName, String flowId, String jsonData) {
        ActionFlowExecute actionFlowExecute = new ActionFlowExecute(
                fileName,
                actionExecuteEntityStorage,
                flowExecuteEntityStorage,
                resultExecuteEntityStorage
        );


        return actionFlowExecute.execute(
                flowId,
                jsonData);
    }
}
