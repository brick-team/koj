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
@Table(name = "af_work", schema = "action-flow", catalog = "")
public class AfWorkEntity {
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "ref_id")
    private String refId;
    @Basic
    @Column(name = "thens")
    private String thens;
    @Basic
    @Column(name = "catchs")
    private String catchs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getThens() {
        return thens;
    }

    public void setThens(String thens) {
        this.thens = thens;
    }

    public String getCatchs() {
        return catchs;
    }

    public void setCatchs(String catchs) {
        this.catchs = catchs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AfWorkEntity that = (AfWorkEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(type, that.type) && Objects.equals(refId, that.refId) && Objects.equals(thens, that.thens) && Objects.equals(catchs, that.catchs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, refId, thens, catchs);
    }
}
