package com.github.CCweixiao.hbase.sdk.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author leojie 2021/6/13 8:52 下午
 */
public final class DateUtil {
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
