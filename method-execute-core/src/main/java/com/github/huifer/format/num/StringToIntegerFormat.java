package com.github.huifer.format.num;

import com.github.huifer.format.Format;

public class StringToIntegerFormat implements Format<String> {
    @Override
    public Integer format(String s,Class<?> clazz) {
        return Integer.valueOf(s);
    }
}
