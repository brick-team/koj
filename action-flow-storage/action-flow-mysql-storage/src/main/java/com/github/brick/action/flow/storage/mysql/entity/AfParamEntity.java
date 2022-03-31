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

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "af_param", schema = "action-flow", catalog = "")
public class AfParamEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "`id`")
    private Long id;
    @Basic
    @Column(name = "`group`")
    private String group;
    @Basic
    @Column(name = "`key`")
    private String key;

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flow_id) {
        this.flowId = flow_id;
    }

    @Basic
    @Column(name = "`value`")
    private String value;
    @Basic
    @Column(name = "`flow_id`")
    private Long flowId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AfParamEntity that = (AfParamEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(group, that.group) && Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, key, value);
    }
}
