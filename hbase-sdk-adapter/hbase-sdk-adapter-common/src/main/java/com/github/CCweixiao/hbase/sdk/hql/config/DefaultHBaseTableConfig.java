package com.github.CCweixiao.hbase.sdk.hql.config;

import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseTableConfig;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

import java.util.List;

/**
 * @author leojie 2020/11/28 7:30 下午
 */
@Deprecated
public class DefaultHBaseTableConfig extends HBaseTableConfig {

    public DefaultHBaseTableConfig(HBaseTableSchema hBaseTableSchema,
                                   List<HBaseColumn> hBaseColumnSchemaList) {
        this.hBaseTableSchema = hBaseTableSchema;
        this.hBaseColumnSchemaList = hBaseColumnSchemaList;
        init();
    }

    @Override
    protected HBaseTableSchema providedHBaseTableSchema() {
        return this.hBaseTableSchema;
    }

    @Override
    protected List<HBaseColumn> providedAllHBaseColumnSchema() {
        return this.hBaseColumnSchemaList;
    }
}
