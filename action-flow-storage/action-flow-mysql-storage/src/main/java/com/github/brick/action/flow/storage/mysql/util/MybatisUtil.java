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

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Zen Huifer
 */
public class MybatisUtil {

    static ThreadLocal<SqlSession> sqlSessionThreadLocal = new InheritableThreadLocal<>();
    private static MybatisUtil mybatisUtil;
    private final String user;
    private final String password;
    private final String url;
    private final String dbDriver;
    private SqlSessionFactory sqlSessionFactory;

    public static final String MAPPER_PATH = "mapper";

    public MybatisUtil(String user, String password, String url, String dbDriver,
            Class<?>... clazz) {
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

    public static SqlSession getThreadLocalSqlSession() {
        return sqlSessionThreadLocal.get();
    }

    private void initSqlSessionFactory(Class<?>... clazz) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory,
                dataSource());
        Configuration configuration = new Configuration(environment);
        for (Class<?> aClass : clazz) {

            configuration.addMapper(aClass);
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(
                configuration);
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL resource = cl.getResource(MAPPER_PATH);
        File file = new File(resource.getFile());

        File[] files = file.listFiles();
        for (File file1 : files) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file1);
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(fileInputStream,
                        configuration, file1.getAbsolutePath(),
                        configuration.getSqlFragments());
                xmlMapperBuilder.parse();
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        this.sqlSessionFactory = sqlSessionFactory;
    }

    private DataSource dataSource() {
        return new org.apache.ibatis.datasource.pooled.PooledDataSource(dbDriver, url, user, password);
    }

    private void close(SqlSession session) {
        sqlSessionThreadLocal.remove();
        session.close();
    }

    private SqlSession open() {
        SqlSession sqlSession1 = sqlSessionThreadLocal.get();
        if (sqlSession1 == null) {

            SqlSession sqlSession = this.sqlSessionFactory.openSession();
            sqlSessionThreadLocal.set(sqlSession);
            return sqlSession;
        }
        else {
            return sqlSession1;
        }
    }

    public void work(ExecuteMapper executeMapper) throws Exception {

        SqlSession open = open();
        try {
            executeMapper.work(open);
            open.commit();
        } catch (Exception e) {
            open.rollback();
            throw e;
        } finally {
            close(open);
        }
    }
}
