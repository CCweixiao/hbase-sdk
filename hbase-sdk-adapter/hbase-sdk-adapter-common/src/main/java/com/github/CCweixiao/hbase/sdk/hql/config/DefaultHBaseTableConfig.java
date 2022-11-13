package com.github.CCweixiao.hbase.sdk.hql.config;

import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseColumnSchema;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseTableConfig;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseTableSchema;

import java.util.List;

/**
 * @author leojie 2020/11/28 7:30 下午
 */
public class DefaultHBaseTableConfig extends HBaseTableConfig {

    public DefaultHBaseTableConfig(HBaseTableSchema hBaseTableSchema,
                                   List<HBaseColumnSchema> hBaseColumnSchemaList) {
        this.hBaseTableSchema = hBaseTableSchema;
        this.hBaseColumnSchemaList = hBaseColumnSchemaList;
        init();
    }

    @Override
    protected HBaseTableSchema providedHBaseTableSchema() {
        return this.hBaseTableSchema;
    }

    @Override
    protected List<HBaseColumnSchema> providedAllHBaseColumnSchema() {
        return this.hBaseColumnSchemaList;
    }
}
