package com.github.CCweixiao.util;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
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
