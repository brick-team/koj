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
@Table(name = "af_api_param", schema = "action-flow", catalog = "")
public class AfApiParamEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "api_id")
    private String apiId;
    @Basic
    @Column(name = "pid")
    private Long pid;
    @Basic
    @Column(name = "in")
    private String in;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "require")
    private String require;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AfApiParamEntity that = (AfApiParamEntity) o;
        return id == that.id && pid == that.pid && Objects.equals(apiId, that.apiId) && Objects.equals(in, that.in) && Objects.equals(name, that.name) && Objects.equals(require, that.require);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apiId, pid, in, name, require);
    }
}
