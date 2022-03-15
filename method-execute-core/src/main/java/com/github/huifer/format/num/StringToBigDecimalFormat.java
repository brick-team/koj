package com.github.huifer.format.num;

import com.github.huifer.format.Format;

import java.math.BigDecimal;

public class StringToBigDecimalFormat implements Format<String, BigDecimal> {
    @Override
    public BigDecimal format(String s) {
        return new BigDecimal(s);
    }
}
