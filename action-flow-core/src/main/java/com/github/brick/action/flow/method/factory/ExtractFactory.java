package com.github.brick.action.flow.method.factory;

import com.github.brick.action.flow.method.enums.ExtractModel;
import com.github.brick.action.flow.method.extract.Extract;
import com.github.brick.action.flow.method.extract.ExtractImpl;

public class ExtractFactory<Type extends com.github.brick.action.flow.method.enums.Type, T>
        implements Factory<ExtractModel, Extract> {
    @Override
    public Extract gen(ExtractModel type) {
        Extract extract = null;
        switch (type) {
            case JSON_PATH:
                extract = new ExtractImpl();
                break;
            default:
                throw new IllegalArgumentException("参数异常，无法生成数据提取器");


        }
        return extract;
    }
}
