package com.github.huifer;

public class StringToIntegerFormat implements Format {
    @Override
    public Object format(String s) {
        return Integer.valueOf(s);
    }
}
