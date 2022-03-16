package com.github.brick.format.num;

import com.github.brick.format.Format;

public class StringToIntegerFormat implements Format<String> {
    @Override
    public Integer format(String s,Class<?> clazz) {
        return Integer.valueOf(s);
    }
}
