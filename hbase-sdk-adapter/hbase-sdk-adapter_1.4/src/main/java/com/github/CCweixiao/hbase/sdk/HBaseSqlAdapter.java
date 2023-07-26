package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.adapter.AbstractHBaseSqlAdapter;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.hql.filter.QueryFilterVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.QueryExtInfo;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import com.github.CCwexiao.hbase.sdk.dsl.util.Util;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.yetus.audience.InterfaceAudience;

import java.io.IOException;
import java.util.*;

/**
 * @author leojie 2020/11/28 8:36 下午
 */
@InterfaceAudience.Private
public class HBaseSqlAdapter extends AbstractHBaseSqlAdapter {

    public HBaseSqlAdapter(Properties properties) {
        super(properties);
    }

    public HBaseSqlAdapter(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public HBaseSqlAdapter(Configuration configuration) {
        super(configuration);
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
    protected Scan constructScan(String tableName, RowKey<?> startRowKey, RowKey<?> endRowKey, QueryExtInfo queryExtInfo, Filter filter, List<HBaseColumn> columnList) {
        Scan scan = new Scan();
        if (startRowKey != null && startRowKey.toBytes() != null) {
            scan.withStartRow(startRowKey.toBytes());
        }
        if (endRowKey != null && endRowKey.toBytes() != null) {
            scan.withStopRow(endRowKey.toBytes());
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
        if (queryExtInfo.isLimitSet()) {
            scan.setLimit(queryExtInfo.getLimit());
        }
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