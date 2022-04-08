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

import com.github.brick.action.flow.method.enums.StorageType;
import com.github.brick.action.flow.method.factory.storage.StorageFactory;
import com.github.brick.action.flow.storage.api.nv.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.nv.FlowExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.nv.ResultExecuteEntityStorage;

import java.io.Serializable;

public class ActionFlowDbContext extends ActionFlowContent {


    public ActionFlowDbContext() {
        this.storageType = StorageType.MYSQL;
        actionExecuteEntityStorage = StorageFactory.factory(this.storageType, ActionExecuteEntityStorage.class);
        flowExecuteEntityStorage = StorageFactory.factory(this.storageType, FlowExecuteEntityStorage.class);
        resultExecuteEntityStorage = StorageFactory.factory(this.storageType, ResultExecuteEntityStorage.class);
    }

    public String execute(Serializable flowId, String jsonData) {
        return null;
    }
}
