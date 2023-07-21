package com.github.CCweixiao.hbase.sdk.template;

import com.github.CCweixiao.hbase.sdk.HBaseSqlAdapter;
import com.github.CCweixiao.hbase.sdk.adapter.IHBaseSqlAdapter;
import com.github.CCweixiao.hbase.sdk.common.model.HQLType;
import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;
import org.apache.hadoop.conf.Configuration;
import java.util.Map;
import java.util.Properties;

/**
 * @author leojie 2022/11/27 17:14
 */
public class HBaseSqlTemplate implements BaseHBaseSqlTemplate {
    private final Configuration configuration;
    private final IHBaseSqlAdapter sqlAdapter;

    private HBaseSqlTemplate(Builder builder) {
        this.configuration = builder.configuration;
        this.sqlAdapter = new HBaseSqlAdapter(configuration);
    }

    @Override
    public HBaseDataSet select(String hsql) {
        return sqlAdapter.select(hsql);
    }

    @Override
    public HBaseDataSet select(String hsql, Map<String, Object> params) {
        return sqlAdapter.select(hsql, params);
    }

    @Override
    public void insert(String hql) {
        sqlAdapter.insert(hql);
    }

    @Override
    public void delete(String hql) {
        sqlAdapter.delete(hql);
    }

    @Override
    public String parseTableNameFromHql(String hql) {
        return sqlAdapter.parseTableNameFromHql(hql);
    }

    @Override
    public HQLType parseHQLType(String hql) {
        return sqlAdapter.parseHQLType(hql);
    }

    static class Builder extends BaseTemplateBuilder<HBaseSqlTemplate> {
        @Override
        HBaseSqlTemplate build() {
            return new HBaseSqlTemplate(this);
        }
    }

    public static HBaseSqlTemplate of(Configuration configuration) {
        return new Builder().configuration(configuration).build();
    }

    public static HBaseSqlTemplate of(Properties properties) {
        return new Builder().configuration(properties).build();
    }

    public static HBaseSqlTemplate of(String zkQuorum, String zkClientPort) {
        return new Builder().configuration(zkQuorum, zkClientPort).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
