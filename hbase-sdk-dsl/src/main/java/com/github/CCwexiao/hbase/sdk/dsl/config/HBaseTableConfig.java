package com.github.CCwexiao.hbase.sdk.dsl.config;

import com.github.CCwexiao.hbase.sdk.dsl.type.TypeHandlers;
import com.github.CCwexiao.hbase.sdk.dsl.util.Util;

import java.util.List;

/**
 * @author leojie 2020/11/27 10:52 下午
 */
public abstract class HBaseTableConfig {

    protected HBaseTableSchema hBaseTableSchema;
    protected  List<HBaseColumnSchema> hBaseColumnSchemaList;

    /**
     * 提供 HBaseTableSchema
     * @return HBaseTableSchema
     */
    protected abstract HBaseTableSchema providedHBaseTableSchema();

    /**
     * 提供获取所有HBase字段的接口
     *
     * @return HBase字段数据模型
     */
    protected abstract List<HBaseColumnSchema> providedAllHBaseColumnSchema();

    /**
     * 提供字段类型转换函数
     *
     * @return 字段数据类型转换函数
     */
    protected abstract TypeHandlers providedColumnTypeHandlers();

    protected void init() {
        List<HBaseColumnSchema> hBaseColumnSchemas = providedAllHBaseColumnSchema();
        TypeHandlers typeHandlers = providedColumnTypeHandlers();

        Util.checkNull(hBaseColumnSchemas);
        Util.checkNull(typeHandlers);

        HBaseTableSchema hBaseTableSchema = providedHBaseTableSchema();
        hBaseTableSchema.init(hBaseColumnSchemas, typeHandlers);

    }

    public HBaseTableSchema gethBaseTableSchema() {
        return hBaseTableSchema;
    }

    public List<HBaseColumnSchema> gethBaseColumnSchemaList() {
        return hBaseColumnSchemaList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(hBaseTableSchema.toString());
        return sb.toString();
    }


}
