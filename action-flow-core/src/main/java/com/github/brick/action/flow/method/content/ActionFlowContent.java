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
import com.github.brick.action.flow.model.execute.ActionExecuteEntity;
import com.github.brick.action.flow.model.execute.FlowExecuteEntity;
import com.github.brick.action.flow.model.execute.ResultExecuteEntity;
import com.github.brick.action.flow.model.xml.ActionFlowXML;
import com.github.brick.action.flow.parse.api.ActionFlowXMLParseApi;
import com.github.brick.action.flow.parse.xml.ActionFlowXMLParseApiImpl;
import com.github.brick.action.flow.storage.api.nv.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.nv.FlowExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.nv.ResultExecuteEntityStorage;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class ActionFlowContent {
    private static final Logger logger = LoggerFactory.getLogger(ActionFlowContent.class);
    private final String[] actionFlowFileNames;
    private final Injector injector;

    protected StorageType storageType;
    protected ActionExecuteEntityStorage actionExecuteEntityStorage;
    protected FlowExecuteEntityStorage flowExecuteEntityStorage;
    protected ResultExecuteEntityStorage resultExecuteEntityStorage;

    public ActionFlowContent(StorageType storageType, String[] actionFiles) {

        this.actionFlowFileNames = actionFiles;
        this.storageType = storageType;
        injector = Guice.createInjector(new ActionFlowGuiceModule());

        if (storageType == StorageType.MYSQL) {
            configJpa(false);
        }


        actionExecuteEntityStorage = StorageFactory.factory(this.storageType, ActionExecuteEntityStorage.class);
        flowExecuteEntityStorage = StorageFactory.factory(this.storageType, FlowExecuteEntityStorage.class);
        resultExecuteEntityStorage = StorageFactory.factory(this.storageType, ResultExecuteEntityStorage.class);
        loads(this.actionFlowFileNames);
    }

    /**
     * 配置 JPA 相关
     */
    protected void configJpa(boolean isSpring) {

    }

    public ActionFlowContent(String... actionFlowFiles) {
        this(StorageType.MEMORY, actionFlowFiles);
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
        ActionFlowXML parse = instance.parseFile(actionFlowXmlFineName);
        storage(actionFlowXmlFineName, parse);
    }

    /**
     * 进行存储操作
     *
     * @param fileName      文件名称
     * @param actionFlowXML action flow xml
     */
    private void storage(String fileName, ActionFlowXML actionFlowXML) {
        List<ActionExecuteEntity> actions = actionFlowXML.getActions();
        List<FlowExecuteEntity> flows = actionFlowXML.getFlows();
        List<ResultExecuteEntity> results = actionFlowXML.getResults();
        actionExecuteEntityStorage.save(fileName, actions);
        flowExecuteEntityStorage.save(fileName, flows);
        resultExecuteEntityStorage.save(fileName, results);
    }


    private static class ActionFlowGuiceModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(ActionFlowXMLParseApi.class).to(ActionFlowXMLParseApiImpl.class);
            super.configure();
        }
    }
}
