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

package com.github.brick.action.flow.method.resource.impl;

import com.github.brick.action.flow.method.resource.JDBCResourceLoader;
import com.github.brick.action.flow.model.config.JdbcConfig;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 从properties文件中读取
 * @author Zen Huifer
 */
public class JDBCResourceLoaderImpl implements JDBCResourceLoader {

    public static final String URL_KEY = "url";
    public static final String DRIVER_KEY = "driver";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";

    @Override
    public Map<String, JdbcConfig> loads(String... files) throws Exception {
        Map<String, JdbcConfig> stringJdbcConfigMap = new HashMap<>();
        for (String file : files) {
            JdbcConfig load = load(file);
            stringJdbcConfigMap.put(file, load);
        }
        return stringJdbcConfigMap;
    }

    @Override
    public JdbcConfig load(String file) throws Exception {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream resourceAsStream = cl.getResourceAsStream(file);
        Properties properties = new Properties();
        properties.load(resourceAsStream);


        JdbcConfig jdbcConfig = new JdbcConfig();
        jdbcConfig.setUrl(properties.getProperty(URL_KEY));
        jdbcConfig.setDriver(properties.getProperty(DRIVER_KEY));
        jdbcConfig.setUsername(properties.getProperty(USERNAME_KEY));
        jdbcConfig.setPassword(properties.getProperty(PASSWORD_KEY));


        return jdbcConfig;
    }
}
