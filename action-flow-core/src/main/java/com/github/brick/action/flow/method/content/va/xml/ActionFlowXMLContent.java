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

package com.github.brick.action.flow.method.content.va.xml;

import com.github.brick.action.flow.execute.ActionFlowExecute;
import com.github.brick.action.flow.method.content.va.ActionFlowContent;
import com.github.brick.action.flow.method.resource.ResourceLoader;
import com.github.brick.action.flow.method.resource.impl.XMLResourceImplLoader;
import com.github.brick.action.flow.model.enums.StorageType;
import com.github.brick.action.flow.model.xml.ActionFlowXML;
import com.github.brick.action.flow.storage.api.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.FlowExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.ResultExecuteEntityStorage;

import java.util.Map;

/**
 * action flow xml 上下文
 *
 * @author Zen Huifer
 */
public abstract class ActionFlowXMLContent extends ActionFlowContent {
    protected static StorageType storageType;
    protected ActionExecuteEntityStorage actionExecuteEntityStorage;
    protected FlowExecuteEntityStorage flowExecuteEntityStorage;
    protected ResultExecuteEntityStorage resultExecuteEntityStorage;
    ResourceLoader<ActionFlowXML, Map<String, ActionFlowXML>>
            xmlResource = new XMLResourceImplLoader();
    Map<String, ActionFlowXML> loads;
    private String[] actionFlowFileNames;

    public ActionFlowXMLContent(String[] actionFlowFileNames) throws Exception {
        this(actionFlowFileNames, false);
    }

    public ActionFlowXMLContent() {
    }

    public ActionFlowXMLContent(String[] actionFlowFileNames, boolean startMetrics)
            throws Exception {
        this.actionFlowFileNames = actionFlowFileNames;
        this.startMetrics = startMetrics;
    }

    @Override
    public void start() throws Exception {
        // 加载XML文件
        load();
        // 处理存储
        storage(this.loads);
        // 处理监控
        startMetrics(this.startMetrics);
        // 初始化执行器
        initActionFlowExecute();
    }

    protected void load() {
        try {
            Map<String, ActionFlowXML> loads = this.xmlResource.loads(
                    actionFlowFileNames);
            this.loads = loads;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initActionFlowExecute() {
        this.actionFlowExecute =
                new ActionFlowExecute(null, this.actionExecuteEntityStorage,
                        this.flowExecuteEntityStorage, this.resultExecuteEntityStorage);
    }

    protected abstract void storage(Map<String, ActionFlowXML> loads) throws Exception;

}
