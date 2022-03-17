package com.github.brick.action.flow.method.format.num;

import com.github.brick.action.flow.method.format.Format;

public class StringToIntegerFormat implements Format<String> {
    @Override
    public Integer format(String s,Class<?> clazz) {
        return Integer.valueOf(s);
    }
}
