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

package com.github.brick.action.flow.core;

import com.github.brick.action.flow.method.enums.FLowModel;
import com.github.brick.action.flow.method.execute.FlowExecute;
import com.github.brick.action.flow.method.execute.FlowExecuteImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowExecuteImplTest {
    private static final Logger logger = LoggerFactory.getLogger(FlowExecuteImplTest.class);
    FlowExecute flowExecute = new FlowExecuteImpl();


    @Test
    public void execute() throws Exception {
        Object execute = flowExecute.execute("fl.xml", "1", FLowModel.XML);
        logger.info("fl执行结果={}", execute);
    }

    @Test
    public void execute2() throws Exception {
        flowExecute.execute("f2.xml", "1", FLowModel.XML);
    }
    @Test
    public void execute3() throws Exception{
        flowExecute.execute("api-flow.xml", "1", FLowModel.XML);

    }
}