package com.github.brick.action.flow.storage.redis;

import com.github.brick.action.flow.method.entity.WatcherEntity;
import com.github.brick.action.flow.method.entity.ActionEntity;
import com.github.brick.action.flow.method.entity.ParamEntity;

import java.util.ArrayList;

import com.github.brick.action.flow.method.entity.*;
import com.github.brick.storage.api.*;

public class AllRedisStorage implements AllStorage {

    ParamStorage paramStorage = new ParamRedisStorage();
    ActionStorage actionStorage = new ActionRedisStorage();
    WatcherStorage watcherStorage = new WatcherRedisStorage();
    ResultStorage resultStorage = new ResultResultStorage();
    ExtractStorage extractStorage = new ExtractRedisStorage();
    FlowStorage flowStorage = new FlowRedisStorage();

    @Override
    public boolean add(AllEntity allEntity) {
        String uid = allEntity.getUid();
        ParamsEntity params = allEntity.getParams();
        paramStorage.save(uid, params);
        ActionsEntity actions = allEntity.getActions();
        actionStorage.save(uid, actions);
        WatchersEntity watchers = allEntity.getWatchers();
        watcherStorage.save(uid, watchers);
        ResultEntity result = allEntity.getResult();
        resultStorage.save(uid, result);
        ExtractsEntity extracts = allEntity.getExtracts();
        extractStorage.save(uid, extracts);
        FlowsEntity flows = allEntity.getFlows();
        flowStorage.save(uid, flows);

        return true;
    }

    @Override
    public AllEntity findByUid(String uid) {
        AllEntity allEntity = new AllEntity();
        allEntity.setParams(this.findParamsByUid(uid));
        allEntity.setActions(this.findActionsByUid(uid));
        allEntity.setWatchers(this.findWatchersByUid(uid));
        allEntity.setResult(this.findResultByUid(uid));
        allEntity.setExtracts(this.findExtractsByUid(uid));
        allEntity.setFlows(this.findFlowsByUid(uid));
        return allEntity;
    }

    @Override
    public ParamsEntity findParamsByUid(String uid) {
        ParamsEntity paramsEntity = new ParamsEntity();
        paramsEntity.setList(paramStorage.list(uid));
        return paramsEntity;
    }

    @Override
    public ActionsEntity findActionsByUid(String uid) {
        ActionsEntity actionsEntity = new ActionsEntity();
        actionsEntity.setList(actionStorage.list(uid));


        return actionsEntity;
    }

    @Override
    public WatchersEntity findWatchersByUid(String uid) {
        WatchersEntity watchersEntity = new WatchersEntity();
        watchersEntity.setList(watcherStorage.list(uid));
        return watchersEntity;
    }

    @Override
    public ResultEntity findResultByUid(String uid) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setKeys(resultStorage.list(uid));
        return resultEntity;
    }

    @Override
    public ExtractsEntity findExtractsByUid(String uid) {
        ExtractsEntity extractsEntity = new ExtractsEntity();
        extractsEntity.setExtractEntities(extractStorage.list(uid));
        return extractsEntity;
    }

    @Override
    public FlowsEntity findFlowsByUid(String uid) {
        FlowsEntity flowsEntity = new FlowsEntity();
        flowsEntity.setFlowEntities(flowStorage.list(uid));
        return flowsEntity;
    }
}
