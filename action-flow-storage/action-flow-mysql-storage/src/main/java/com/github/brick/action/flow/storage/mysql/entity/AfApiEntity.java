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
@Table(name = "af_api", schema = "action-flow", catalog = "")
public class AfApiEntity {
        @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "url")
    private String url;
    @Basic
    @Column(name = "method")
    private String method;
    @Basic
    @Column(name = "desca")
    private String desca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDesca() {
        return desca;
    }

    public void setDesca(String desca) {
        this.desca = desca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AfApiEntity that = (AfApiEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(url, that.url) && Objects.equals(method, that.method) && Objects.equals(desca, that.desca);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, method, desca);
    }
}
