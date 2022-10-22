package com.github.CCweixiao.hbase.sdk.service;

/**
 * @author leojie 2022/10/21 16:50
 */
public class HBaseSdkVersionHelp {
    public static void printVersion() {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("main classloader is " + HBaseSdkVersionHelp.class.getClassLoader());
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println(new com.github.CCweixiao.hbase.sdk.adapter_12.VersionInfoImpl().version());
        System.out.println(new com.github.CCweixiao.hbase.sdk.adapter_14.VersionInfoImpl().version());
        System.out.println(new com.github.CCweixiao.hbase.sdk.adapter_22.VersionInfoImpl().version());
    }
}
