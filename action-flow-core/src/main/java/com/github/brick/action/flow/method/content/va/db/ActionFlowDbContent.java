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

package com.github.brick.action.flow.method.content.va.db;

import com.github.brick.action.flow.execute.ActionFlowExecute;
import com.github.brick.action.flow.method.content.va.ActionFlowContent;
import com.github.brick.action.flow.method.factory.storage.StorageFactory;
import com.github.brick.action.flow.method.resource.ResourceLoader;
import com.github.brick.action.flow.method.resource.impl.JDBCResourceLoaderImpl;
import com.github.brick.action.flow.model.config.JdbcConfig;
import com.github.brick.action.flow.model.enums.StorageType;
import com.github.brick.action.flow.storage.api.ActionExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.FlowExecuteEntityStorage;
import com.github.brick.action.flow.storage.api.ResultExecuteEntityStorage;
import com.github.brick.action.flow.storage.mysql.config.MysqlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * action flow 非文件模式， 存储媒介：mysql
 *
 * @author Zen Huifer
 */
public class ActionFlowDbContent extends ActionFlowContent {
    public static final String MYSQL_CONFIG_FILE = "action_flow_jdbc.properties";
    private static final Logger logger =
            LoggerFactory.getLogger(ActionFlowDbContent.class);
    protected static StorageType storageType;

    static {
        storageType = StorageType.MYSQL;
    }

    protected boolean beanFromSpring = false;
    protected ApplicationContext context;
    ResourceLoader<JdbcConfig, Map<String, JdbcConfig>> resourceLoader =
            new JDBCResourceLoaderImpl();
    ActionExecuteEntityStorage actionExecuteEntityStorage;
    FlowExecuteEntityStorage flowExecuteEntityStorage;
    ResultExecuteEntityStorage resultExecuteEntityStorage;
    private JdbcConfig jdbcConfig;

    public ActionFlowDbContent(JdbcConfig datasource) {
        this.jdbcConfig = datasource;
    }

    public ActionFlowDbContent(boolean beanFromSpring, ApplicationContext context) {
        this.beanFromSpring = beanFromSpring;
        this.context = context;
    }

    @Override
    protected void initActionFlowExecute() throws Exception {
        this.actionFlowExecute =
                new ActionFlowExecute(null, this.actionExecuteEntityStorage,
                        this.flowExecuteEntityStorage, this.resultExecuteEntityStorage);
        actionFlowExecute.setObjectSearchFromSpring(this.beanFromSpring);
        if (this.beanFromSpring) {
            if (context == null) {
                throw new Exception("bean希望从spring容器中寻找，但spring容器不存在");
            }
            actionFlowExecute.setContext(context);
        }
    }

    @Override
    public void start() throws Exception {
        logger.info("action flow 非文件模式启动，存储媒介：mysql");

        if (jdbcConfig == null) {
            JdbcConfig load = this.resourceLoader.load(MYSQL_CONFIG_FILE);
            this.jdbcConfig = load;
        }

        MysqlConfig.init(
                jdbcConfig.getUsername(), jdbcConfig.getPassword(), jdbcConfig.getUrl(),
                jdbcConfig.getDriver()
        );

        ActionExecuteEntityStorage actionExecuteEntityStorage = StorageFactory.factory(
                storageType, ActionExecuteEntityStorage.class);
        this.actionExecuteEntityStorage = actionExecuteEntityStorage;
        FlowExecuteEntityStorage flowExecuteEntityStorage = StorageFactory.factory(
                storageType, FlowExecuteEntityStorage.class);
        this.flowExecuteEntityStorage = flowExecuteEntityStorage;
        ResultExecuteEntityStorage resultExecuteEntityStorage = StorageFactory.factory(
                storageType, ResultExecuteEntityStorage.class);
        this.resultExecuteEntityStorage = resultExecuteEntityStorage;

        // 初始化执行器
        initActionFlowExecute();

    }
}
