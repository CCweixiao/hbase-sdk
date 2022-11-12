package com.github.CCwexiao.hbase.sdk.dsl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author leojie 2020/11/28 6:04 下午
 */
public class DateUtil {
    public static final String MSFormat     = "yyyy-MM-dd_HH:mm:ss:SSS";
    public static final String SecondFormat = "yyyy-MM-dd_HH:mm:ss";
    public static final String MinuteFormat = "yyyy-MM-dd_HH:mm";
    public static final String HourFormat   = "yyyy-MM-dd_HH";
    public static final String DayFormat    = "yyyy-MM-dd";

    public static Date parse(String str, String format) {
        Util.checkEmptyString(str);
        Util.checkEmptyString(format);

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            //ignore.
        }
        return null;
    }
}
