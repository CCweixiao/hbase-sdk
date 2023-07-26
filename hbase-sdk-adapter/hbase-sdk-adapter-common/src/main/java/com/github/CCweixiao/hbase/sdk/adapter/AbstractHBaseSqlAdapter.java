package com.github.CCweixiao.hbase.sdk.adapter;

import com.github.CCweixiao.hbase.sdk.common.constants.HBaseConfigKeys;
import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlTableSchemaMissingException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.model.HQLType;
import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataRow;
import com.github.CCweixiao.hbase.sdk.common.type.TypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.connection.HBaseConnectionUtil;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.QueryExtInfo;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.context.HBaseSqlContext;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLErrorStrategy;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLStatementsLexer;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import com.github.CCwexiao.hbase.sdk.dsl.model.TableQueryProperties;
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
import java.util.List;
import java.util.Properties;

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

    protected HBaseColumn findColumnSchema(String tableName, String familyName, String columnName) {
        return this.getTableSchema(tableName).findColumn(familyName, columnName);
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

    protected abstract Get constructGet(RowKey<?> rowKey, QueryExtInfo queryExtInfo, Filter filter, List<HBaseColumn> columnList);

    protected abstract Scan constructScan(String tableName, RowKey<?> startRowKey, RowKey<?> endRowKey, QueryExtInfo queryExtInfo, Filter filter, List<HBaseColumn> columnList);

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
            if (ts < 1) {
                delete.addColumn(familyBytes, qualifierBytes);
            } else {
                delete.addColumn(familyBytes, qualifierBytes, ts);
            }
        }
        return delete;
    }

    protected List<HBaseDataRow> convertResultToDataRow(Result result, HBaseTableSchema tableSchema, QueryExtInfo queryExtInfo) {
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

    protected byte[] convertValueToBytes(Object value, HBaseColumn column) {
        TypeHandler<?> typeHandler = column.getColumnType().getTypeHandler();
        return typeHandler.toBytes(column.getColumnType().getTypeClass(), value);
    }

    private TableQueryProperties getTableQueryProperties(String tableName) {
        return this.getTableSchema(tableName).getTableQuerySetting();
    }

    protected HBaseSQLParser.ProgContext parseProgContext(String hql) {
        checkHqlIsNotEmpty(hql);
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

    public String parseTableNameFromHql(String hql) {
        checkHqlIsNotEmpty(hql);
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
            throw new HBaseSqlAnalysisException("The table name cannot be parsed from the input hql.");
        }
        checkTableNameIsNotEmpty(tableName);
        return tableName;
    }

    public HQLType parseHQLType(String hql) {
        checkHqlIsNotEmpty(hql);
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
