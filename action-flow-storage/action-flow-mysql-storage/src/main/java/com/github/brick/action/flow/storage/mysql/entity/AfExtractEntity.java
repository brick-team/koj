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
@Table(name = "af_extract", schema = "action-flow", catalog = "")
public class AfExtractEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "from_action")
    private String fromAction;
    @Basic
    @Column(name = "from_api")
    private String fromApi;
    @Basic
    @Column(name = "el")
    private String el;
    @Basic
    @Column(name = "el_type")
    private String elType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromAction() {
        return fromAction;
    }

    public void setFromAction(String fromAction) {
        this.fromAction = fromAction;
    }

    public String getFromApi() {
        return fromApi;
    }

    public void setFromApi(String fromApi) {
        this.fromApi = fromApi;
    }

    public String getEl() {
        return el;
    }

    public void setEl(String el) {
        this.el = el;
    }

    public String getElType() {
        return elType;
    }

    public void setElType(String elType) {
        this.elType = elType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AfExtractEntity that = (AfExtractEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(fromAction, that.fromAction) && Objects.equals(fromApi, that.fromApi) && Objects.equals(el, that.el) && Objects.equals(elType, that.elType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromAction, fromApi, el, elType);
    }
}
