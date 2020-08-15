package com.leo.hbase.sdk.core.util;

/**
 * string util
 * @author leo.jie (weixiao.me@aliyun.com)
 */
public class StrUtil {
    public static boolean isBlank(CharSequence str){
        int length;
        if((str == null) || ((length = str.length())==0)){
            return true;
        }
        for (int i = 0; i < length; i++) {
            if(!CharUtil.isBlankChar(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence str){
        return !isBlank(str);
    }

    public static void main(String[] args) {
        System.out.println(isBlank("a dbc"));
    }
}
