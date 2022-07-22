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

    /**
     * 用于启动
     * 注意:<b>需要在子类中调用 {@link ActionFlowContent#initActionFlowExecute()} 方法完成成员变量 {@link ActionFlowContent#actionFlowExecute} </b>
     *
     * @throws Exception
     */
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

    /**
     * 核心执行方法
     */
    public String execute(String fileName, Serializable flowId, String jsonData) {
        if (this.actionFlowExecute != null) {
            actionFlowExecute.setFileName(fileName);
            return actionFlowExecute.execute(flowId, jsonData);
        }
        throw new RuntimeException("执行器为空无法执行");
    }

    /**
     * 核心执行方法
     */
    public String execute(Serializable flowId, String jsonData) {
        if (this.actionFlowExecute != null) {
            return actionFlowExecute.execute(flowId, jsonData);
        }
        throw new RuntimeException("执行器为空无法执行");
    }

    /**
     * 用于初始化成员变量 {@link ActionFlowContent#actionFlowExecute}
     */
    protected abstract void initActionFlowExecute() throws Exception;


}
