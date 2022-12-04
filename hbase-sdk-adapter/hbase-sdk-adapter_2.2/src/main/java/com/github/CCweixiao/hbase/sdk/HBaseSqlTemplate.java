package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.hql.HBaseSQLExtendContextUtil;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCweixiao.hbase.sdk.common.model.HBaseCellResult;
import com.github.CCwexiao.hbase.sdk.dsl.client.QueryExtInfo;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseSqlContext;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSqlAnalysisUtil;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.manual.RowKeyRange;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import com.github.CCwexiao.hbase.sdk.dsl.util.Util;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.*;

/**
 * @author leojie 2020/11/28 8:36 下午
 */
public class HBaseSqlTemplate extends AbstractHBaseSqlTemplate {

    @Override
    protected Scan constructScan(String tableName, RowKey<?> startRowKey, RowKey<?> endRowKey, Filter filter, QueryExtInfo queryExtInfo) {
        Util.checkRowKey(startRowKey);
        Util.checkRowKey(endRowKey);

        Scan scan = new Scan();
        scan.withStartRow(startRowKey.toBytes());
        scan.withStopRow(endRowKey.toBytes());
        scan.setCaching(getScanCaching(tableName));
        scan.setBatch(getScanBatch(tableName));
        scan.setCacheBlocks(scanCacheBlocks(tableName));

        if (filter != null) {
            scan.setFilter(filter);
        }

        return scan;
    }


    @Override
    public List<List<HBaseCellResult>> select(String hql, Map<String, Object> params) {
        checkSql(hql);
        HBaseSQLParser.ProgContext progContext = parseProgContext(hql);
        HBaseSQLParser.SelecthqlcContext context = HBaseSqlAnalysisUtil.parseSelectHqlContext(progContext);
        MyAssert.checkNotNull(context);
        String tableName = parseTableName(progContext);
        checkTableName(tableName);
        HBaseTableSchema tableSchema = HBaseSqlContext.getTableSchema(tableName);
        // cid List
        HBaseSQLParser.SelectColListContext selectColListContext = context.selectColList();
        final List<HBaseColumn> queryColumnSchemaList = HBaseSqlAnalysisUtil.extractColumnSchemaList(tableSchema, selectColListContext);
        MyAssert.checkArgument(!queryColumnSchemaList.isEmpty(), "The column list of select is not empty");

        // filter
        Filter filter = HBaseSQLExtendContextUtil.parseFilter(context.wherec(), tableSchema, params);

        // rowKeys
        RowKeyRange rowKeyRange = HBaseSqlAnalysisUtil.extractRowKeyRange(tableSchema, context.rowKeyRangeExp());
        RowKey<?> startRowKey = rowKeyRange.getStart();
        RowKey<?> endRowKey = rowKeyRange.getEnd();
        List<RowKey<?>> queryInRows = rowKeyRange.getContainsSomeKeys();
        QueryExtInfo queryExtInfo = HBaseSqlAnalysisUtil.parseQueryExtInfo(tableSchema, context);

        // in 查询
        if (queryInRows != null && !queryInRows.isEmpty()) {
            if (queryExtInfo.isLimitSet()) {
                throw new HBaseOperationsException("select in query should not with limit.");
            }
            List<Get> queryGetList = new ArrayList<>();
            queryInRows.forEach(rowKey -> queryGetList.add(constructGet(rowKey, queryExtInfo, queryColumnSchemaList)));

            return this.execute(tableName, table -> {
                List<List<HBaseCellResult>> resultList = new ArrayList<>();
                final Result[] results = table.get(queryGetList);
                if (results != null && results.length > 0) {
                    for (Result result : results) {
                        List<HBaseCellResult> temp = convertToHBaseCellResultList(tableName, result, tableSchema);
                        if (!temp.isEmpty()) {
                            resultList.add(temp);
                        }
                    }
                }
                return resultList;
            }).orElse(new ArrayList<>(0));
        }

        Util.checkRowKey(startRowKey);
        Util.checkRowKey(endRowKey);
        //scan 查询
        Scan scan = constructScan(tableName, startRowKey, endRowKey, filter, queryExtInfo);

        if (queryExtInfo.isMaxVersionSet()) {
            scan.setMaxVersions(queryExtInfo.getMaxVersions());
        }

        if (queryExtInfo.isTimeRangeSet()) {
            try {
                scan.setTimeRange(queryExtInfo.getMinStamp(), queryExtInfo.getMaxStamp());
            } catch (IOException e) {
                // should never happen.
                throw new HBaseOperationsException("should never happen.", e);
            }
        }

        applyRequestFamilyAndQualifier(queryColumnSchemaList, scan);
        try {
            return this.execute(tableName, table -> {
                long startIndex = 0L;
                long limit = Long.MAX_VALUE;

                if (queryExtInfo.isLimitSet()) {
                    limit = queryExtInfo.getLimit();
                }

                List<List<HBaseCellResult>> resultList = new ArrayList<>();

                try (ResultScanner scanner = table.getScanner(scan)) {
                    long ignoreCounter = startIndex;
                    long resultCounter = 0L;
                    Result result;
                    while ((result = scanner.next()) != null) {
                        if (ignoreCounter-- > 0) {
                            continue;
                        }
                        List<HBaseCellResult> temp = convertToHBaseCellResultList(tableName, result, tableSchema);
                        if (!temp.isEmpty()) {
                            resultList.add(temp);
                            if (++resultCounter >= limit) {
                                break;
                            }
                        }
                    }

                    return resultList;
                }
            }).orElse(new ArrayList<>(0));
        } catch (Exception e) {
            throw new HBaseOperationsException("select. hql=" + hql, e);
        }
    }

