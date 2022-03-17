package com.github.brick.action.flow.method.factory;

import com.github.brick.action.flow.method.enums.FLowModel;
import com.github.brick.action.flow.method.parse.XMLActionFlowMethodParseApi;
import com.github.brick.action.flow.parse.api.ActionFlowMethodParseApi;

public class ActionFlowParseApiFactory<Type extends com.github.brick.action.flow.method.enums.Type, T>
        implements Factory<FLowModel, ActionFlowMethodParseApi> {


    @Override
    public ActionFlowMethodParseApi gen(FLowModel type) {
        ActionFlowMethodParseApi actionFlowMethodParseApi = null;
        if (type == FLowModel.XML) {
            actionFlowMethodParseApi = new XMLActionFlowMethodParseApi();
        } else {
            throw new IllegalArgumentException("参数异常，无法生成文件解析器");
        }
        return actionFlowMethodParseApi;
    }
}
