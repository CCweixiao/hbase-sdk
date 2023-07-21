package com.github.CCweixiao.hbase.sdk.template;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;

import java.util.Properties;

/**
 * @author leojie 2023/7/21 17:15
 */
public abstract class BaseTemplateBuilder<T> {
    protected Configuration configuration;

    public BaseTemplateBuilder() {
    }

    public BaseTemplateBuilder<T> configuration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public BaseTemplateBuilder<T> configuration(Properties properties) {
        if (properties == null || properties.isEmpty()) {
            this.configuration = HBaseConfiguration.create();
            return this;
        }
        if (this.configuration == null) {
            this.configuration = HBaseConfiguration.create();
            for (String k : properties.stringPropertyNames()) {
                this.configuration.set(k, properties.getProperty(k));
            }
        }
        return this;
    }

    public BaseTemplateBuilder<T> configuration(String key, String value) {
        if (this.configuration == null) {
            this.configuration = HBaseConfiguration.create();
        }
        this.configuration.set(key, value);
        return this;
    }

    public BaseTemplateBuilder<T> zookeeperQuorum(String zkQuorum) {
        return this.configuration(HConstants.ZOOKEEPER_QUORUM, zkQuorum);
    }

    public BaseTemplateBuilder<T> zookeeperClientPort(String zkClientPort) {
        return this.configuration(HConstants.ZOOKEEPER_CLIENT_PORT, zkClientPort);
    }

    abstract T build();
}
