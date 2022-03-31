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
@Table(name = "af_action_param_ex", schema = "action-flow", catalog = "")
public class AfActionParamExEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "action_param_id")
    private Long actionParamId;
    @Basic
    @Column(name = "param_group_id")
    private String paramGroupId;
    @Basic
    @Column(name = "ex")
    private String ex;
    @Basic
    @Column(name = "ex_id")
    private Long exId;
    @Basic
    @Column(name = "format_id")
    private Long formatId;
    @Basic
    @Column(name = "value")
    private String value;
    @Basic
    @Column(name = "flow_id")
    private Long flowId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActionParamId() {
        return actionParamId;
    }

    public void setActionParamId(Long actionParamId) {
        this.actionParamId = actionParamId;
    }

    public String getParamGroupId() {
        return paramGroupId;
    }

    public void setParamGroupId(String paramGroupId) {
        this.paramGroupId = paramGroupId;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public Long getExId() {
        return exId;
    }

    public void setExId(Long exId) {
        this.exId = exId;
    }

    public Long getFormatId() {
        return formatId;
    }

    public void setFormatId(Long formatId) {
        this.formatId = formatId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AfActionParamExEntity that = (AfActionParamExEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(actionParamId, that.actionParamId) && Objects.equals(paramGroupId, that.paramGroupId) && Objects.equals(ex, that.ex) && Objects.equals(exId, that.exId) && Objects.equals(formatId, that.formatId) && Objects.equals(value, that.value) && Objects.equals(flowId, that.flowId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, actionParamId, paramGroupId, ex, exId, formatId, value, flowId);
    }
}
