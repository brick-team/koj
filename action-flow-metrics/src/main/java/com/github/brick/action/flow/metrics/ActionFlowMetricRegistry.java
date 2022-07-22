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

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * action flow metric register
 * @author Zen Huifer
 */
public class ActionFlowMetricRegistry {
    static MetricRegistry metricRegistry;
    static Slf4jReporter slf4jReporter;
    static ConsoleReporter consoleReporter;

    static {
        metricRegistry = new MetricRegistry();
        slf4jReporter = slf4jReporter(metricRegistry);
        consoleReporter = consoleReporter(metricRegistry);
    }

    public static Slf4jReporter getSlf4jReporter() {
        return slf4jReporter;
    }

    public static ConsoleReporter getConsoleReporter() {
        return consoleReporter;
    }

    private static Slf4jReporter slf4jReporter(MetricRegistry metrics) {
        return Slf4jReporter.forRegistry(metrics).outputTo(LoggerFactory.getLogger(ActionFlowMetricRegistry.class)).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
    }

    private static ConsoleReporter consoleReporter(MetricRegistry metrics) {
        return ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
    }

    public static <T extends Metric> void register(String name, T metric) {
        metricRegistry.register(name, metric);
    }
}
