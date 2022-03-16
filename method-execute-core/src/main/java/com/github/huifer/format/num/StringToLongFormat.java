package com.github.huifer.format.num;

import com.github.huifer.format.Format;

public class StringToLongFormat implements Format<String> {
    @Override
    public Long format(String s,Class<?> clazz) {
        return Long.valueOf(s);
    }
}