    @Override
    public List<List<HBaseCellResult>> select(String hsql) {
        return select(hsql, new HashMap<>(0));
    }

    @Override
    public void insert(String hql) {
        checkSql(hql);
        HBaseSQLParser.ProgContext progContext = parseProgContext(hql);
        HBaseSQLParser.InserthqlcContext context = HBaseSqlAnalysisUtil.parseInsertHqlContext(progContext);
        MyAssert.checkNotNull(context);
        String tableName = parseTableName(progContext);
        checkTableName(tableName);
        HBaseTableSchema tableSchema = HBaseSqlContext.getTableSchema(tableName);
        List<HBaseColumn> insertHbaseColumnSchemaList = HBaseSqlAnalysisUtil.extractColumnSchemaList(tableSchema, context.colList());

        final List<HBaseSQLParser.InsertValueContext> insertValueContextList = context.insertValueList().insertValue();

        MyAssert.checkArgument(insertHbaseColumnSchemaList.size() == insertValueContextList.size(),
                "The length of the list of fields to be inserted and the list of values are inconsistent.");

        final HBaseSQLParser.RowKeyExpContext rowKeyExpContext = context.rowKeyExp();
        RowKey<?> rowKey = HBaseSqlAnalysisUtil.extractRowKey(tableSchema, rowKeyExpContext);
        Util.checkRowKey(rowKey);

        Date ts = null;
        HBaseSQLParser.TsExpContext tsExpContext = context.tsExp();
        if (tsExpContext != null) {
            ts = HBaseSqlAnalysisUtil.extractTimeStampDate(tsExpContext);
        }
        Put put = new Put(rowKey.toBytes());

        for (int i = 0; i < insertHbaseColumnSchemaList.size(); i++) {
            HBaseColumn hbaseColumnSchema = insertHbaseColumnSchemaList.get(i);
            HBaseSQLParser.InsertValueContext insertValueContext = insertValueContextList.get(i);
            Object value = HBaseSqlAnalysisUtil.extractInsertConstantValue(hbaseColumnSchema, insertValueContext);
            byte[] data = convertValueToBytes(value, hbaseColumnSchema);
            if (ts == null) {
                put.addColumn(Bytes.toBytes(hbaseColumnSchema.getFamilyName()),
                        Bytes.toBytes(hbaseColumnSchema.getColumnName()), data);
            } else {
                put.addColumn(Bytes.toBytes(hbaseColumnSchema.getFamilyName()),
                        Bytes.toBytes(hbaseColumnSchema.getColumnName()), ts.getTime(), data);
            }
        }

        this.execute(tableName, mutator -> {
            mutator.mutate(put);
        });

    }

