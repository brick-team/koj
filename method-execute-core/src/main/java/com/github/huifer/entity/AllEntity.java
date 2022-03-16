package com.github.huifer.entity;

import lombok.Data;

@Data
public class AllEntity {
    private ParamsEntity params;
    private ActionsEntity actions;
    private WatchersEntity watchers;
    private ResultEntity result;

    // 解析
    private ExtractsEntity extracts;
    private FlowsEntity flows;

}
