package com.github.huifer.entity;

import lombok.Data;

@Data
public class DocTag {
    private ParamsTag params;
    private ActionsTag actions;
    private WatchersTag watchers;
    private ResultTag result;

    // 解析
    private ExtractsTag extracts;
    private FlowTag flow;

}
