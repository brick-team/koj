package com.github.brick.action.flow.parse.api;

import com.github.brick.action.flow.method.entity.AllEntity;

/**
 * 核心解析类
 */
public interface ActionFlowMethodParseApi {

    AllEntity parse(String file) throws Exception;
}
