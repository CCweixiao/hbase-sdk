package com.github.CCweixiao.hbase.sdk.service;

import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.github.CCweixiao.hbase.sdk.common.constants.Constants.*;

/**
 * @author leojie 2022/10/22 14:00
 */
public enum HBaseClientAdapterVersionEnum {
    /**
     * default hbase adapter
     */
    HBASE_ADAPTER_12("1.2.x", HBASE_ADMIN_TEMPLATE_ADAPTER_12, HBASE_TEMPLATE_ADAPTER_12),
    HBASE_ADAPTER_14("1.4.x", HBASE_ADMIN_TEMPLATE_ADAPTER_14, HBASE_TEMPLATE_ADAPTER_14),
    HBASE_ADAPTER_22("2.x.x", HBASE_ADMIN_TEMPLATE_ADAPTER_22, HBASE_TEMPLATE_ADAPTER_22);


    private final String hbaseClientVersion;
    private final String hbaseAdminTemplateAdapterClassName;
    private final String hbaseTemplateAdapterClassName;

    HBaseClientAdapterVersionEnum(String hbaseClientVersion, String hbaseAdminTemplateAdapterClassName,
                                  String hbaseTemplateAdapterClassName) {
        this.hbaseClientVersion = hbaseClientVersion;
        this.hbaseAdminTemplateAdapterClassName = hbaseAdminTemplateAdapterClassName;
        this.hbaseTemplateAdapterClassName = hbaseTemplateAdapterClassName;
    }

    public static String getHBaseAdminTemplateAdapterClassNameByVersion(String version) {
        String className = "";
        if (StrUtil.isBlank(version)) {
            return className;
        }
        for (HBaseClientAdapterVersionEnum value : HBaseClientAdapterVersionEnum.values()) {
            if (version.equals(value.getHBaseClientVersion())) {
                className = value.getHBaseAdminTemplateAdapterClassName();
                break;
            }
        }
        return className;
    }

    public static String getHBaseTemplateAdapterClassNameByVersion(String version) {
        String className = "";
        if (StrUtil.isBlank(version)) {
            return className;
        }
        for (HBaseClientAdapterVersionEnum value : HBaseClientAdapterVersionEnum.values()) {
            if (version.equals(value.getHBaseClientVersion())) {
                className = value.getHBaseTemplateAdapterClassName();
                break;
            }
        }
        return className;
    }

    public String getHBaseClientVersion() {
        return hbaseClientVersion;
    }

    public String getHBaseAdminTemplateAdapterClassName() {
        return hbaseAdminTemplateAdapterClassName;
    }

    public String getHBaseTemplateAdapterClassName() {
        return hbaseTemplateAdapterClassName;
    }

    public static String toShowString() {
        return Arrays.stream(HBaseClientAdapterVersionEnum.values())
                .map(HBaseClientAdapterVersionEnum::getHBaseClientVersion).collect(Collectors.toList()).toString();
    }
}
