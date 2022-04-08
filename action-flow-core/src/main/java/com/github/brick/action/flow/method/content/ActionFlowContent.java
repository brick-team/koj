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
import com.github.brick.action.flow.model.xml.ActionFlowXML;
import com.github.brick.action.flow.parse.api.ActionFlowXMLParseApi;
import com.github.brick.action.flow.parse.xml.ActionFlowXMLParseApiImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionFlowContent {
    private static final Logger logger = LoggerFactory.getLogger(ActionFlowContent.class);
    private String[] actionFlowFileNames;
    private Injector injector;

    private StorageType storageType;

    public ActionFlowContent(StorageType storageType, String[] actionFiles) {

        this.actionFlowFileNames = actionFiles;
        this.storageType = storageType;
        injector = Guice.createInjector(new ActionFlowGuiceModule());
        loads(this.actionFlowFileNames);
    }

    public ActionFlowContent() {
    }

    public ActionFlowContent(String... actionFlowFiles) {
        this(StorageType.MEMORY, actionFlowFiles);
    }

    public String[] getActionFlowFileNames() {
        return actionFlowFileNames;
    }

    private void loads(String... actionFlowFileNames) {
        for (String actionFlowFileName : actionFlowFileNames) {
            load(actionFlowFileName);
        }
    }

    private void load(String actionFlowFineName) {
        if (actionFlowFineName.endsWith(".xml")) {
            try {
                loadXml(actionFlowFineName);
            } catch (Exception e) {
                logger.error("action-flow xml文档解析错误， ", e);
            }
        }
    }

    private void loadXml(String actionFlowXmlFineName) throws Exception {
        ActionFlowXMLParseApi instance = injector.getInstance(ActionFlowXMLParseApi.class);
        ActionFlowXML parse = instance.parse(actionFlowXmlFineName);
        StorageType storageType = this.storageType;
    }

    private static class ActionFlowGuiceModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(ActionFlowXMLParseApi.class).to(ActionFlowXMLParseApiImpl.class);
            super.configure();
        }
    }
}
