package com.github.huifer.format.num;

import com.github.huifer.format.Format;

import java.math.BigDecimal;

public class StringToBigDecimalFormat implements Format<String> {
    @Override
    public BigDecimal format(String s,Class<?> clazz) {
        return new BigDecimal(s);
    }
}
