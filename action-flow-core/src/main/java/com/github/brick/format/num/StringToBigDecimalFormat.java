package com.github.brick.format.num;

import com.github.brick.format.Format;

import java.math.BigDecimal;

public class StringToBigDecimalFormat implements Format<String> {
    @Override
    public BigDecimal format(String s,Class<?> clazz) {
        return new BigDecimal(s);
    }
}
