package com.github.CCweixiao.hbase.sdk.service.impl;

import com.github.CCweixiao.hbase.sdk.common.HBaseTableOperations;
import com.github.CCweixiao.hbase.sdk.service.HBaseTemplateFactory;
import com.github.CCweixiao.hbase.sdk.service.IHBaseTableTemplate;

import java.util.Properties;

/**
 * @author leojie 2022/10/22 18:58
 */
public class HBaseTableTemplateBuilder implements IHBaseTableTemplate {
    private final String sdkVersion;
    private final Properties properties;

    private HBaseTableTemplateBuilder(Builder builder) {
        this.sdkVersion = builder.sdkAdapterVersion;
        this.properties = builder.properties;
    }

    public static class Builder {
        private String sdkAdapterVersion;
        private Properties properties;

        public Builder() {}

        public Builder sdkAdapterVersion(String sdkAdapterVersion) {
            this.sdkAdapterVersion = sdkAdapterVersion;
            return this;
        }

        public Builder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public Builder addProp(String key, String value) {
            if (this.properties == null) {
                this.properties = new Properties();
            }
            this.properties.setProperty(key, value);
            return this;
        }
        public HBaseTableTemplateBuilder build() {
            return new HBaseTableTemplateBuilder(this);
        }
    }

    private HBaseTableOperations getHBaseTableOperations() {
        return new HBaseTemplateFactory().getHBaseTableOperations(this.getSdkVersion(), this.getProperties());
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public Properties getProperties() {
        return properties;
    }
}
