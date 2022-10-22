package com.github.CCweixiao.hbase.sdk.adapter_12;

import com.github.CCweixiao.hbase.sdk.common.version.IVersionInfo;

/**
 * @author leojie 2022/10/21 16:31
 */
public class VersionInfoImpl implements IVersionInfo {
    @Override
    public String version() {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("adapter_12 classloader is " + this.getClass().getClassLoader());
        System.out.println("-----------------------------------------------------------------------------");
        return "hbase-sdk-hbase1.2";
    }
}
