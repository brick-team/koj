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

package com.github.brick.action.flow.web.common.config;

import com.github.brick.action.flow.model.config.JdbcConfig;
import com.github.brick.action.flow.model.enums.StorageType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * action flow configuration
 */
@ConfigurationProperties(prefix = "action-flow")
public class ActionFlowConfiguration {
    /**
     * 安全配置
     */
    private SecurityConfiguration security;

    /**
     * 存储配置
     */
    private StorageConfiguration storage;

    private DBConfiguration datasource;

    private DiscoveryClient discovery;

    public DiscoveryClient getDiscovery() {
        return discovery;
    }

    public void setDiscovery(DiscoveryClient discovery) {
        this.discovery = discovery;
    }

    public DBConfiguration getDatasource() {
        return datasource;
    }

    public void setDatasource(DBConfiguration datasource) {
        this.datasource = datasource;
    }

    public StorageConfiguration getStorage() {
        return storage;
    }

    public void setStorage(StorageConfiguration storage) {
        this.storage = storage;
    }

    public SecurityConfiguration getSecurity() {
        return security;
    }

    public void setSecurity(SecurityConfiguration security) {
        this.security = security;
    }


    public static class StorageConfiguration {
        private StorageType type;

        public StorageType getType() {
            return type;
        }

        public void setType(StorageType type) {
            this.type = type;
        }
    }


    public static class SecurityConfiguration {
        private boolean enable;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }


    public static class DBConfiguration extends JdbcConfig {

    }

    public static class DiscoveryClient {
        private boolean enable;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }



}
