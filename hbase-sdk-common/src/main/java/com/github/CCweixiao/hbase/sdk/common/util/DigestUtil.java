package com.github.CCweixiao.hbase.sdk.common.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * @author leojie 2023/7/9 13:20
 */
public class DigestUtil {
   public static String md5Hex(String str) {
       return Hashing.md5().hashBytes(str.getBytes(StandardCharsets.UTF_8)).toString();
   }

    public static void main(String[] args) {
        System.out.println(md5Hex("12131321"));
    }
}
