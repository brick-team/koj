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

package com.github.brick.action.flow.web.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * action flow 安全相关配置
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "action-flow.security.enable", matchIfMissing = false)
public class ActionFlowSecurityAutoConfiguration {
    public static final String ACTION_FLOW_URL_PRE = "/brick/action_flow/";
    private static final Logger logger = LoggerFactory.getLogger(ActionFlowSecurityAutoConfiguration.class);

    @Value("${server.servlet.context-path:}")
    private String servletContextPath;

    public ActionFlowSecurityAutoConfiguration() {
    }

    @Bean
    public FilterRegistrationBean<ActionFlowSecurityFilter> getFilterRegistrationBean() {
        logger.debug("注册 Action Flow 安全过滤器");
        FilterRegistrationBean<ActionFlowSecurityFilter> bean = new FilterRegistrationBean(new ActionFlowSecurityFilter());
        bean.addUrlPatterns(servletContextPath + ACTION_FLOW_URL_PRE);
        return bean;
    }

    @Bean
    public SecurityController securityController() {
        logger.debug("实例化 SecurityController");
        return new SecurityController();
    }


}
