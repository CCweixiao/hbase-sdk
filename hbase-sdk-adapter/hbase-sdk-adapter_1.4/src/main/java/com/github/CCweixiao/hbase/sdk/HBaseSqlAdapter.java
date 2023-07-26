package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.adapter.AbstractHBaseSqlAdapter;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlExecuteException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.model.HQLType;
import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataRow;
import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.hql.filter.QueryFilterVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.QueryExtInfo;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSqlAnalysisUtil;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.manual.RowKeyRange;
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
    public HBaseDataSet select(String hql, Map<String, Object> params) {
        HBaseSQLParser.ProgContext progContext = parseProgContext(hql);
        HBaseSQLParser.SelecthqlcContext selectHqlContext = HBaseSqlAnalysisUtil.parseSelectHqlContext(progContext);
        MyAssert.checkNotNull(selectHqlContext);
        String tableName = parseTableNameFromHql(progContext);
        HBaseTableSchema tableSchema = this.getTableSchema(tableName);
        // col List
        HBaseSQLParser.SelectColListContext selectColListContext = selectHqlContext.selectColList();
        final List<HBaseColumn> queryColumnSchemaList = HBaseSqlAnalysisUtil.extractColumnSchemaList(tableSchema, selectColListContext);
        MyAssert.checkArgument(!queryColumnSchemaList.isEmpty(), "The column list of query is not empty.");
        // filter
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

        // = row key; get row
        if (eqRowKey != null) {
            Get get = constructGet(eqRowKey, queryExtInfo, filter, queryColumnSchemaList);
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
                getArr[i] = constructGet(queryInRows.get(i), queryExtInfo, filter, queryColumnSchemaList);
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
        Scan scan = constructScan(tableName, startRowKey, endRowKey, queryExtInfo, filter, queryColumnSchemaList);

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
        HBaseSQLParser.ProgContext progContext = parseProgContext(hql);
        HBaseSQLParser.InserthqlcContext insertHqlContext = HBaseSqlAnalysisUtil.parseInsertHqlContext(progContext);
        MyAssert.checkNotNull(insertHqlContext);
        String tableName = parseTableNameFromHql(progContext);
        HBaseTableSchema tableSchema = this.getTableSchema(tableName);
        List<HBaseColumn> insertColumnSchemaList = HBaseSqlAnalysisUtil.extractColumnSchemaList(tableSchema, insertHqlContext.colList());

        final List<HBaseSQLParser.InsertValueContext> insertValueContextList = insertHqlContext.insertValueList().insertValue();
        MyAssert.checkArgument(insertColumnSchemaList.size() == insertValueContextList.size(),
                "The inserted fields length should be same as the values length.");

        final HBaseSQLParser.RowKeyExpContext rowKeyExpContext = insertHqlContext.rowKeyExp();
        RowKey<?> rowKey = HBaseSqlAnalysisUtil.extractRowKey(tableSchema, rowKeyExpContext);

        long ts = -1;
        HBaseSQLParser.TsExpContext tsExpContext = insertHqlContext.tsExp();
        if (tsExpContext != null) {
            ts = HBaseSqlAnalysisUtil.extractTimeStamp(tsExpContext);
        }

        Put put = new Put(rowKey.toBytes());
        for (int i = 0; i < insertColumnSchemaList.size(); i++) {
            HBaseColumn hbaseColumnSchema = insertColumnSchemaList.get(i);
            HBaseSQLParser.InsertValueContext insertValueContext = insertValueContextList.get(i);
            Object value = HBaseSqlAnalysisUtil.extractInsertConstantValue(hbaseColumnSchema, insertValueContext);
            byte[] data = convertValueToBytes(value, hbaseColumnSchema);
            if (ts == -1) {
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
        HBaseSQLParser.ProgContext progContext = parseProgContext(hql);
        HBaseSQLParser.DeletehqlcContext deleteHqlContext = HBaseSqlAnalysisUtil.parseDeleteHqlContext(progContext);
        String tableName = parseTableNameFromHql(progContext);
        HBaseTableSchema tableSchema = this.getTableSchema(tableName);
        //delete col list
        HBaseSQLParser.SelectColListContext deleteColListContext = deleteHqlContext.selectColList();
        List<HBaseColumn> deleteColumnSchemaList = HBaseSqlAnalysisUtil.extractColumnSchemaList(tableSchema, deleteColListContext);
        MyAssert.checkArgument(!deleteColumnSchemaList.isEmpty(), "The column will to be deleted must not be empty.");
        //Row in start row and end row
        RowKeyRange rowKeyRange = HBaseSqlAnalysisUtil.extractRowKeyRange(tableSchema, deleteHqlContext.rowKeyRangeExp());
        RowKey<?> startRowKey = rowKeyRange.getStart();
        RowKey<?> endRowKey = rowKeyRange.getEnd();
        // delete one row key
        RowKey<?> eqRowKey = rowKeyRange.getEqRow();
        // delete in row key list
        List<RowKey<?>> inRowKeyList = rowKeyRange.getInSomeKeys();
        //filter
        Filter filter = this.parseFilter(deleteHqlContext.wherec(), tableSchema);

        long ts = -1;
        HBaseSQLParser.TsExpContext tsExpContext = deleteHqlContext.tsExp();
        if (tsExpContext != null) {
            ts = HBaseSqlAnalysisUtil.extractTimeStamp(tsExpContext);
        }
        // delete eq row key
        if (eqRowKey != null) {
            deleteInEqRowKey(tableName, eqRowKey, deleteColumnSchemaList, ts);
            return;
        }
        // delete in row keys
        if (inRowKeyList != null && !inRowKeyList.isEmpty()) {
            deleteInRowKeys(tableName, inRowKeyList, deleteColumnSchemaList, ts);
            return;
        }
        if (startRowKey != null && endRowKey != null) {
            Util.checkRowKey(startRowKey);
            Util.checkRowKey(endRowKey);
            deleteWithScanFirst(tableName, startRowKey, endRowKey, filter, deleteColumnSchemaList, ts);
        }
    }

    public String parseTableNameFromHql(String hql) {
        return super.parseTableNameFromHql(hql);
    }

    public HQLType parseHQLType(String hql) {
        return super.parseHQLType(hql);
    }

    private void deleteInEqRowKey(String tableName, RowKey<?> eqRowKey,
                                  List<HBaseColumn> deleteColumnSchemaList, long ts) {
        if (eqRowKey == null || eqRowKey.toBytes() == null) {
            return;
        }
        Delete delete = constructDelete(eqRowKey, deleteColumnSchemaList, ts);
        this.execute(tableName, table -> {
            table.mutate(delete);
        });
    }

    private void deleteInRowKeys(String tableName, List<RowKey<?>> rowKeys,
                                 List<HBaseColumn> deleteColumnSchemaList, long ts) {
        if (rowKeys == null || rowKeys.isEmpty()) {
            return;
        }
        List<Delete> deletes = new ArrayList<>();
        for (RowKey<?> rowKey : rowKeys) {
            if (rowKey == null || rowKey.toBytes() == null) {
                continue;
            }
            deletes.add(constructDelete(rowKey, deleteColumnSchemaList, ts));
        }
        this.execute(tableName, table -> {
            table.mutate(deletes);
        });
    }

    private void deleteWithScanFirst(String tableName, RowKey<?> startRowKey, RowKey<?> endRowKey,
                                     Filter filter, List<HBaseColumn> deleteColumnSchemaList, long ts) {

        final int deleteBatch = getDeleteBatch(tableName);
        // todo 优化这里的scan逻辑
        while (true) {
            Scan tempScan = constructScan(tableName, startRowKey, endRowKey, null, filter, deleteColumnSchemaList);
            // 只扫描row
            tempScan.addFamily(null);
            List<Delete> deletes = new ArrayList<>();
            try {
                this.execute(tableName, table -> {
                    try (ResultScanner scanner = table.getScanner(tempScan)) {
                        Result result;
                        while ((result = scanner.next()) != null) {
                            deletes.add(constructDelete(result, deleteColumnSchemaList, ts));
                            if (deletes.size() >= deleteBatch) {
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
                this.execute(tableName, table -> {
                    table.mutate(deletes);
                    deletes.clear();
                });
            } catch (Exception e) {
                throw new HBaseSqlExecuteException("delete internal. scan = " + tempScan, e);
            }

            if (deletes.size() > 0) {
                throw new HBaseSqlExecuteException("delete internal fail. deletes=" + deletes);
            }

            if (deleteListSize < deleteBatch) {
                return;
            }

        }
    }
}