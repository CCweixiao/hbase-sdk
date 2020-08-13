package com.leo.hbase.sdk.core.util;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/6/9 9:27 下午
 * @since 1.0
 */
public class CharUtil {
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c) ||
                c == '\ufeff' || c == '\u202a';
    }
}
