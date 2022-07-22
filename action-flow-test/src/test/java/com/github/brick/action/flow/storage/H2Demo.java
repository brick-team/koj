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
import com.github.brick.action.flow.storage.mysql.util.ExecuteMapper;
import com.github.brick.action.flow.storage.mysql.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @author Zen Huifer
 */
public class H2Demo {
    @Before
    public void before() {

        String user = "sa";
        String password = "sa";
        String databasenameURL = "jdbc:h2:~/22;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
        String dbDriver = "org.h2.Driver";


        MybatisUtil
                mybatisUtil = new MybatisUtil(user, password, databasenameURL, dbDriver,
                PeopleMapper.class);
    }

    @Test
    public void hh() throws Exception {
        MybatisUtil gen = MybatisUtil.gen();

        gen.work(new ExecuteMapper() {
            @Override public void work(SqlSession session) throws Exception {
                Connection connection = session.getConnection();
                Statement statement = connection.createStatement();
                statement.execute(
"DROP TABLE IF EXISTS `work`;\n" + "CREATE TABLE `work` (\n"
        + "                        `id` int NOT NULL AUTO_INCREMENT COMMENT '表主键',\n"
        + "                        `action_id` int NOT NULL COMMENT 'action表关联id'\n"
        + ") ENGINE = InnoDB ROW_FORMAT = DYNAMIC;");
                connection.commit();
            }
        });

        //        gen.work(new ExecuteMapper() {
        //            @Override
        //            public void work(SqlSession session) throws Exception {
        //                Connection connection = session.getConnection();
        //                Statement statement = connection.createStatement();
        //                statement.execute("insert into grade VALUES(1,'22')");
        //                connection.commit();
        //            }
        //        });

        //        gen.work(new ExecuteMapper() {
        //            @Override
        //            public void work(SqlSession session) throws Exception {
        //                Connection connection = session.getConnection();
        //                Statement statement = connection.createStatement();
        //
        //                ResultSet rs = statement.executeQuery("select * from grade");
        //
        //                while(rs.next()){
        //                    //检索
        //                    int id  = rs.getInt("id");
        //                    String name = rs.getString("teachername");
        //
        //                    //显示
        //                    System.out.print("ID: " + id);
        //                    System.out.print(", Name: " + name);
        //                    System.out.println();
        //                }
        //
        //                System.out.println();
        //            }
        //        });

    }
}
