package com.github.CCweixiao.hbase.sdk.common.version;

import static com.github.CCweixiao.hbase.sdk.common.constants.Constants.*;

/**
 * @author leojie 2022/10/22 14:00
 */
public enum HBaseSdkVersionEnum {
    /**
     * default hbase adapter
     */
    HBASE_ADAPTER_12("1.2.x", HBASE_ADMIN_TEMPLATE_ADAPTER_12, HBASE_TEMPLATE_ADAPTER_12),
    HBASE_ADAPTER_14("1.4.x", HBASE_ADMIN_TEMPLATE_ADAPTER_14, HBASE_TEMPLATE_ADAPTER_14),
    HBASE_ADAPTER_22("2.x", HBASE_ADMIN_TEMPLATE_ADAPTER_22, HBASE_TEMPLATE_ADAPTER_22);


    private String supportVersion;
    private String hbaseAdminTemplateAdapter;
    private String hbaseTemplateAdapter;

    HBaseSdkVersionEnum(String supportVersion, String hbaseAdminTemplateAdapter, String hbaseTemplateAdapter) {
        this.supportVersion = supportVersion;
        this.hbaseAdminTemplateAdapter = hbaseAdminTemplateAdapter;
        this.hbaseTemplateAdapter = hbaseTemplateAdapter;
    }

    public String getSupportVersion() {
        return supportVersion;
    }

    public void setSupportVersion(String supportVersion) {
        this.supportVersion = supportVersion;
    }

    public String getHbaseAdminTemplateAdapter() {
        return hbaseAdminTemplateAdapter;
    }

    public void setHbaseAdminTemplateAdapter(String hbaseAdminTemplateAdapter) {
        this.hbaseAdminTemplateAdapter = hbaseAdminTemplateAdapter;
    }

    public String getHbaseTemplateAdapter() {
        return hbaseTemplateAdapter;
    }

    public void setHbaseTemplateAdapter(String hbaseTemplateAdapter) {
        this.hbaseTemplateAdapter = hbaseTemplateAdapter;
    }

}
