package com.leo.hbase.sdk.core.util;

/**
 * string util
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/6/9 9:24 下午
 * @since 1.0
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
