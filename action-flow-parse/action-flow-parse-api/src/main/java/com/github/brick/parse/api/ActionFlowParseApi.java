package com.github.brick.parse.api;

import com.github.brick.entity.AllEntity;

/**
 * 核心解析类
 */
public interface ActionFlowParseApi {

    AllEntity parse(String file) throws Exception;
}
