package com.github.brick.format.num;

import com.github.brick.format.Format;

public class StringToLongFormat implements Format<String> {
    @Override
    public Long format(String s,Class<?> clazz) {
        return Long.valueOf(s);
    }
}
