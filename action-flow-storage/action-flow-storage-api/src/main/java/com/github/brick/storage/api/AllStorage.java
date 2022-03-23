package com.github.brick.storage.api;

import com.github.brick.action.flow.method.entity.*;

public interface AllStorage {

    boolean add(AllEntity allEntity);


    AllEntity findByUid(String uid);

    ParamsEntity findParamsByUid(String uid);

    ActionsEntity findActionsByUid(String uid);

    WatchersEntity findWatchersByUid(String uid);

    ResultEntity findResultByUid(String uid);

    ExtractsEntity findExtractsByUid(String uid);

    FlowsEntity findFlowsByUid(String uid);
}
