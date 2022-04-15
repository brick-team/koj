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

package com.github.brick.action.flow.method.content.va;

import com.github.brick.action.flow.method.resource.Resource;
import com.github.brick.action.flow.method.resource.XMLResource;
import com.github.brick.action.flow.metrics.ActionFlowMetricRegistry;
import com.github.brick.action.flow.model.enums.StorageType;
import com.github.brick.action.flow.model.xml.ActionFlowXML;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Zen Huifer
 */
public abstract class ActionFlowContent {

    Resource xmlResource = new XMLResource();
    Map<String, ActionFlowXML> loads;
    private String[] actionFlowFileNames;
    private boolean startMetrics = false;

    public ActionFlowContent(String[] actionFlowFileNames) {
        this(actionFlowFileNames, false);
    }

    public ActionFlowContent(String[] actionFlowFileNames, boolean startMetrics) {
        this.actionFlowFileNames = actionFlowFileNames;
        this.startMetrics = startMetrics;
        start();
    }

    public ActionFlowContent() {
    }

    public void start() {
        load();
        storage(this.loads);
        startMetrics(this.startMetrics);
    }

    /**
     * 启动监控相关
     *
     * @param b 是否启用,默认采用false
     */
    protected void startMetrics(boolean b) {
        if (b) {
            ActionFlowMetricRegistry.getConsoleReporter().start(1, TimeUnit.SECONDS);
            ActionFlowMetricRegistry.getSlf4jReporter().start(1, TimeUnit.SECONDS);
        }
    }

    protected abstract void storage(Map<String, ActionFlowXML> loads);

    protected void load() {
        try {
            Map<String, ActionFlowXML> loads = this.xmlResource.loads(actionFlowFileNames);
            this.loads = loads;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
