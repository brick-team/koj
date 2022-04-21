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

package com.github.brick.action.flow.storage.mysql.mapper;

import com.github.brick.action.flow.model.entity.Action;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author xupenggao
 * @version 1.0
 * @description action表Mapper文件
 * @date 2022/4/21
 */

@Mapper
public interface ActionMapper {

    /**
     * 批量插入
     *
     * @param list 数据列表
     * @return int
     */
    int insertAll(@Param("list") List<Action> list);

    /**
     * 查询通过id
     *
     * @param id id
     * @return {@link List}<{@link Action}>
     */
    List<Action> queryById(@Param("id") Serializable id);

}
