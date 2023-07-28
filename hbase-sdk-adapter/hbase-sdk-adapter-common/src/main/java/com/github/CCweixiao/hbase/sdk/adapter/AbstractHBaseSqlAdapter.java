package com.github.CCweixiao.hbase.sdk.adapter;

import com.github.CCweixiao.hbase.sdk.common.constants.HBaseConfigKeys;
import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlExecuteException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlTableSchemaMissingException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.model.HQLType;
import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataRow;
import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;
import com.github.CCweixiao.hbase.sdk.common.type.TypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.connection.HBaseConnectionUtil;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.data.InsertRowData;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.visitor.InsertValueVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.QueryExtInfo;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.context.HBaseSqlContext;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLErrorStrategy;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLStatementsLexer;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSqlAnalysisUtil;
import com.github.CCwexiao.hbase.sdk.dsl.manual.RowKeyRange;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import com.github.CCwexiao.hbase.sdk.dsl.model.TableQueryProperties;
import com.github.CCwexiao.hbase.sdk.dsl.util.Util;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.yetus.audience.InterfaceAudience;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/11/28 8:34 下午
 */
@InterfaceAudience.Private
public abstract class AbstractHBaseSqlAdapter extends AbstractHBaseBaseAdapter implements IHBaseSqlAdapter {

    public AbstractHBaseSqlAdapter(Properties properties) {
        super(properties);
    }

