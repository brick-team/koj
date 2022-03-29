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

package com.github.brick.action.flow.storage.api;

import com.github.brick.action.flow.method.entity.*;

public interface AllStorage {

    boolean add(AllEntity allEntity);


    AllEntity findByUid(String groupId);

    ParamsEntity findParamsByUid(String groupId);

    ActionsEntity findActionsByUid(String groupId);

    WatchersEntity findWatchersByUid(String groupId);

    ResultEntity findResultByUid(String groupId);

    ExtractsEntity findExtractsByUid(String groupId);

    FlowsEntity findFlowsByUid(String groupId);
}
