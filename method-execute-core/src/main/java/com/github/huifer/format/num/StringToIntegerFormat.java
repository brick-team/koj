package com.github.huifer.format.num;

import com.github.huifer.format.Format;

public class StringToIntegerFormat implements Format<String ,Integer> {
    @Override
    public Integer format(String s) {
        return Integer.valueOf(s);
    }
}