    public AbstractHBaseSqlAdapter(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public AbstractHBaseSqlAdapter(Configuration configuration) {
        super(configuration);
    }

    @Override
    public HBaseDataSet select(String hql, Map<String, Object> params) {
        HBaseSQLParser.ProgContext progContext = parseQueryContext(hql);
        HBaseSQLParser.SelecthqlcContext selectHqlContext = HBaseSqlAnalysisUtil.parseSelectHqlContext(progContext);
        String tableName = parseTableNameFromHql(progContext);
        HBaseTableSchema tableSchema = this.getTableSchema(tableName);
        // 解析查询字段
        HBaseSQLParser.SelectColListContext selectColListContext = selectHqlContext.selectColList();
        final List<HBaseColumn> queryColumns = HBaseSqlAnalysisUtil.extractColumnSchemaList(tableSchema, selectColListContext);
        if (queryColumns == null || queryColumns.isEmpty()) {
            throw new HBaseSqlAnalysisException(String.format("The list of field names to be selected cannot be parsed from hql [%s]", hql));
        }
        // 解析字段或row key的filter条件
        Filter filter = this.parseFilter(selectHqlContext.wherec(), params, tableSchema);
        // row key range
        RowKeyRange rowKeyRange = HBaseSqlAnalysisUtil.extractRowKeyRange(tableSchema, selectHqlContext.rowKeyRangeExp());
        // start or end row
        RowKey<?> startRowKey = rowKeyRange.getStart();
        RowKey<?> endRowKey = rowKeyRange.getEnd();
        // in some keys
        List<RowKey<?>> queryInRows = rowKeyRange.getInSomeKeys();
        // eq row
        RowKey<?> eqRowKey = rowKeyRange.getEqRow();
        QueryExtInfo queryExtInfo = HBaseSqlAnalysisUtil.parseQueryExtInfo(tableSchema, selectHqlContext);

        // = rowKey 即：get row
        if (eqRowKey != null) {
            Get get = constructGet(eqRowKey, queryExtInfo, filter, queryColumns);
            return this.execute(tableName, table -> {
                Result result = table.get(get);
                if (result == null) {
                    return null;
                }
                List<HBaseDataRow> rowList = convertResultToDataRow(result, tableSchema, queryExtInfo);
                return HBaseDataSet.of(tableName).appendRows(rowList);
            }).orElse(HBaseDataSet.of(tableName));
        }

        // in row keys; get rows
        if (queryInRows != null && !queryInRows.isEmpty()) {
            long limit;
            if (queryExtInfo.isLimitSet()) {
                limit = queryExtInfo.getLimit();
            } else {
                limit = queryInRows.size();
            }
            if (limit > queryInRows.size()) {
                limit = queryInRows.size();
            }
            Get[] getArr = new Get[(int) limit];
            for (int i = 0; i < getArr.length; i++) {
                getArr[i] = constructGet(queryInRows.get(i), queryExtInfo, filter, queryColumns);
            }
            return this.execute(tableName, table -> {
                HBaseDataSet dataSet = HBaseDataSet.of(tableName);
                final Result[] results = table.get(Arrays.asList(getArr));
                if (results != null && results.length > 0) {
                    for (Result result : results) {
                        List<HBaseDataRow> rowList = convertResultToDataRow(result, tableSchema, queryExtInfo);
                        dataSet.appendRows(rowList);
                    }
                }
                return dataSet;
            }).orElse(HBaseDataSet.of(tableName));
        }

        // scan 查询
        Scan scan = constructScan(tableName, startRowKey, endRowKey, queryExtInfo, filter, queryColumns);

        try {
            return this.execute(tableName, table -> {
                long limit = Long.MAX_VALUE;

                if (queryExtInfo.isLimitSet()) {
                    limit = queryExtInfo.getLimit();
                }

                HBaseDataSet dataSet = HBaseDataSet.of(tableName);

                try (ResultScanner scanner = table.getScanner(scan)) {
                    long resultCounter = 0L;
                    Result result;
                    while ((result = scanner.next()) != null) {
                        List<HBaseDataRow> rowList = convertResultToDataRow(result, tableSchema, queryExtInfo);
                        dataSet.appendRows(rowList);
                        if (++resultCounter >= limit) {
                            break;
                        }
                    }
                    return dataSet;
                }
            }).orElse(HBaseDataSet.of(tableName));
        } catch (Exception e) {
            throw new HBaseSqlExecuteException("Select error. hql=" + hql, e);
        }
    }

    @Override
    public HBaseDataSet select(String hql) {
        return select(hql, new HashMap<>(0));
    }

    @Override
    public void insert(String hql) {
        HBaseSQLParser.QueryContext queryContext = parseQueryContext(hql);
        HBaseSQLParser.InsertStatementContext insertStatementContext = queryContext.insertStatement();
        String tableName = insertStatementContext.tableName().ID().getText();
        HBaseTableSchema tableSchema = this.getTableSchema(tableName);


        List<String> insertCols = insertStatementContext.columnList()
                .column().stream().map(c -> c.ID().getText())
                .collect(Collectors.toList());


        List<InsertRowData> rowDataList = new InsertValueVisitor(tableSchema, insertCols)
                .parseInsertConstantValue(insertStatementContext);


        List<HBaseColumn> insertColumns = HBaseSqlAnalysisUtil.extractColumnSchemaList(tableSchema, insertHqlContext.colList());
        if (insertColumns.isEmpty()) {
            throw new HBaseSqlAnalysisException(String.format("The list of field names to be inserted cannot be parsed from hql [%s]", hql));
        }
        final List<HBaseSQLParser.InsertValueContext> insertValueContextList = insertHqlContext.insertValueList().insertValue();
        MyAssert.checkArgument(insertColumns.size() == insertValueContextList.size(),
                "The inserted fields length should be same as the values length.");

        final HBaseSQLParser.RowKeyExpContext rowKeyExpContext = insertHqlContext.rowKeyExp();
        RowKey<?> rowKey = HBaseSqlAnalysisUtil.extractRowKey(tableSchema, rowKeyExpContext);

        long ts = -1;
        HBaseSQLParser.TsExpContext tsExpContext = insertHqlContext.tsExp();
        if (tsExpContext != null) {
            ts = HBaseSqlAnalysisUtil.extractTimeStamp(tsExpContext);
        }

        Put put = new Put(rowKey.toBytes());
        for (int i = 0; i < insertColumns.size(); i++) {
            HBaseColumn hbaseColumnSchema = insertColumns.get(i);
            HBaseSQLParser.InsertValueContext insertValueContext = insertValueContextList.get(i);
            Object value = HBaseSqlAnalysisUtil.extractInsertConstantValue(hbaseColumnSchema, insertValueContext);
            byte[] data = convertValueToBytes(value, hbaseColumnSchema);
            if (ts < 0) {
                put.addColumn(hbaseColumnSchema.getFamilyNameBytes(), hbaseColumnSchema.getColumnNameBytes(), data);
            } else {
                put.addColumn(hbaseColumnSchema.getFamilyNameBytes(), hbaseColumnSchema.getColumnNameBytes(), ts, data);
            }
        }

        this.execute(tableName, table -> {
            table.put(put);
            return true;
        });
    }

    @Override
    public void delete(String hql) {
        HBaseSQLParser.ProgContext progContext = parseQueryContext(hql);
        HBaseSQLParser.DeletehqlcContext deleteHqlContext = HBaseSqlAnalysisUtil.parseDeleteHqlContext(progContext);
        String tableName = parseTableNameFromHql(progContext);
        HBaseTableSchema tableSchema = this.getTableSchema(tableName);
        //delete col list
        HBaseSQLParser.SelectColListContext deleteColListContext = deleteHqlContext.selectColList();
        List<HBaseColumn> deleteColumns = HBaseSqlAnalysisUtil.extractColumnSchemaList(tableSchema, deleteColListContext);
        if (deleteColumns == null || deleteColumns.isEmpty()) {
            throw new HBaseSqlAnalysisException(String.format("The list of field names to be deleted cannot be parsed from hql [%s]", hql));
        }
        //Row in start row and end row
        RowKeyRange rowKeyRange = HBaseSqlAnalysisUtil.extractRowKeyRange(tableSchema, deleteHqlContext.rowKeyRangeExp());
        RowKey<?> startRowKey = rowKeyRange.getStart();
        RowKey<?> endRowKey = rowKeyRange.getEnd();
        // delete one row key
        RowKey<?> eqRowKey = rowKeyRange.getEqRow();
        // delete in row key list
        List<RowKey<?>> inRowKeyList = rowKeyRange.getInSomeKeys();
        // filter
        Filter filter = this.parseFilter(deleteHqlContext.wherec(), tableSchema);

        long ts = -1;
        HBaseSQLParser.TsExpContext tsExpContext = deleteHqlContext.tsExp();
        if (tsExpContext != null) {
            ts = HBaseSqlAnalysisUtil.extractTimeStamp(tsExpContext);
        }
        // delete eq row key
        if (eqRowKey != null) {
            deleteEqRowKey(tableName, eqRowKey, deleteColumns, ts);
            return;
        }
        // delete in row keys
        if (inRowKeyList != null && !inRowKeyList.isEmpty()) {
            deleteInRowKeys(tableName, inRowKeyList, deleteColumns, ts);
            return;
        }
        if (startRowKey != null && endRowKey != null) {
            Util.checkRowKey(startRowKey);
            Util.checkRowKey(endRowKey);
            deleteWithScanFirst(hql, tableName, startRowKey, endRowKey, filter, deleteColumns, ts);
        }
    }

    private void deleteEqRowKey(String tableName, RowKey<?> eqRowKey, List<HBaseColumn> deleteColumns, long ts) {
        if (eqRowKey == null || eqRowKey.toBytes() == null) {
            return;
        }
        Delete delete = constructDelete(eqRowKey, deleteColumns, ts);
        this.execute(tableName, table -> {
            table.delete(delete);
            return true;
        });
    }

    private void deleteInRowKeys(String tableName, List<RowKey<?>> rowKeys,
                                 List<HBaseColumn> deleteColumns, long ts) {
        List<Mutation> deletes = new ArrayList<>();
        for (RowKey<?> rowKey : rowKeys) {
            if (rowKey == null || rowKey.toBytes() == null) {
                continue;
            }
            deletes.add(constructDelete(rowKey, deleteColumns, ts));
        }
        this.executeDeleteBatch(tableName, deletes);
    }

    private void deleteWithScanFirst(String hql, String tableName, RowKey<?> startRowKey, RowKey<?> endRowKey,
                                     Filter filter, List<HBaseColumn> deleteColumns, long ts) {

        final int deleteBatch = getDeleteBatch(tableName);
        while (true) {
            Scan firstScan = constructScan(tableName, startRowKey, endRowKey, null, filter, deleteColumns);
            // 只扫描row
            firstScan.addFamily(null);
            List<Mutation> deletes = new ArrayList<>(deleteBatch);
            try {
                this.execute(tableName, table -> {
                    try (ResultScanner scanner = table.getScanner(firstScan)) {
                        Result result;
                        while ((result = scanner.next()) != null) {
                            deletes.add(constructDelete(result, deleteColumns, ts));
                            if (deletes.size() >= deleteBatch) {
                                // 重置startKey
                                startRowKey.setValueBytes(result.getRow());
                                break;
                            }
                        }
                    }
                    return null;
                });
            } catch (Exception e) {
                throw new HBaseSqlExecuteException("Delete first scan error.", e);
            }

            final int deleteListSize = deletes.size();
            if (deleteListSize == 0) {
                return;
            }
            try {
                this.executeDeleteBatch(tableName, deletes);
                deletes.clear();
            } catch (Exception e) {
                throw new HBaseSqlExecuteException("Delete error. hql=" + hql, e);
            }

            if (deleteListSize < deleteBatch) {
                return;
            }

        }
    }

    protected int getScanCaching(String tableName) {
        return getTableQueryProperties(tableName).getScanCaching();
    }

    protected int getScanBatch(String tableName) {
        return getTableQueryProperties(tableName).getScanBatch();
    }

    protected int getDeleteBatch(String tableName) {
        return getTableQueryProperties(tableName).getDeleteBatch();
    }

    protected boolean scanCacheBlocks(String tableName) {
        return getTableQueryProperties(tableName).isScanCacheBlocks();
    }

    protected abstract Filter parseFilter(HBaseSQLParser.WherecContext whereContext, HBaseTableSchema tableSchema);
    protected abstract Filter parseFilter(HBaseSQLParser.WherecContext whereContext, Map<String, Object> queryParams, HBaseTableSchema tableSchema);

    protected abstract Get constructGet(RowKey<?> rowKey, QueryExtInfo queryExtInfo, Filter filter, List<HBaseColumn> columnList);

    protected abstract Scan constructScan(String tableName, RowKey<?> startRowKey, RowKey<?> endRowKey, QueryExtInfo queryExtInfo,
                                          Filter filter, List<HBaseColumn> columnList);

    protected abstract Delete constructDelete(RowKey<?> rowKey, List<HBaseColumn> columnSchemaList, long ts);

    protected abstract Delete constructDelete(Result result, List<HBaseColumn> columnSchemaList, long ts);

    private List<HBaseDataRow> convertResultToDataRow(Result result, HBaseTableSchema tableSchema, QueryExtInfo queryExtInfo) {
        int maxVersion = 1;
        if (queryExtInfo.isMaxVersionSet()) {
            maxVersion = queryExtInfo.getMaxVersions();
        }
        List<HBaseDataRow> dataRows = new ArrayList<>(maxVersion);
        Object rowKey = tableSchema.findRow().convertBytesToVal(result.getRow());
        if (rowKey == null) {
            return new ArrayList<>(0);
        }
        for (HBaseColumn columnSchema : tableSchema.findAllColumns()) {
            List<Cell> cells = result.getColumnCells(columnSchema.getFamilyNameBytes(), columnSchema.getColumnNameBytes());
            if (cells == null || cells.isEmpty()) {
                continue;
            }
            for (int i = 0; i < cells.size(); i++) {
                if (dataRows.size() < i + 1) {
                    dataRows.add(HBaseDataRow.of(rowKey));
                }
                Cell cell = cells.get(i);
                Object value = columnSchema.getColumnType().getTypeHandler().
                        toObject(columnSchema.getColumnType().getTypeClass(), CellUtil.cloneValue(cell));

                dataRows.get(i).appendColumn(columnSchema.getFamily(), columnSchema.getColumnName(), columnSchema.getColumnType(),
                        value, cell.getTimestamp());
            }
        }
        return dataRows;
    }

    private byte[] convertValueToBytes(Object value, HBaseColumn column) {
        TypeHandler<?> typeHandler = column.getColumnType().getTypeHandler();
        return typeHandler.toBytes(column.getColumnType().getTypeClass(), value);
    }

    private TableQueryProperties getTableQueryProperties(String tableName) {
        return this.getTableSchema(tableName).getTableQuerySetting();
    }

    protected HBaseSQLParser.QueryContext parseQueryContext(String hql) {
        checkHqlIsNotEmpty(hql);
        try {
            ANTLRInputStream input = new ANTLRInputStream(new StringReader(hql));
            HBaseSQLStatementsLexer lexer = new HBaseSQLStatementsLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            HBaseSQLParser parser = new HBaseSQLParser(tokens);
            parser.setErrorHandler(HBaseSQLErrorStrategy.INSTANCE);
            return parser.query();
        } catch (Exception e) {
            throw new HBaseSqlAnalysisException(String.format("The hql %s was parsed failed.", hql), e);
        }
    }

    public String parseTableNameFromHql(String hql) {
        checkHqlIsNotEmpty(hql);
        HBaseSQLParser.ProgContext progContext = parseQueryContext(hql);
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
            throw new HBaseSqlAnalysisException("The table name cannot be parsed from the input hql.");
        }
        checkTableNameIsNotEmpty(tableName);
        return tableName;
    }

