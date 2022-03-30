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

@Entity
@Table(name = "af_watcher_rs")
public class AfWatcherRsEntity {
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "watcher_id")
    private String watcherId;

    @Column(name = "then_action_ids")
    private String thenActionIds;

    @Column(name = "then_api_ids")
    private String thenApiIds;

    @Column(name = "catch_action_ids")
    private String catchActionIds;

    @Column(name = "catch_api_ids")
    private String catchApiIds;

    public String getCatchApiIds() {
        return catchApiIds;
    }

    public void setCatchApiIds(String catchApiIds) {
        this.catchApiIds = catchApiIds;
    }

    public String getCatchActionIds() {
        return catchActionIds;
    }

    public void setCatchActionIds(String catchActionIds) {
        this.catchActionIds = catchActionIds;
    }

    public String getThenApiIds() {
        return thenApiIds;
    }

    public void setThenApiIds(String thenApiIds) {
        this.thenApiIds = thenApiIds;
    }

    public String getThenActionIds() {
        return thenActionIds;
    }

    public void setThenActionIds(String thenActionIds) {
        this.thenActionIds = thenActionIds;
    }

    public String getWatcherId() {
        return watcherId;
    }

    public void setWatcherId(String watcherId) {
        this.watcherId = watcherId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}