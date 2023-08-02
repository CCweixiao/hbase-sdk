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
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.connection.HBaseConnectionUtil;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.HBaseSQLErrorListener;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.data.InsertRowData;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.visitor.InsertValueVisitor;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.visitor.RowKeyRangeVisitor;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.visitor.SelectColListVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.QueryExtInfo;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.context.HBaseSqlContext;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.HBaseSQLStatementsLexer;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.data.RowKeyRange;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import com.github.CCwexiao.hbase.sdk.dsl.model.TableQueryProperties;
import com.github.CCwexiao.hbase.sdk.dsl.util.Util;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
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
    protected static final TableName HQL_META_DATA_TABLE_NAME = TableName.valueOf("HQL.META_DATA");
    protected static final byte[] HQL_META_DATA_TABLE_FAMILY = Bytes.toBytes( "f");
    protected static final byte[] HQL_META_DATA_TABLE_QUALIFIER = Bytes.toBytes( "schema");

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
    public void createVirtualTable(String hql) {
        HBaseSQLParser.QueryContext queryContext = parseQueryContext(hql);
        HBaseSQLParser.CreateTableStatementContext createTableStatementContext = queryContext.createTableStatement();
        if (createTableStatementContext == null) {
            throw new HBaseSqlAnalysisException("Please enter the statement create virtual table.");
        }
        HBaseSQLParser.FieldsContext fieldsContext = createTableStatementContext.fields();
        List<HBaseSQLParser.FieldContext> fields = fieldsContext.field();
        String virtualTableName = HMHBaseConstants.getFullTableName(createTableStatementContext.tableName().getText().trim());
        HBaseTableSchema.Builder tableSchemaBuilder = HBaseTableSchema.of(virtualTableName);
        for (HBaseSQLParser.FieldContext fieldContext : fields) {
            String filedName = fieldContext.fieldName().ID().getText();
            String fieldType = fieldContext.fieldType().ID().getText();
            TerminalNode nullable = fieldContext.NULLABLE();
            HBaseColumn row = getRowColumn(fieldContext, filedName, fieldType, nullable);
            if (row != null) {
                tableSchemaBuilder.addColumn(row);
            } else {
                HBaseColumn column = getColumn(filedName, fieldType, nullable);
                if (column != null) {
                    tableSchemaBuilder.addColumn(column);
                }
            }
        }

        HBaseSQLParser.PropertiesContext properties = createTableStatementContext.properties();
        if (properties != null) {
            List<HBaseSQLParser.KeyValueContext> keyValueContexts = properties.keyValue();
            for (HBaseSQLParser.KeyValueContext keyValueContext : keyValueContexts) {
                List<TerminalNode> kvs = keyValueContext.ID();
                String key = kvs.get(0).getText().trim();
                String value = kvs.get(1).getText().trim();
                if (HBaseConfigKeys.HBASE_CLIENT_SCANNER_CACHING.equals(key)) {
                    tableSchemaBuilder.scanCaching(Integer.parseInt(value));
                } else if (HBaseConfigKeys.HBASE_CLIENT_BLOCK_CACHE.equals(key)) {
                    tableSchemaBuilder.scanCacheBlocks(Boolean.parseBoolean(value));
                } else {
                    throw new HBaseSqlAnalysisException(String.format("Configuration [%s] not currently supported.", key));
                }
            }
        }
        HBaseTableSchema tableSchema = tableSchemaBuilder.build();
        checkAndCreateHqlMetaTable();
        boolean res = saveTableSchemaMeta(tableSchema);
        if (res) {
            registerTableSchema(tableSchema);
        }
    }

    @Override
    public void dropVirtualTable(String hql) {
        HBaseSQLParser.QueryContext queryContext = parseQueryContext(hql);
        HBaseSQLParser.DropTableStatementContext dropTableStatementContext = queryContext.dropTableStatement();
        if (dropTableStatementContext == null) {
            throw new HBaseSqlAnalysisException("Please enter the statement drop virtual table.");
        }
        String virtualTableName = HMHBaseConstants.getFullTableName(dropTableStatementContext.tableName().getText().trim());

        Get get = new Get(Bytes.toBytes(virtualTableName));
        boolean virtualTableExists = this.execute(HQL_META_DATA_TABLE_NAME.getNameAsString(), table -> {
            Result result = table.get(get);
            if (result == null) {
                return false;
            }
            byte[] value = result.getValue(HQL_META_DATA_TABLE_FAMILY, HQL_META_DATA_TABLE_QUALIFIER);
            return value != null && StringUtil.isNotBlank(Bytes.toString(value));
        }).orElse(false);
        if (!virtualTableExists) {
            throw new HBaseSqlAnalysisException(String.format("The virtual table %s does not exist.", virtualTableName));
        }
        Delete delete = new Delete(Bytes.toBytes(virtualTableName));
        boolean deleteRes = this.execute(HQL_META_DATA_TABLE_NAME.getNameAsString(), table -> {
            table.delete(delete);
            return true;
        }).orElse(false);
        if (!deleteRes) {
            throw new HBaseSqlAnalysisException(String.format("The virtual table %s failed to be deleted.", virtualTableName));
        }
        removeTableSchema(virtualTableName);
    }

    protected abstract void checkAndCreateHqlMetaTable();

    protected abstract boolean saveTableSchemaMeta(HBaseTableSchema tableSchema);

    protected HBaseTableSchema getTableSchema(String tableName) {
        String uniqueKey = HBaseConnectionUtil.generateUniqueConnectionKey(this.getProperties());
        uniqueKey = uniqueKey + "#" + HMHBaseConstants.getFullTableName(tableName);
        HBaseTableSchema tableSchema = HBaseSqlContext.getInstance().getTableSchema(uniqueKey);
        if (tableSchema != null) {
            return tableSchema;
        }
        Get get = new Get(Bytes.toBytes(tableName));
        String tableSchemaJson = this.execute(HQL_META_DATA_TABLE_NAME.getNameAsString(), table -> {
            Result result = table.get(get);
            if (result == null) {
                return "";
            }
            byte[] value = result.getValue(HQL_META_DATA_TABLE_FAMILY, HQL_META_DATA_TABLE_QUALIFIER);
            return Bytes.toString(value);
        }).orElse("");
        tableSchema = HBaseTableSchema.empty().build();
        tableSchema = tableSchema.convert(tableSchemaJson);
        if (tableSchema == null) {
            throw new HBaseSqlTableSchemaMissingException(
                    String.format("The table [%s] has no table schema, please register first.", tableName));
        }
        this.registerTableSchema(tableSchema);
        return tableSchema;
    }

    private HBaseColumn getColumn(String filedName, String fieldType, TerminalNode nullable) {
        String[] cols = filedName.split(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR);
        if (cols.length != 2) {
            throw new HBaseSqlAnalysisException("An example field name is family:qualifier.");
        }
        String family = cols[0].trim();
        String qualifier = cols[1].trim();
        return HBaseColumn.of(family, qualifier)
                .nullable(nullable != null)
                .columnType(ColumnType.getColumnType(fieldType)).build();
    }


    private HBaseColumn getRowColumn(HBaseSQLParser.FieldContext fieldContext, String filedName,
                                     String fieldType, TerminalNode nullable) {
        TerminalNode isRowKey = fieldContext.ISROWKEY();
        if (isRowKey == null) {
            return null;
        }
        if (nullable != null) {
            throw new HBaseSqlAnalysisException("The rowKey field cannot be nullable.");
        }
        fieldType = fieldType.toLowerCase();
        return HBaseColumn.of("", filedName)
                .columnIsRow(true)
                .columnType(ColumnType.getColumnType(fieldType))
                .nullable(false)
                .build();
    }

    @Override
    public HBaseDataSet select(String hql, Map<String, Object> params) {
        HBaseSQLParser.QueryContext queryContext = parseQueryContext(hql);
        HBaseSQLParser.SelectStatementContext selectStatementContext = queryContext.selectStatement();
        if (selectStatementContext == null) {
            throw new HBaseSqlAnalysisException("Please enter the statement select table.");
        }
        String tableName = selectStatementContext.tableName().getText();
        HBaseTableSchema tableSchema = this.getTableSchema(tableName);
        // 解析查询字段 * 或指定字段
        HBaseSQLParser.SelectColListContext selectColListContext = selectStatementContext.selectColList();

        // 解析查询字段
        final List<HBaseColumn> queryColumns = new SelectColListVisitor(tableSchema).extractColumns(selectColListContext);
        if (queryColumns == null || queryColumns.isEmpty()) {
            throw new HBaseSqlAnalysisException(String.format("The list of field names to be selected cannot be parsed from hql [%s]", hql));
        }
        RowKeyRangeVisitor rowKeyRangeVisitor = new RowKeyRangeVisitor(tableSchema);
        RowKeyRange rowKeyRange = rowKeyRangeVisitor.extractRowKeyRange(selectStatementContext.rowKeyRangeExp());
        QueryExtInfo queryExtInfo = rowKeyRangeVisitor.parseQueryExtInfo(selectStatementContext);
        // 解析字段或row key的filter条件
        Filter filter = this.parseFilter(selectStatementContext.wherec(), params, tableSchema);
        // = rowKey 即：get row

        if (rowKeyRange.isMatchGet()) {
            Get get = constructGet(rowKeyRange.getEqRow(), queryExtInfo, filter, queryColumns);
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
        if (rowKeyRange.isMatchGetRows()) {
            List<RowKey<?>> queryInRows = rowKeyRange.getInSomeKeys();
            int limit;
            if (queryExtInfo.isLimitSet()) {
                limit = queryExtInfo.getLimit();
            } else {
                limit = queryInRows.size();
            }
            if (limit > queryInRows.size()) {
                limit = queryInRows.size();
            }
            Get[] getArr = new Get[limit];
            for (int i = 0; i < getArr.length; i++) {
                getArr[i] = constructGet(queryInRows.get(i), queryExtInfo, filter, queryColumns);
            }
            return this.execute(tableName, table -> {
                HBaseDataSet dataSet = HBaseDataSet.of(tableName);
                final Result[] results = table.get(Arrays.asList(getArr));
                if (results != null) {
                    for (Result result : results) {
                        List<HBaseDataRow> rowList = convertResultToDataRow(result, tableSchema, queryExtInfo);
                        dataSet.appendRows(rowList);
                    }
                }
                return dataSet;
            }).orElse(HBaseDataSet.of(tableName));
        }
        Scan scan = constructScan(tableName, rowKeyRange, queryExtInfo, filter, queryColumns);
        // 构造scan查询条件
       if (rowKeyRange.isMatchScanByRowPrefix()) {
            scan = setScanRowPrefixFilter(scan, rowKeyRange.getRowPrefix());
        }

        try {
            return queryToDataSet(tableSchema, queryExtInfo, scan);
        } catch (Exception e) {
            throw new HBaseSqlExecuteException("Select error. hql=" + hql, e);
        }
    }

    private HBaseDataSet queryToDataSet(HBaseTableSchema tableSchema, QueryExtInfo queryExtInfo, Scan scan) {
        String tableName = tableSchema.getTableName();
        return this.execute(tableName, table -> {
            int limit = Integer.MAX_VALUE;

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
    }

    @Override
    public HBaseDataSet select(String hql) {
        return select(hql, new HashMap<>(0));
    }

    @Override
    public void insert(String hql) {
        HBaseSQLParser.QueryContext queryContext = parseQueryContext(hql);
        HBaseSQLParser.InsertStatementContext insertStatementContext = queryContext.insertStatement();
        if (insertStatementContext == null) {
            throw new HBaseSqlAnalysisException("Please enter the statement insert into table.");
        }
        String tableName = insertStatementContext.tableName().ID().getText();
        HBaseTableSchema tableSchema = this.getTableSchema(tableName);


        List<String> insertCols = insertStatementContext.columnList()
                .column().stream().map(c -> c.ID().getText())
                .collect(Collectors.toList());

        InsertValueVisitor insertValueVisitor = new InsertValueVisitor(tableSchema, insertCols);
        long timestamp = insertValueVisitor.parseTimestamp(insertStatementContext);
        List<InsertRowData> rowDataList = new InsertValueVisitor(tableSchema, insertCols)
                .parseInsertConstantValue(insertStatementContext);
        if (rowDataList.size() == 1) {
            Put put = this.constructPut(rowDataList.get(0), timestamp);
            this.executeSave(tableName, put);
        }

        List<Mutation> puts = rowDataList.stream().map(rowData -> this.constructPut(rowData, timestamp))
                .collect(Collectors.toList());
        this.executeSaveBatch(tableName, puts);
    }

    @Override
    public void delete(String hql) {
        HBaseSQLParser.QueryContext queryContext = parseQueryContext(hql);
        HBaseSQLParser.DeleteStatementContext deleteStatementContext = queryContext.deleteStatement();
        if (deleteStatementContext == null) {
            throw new HBaseSqlAnalysisException("Please enter the statement delete from table.");
        }
        String tableName = deleteStatementContext.tableName().getText();
        HBaseTableSchema tableSchema = this.getTableSchema(tableName);
        // 解析删除字段 * 或指定字段
        HBaseSQLParser.SelectColListContext deleteColListContext = deleteStatementContext.selectColList();
        List<HBaseColumn> deleteColumns = new SelectColListVisitor(tableSchema).extractColumns(deleteColListContext);
        if (deleteColumns == null || deleteColumns.isEmpty()) {
            throw new HBaseSqlAnalysisException(String.format("The list of field names to be deleted cannot be parsed from hql [%s]", hql));
        }
        //Row in start row and end row
        RowKeyRangeVisitor rowKeyRangeVisitor = new RowKeyRangeVisitor(tableSchema);
        RowKeyRange rowKeyRange = rowKeyRangeVisitor.extractRowKeyRange(deleteStatementContext.rowKeyRangeExp());
        RowKey<?> startRowKey = rowKeyRange.getStart();
        RowKey<?> endRowKey = rowKeyRange.getStop();
        // delete one row key
        RowKey<?> eqRowKey = rowKeyRange.getEqRow();
        // delete in row key list
        List<RowKey<?>> inRowKeyList = rowKeyRange.getInSomeKeys();
        // filter
        Filter filter = this.parseFilter(deleteStatementContext.wherec(), tableSchema);
        long ts = 0;
        if (deleteStatementContext.TS() != null) {
            ts = rowKeyRangeVisitor.extractTimeStamp(deleteStatementContext.tsExp());
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
            deleteWithScanFirst(hql, tableName, rowKeyRange, filter, deleteColumns, ts);
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

    private void deleteWithScanFirst(String hql, String tableName, RowKeyRange rowKeyRange,
                                     Filter filter, List<HBaseColumn> deleteColumns, long ts) {

        final int deleteBatch = getDeleteBatch(tableName);
        while (true) {
            Scan firstScan = constructScan(tableName, rowKeyRange, null, filter, deleteColumns);
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
                                rowKeyRange.getStart().setValueBytes(result.getRow());
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

    protected abstract Scan constructScan(String tableName, RowKeyRange rowKeyRange, QueryExtInfo queryExtInfo,
                                          Filter filter, List<HBaseColumn> columnList);

    protected abstract Scan setScanRowPrefixFilter(Scan scan, RowKey<?> rowPrefixKey);

    protected abstract Put constructPut(InsertRowData rowData, long ts);

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

    private TableQueryProperties getTableQueryProperties(String tableName) {
        return this.getTableSchema(tableName).getTableQueryProperties();
    }

    protected HBaseSQLParser.QueryContext parseQueryContext(String hql) {
        if (StringUtil.isBlank(hql)) {
            throw new HBaseSqlAnalysisException("Please enter hql.");
        }
        try {
            ANTLRInputStream input = new ANTLRInputStream(new StringReader(hql));
            HBaseSQLStatementsLexer lexer = new HBaseSQLStatementsLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            HBaseSQLParser parser = new HBaseSQLParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new HBaseSQLErrorListener());
            return parser.query();
        } catch (Exception e) {
            throw new HBaseSqlAnalysisException(String.format("The hql %s was parsed failed.", hql), e);
        }
    }

    public String parseTableNameFromHql(String hql) {
        HBaseSQLParser.QueryContext queryContext = parseQueryContext(hql);
        return parseTableNameFromHql(queryContext);
    }

    protected String parseTableNameFromHql(HBaseSQLParser.QueryContext queryContext) {
        String tableName;
        if (queryContext.selectStatement() != null) {
            tableName = queryContext.selectStatement().tableName().getText();
        } else if (queryContext.insertStatement() != null) {
            tableName = queryContext.insertStatement().tableName().getText();
        } else if (queryContext.deleteStatement() != null) {
            tableName = queryContext.deleteStatement().tableName().getText();
        } else {
            throw new HBaseSqlAnalysisException("The table name cannot be parsed from the input hql.");
        }
        checkTableNameIsNotEmpty(tableName);
        return tableName;
    }

    public HQLType parseHQLType(String hql) {
        HBaseSQLParser.QueryContext queryContext = parseQueryContext(hql);
        return parseHQLType(queryContext);
    }

    protected HQLType parseHQLType(HBaseSQLParser.QueryContext queryContext) {

        if (queryContext.insertStatement() != null) {
            return HQLType.PUT;
        }
        if (queryContext.selectStatement() != null) {
            return HQLType.SELECT;
        }
        if (queryContext.deleteStatement() != null) {
            return HQLType.DELETE;
        }
        throw new HBaseOperationsException("can't parse hql type.");
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
        TableQueryProperties tableQueryProperties = tableSchema.getTableQueryProperties();
        if (tableQueryProperties.getScanCaching() < 1) {
            tableQueryProperties.setScanCaching(caching);
        }
        tableSchema.setTableQuerySetting(tableQueryProperties);
        HBaseSqlContext.getInstance().registerTableSchema(uniqueKey, tableSchema);
    }

    private void removeTableSchema(String virtualTableName) {
        String uniqueKey = HBaseConnectionUtil.generateUniqueConnectionKey(this.getProperties());
        uniqueKey = uniqueKey + "#" + virtualTableName;
        HBaseSqlContext.getInstance().removeTableSchema(uniqueKey);
    }


    @Override
    public void printTableSchema(String tableName) {
        HBaseTableSchema tableSchema = this.getTableSchema(tableName);
        if (tableSchema == null) {
            return;
        }
        tableSchema.printSchema();
    }
}
