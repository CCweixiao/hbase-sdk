package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.model.HQLType;
import com.github.CCweixiao.hbase.sdk.common.type.TypeHandler;
import com.github.CCweixiao.hbase.sdk.common.model.HBaseCellResult;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.connection.ConnectionFactory;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.QueryExtInfo;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.context.HBaseSqlContext;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLErrorStrategy;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLStatementsLexer;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import com.github.CCwexiao.hbase.sdk.dsl.model.TableQuerySetting;
import com.github.CCwexiao.hbase.sdk.dsl.util.Util;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/11/28 8:34 下午
 */
public abstract class AbstractHBaseSqlTemplate implements IHBaseOperations {

    abstract List<List<HBaseCellResult>> select(String hsql);

    abstract List<List<HBaseCellResult>> select(String hsql, Map<String, Object> params);

    abstract void insert(String hsql);

    abstract void delete(String hsql);

    @Override
    public Connection getConnection() {
        return ConnectionFactory.getConnection(HBaseSqlContext.getConnProperties());
    }

    protected HBaseColumn findColumnSchema(String tableName, String familyName, String columnName) {
        return HBaseSqlContext.getTableSchema(tableName).findColumn(familyName, columnName);
    }

    protected int getScanCaching(String tableName) {
        return getTableQuerySetting(tableName).getScanCaching();
    }

    protected int getScanBatch(String tableName) {
        return getTableQuerySetting(tableName).getScanBatch();
    }

    protected int getDeleteBatch(String tableName) {
        return getTableQuerySetting(tableName).getDeleteBatch();
    }

    protected boolean scanCacheBlocks(String tableName) {
        return getTableQuerySetting(tableName).isScanCacheBlocks();
    }

    protected Get constructGet(RowKey<?> rowKey, QueryExtInfo queryExtInfo, Filter filter, List<HBaseColumn> columnSchemaList) {
        Get get = constructGet(rowKey);
        if (queryExtInfo != null) {
            if (queryExtInfo.isMaxVersionSet()) {
                try {
                    get.setMaxVersions(queryExtInfo.getMaxVersions());
                } catch (IOException e) {
                    throw new HBaseOperationsException("should never happen.", e);
                }
            }
            if (queryExtInfo.isTimeRangeSet()) {
                try {
                    get.setTimeRange(queryExtInfo.getMinStamp(), queryExtInfo.getMaxStamp());
                } catch (IOException e) {
                    throw new HBaseOperationsException("should never happen.", e);
                }
            }
        }
        if (filter != null) {
            get.setFilter(filter);
        }
        if (columnSchemaList != null && !columnSchemaList.isEmpty()) {
            for (HBaseColumn column : columnSchemaList) {
                if (column.columnIsRow()) {
                    continue;
                }
                get.addColumn(Bytes.toBytes(column.getFamilyName()), Bytes.toBytes(column.getColumnName()));
            }
        }
        return get;
    }

    protected Get constructGet(RowKey<?> rowKey, QueryExtInfo queryExtInfo, List<HBaseColumn> columnSchemaList) {
        return constructGet(rowKey, queryExtInfo, null, columnSchemaList);
    }

    protected Get constructGet(RowKey<?> rowKey) {
        Util.checkRowKey(rowKey);
        return new Get(rowKey.toBytes());
    }

    protected abstract Scan constructScan(String tableName, RowKey<?> startRowKey, RowKey<?> endRowKey, Filter filter, QueryExtInfo queryExtInfo);

    protected Delete constructDelete(RowKey<?> rowKey, List<HBaseColumn> columnSchemaList, Date ts) {
        return constructDelete(rowKey.toBytes(), columnSchemaList, ts);
    }

    protected Delete constructDelete(Result result, List<HBaseColumn> columnSchemaList, Date ts) {
        return constructDelete(result.getRow(), columnSchemaList, ts);
    }

    private Delete constructDelete(byte[] row, List<HBaseColumn> columnSchemaList, Date ts) {
        Delete delete = new Delete(row);
        if (columnSchemaList == null || columnSchemaList.isEmpty()) {
            return delete;
        }
        for (HBaseColumn hBaseColumnSchema : columnSchemaList) {
            if (hBaseColumnSchema.columnIsRow()) {
                continue;
            }
            byte[] familyBytes = Bytes.toBytes(hBaseColumnSchema.getFamilyName());
            byte[] qualifierBytes = Bytes.toBytes(hBaseColumnSchema.getColumnName());
            if (ts == null) {
                delete.addColumn(familyBytes, qualifierBytes);
            } else {
                delete.addColumn(familyBytes, qualifierBytes, ts.getTime());
            }
        }
        return delete;
    }

