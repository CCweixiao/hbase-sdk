package com.github.CCwexiao.hbase.sdk.dsl.config;

import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;

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


    protected void init() {
        List<HBaseColumnSchema> hBaseColumnSchemas = providedAllHBaseColumnSchema();
        ObjUtil.checkIsNull(hBaseColumnSchemas);
        HBaseTableSchema hBaseTableSchema = providedHBaseTableSchema();
        hBaseTableSchema.init(hBaseColumnSchemas);
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
