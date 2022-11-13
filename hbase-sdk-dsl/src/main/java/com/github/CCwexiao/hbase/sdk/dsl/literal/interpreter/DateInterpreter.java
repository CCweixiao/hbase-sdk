package com.github.CCwexiao.hbase.sdk.dsl.literal.interpreter;

import com.github.CCweixiao.hbase.sdk.common.util.DateUtil;
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
        DATE_FORMATS.add(DateUtil.MS_FORMAT);
        DATE_FORMATS.add(DateUtil.SECOND_FORMAT);
        DATE_FORMATS.add(DateUtil.MINUTE_FORMAT);
        DATE_FORMATS.add(DateUtil.HOUR_FORMAT);
        DATE_FORMATS.add(DateUtil.DAY_FORMAT);
    }

    @Override
    protected Object interpretInternal(String literalValue) {
        for (String s : DATE_FORMATS) {
            Date date = DateUtil.parse(literalValue, s);
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
