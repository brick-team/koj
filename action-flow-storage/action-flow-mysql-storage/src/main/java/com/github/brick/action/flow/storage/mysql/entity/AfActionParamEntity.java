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

package com.github.brick.action.flow.storage.mysql.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "af_action_param", schema = "action-flow", catalog = "")
public class AfActionParamEntity {
        @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @Column(name = "`id`")
    private Long id;
    @Basic
    @Column(name = "`action_id`")
    private Long actionId;
    @Basic
    @Column(name = "`arg_name`")
    private String argName;
    @Basic
    @Column(name = "`index`")
    private Integer index;
    @Basic
    @Column(name = "`type`")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getArgName() {
        return argName;
    }

    public void setArgName(String argName) {
        this.argName = argName;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AfActionParamEntity that = (AfActionParamEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(actionId, that.actionId) && Objects.equals(argName, that.argName) && Objects.equals(index, that.index) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, actionId, argName, index, type);
    }
}
