package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.IHBaseSqlOperations;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlExecuteException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.model.HQLType;
import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataRow;
import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;
import com.github.CCweixiao.hbase.sdk.common.type.TypeHandler;
import com.github.CCweixiao.hbase.sdk.common.model.HBaseCellResult;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.QueryExtInfo;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.context.HBaseSqlContext;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLErrorStrategy;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLStatementsLexer;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import com.github.CCwexiao.hbase.sdk.dsl.model.KeyValue;
import com.github.CCwexiao.hbase.sdk.dsl.model.TableQuerySetting;
import com.github.CCwexiao.hbase.sdk.dsl.util.Util;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author leojie 2020/11/28 8:34 下午
 */
public abstract class AbstractHBaseSqlTemplate extends AbstractHBaseOperations implements IHBaseSqlOperations {

    public AbstractHBaseSqlTemplate(Properties properties) {
        super(properties);
    }

    public AbstractHBaseSqlTemplate(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public AbstractHBaseSqlTemplate(Configuration configuration) {
        super(configuration);
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
        Util.checkRowKey(rowKey);
        Get get = new Get(rowKey.toBytes());
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
                get.addColumn(column.getFamilyNameBytes(), column.getColumnNameBytes());
            }
        }
        return get;
    }
    protected abstract Scan constructScan(String tableName, RowKey<?> startRowKey, RowKey<?> endRowKey, Filter filter, QueryExtInfo queryExtInfo);

    protected Delete constructDelete(RowKey<?> rowKey, List<HBaseColumn> columnSchemaList, long ts) {
        return constructDelete(rowKey.toBytes(), columnSchemaList, ts);
    }

    protected Delete constructDelete(Result result, List<HBaseColumn> columnSchemaList, long ts) {
        return constructDelete(result.getRow(), columnSchemaList, ts);
    }

    private Delete constructDelete(byte[] row, List<HBaseColumn> columnSchemaList, long ts) {
        Delete delete = new Delete(row);
        if (columnSchemaList == null || columnSchemaList.isEmpty()) {
            return delete;
        }
        for (HBaseColumn hBaseColumnSchema : columnSchemaList) {
            if (hBaseColumnSchema.columnIsRow()) {
                continue;
            }
            byte[] familyBytes = hBaseColumnSchema.getFamilyNameBytes();
            byte[] qualifierBytes = hBaseColumnSchema.getColumnNameBytes();
            if (ts == -1) {
                delete.addColumn(familyBytes, qualifierBytes);
            } else {
                delete.addColumn(familyBytes, qualifierBytes, ts);
            }
        }
        return delete;
    }

    protected HBaseDataRow convertToHBaseDataRow(Result result, HBaseColumn rowColumn,
                                                 Map<KeyValue, HBaseColumn> columnsMap) {
        Object rowKey = rowColumn.toObject(result.getRow());
        final Cell[] cells = result.rawCells();
        if (cells == null || cells.length == 0) {
            return HBaseDataRow.of(rowKey);
        }
        if (columnsMap.isEmpty()) {
            return HBaseDataRow.of(rowKey);
        }
        HBaseDataRow row = HBaseDataRow.of(rowColumn.getColumnName(), rowKey);
        for (Cell cell : result.listCells()) {

            KeyValue tmp = new KeyValue(CellUtil.cloneFamily(cell), CellUtil.cloneQualifier(cell));
            if (!columnsMap.containsKey(tmp)) {
                continue;
            }
            HBaseColumn column = columnsMap.get(tmp);
            Object value = column.getColumnType().getTypeHandler().
                    toObject(column.getColumnType().getTypeClass(), CellUtil.cloneValue(cell));
            row.appendColumn(column.getFamilyName(), column.getColumnName(), value);
        }
        return row;
    }

    @Deprecated
    protected List<HBaseCellResult> convertToHBaseCellResultList(String tableName, Result result, HBaseTableSchema tableSchema) {
        HBaseColumn rowColumn = tableSchema.findRow();
        Object rowKey = rowColumn.toObject(result.getRow());
        final Cell[] cells = result.rawCells();
        if (cells == null || cells.length == 0) {
            return new ArrayList<>();
        }
        String familyName = null;
        String columnName = null;

        try {
            List<HBaseCellResult> resultList = new ArrayList<>();
            for (Cell cell : cells) {
                familyName = Bytes.toString(CellUtil.cloneFamily(cell));
                columnName = Bytes.toString(CellUtil.cloneQualifier(cell));
                byte[] valueByteArr = CellUtil.cloneValue(cell);
                final HBaseColumn column = findColumnSchema(tableName, familyName, columnName);
                final TypeHandler<?> typeHandler = column.getColumnType().getTypeHandler();
                Object value = typeHandler.toObject(column.getColumnType().getTypeClass(), valueByteArr);
                long timestamp = cell.getTimestamp();
                HBaseCellResult cellResult = new HBaseCellResult();
                cellResult.setFamilyName(familyName);
                cellResult.setColumnName(columnName);
                cellResult.setValue(value);
                cellResult.setTimestamp(timestamp);
                cellResult.setRowKey(rowKey);
                resultList.add(cellResult);
            }
            return resultList;

        } catch (Exception e) {
            throw new HBaseSqlExecuteException(
                    "Convert result exception. familyName=" + familyName
                            + " columnName=" + columnName
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
            scan.addColumn(hbaseColumnSchema.getFamilyNameBytes(), hbaseColumnSchema.getColumnNameBytes());
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
            throw new HBaseSqlAnalysisException(String.format("The hql %s was parsed failed.", hql), e);
        }
    }

    protected String parseTableNameFromHql(String hql) {
        checkSql(hql);
        HBaseSQLParser.ProgContext progContext = parseProgContext(hql);
        return parseTableNameFromHql(progContext);
    }

    protected String parseTableNameFromHql(HBaseSQLParser.ProgContext progContext) {
        MyAssert.checkNotNull(progContext);
        String tableName;
        if (progContext instanceof HBaseSQLParser.InsertHqlClContext) {
            tableName = ((HBaseSQLParser.InsertHqlClContext) progContext).inserthqlc().tableName().STRING().getText();
        } else if (progContext instanceof HBaseSQLParser.SelectHqlClContext) {
            tableName = ((HBaseSQLParser.SelectHqlClContext) progContext).selecthqlc().tableName().STRING().getText();
        } else if (progContext instanceof HBaseSQLParser.DeleteHqlClContext) {
            tableName = ((HBaseSQLParser.DeleteHqlClContext) progContext).deletehqlc().tableName().STRING().getText();
        } else {
            throw new HBaseSqlAnalysisException("Can not parse hbase table name from hql.");
        }
        checkTableName(tableName);
        return tableName;
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

    private void checkTableName(String tableName) {
        MyAssert.checkArgument(StringUtil.isNotBlank(tableName), "The table name is not empty.");
    }
}
