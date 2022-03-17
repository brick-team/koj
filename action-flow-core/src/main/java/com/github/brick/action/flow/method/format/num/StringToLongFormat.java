package com.github.brick.action.flow.method.format.num;

import com.github.brick.action.flow.method.format.Format;

public class StringToLongFormat implements Format<String> {
    @Override
    public Long format(String s,Class<?> clazz) {
        return Long.valueOf(s);
    }
}
