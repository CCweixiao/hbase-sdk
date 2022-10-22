package com.github.CCweixiao.hbase.sdk.service.impl;

import com.github.CCweixiao.hbase.sdk.common.HBaseAdminOperations;
import com.github.CCweixiao.hbase.sdk.service.IHBaseAdminTemplate;
import com.github.CCweixiao.hbase.sdk.service.HBaseTemplateFactory;

import java.util.List;
import java.util.Properties;

/**
 * @author leojie 2022/10/22 18:57
 */
public class HBaseAdminTemplateBuilder implements IHBaseAdminTemplate {
    private final String sdkVersion;
    private final Properties properties;

    private HBaseAdminTemplateBuilder(Builder builder) {
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
        public HBaseAdminTemplateBuilder build() {
            return new HBaseAdminTemplateBuilder(this);
        }
    }


    @Override
    public List<String> listTableNames() {
        return getHBaseAdminTemplate().listTableNames();
    }

    private HBaseAdminOperations getHBaseAdminTemplate() {
       return new HBaseTemplateFactory().getHBaseAdminOperations(this.getSdkVersion(), this.getProperties());
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public Properties getProperties() {
        return properties;
    }
}
