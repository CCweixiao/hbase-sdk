package com.github.CCweixiao.hbase.sdk.template;

import com.github.CCweixiao.hbase.sdk.HBaseSqlAdapter;
import com.github.CCweixiao.hbase.sdk.adapter.IHBaseSqlAdapter;
import com.github.CCweixiao.hbase.sdk.common.model.HQLType;
import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
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
        this.sqlAdapter = new HBaseSqlAdapter(this.configuration);
    }

    @Override
    public HBaseDataSet select(String hql) {
        return sqlAdapter.select(hql);
    }

    @Override
    public HBaseDataSet select(String hql, Map<String, Object> params) {
        return sqlAdapter.select(hql, params);
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

    @Override
    public void registerTableSchema(HBaseTableSchema tableSchema) {
        sqlAdapter.registerTableSchema(tableSchema);
    }

    @Override
    public HBaseTableSchema getTableSchema(String tableName) {
        return sqlAdapter.getTableSchema(tableName);
    }

    @Override
    public void printTableSchema(String tableName) {
        sqlAdapter.printTableSchema(tableName);
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