    public HQLType parseHQLType(String hql) {
        checkHqlIsNotEmpty(hql);
        HBaseSQLParser.ProgContext progContext = parseQueryContext(hql);
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

    protected void checkHqlIsNotEmpty(String hql) {
        MyAssert.checkArgument(StringUtil.isNotBlank(hql), "The hql is not empty.");
    }

    private void checkTableNameIsNotEmpty(String tableName) {
        MyAssert.checkArgument(StringUtil.isNotBlank(tableName), "The table name is not empty.");
    }

    @Override
    public void registerTableSchema(HBaseTableSchema tableSchema) {
        String uniqueKey = HBaseConnectionUtil.generateUniqueConnectionKey(this.getProperties());
        uniqueKey = uniqueKey + "#" + tableSchema.getTableName();
        int caching = this.getConfiguration().getInt(HBaseConfigKeys.HBASE_CLIENT_SCANNER_CACHING,
                HBaseConfigKeys.HBASE_CLIENT_DEFAULT_SCANNER_CACHING);
        TableQueryProperties tableQueryProperties = tableSchema.getTableQuerySetting();
        if (tableQueryProperties.getScanCaching() < 1) {
            tableQueryProperties.setScanCaching(caching);
        }
        tableSchema.setTableQuerySetting(tableQueryProperties);
        HBaseSqlContext.getInstance().registerTableSchema(uniqueKey, tableSchema);
    }

    @Override
    public HBaseTableSchema getTableSchema(String tableName) {
        String uniqueKey = HBaseConnectionUtil.generateUniqueConnectionKey(this.getProperties());
        uniqueKey = uniqueKey + "#" + HMHBaseConstants.getFullTableName(tableName);
        HBaseTableSchema tableSchema = HBaseSqlContext.getInstance().getTableSchema(uniqueKey);
        if (tableSchema == null) {
            throw new HBaseSqlTableSchemaMissingException(
                    String.format("The table [%s] has no table schema, please register first.", tableName));
        }
        return tableSchema;
    }

    @Override
    public void printTableSchema(String tableName) {
        HBaseTableSchema tableSchema = getTableSchema(tableName);
        if (tableSchema == null) {
            return;
        }
        tableSchema.printSchema();
    }
}
