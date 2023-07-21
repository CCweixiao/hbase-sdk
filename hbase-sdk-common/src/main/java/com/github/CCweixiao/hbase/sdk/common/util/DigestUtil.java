package com.github.CCweixiao.hbase.sdk.common.util;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkCryptoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author leojie 2023/7/9 13:20
 */
public class DigestUtil {

    public static String md5Hex(String inStr){
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new HBaseSdkCryptoException(e);
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }


    public static void main(String[] args) {
        System.out.println(md5Hex("12131321"));
    }
}
