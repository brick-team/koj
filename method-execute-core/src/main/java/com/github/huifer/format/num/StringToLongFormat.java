package com.github.huifer.format.num;

import com.github.huifer.format.Format;

public class StringToLongFormat implements Format<String, Long> {
    @Override
    public Long format(String s) {
        return Long.valueOf(s);
    }
}