    protected List<HBaseCellResult> convertToHBaseCellResultList(String tableName, Result result, HBaseTableSchema tableSchema) {
        HBaseColumn row = tableSchema.findRow();
        final Cell[] cells = result.rawCells();
        if (cells == null || cells.length == 0) {
            return new ArrayList<>();
        }
        String familyStr = null;
        String qualifierStr = null;

        try {
            List<HBaseCellResult> resultList = new ArrayList<>();

            for (Cell cell : cells) {
                familyStr = Bytes.toString(CellUtil.cloneFamily(cell));
                qualifierStr = Bytes.toString(CellUtil.cloneQualifier(cell));
                byte[] hbaseVal = CellUtil.cloneValue(cell);
                final HBaseColumn column = findColumnSchema(tableName, familyStr, qualifierStr);
                final TypeHandler<?> typeHandler = column.getColumnType().getTypeHandler();
                Object valueObject = typeHandler.toObject(column.getColumnType().getTypeClass(), hbaseVal);

                long ts = cell.getTimestamp();
                HBaseCellResult cellResult = new HBaseCellResult();
                cellResult.setFamilyStr(familyStr);
                cellResult.setQualifierStr(qualifierStr);
                cellResult.setColumnName(familyStr + ":" + qualifierStr);
                cellResult.setValue(valueObject);
                cellResult.setTimestamp(ts);
                Object rowKey = row.getColumnType().getTypeHandler().toObject(row.getColumnType().getTypeClass(), result.getRow());
                cellResult.setRowKey(rowKey);
                resultList.add(cellResult);
            }
            return resultList;

        } catch (Exception e) {
            throw new HBaseOperationsException(
                    "convert result exception. familyStr=" + familyStr
                            + " qualifierStr=" + qualifierStr
                            + " result="
                            + result, e);
        }
    }

    /**
     * 筛选我们需要的字段列表
     *
     * @param hbaseColumnSchemaList 字段列表
     * @param scan                  scan
     */
    protected void applyRequestFamilyAndQualifier(List<HBaseColumn> hbaseColumnSchemaList, Scan scan) {
        for (HBaseColumn hbaseColumnSchema : hbaseColumnSchemaList) {
            if (hbaseColumnSchema.columnIsRow()) {
                continue;
            }
            scan.addColumn(Bytes.toBytes(hbaseColumnSchema.getFamilyName()),
                    Bytes.toBytes(hbaseColumnSchema.getColumnName()));
        }
    }

    protected byte[] convertValueToBytes(Object value, HBaseColumn column) {
        TypeHandler<?> typeHandler = column.getColumnType().getTypeHandler();
        return typeHandler.toBytes(column.getColumnType().getTypeClass(), value);
    }

    private TableQuerySetting getTableQuerySetting(String tableName) {
        return HBaseSqlContext.getTableSchema(tableName).getTableQuerySetting();
    }

    protected HBaseSQLParser.ProgContext parseProgContext(String hql) {
        checkSql(hql);
        try {
            ANTLRInputStream input = new ANTLRInputStream(new StringReader(hql));
            HBaseSQLStatementsLexer lexer = new HBaseSQLStatementsLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            HBaseSQLParser parser = new HBaseSQLParser(tokens);
            parser.setErrorHandler(HBaseSQLErrorStrategy.INSTANCE);
            return parser.prog();
        } catch (Exception e) {
            throw new HBaseOperationsException("parse hql error.", e);
        }
    }

    protected String parseTableName(String hql) {
        checkSql(hql);
        HBaseSQLParser.ProgContext progContext = parseProgContext(hql);
        return parseTableName(progContext);
    }

    protected String parseTableName(HBaseSQLParser.ProgContext progContext) {
        MyAssert.checkNotNull(progContext);

        if (progContext instanceof HBaseSQLParser.InsertHqlClContext) {
            return ((HBaseSQLParser.InsertHqlClContext) progContext).inserthqlc().tableName().STRING().getText();
        }
        if (progContext instanceof HBaseSQLParser.SelectHqlClContext) {
            return ((HBaseSQLParser.SelectHqlClContext) progContext).selecthqlc().tableName().STRING().getText();
        }
        if (progContext instanceof HBaseSQLParser.DeleteHqlClContext) {
            return ((HBaseSQLParser.DeleteHqlClContext) progContext).deletehqlc().tableName().STRING().getText();
        }
        throw new HBaseOperationsException("can't parse the name of hbase table.");
    }

    protected HQLType parseHQLType(String hql) {
        checkSql(hql);
        HBaseSQLParser.ProgContext progContext = parseProgContext(hql);
        return parseHQLType(progContext);
    }

    protected HQLType parseHQLType(HBaseSQLParser.ProgContext progContext) {
        MyAssert.checkNotNull(progContext);

        if (progContext instanceof HBaseSQLParser.InsertHqlClContext) {
            return HQLType.PUT;
        }
        if (progContext instanceof HBaseSQLParser.SelectHqlClContext) {
            return HQLType.SELECT;
        }
        if (progContext instanceof HBaseSQLParser.DeleteHqlClContext) {
            return HQLType.DELETE;
        }
        throw new HBaseOperationsException("can't parse hql type.");
    }

    protected void checkSql(String sql) {
        MyAssert.checkArgument(StringUtil.isNotBlank(sql), "The sql is not empty.");
    }

    protected void checkTableName(String tableName) {
        MyAssert.checkArgument(StringUtil.isNotBlank(tableName), "The table name is not empty.");
    }
}
