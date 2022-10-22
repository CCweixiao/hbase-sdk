package com.github.CCweixiao.hbase.sdk.adapter_22;

import com.github.CCweixiao.hbase.sdk.common.version.IVersionInfo;

/**
 * @author leojie 2022/10/21 16:31
 */
public class VersionInfoImpl implements IVersionInfo {
    @Override
    public String version() {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("adapter_22 classloader is " + this.getClass().getClassLoader());
        System.out.println("-----------------------------------------------------------------------------");
        return "hbase-sdk-hbase2.2";
    }
}
