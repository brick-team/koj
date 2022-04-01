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

package com.github.brick.action.flow.web.common.ctr;

import java.util.Map;

/**
 * action flow 执行控制器
 */
public interface ActionFlowCtr {


    /**
     * 核心执行器: 根据流程id执行
     *
     * @param flowId 流程id
     * @param param  外部参数
     * @return 执行结果
     */
    Object execute(String flowId, Map<String, String> param);
}
