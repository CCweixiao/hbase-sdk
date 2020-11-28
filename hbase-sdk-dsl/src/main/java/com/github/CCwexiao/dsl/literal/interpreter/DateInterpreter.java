package com.github.CCwexiao.dsl.literal.interpreter;

import com.github.CCwexiao.dsl.literal.AbstractLiteralInterpreter;
import com.github.CCwexiao.dsl.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author leojie 2020/11/28 6:03 下午
 */
public class DateInterpreter extends AbstractLiteralInterpreter {
    private static List<String> dateFormats = new ArrayList<>();

    static {
        dateFormats.add(DateUtil.MSFormat);
        dateFormats.add(DateUtil.SecondFormat);
        dateFormats.add(DateUtil.MinuteFormat);
        dateFormats.add(DateUtil.HourFormat);
        dateFormats.add(DateUtil.DayFormat);
    }

    @Override
    protected Object interpretInternal(String literalValue) {
        for (String s : dateFormats) {
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
