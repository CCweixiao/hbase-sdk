package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.adapter.AbstractHBaseSqlAdapter;
import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.data.InsertColData;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.data.InsertRowData;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.data.RowKeyRange;
import com.github.CCweixiao.hbase.sdk.hql.filter.QueryFilterVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.QueryExtInfo;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import com.github.CCwexiao.hbase.sdk.dsl.util.Util;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.yetus.audience.InterfaceAudience;

import java.io.IOException;
import java.util.*;

/**
 * @author leojie 2020/11/28 8:36 下午
 */
@InterfaceAudience.Private
public class HBaseSqlAdapter extends AbstractHBaseSqlAdapter {

    public HBaseSqlAdapter(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected void checkAndCreateHqlMetaTable() {
        this.execute(admin -> {
            if (admin.tableExists(HQL_META_DATA_TABLE_NAME)) {
                return true;
            }
            HTableDescriptor tableDescriptor = new HTableDescriptor(HQL_META_DATA_TABLE_NAME);
            HColumnDescriptor columnDescriptor = new HColumnDescriptor(HQL_META_DATA_TABLE_FAMILY);
            tableDescriptor.addFamily(columnDescriptor);
            admin.createTable(tableDescriptor);
            return true;
        });
    }

    @Override
    protected boolean saveTableSchemaMeta(HBaseTableSchema tableSchema) {
        String tableName = HMHBaseConstants.getFullTableName(tableSchema.getTableName());
        Boolean oriTableExists = this.execute(admin -> admin.tableExists(TableName.valueOf(tableName)));
        if (!oriTableExists) {
            throw new HBaseSqlAnalysisException(String.format("The virtual table %s was created failed, " +
                    "because the original table %s does not exist.", tableName, tableName));
        }

        String tableSchemaJson = tableSchema.toJson();
        Get get = new Get(Bytes.toBytes(tableName));
        Optional<String> res = this.execute(tableName, table -> {
            Result result = table.get(get);
            if (result == null) {
                return "";
            }
            return Bytes.toString(result.getRow());
        });
        if (StringUtil.isNotBlank(res.orElse(""))) {
            throw new HBaseSqlAnalysisException(String.format("The virtual table %s has been created.", tableName));
        }
        Put put = new Put(Bytes.toBytes(tableName));
        put.addColumn(HQL_META_DATA_TABLE_FAMILY, HQL_META_DATA_TABLE_QUALIFIER, Bytes.toBytes(tableSchemaJson));
        this.executeSave(HQL_META_DATA_TABLE_NAME.getNameAsString(), put);
        return true;
    }

    @Override
    protected Filter parseFilter(HBaseSQLParser.WherecContext whereContext, HBaseTableSchema tableSchema) {
        if (whereContext == null) {
            return null;
        }
        if (whereContext.conditionc() == null) {
            return null;
        }
        QueryFilterVisitor filterVisitor = new QueryFilterVisitor(tableSchema, new HashMap<>(0));
        return whereContext.conditionc().accept(filterVisitor);
    }

    @Override
    protected Filter parseFilter(HBaseSQLParser.WherecContext whereContext, Map<String, Object> queryParams, HBaseTableSchema tableSchema) {
        if (whereContext == null) {
            return null;
        }
        if (whereContext.conditionc() == null) {
            return null;
        }
        QueryFilterVisitor filterVisitor = new QueryFilterVisitor(tableSchema, queryParams);
        return whereContext.conditionc().accept(filterVisitor);
    }

    @Override
    protected Get constructGet(RowKey<?> rowKey, QueryExtInfo queryExtInfo, Filter filter, List<HBaseColumn> columnList) {
        Util.checkRowKey(rowKey);
        Get get = new Get(rowKey.toBytes());
        if (queryExtInfo != null) {
            if (queryExtInfo.isMaxVersionSet()) {
                try {
                    get.setMaxVersions(queryExtInfo.getMaxVersions());
                } catch (IOException e) {
                    throw new IllegalArgumentException("should never happen.", e);
                }
            }
            if (queryExtInfo.isTimeRangeSet()) {
                try {
                    get.setTimeRange(queryExtInfo.getMinStamp(), queryExtInfo.getMaxStamp());
                } catch (IOException e) {
                    throw new IllegalArgumentException("should never happen.", e);
                }
            }
        }
        if (filter != null) {
            get.setFilter(filter);
        }
        if (columnList != null && !columnList.isEmpty()) {
            for (HBaseColumn column : columnList) {
                if (column.columnIsRow()) {
                    continue;
                }
                get.addColumn(column.getFamilyNameBytes(), column.getColumnNameBytes());
            }
        }
        return get;
    }

    @Override
    protected Scan constructScan(String tableName, RowKeyRange rowKeyRange, QueryExtInfo queryExtInfo,
                                 Filter filter, List<HBaseColumn> columnList) {
        Scan scan = new Scan();
        RowKey<?> startRowKey = rowKeyRange.getStart();
        if (startRowKey != null && startRowKey.toBytes() != null) {
            scan.setStartRow(startRowKey.toBytes());
        }
        RowKey<?> endRowKey = rowKeyRange.getStop();
        if (endRowKey != null && endRowKey.toBytes() != null) {
            scan.setStopRow(endRowKey.toBytes());
        }
        if (queryExtInfo.isMaxVersionSet()) {
            scan.setMaxVersions(queryExtInfo.getMaxVersions());
        }
        if (queryExtInfo.isTimeRangeSet()) {
            try {
                scan.setTimeRange(queryExtInfo.getMinStamp(), queryExtInfo.getMaxStamp());
            } catch (IOException e) {
                throw new IllegalArgumentException("Shouldn't happen.", e);
            }
        }
        // hbase1.2中，scan无法设置limit
        scan.setCaching(getScanCaching(tableName));
        scan.setCacheBlocks(scanCacheBlocks(tableName));
        if (filter != null) {
            scan.setFilter(filter);
        } else {
            scan.setBatch(getScanBatch(tableName));
        }
        if (columnList != null && !columnList.isEmpty()) {
            for (HBaseColumn column : columnList) {
                if (column.columnIsRow()) {
                    continue;
                }
                scan.addColumn(column.getFamilyNameBytes(), column.getColumnNameBytes());
            }
        }
        return scan;
    }

    @Override
    protected Scan setScanRowPrefixFilter(Scan scan, RowKey<?> rowPrefixKey) {
        scan.setRowPrefixFilter(rowPrefixKey.toBytes());
        return scan;
    }

    @Override
    protected Put constructPut(InsertRowData rowData, long ts) {
        Put put = new Put(rowData.getRows());
        for (InsertColData colData : rowData.getColDataList()) {
            if (ts > 0) {
                put.addColumn(colData.getFamily(), colData.getQualifier(), ts, colData.getValue());
            } else {
                put.addColumn(colData.getFamily(), colData.getQualifier(), colData.getValue());
            }
        }
        return put;
    }

    @Override
    protected Delete constructDelete(RowKey<?> rowKey, List<HBaseColumn> columnSchemaList, long ts) {
        return constructDelete(rowKey.toBytes(), columnSchemaList, ts);
    }

    @Override
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
}
