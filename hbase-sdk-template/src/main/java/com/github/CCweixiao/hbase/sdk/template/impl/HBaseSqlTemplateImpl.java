package com.github.CCweixiao.hbase.sdk.template.impl;

import com.github.CCweixiao.hbase.sdk.HBaseSqlTemplate;
import com.github.CCweixiao.hbase.sdk.common.model.HBaseCellResult;
import com.github.CCweixiao.hbase.sdk.template.IHBaseSqlTemplate;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseTableConfig;
import org.apache.hadoop.hbase.HConstants;

import java.util.List;
import java.util.Properties;

/**
 * @author leojie 2022/11/27 17:14
 */
public class HBaseSqlTemplateImpl implements IHBaseSqlTemplate {

    private final Properties properties;
    private final HBaseSqlTemplate sqlOperations;

    private final HBaseTableConfig hbaseTableConfig;

    private HBaseSqlTemplateImpl(Builder builder) {
        this.properties = builder.properties;
        this.hbaseTableConfig = builder.hbaseTableConfig;
        this.sqlOperations = new HBaseSqlTemplate(properties);
        this.sqlOperations.setHBaseTableConfig(hbaseTableConfig);
    }

    @Override
    public List<List<HBaseCellResult>> select(String hsql) {
        return sqlOperations.select(hsql);
    }

    @Override
    public void insert(String hql) {
        sqlOperations.insert(hql);
    }

    @Override
    public void delete(String hql) {
        sqlOperations.delete(hql);
    }

    public static class Builder {
        private Properties properties;
        private HBaseTableConfig hbaseTableConfig;

        public Builder() {
        }

        public Builder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public Builder hbaseTableConfig(HBaseTableConfig hbaseTableConfig) {
            this.hbaseTableConfig = hbaseTableConfig;
            return this;
        }

        public Builder addProp(String key, String value) {
            if (this.properties == null) {
                this.properties = new Properties();
            }
            this.properties.setProperty(key, value);
            return this;
        }

        public Builder zookeeperQuorum(String zookeeperQuorum) {
            addProp(HConstants.ZOOKEEPER_QUORUM, zookeeperQuorum);
            return this;
        }

        public Builder zookeeperClientPort(String zookeeperClientPort) {
            addProp(HConstants.ZOOKEEPER_CLIENT_PORT, zookeeperClientPort);
            return this;
        }

        public HBaseSqlTemplateImpl build() {
            return new HBaseSqlTemplateImpl(this);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
