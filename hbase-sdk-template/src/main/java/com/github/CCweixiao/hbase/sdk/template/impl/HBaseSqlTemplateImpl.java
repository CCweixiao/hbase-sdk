package com.github.CCweixiao.hbase.sdk.template.impl;

import com.github.CCweixiao.hbase.sdk.HBaseSqlTemplate;
import com.github.CCweixiao.hbase.sdk.common.IHBaseSqlOperations;
import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;
import com.github.CCweixiao.hbase.sdk.template.IHBaseSqlTemplate;
import org.apache.hadoop.hbase.HConstants;

import java.util.Map;
import java.util.Properties;

/**
 * @author leojie 2022/11/27 17:14
 */
public class HBaseSqlTemplateImpl implements IHBaseSqlTemplate {
    private final Properties properties;
    private final IHBaseSqlOperations sqlOperations;

    private HBaseSqlTemplateImpl(HBaseSqlTemplateImpl.Builder builder) {
        this.properties = builder.properties;
        this.sqlOperations = new HBaseSqlTemplate(properties);
    }

    @Override
    public HBaseDataSet select(String hsql) {
        return sqlOperations.select(hsql);
    }

    @Override
    public HBaseDataSet select(String hsql, Map<String, Object> params) {
        return sqlOperations.select(hsql, params);
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

        public Builder() {
        }

        public HBaseSqlTemplateImpl.Builder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public HBaseSqlTemplateImpl.Builder addProp(String key, String value) {
            if (this.properties == null) {
                this.properties = new Properties();
            }
            this.properties.setProperty(key, value);
            return this;
        }

        public HBaseSqlTemplateImpl.Builder zookeeperQuorum(String zookeeperQuorum) {
            addProp(HConstants.ZOOKEEPER_QUORUM, zookeeperQuorum);
            return this;
        }

        public HBaseSqlTemplateImpl.Builder zookeeperClientPort(String zookeeperClientPort) {
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
