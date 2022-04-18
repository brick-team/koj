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

import com.github.brick.action.flow.execute.ActionFlowExecute;
import com.github.brick.action.flow.metrics.ActionFlowMetricRegistry;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author Zen Huifer
 */
public abstract class ActionFlowContent {

    protected boolean startMetrics = false;
    protected ActionFlowExecute actionFlowExecute;

    public abstract void start() throws Exception;

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

    protected String execute(String fileName, Serializable flowId, String jsonData) {
        if (this.actionFlowExecute != null) {
            actionFlowExecute.setFileName(fileName);
            return actionFlowExecute.execute(flowId, jsonData);
        }
        throw new RuntimeException("执行器为空无法执行");
    }

    protected abstract void initActionFlowExecute();


}
