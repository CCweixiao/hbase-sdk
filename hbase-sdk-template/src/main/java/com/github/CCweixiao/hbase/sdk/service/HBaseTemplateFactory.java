package com.github.CCweixiao.hbase.sdk.service;

import com.github.CCweixiao.hbase.sdk.common.HBaseAdminOperations;
import com.github.CCweixiao.hbase.sdk.common.HBaseTableOperations;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkUnsupportedVersionException;
import com.github.CCweixiao.hbase.sdk.common.version.HBaseSdkVersionEnum;

import java.util.Properties;

/**
 * @author leojie 2022/10/22 14:28
 */
public class HBaseTemplateFactory {
    public HBaseAdminOperations getHBaseAdminOperations(String hbaseAdapterVersion, Properties prop) {
        HBaseSdkVersionEnum versionEnum = getHBaseSdkVersionEnum(hbaseAdapterVersion);
        HBaseAdminOperations operations = null;
        switch (versionEnum) {
            case HBASE_ADAPTER_12:
                operations = new com.github.CCweixiao.hbase.sdk.adapter_12.HBaseAdminTemplate(prop);
                break;
            case HBASE_ADAPTER_14:
                operations = new com.github.CCweixiao.hbase.sdk.adapter_14.HBaseAdminTemplate(prop);
                break;
            case HBASE_ADAPTER_22:
                operations = new com.github.CCweixiao.hbase.sdk.adapter_22.HBaseAdminTemplate(prop);
                break;
            default:
                break;
        }
        return operations;
    }

    public HBaseTableOperations getHBaseTableOperations(String hbaseAdapterVersion, Properties prop) {
        HBaseSdkVersionEnum versionEnum = getHBaseSdkVersionEnum(hbaseAdapterVersion);
        HBaseTableOperations operations = null;
        switch (versionEnum) {
            case HBASE_ADAPTER_12:
                operations = new com.github.CCweixiao.hbase.sdk.adapter_12.HBaseTemplate(prop);
                break;
            case HBASE_ADAPTER_14:
                operations = new com.github.CCweixiao.hbase.sdk.adapter_14.HBaseTemplate(prop);
                break;
            case HBASE_ADAPTER_22:
                operations = new com.github.CCweixiao.hbase.sdk.adapter_22.HBaseTemplate(prop);
                break;
            default:
                break;
        }
        return operations;
    }

    private HBaseSdkVersionEnum getHBaseSdkVersionEnum(String supportVersion) {
        if (supportVersion == null || supportVersion.trim().length() == 0) {
            throw new HBaseSdkUnsupportedVersionException("The version of hbase adapter is not empty.");
        }
        for (HBaseSdkVersionEnum value : HBaseSdkVersionEnum.values()) {
            if (supportVersion.equals(value.getSupportVersion())) {
                return value;
            }
        }
        throw new HBaseSdkUnsupportedVersionException("The version " + supportVersion + "  of hbase adapter is not supported.");
    }
}
