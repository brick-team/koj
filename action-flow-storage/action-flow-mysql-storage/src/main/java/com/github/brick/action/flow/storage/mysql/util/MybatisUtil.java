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

package com.github.brick.action.flow.storage.mysql.util;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

/**
 * @author Zen Huifer
 */
public class MybatisUtil {

    private static MybatisUtil mybatisUtil;
    private final String user;
    private final String password;
    private final String url;
    private final String dbDriver;
    private SqlSessionFactory sqlSessionFactory;

    public MybatisUtil(String user, String password, String url, String dbDriver, Class<?>... clazz) {
        this.user = user;
        this.password = password;
        this.url = url;
        this.dbDriver = dbDriver;
        initSqlSessionFactory(clazz);
        mybatisUtil = this;

    }

    public static MybatisUtil gen() {
        return mybatisUtil;
    }


    private void initSqlSessionFactory(Class<?>... clazz) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource());
        Configuration configuration = new Configuration(environment);
        for (Class<?> aClass : clazz) {

            configuration.addMapper(aClass);
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        this.sqlSessionFactory = sqlSessionFactory;
    }


    private DataSource dataSource() {
        return new org.apache.ibatis.datasource.pooled.PooledDataSource(dbDriver, url, user, password);
    }

    private void close(SqlSession session) {
        session.close();
    }


    private SqlSession open() {
        return this.sqlSessionFactory.openSession();
    }

    public void work(ExecuteMapper executeMapper) {

        SqlSession open = open();
        try {
            executeMapper.work(open);
            open.commit();
        } catch (Exception e) {
            open.rollback();
            e.printStackTrace();
        } finally {
            close(open);
        }
    }
}
