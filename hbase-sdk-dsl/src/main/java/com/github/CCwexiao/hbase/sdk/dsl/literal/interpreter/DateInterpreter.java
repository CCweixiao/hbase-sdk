package com.github.CCwexiao.hbase.sdk.dsl.literal.interpreter;

import com.github.CCweixiao.hbase.sdk.common.util.DateAndTimeUtil;
import com.github.CCwexiao.hbase.sdk.dsl.literal.AbstractLiteralInterpreter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author leojie 2020/11/28 6:03 下午
 */
public class DateInterpreter extends AbstractLiteralInterpreter {
    private static final List<String> DATE_FORMATS = new ArrayList<>();

    static {
        DATE_FORMATS.add(DateAndTimeUtil.MS_FORMAT);
        DATE_FORMATS.add(DateAndTimeUtil.SECOND_FORMAT);
        DATE_FORMATS.add(DateAndTimeUtil.MINUTE_FORMAT);
        DATE_FORMATS.add(DateAndTimeUtil.HOUR_FORMAT);
        DATE_FORMATS.add(DateAndTimeUtil.DAY_FORMAT);
    }

    @Override
    protected Object interpretInternal(String literalValue) {
        for (String s : DATE_FORMATS) {
            Date date = DateAndTimeUtil.parse(literalValue, s);
            if (date != null) {
                return date;
            }
        }
        return null;
    }

    @Override
    public Class<?> getTypeCanInterpret() {
        return Date.class;
    }
}
