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
@Table(name = "af_action", schema = "action-flow", catalog = "")
public class AfActionEntity {
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "clazz_str")
    private String clazzStr;
    @Basic
    @Column(name = "method_str")
    private String methodStr;
    @Basic
    @Column(name = "async")
    private boolean async;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClazzStr() {
        return clazzStr;
    }

    public void setClazzStr(String clazzStr) {
        this.clazzStr = clazzStr;
    }

    public String getMethodStr() {
        return methodStr;
    }

    public void setMethodStr(String methodStr) {
        this.methodStr = methodStr;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AfActionEntity that = (AfActionEntity) o;
        return async == that.async && Objects.equals(id, that.id) && Objects.equals(clazzStr, that.clazzStr) && Objects.equals(methodStr, that.methodStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clazzStr, methodStr, async);
    }
}
