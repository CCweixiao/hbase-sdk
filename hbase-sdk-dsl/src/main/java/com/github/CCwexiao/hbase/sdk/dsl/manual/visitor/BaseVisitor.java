package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLBaseVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.*;
import com.github.CCwexiao.hbase.sdk.dsl.model.TableQuerySetting;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

/**
 * @author leojie 2022/12/3 10:30
 */
public abstract class BaseVisitor<T> extends HBaseSQLBaseVisitor<T> {
    protected HBaseTableSchema tableSchema;
    protected HBaseColumn column;

    public BaseVisitor() {
        this(null, null);
    }

    public BaseVisitor(HBaseTableSchema tableSchema) {
        this(tableSchema, null);
    }

    public BaseVisitor(HBaseColumn column) {
        this(null, column);
    }

    public BaseVisitor(HBaseTableSchema tableSchema, HBaseColumn column) {
        this.tableSchema = tableSchema;
        this.column = column;
    }

    public HBaseTableSchema getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(HBaseTableSchema tableSchema) {
        this.tableSchema = tableSchema;
    }

    public TableQuerySetting getSqlRuntimeSetting() {
        return this.tableSchema.getTableQuerySetting();
    }

    public RowKey<byte[]> startRow() {
        return new BytesRowKey(new byte[0]);
    }

    public RowKey<byte[]> endRow() {
        return new BytesRowKey(new byte[0]);
    }
}
