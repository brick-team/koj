package com.github.brick.factory;

import com.github.brick.enums.FLowModel;
import com.github.brick.parse.XMLActionFlowParseApi;
import com.github.brick.parse.api.ActionFlowParseApi;

public class ActionFlowParseApiFactory<Type extends com.github.brick.enums.Type, T>
        implements Factory<FLowModel, ActionFlowParseApi> {


    @Override
    public ActionFlowParseApi gen(FLowModel type) {
        ActionFlowParseApi actionFlowParseApi = null;
        switch (type) {
            case XML -> {
                actionFlowParseApi = new XMLActionFlowParseApi();
            }
            default -> {
                throw new IllegalArgumentException("参数异常，无法生成文件解析器");
            }
        }
        return actionFlowParseApi;
    }
}
