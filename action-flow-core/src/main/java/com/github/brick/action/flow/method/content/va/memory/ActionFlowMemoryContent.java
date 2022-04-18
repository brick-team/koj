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

package com.github.brick.action.flow.method.content.va.memory;

import com.github.brick.action.flow.execute.ActionFlowExecute;
import com.github.brick.action.flow.method.content.va.ActionFlowContent;
import com.github.brick.action.flow.method.factory.storage.StorageFactory;
import com.github.brick.action.flow.model.enums.StorageType;
import com.github.brick.action.flow.storage.api.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.FlowExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.ResultExecuteEntityStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * action flow 非文件模式， 存储媒介：内存
 *
 * @author Zen Huifer
 */
public class ActionFlowMemoryContent extends ActionFlowContent {
    private static final Logger logger =
            LoggerFactory.getLogger(ActionFlowMemoryContent.class);
    protected static StorageType storageType;

    static {
        storageType = StorageType.MEMORY;
    }


    @Override
    protected void initActionFlowExecute() {
        this.actionFlowExecute =
                new ActionFlowExecute(null, this.actionExecuteEntityStorage,
                        this.flowExecuteEntityStorage, this.resultExecuteEntityStorage);
    }

    ActionExecuteEntityStorage actionExecuteEntityStorage;
    FlowExecuteEntityStorage flowExecuteEntityStorage;
    ResultExecuteEntityStorage resultExecuteEntityStorage;

    @Override
    public void start() throws Exception {
        logger.info("action flow 非文件模式启动，存储媒介：内存");


        ActionExecuteEntityStorage actionExecuteEntityStorage = StorageFactory.factory(
                storageType, ActionExecuteEntityStorage.class);
        this.actionExecuteEntityStorage = actionExecuteEntityStorage;
        FlowExecuteEntityStorage flowExecuteEntityStorage = StorageFactory.factory(
                storageType, FlowExecuteEntityStorage.class);
        this.flowExecuteEntityStorage = flowExecuteEntityStorage;
        ResultExecuteEntityStorage resultExecuteEntityStorage = StorageFactory.factory(
                storageType, ResultExecuteEntityStorage.class);
        this.resultExecuteEntityStorage = resultExecuteEntityStorage;

        // 初始化执行器
        initActionFlowExecute();
    }
}
