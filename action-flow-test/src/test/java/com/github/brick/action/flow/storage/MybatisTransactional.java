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

package com.github.brick.action.flow.storage;

import com.github.brick.action.flow.storage.mapper.PeopleMapper;
import com.github.brick.action.flow.storage.mysql.util.MybatisUtil;
import com.mysql.cj.jdbc.Driver;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Zen Huifer
 */
public class MybatisTransactional {

    @Test
    public void session() throws Exception {
        MybatisUtil.gen().work(session -> {
            extracted1();
            extracted2();

        });


    }

    private void extracted1() throws Exception {
        SqlSession session = MybatisUtil.getThreadLocalSqlSession();
        PeopleMapper mapper = session.getMapper(PeopleMapper.class);
        mapper.insert(1, "f");
//            int i = 1 / 0;
    }

    private void extracted2() throws Exception {
        SqlSession session = MybatisUtil.getThreadLocalSqlSession();

        PeopleMapper mapper = session.getMapper(PeopleMapper.class);
        mapper.insert(1, "2");
        int i = 1 / 0;
    }


    @Before
    public void extracted() {
        String user = "root";
        String password = "root123@";
        String databasenameURL = "jdbc:mysql://localhost:3306";
        String dbDriver = Driver.class.getName();


        MybatisUtil mybatisUtil = new MybatisUtil(user, password, databasenameURL, dbDriver, PeopleMapper.class);
    }
}
