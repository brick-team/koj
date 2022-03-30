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
@Table(name = "af_api_param_ex", schema = "action-flow", catalog = "")
public class AfApiParamExEntity {
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "param_group")
    private String paramGroup;
    @Basic
    @Column(name = "ex")
    private String ex;
    @Basic
    @Column(name = "ex_id")
    private String exId;
    @Basic
    @Column(name = "el")
    private String el;
    @Basic
    @Column(name = "flow_id")
    private String flowId;

    @Basic
    @Column(name = "api_param_id")
    private Long apiParamId;

    public Long getApiParamId() {
        return apiParamId;
    }

    public void setApiParamId(Long apiParamId) {
        this.apiParamId = apiParamId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParamGroup() {
        return paramGroup;
    }

    public void setParamGroup(String paramGroup) {
        this.paramGroup = paramGroup;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getExId() {
        return exId;
    }

    public void setExId(String exId) {
        this.exId = exId;
    }

    public String getEl() {
        return el;
    }

    public void setEl(String el) {
        this.el = el;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AfApiParamExEntity that = (AfApiParamExEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(paramGroup, that.paramGroup) && Objects.equals(ex, that.ex) && Objects.equals(exId, that.exId) && Objects.equals(el, that.el) && Objects.equals(flowId, that.flowId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paramGroup, ex, exId, el, flowId);
    }
}
