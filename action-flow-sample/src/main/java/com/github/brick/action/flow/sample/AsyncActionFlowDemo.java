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

package com.github.brick.action.flow.sample;

import com.github.brick.action.flow.method.enums.FLowModel;
import com.github.brick.action.flow.method.execute.FlowExecute;
import com.github.brick.action.flow.method.execute.FlowExecuteImpl;

public class AsyncActionFlowDemo {
    public static void main(String[] args) throws Exception {
        // 两个action异步处理，最终组装数据
        long l = System.currentTimeMillis();
        FlowExecute flowExecute = new FlowExecuteImpl();
        Object execute = flowExecute.execute("async.xml", "1", FLowModel.XML);
        System.out.println(execute);
        System.out.println(System.currentTimeMillis() - l);
    }
}