    @Override
    public void delete(String hql) {
        checkSql(hql);
        HBaseSQLParser.ProgContext progContext = parseProgContext(hql);
        HBaseSQLParser.DeletehqlcContext context = HBaseSqlAnalysisUtil.parseDeleteHqlContext(progContext);
        String tableName = parseTableName(progContext);
        checkTableName(tableName);
        HBaseTableSchema tableSchema = HBaseSqlContext.getTableSchema(tableName);
        //cid list
        HBaseSQLParser.SelectColListContext selectColListContext = context.selectColList();
        List<HBaseColumn> deleteHbaseColumnSchemaList = HBaseSqlAnalysisUtil.extractColumnSchemaList(tableSchema, selectColListContext);
        MyAssert.checkArgument(!deleteHbaseColumnSchemaList.isEmpty(), "The column will to delete must not be empty.");
        //filter
        Filter filter = HBaseSQLExtendContextUtil.parseFilter(context.wherec(), tableSchema);

        //row keys.
        RowKeyRange rowKeyRange = HBaseSqlAnalysisUtil.extractRowKeyRange(tableSchema, context.rowKeyRangeExp());
        RowKey<?> startRowKey = rowKeyRange.getStart();
        RowKey<?> endRowKey = rowKeyRange.getEnd();
        List<RowKey<?>> inRowKeyList = rowKeyRange.getContainsSomeKeys();

        Date ts = null;
        HBaseSQLParser.TsExpContext tsExpContext = context.tsExp();
        if (tsExpContext != null) {
            ts = HBaseSqlAnalysisUtil.extractTimeStampDate(tsExpContext);
        }
        // delete in rowKeys
        if (inRowKeyList != null && !inRowKeyList.isEmpty()) {
            deleteInRowKeys(tableName, inRowKeyList, filter, deleteHbaseColumnSchemaList, ts);

        } else {
            Util.checkRowKey(startRowKey);
            Util.checkRowKey(endRowKey);
            deleteInternalWithScanFirst(tableName, startRowKey, endRowKey, filter, deleteHbaseColumnSchemaList, ts);
        }

    }

    private void deleteInRowKeys(String tableName, List<RowKey<?>> rowKeys, Filter filter, List<HBaseColumn> columnList, Date ts) {
        if (rowKeys == null || rowKeys.isEmpty()) {
            return;
        }
        List<Get> getList = new ArrayList<>();
        rowKeys.forEach(rowKey -> getList.add(constructGet(rowKey)));
        List<Delete> deletes = new LinkedList<>();
        this.execute(tableName, table -> {
            Result[] results = table.get(getList);
            for (Result result : results) {
                deletes.add(constructDelete(result, columnList, ts));
            }
            return null;
        });

        this.execute(tableName, table -> {
            table.mutate(deletes);
        });
    }

    private void deleteInternalWithScanFirst(String tableName, RowKey<?> startRowKey, RowKey<?> endRowKey,
                                             Filter filter, List<HBaseColumn> columnList, Date ts) {

        final int deleteBatch = getDeleteBatch(tableName);

        while (true) {
            Scan tempScan = constructScan(tableName, startRowKey, endRowKey, filter, null);

            List<Delete> deletes = new LinkedList<>();

            try {
                this.execute(tableName, table -> {
                    try (ResultScanner scanner = table.getScanner(tempScan)) {
                        Result result;
                        while ((result = scanner.next()) != null) {
                            deletes.add(constructDelete(result, columnList, ts));
                            if (deletes.size() >= deleteBatch) {
                                break;
                            }
                        }
                    }
                    return null;
                });
            } catch (Exception e) {
                throw new HBaseOperationsException("delete internal. scan = " + tempScan, e);
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
                throw new HBaseOperationsException("delete internal. scan = " + tempScan, e);
            }

            if (deletes.size() > 0) {
                throw new HBaseOperationsException("delete internal fail. deletes=" + deletes);
            }

            if (deleteListSize < deleteBatch) {
                return;
            }

        }
    }
}
