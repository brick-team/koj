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

package com.github.brick.action.flow.web.common.init;

import com.github.brick.action.flow.method.content.va.db.ActionFlowDbContent;
import com.github.brick.action.flow.method.content.va.memory.ActionFlowMemoryContent;
import com.github.brick.action.flow.web.common.config.ActionFlowConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Action flow 初始化
 *
 * @author Zen Huifer
 */
@Component
public class ActionFlowContentInit {

    @ConditionalOnProperty(value = "action-flow.storage.type", havingValue = "mysql")
    @Bean
    public ActionFlowDbContent actionFlowMysqlContent(
            @Autowired ActionFlowConfiguration actionFlowConfiguration
    ) throws Exception {

        ActionFlowDbContent actionFlowDbContent =
                new ActionFlowDbContent(actionFlowConfiguration.getDatasource());
        actionFlowDbContent.start();
        return actionFlowDbContent;
    }

    @ConditionalOnProperty(value = "action-flow.storage.type", havingValue = "memory")
    @Bean
    public ActionFlowMemoryContent actionFlowMemoryContent(
    ) throws Exception {
        ActionFlowMemoryContent actionFlowMemoryContent = new ActionFlowMemoryContent();
        actionFlowMemoryContent.start();
        return actionFlowMemoryContent;
    }



}
