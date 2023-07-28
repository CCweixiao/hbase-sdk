package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlTableColumnsMissingException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlTableSchemaMissingException;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLBaseVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.*;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Map;

/**
 * @author leojie 2022/12/3 10:30
 */
public abstract class BaseVisitor<T> extends HBaseSQLBaseVisitor<T> {
    protected final HBaseTableSchema tableSchema;

    public BaseVisitor(HBaseTableSchema tableSchema) {
        this.tableSchema = tableSchema;
    }

    public HBaseTableSchema getTableSchema() {
        if (this.tableSchema == null) {
            throw new HBaseSqlTableSchemaMissingException("Please register the table schema first.");
        }
        return tableSchema;
    }

    public Map<String, HBaseColumn> getAllColumns() {
        Map<String, HBaseColumn> schemaMap = this.getTableSchema().getColumnSchemaMap();
        if (schemaMap == null || schemaMap.isEmpty()) {
            throw new HBaseSqlTableColumnsMissingException("No column added for registered schema.");
        }
        return schemaMap;
    }

    public HBaseColumn findRow() {
        return this.getTableSchema().findRow();
    }

    public RowKey<byte[]> startRow() {
        return new BytesRowKey(new byte[0]);
    }

    public RowKey<byte[]> endRow() {
        return new BytesRowKey(new byte[0]);
    }

    protected String parseValueFromValueContext(HBaseSQLParser.ValueContext valueContext) {
        if (valueContext == null) {
            return null;
        }
        if (valueContext.NULL() != null) {
            return null;
        }
        if (valueContext.ID() != null) {
            return valueContext.ID().getText();
        }

        if (valueContext.STRING() != null) {
            return valueContext.STRING().getText();
        }
        return null;
    }
}
