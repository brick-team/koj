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

package com.github.brick.action.flow.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Timer;

/**
 * common metrics
 */
public abstract class CommonMetrics implements Named {

    static Timer timer;
    static Counter counter;


    public CommonMetrics() {

    timer = new Timer();
        counter = new Counter();
        ActionFlowMetricRegistry.register(name() + "-timer", timer);
        ActionFlowMetricRegistry.register(name() + "-counter", counter);
    }

    public Object metrics(ActionFlowMetricsBase ActionFlowMetricsBase) {

        Timer.Context time = timer.time();
        try {
            return ActionFlowMetricsBase.ex();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            counter.inc();
            time.stop();
        }
        return null;

    }
}
