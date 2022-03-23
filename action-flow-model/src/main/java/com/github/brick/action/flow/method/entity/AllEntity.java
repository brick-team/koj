package com.github.brick.action.flow.method.entity;

import lombok.Data;

@Data
public class AllEntity {
    private String uid;
    private ParamsEntity params = new ParamsEntity();
    private ActionsEntity actions = new ActionsEntity();
    private WatchersEntity watchers = new WatchersEntity();
    private ResultEntity result = new ResultEntity();

    // 解析
    private ExtractsEntity extracts = new ExtractsEntity();
    private FlowsEntity flows = new FlowsEntity();


}
