package com.github.CCweixiao.hbase.sdk.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author leojie 2021/6/13 8:52 下午
 */
public final class DateUtil {
    public static final String MS_FORMAT = "yyyy-MM-dd_HH:mm:ss:SSS";
    public static final String SECOND_FORMAT = "yyyy-MM-dd_HH:mm:ss";
    public static final String MINUTE_FORMAT = "yyyy-MM-dd_HH:mm";
    public static final String HOUR_FORMAT = "yyyy-MM-dd_HH";
    public static final String DAY_FORMAT = "yyyy-MM-dd";

    public static Date parse(String str, String format) {
        ObjUtil.checkEmptyString(str);
        ObjUtil.checkEmptyString(format);

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            //ignore.
        }
        return null;
    }

    public static String parseDatetimeToStr(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(localDateTime);
    }
    public static String parseTimestampToTimeStr(long time){
        DateTimeFormatter df= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.of("Asia/Shanghai")));
    }

    public static void main(String[] args) {
        System.out.println(parseTimestampToTimeStr(1623586470408L));
    }


}
