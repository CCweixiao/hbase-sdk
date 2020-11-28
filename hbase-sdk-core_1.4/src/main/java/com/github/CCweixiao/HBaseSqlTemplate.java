package com.github.CCweixiao;

import com.github.CCweixiao.client.HBaseSQLExtendContextUtil;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCwexiao.dsl.auto.HBaseSQLParser;
import com.github.CCwexiao.dsl.client.HBaseCellResult;
import com.github.CCwexiao.dsl.client.QueryExtInfo;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.config.HBaseColumnSchema;
import com.github.CCwexiao.dsl.manual.HBaseSQLContextUtil;
import com.github.CCwexiao.dsl.manual.RowKeyRange;
import com.github.CCwexiao.dsl.util.TreeUtil;
import com.github.CCwexiao.dsl.util.Util;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.*;

/**
 * @author leojie 2020/11/28 8:36 下午
 */
public class HBaseSqlTemplate extends AbstractHBaseSqlTemplate {
    public HBaseSqlTemplate(Configuration configuration) {
        super(configuration);
    }

    public HBaseSqlTemplate(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public HBaseSqlTemplate(Properties properties) {
        super(properties);
    }

    @Override
    public List<List<HBaseCellResult>> select(String hql) {
        Util.checkEmptyString(hql);
        HBaseSQLParser.ProgContext progContext = TreeUtil.parseProgContext(hql);
        HBaseSQLParser.SelecthqlcContext context = HBaseSQLContextUtil.parseSelecthqlcContext(progContext);
        Util.checkNull(context);

        String tableName = TreeUtil.parseTableName(progContext);
        checkTableName(tableName);

        // cid List
        HBaseSQLParser.SelectCidListContext selectCidListContext = context.selectCidList();
        final List<HBaseColumnSchema> queryHBaseColumnSchemaList = HBaseSQLContextUtil.parseHBaseColumnSchemaList(hBaseTableConfig, selectCidListContext);
        Util.check(!queryHBaseColumnSchemaList.isEmpty());

        // filter
        Filter filter = HBaseSQLExtendContextUtil.parseFilter(context.wherec(), hBaseTableConfig, runtimeSetting);

        // rowKeys
        RowKeyRange rowKeyRange = HBaseSQLContextUtil.parseRowKeyRange(context.rowKeyRange(), runtimeSetting);
        RowKey startRowKey = rowKeyRange.getStart();
        RowKey endRowKey = rowKeyRange.getEnd();

        Util.checkRowKey(startRowKey);
        Util.checkRowKey(endRowKey);

        QueryExtInfo queryExtInfo = HBaseSQLContextUtil.parseQueryExtInfo(context);

        //scan
        Scan scan = constructScan(startRowKey, endRowKey, filter, queryExtInfo);


        if (queryExtInfo.isMaxVersionSet()) {
            scan.setMaxVersions(queryExtInfo.getMaxVersions());
        }

        if (queryExtInfo.isTimeRangeSet()) {
            try {
                scan.setTimeRange(queryExtInfo.getMinStamp(),
                        queryExtInfo.getMaxStamp());
            } catch (IOException e) {
                // should never happen.
                throw new HBaseOperationsException("should never happen.", e);
            }
        }

        applyRequestFamilyAndQualifier(queryHBaseColumnSchemaList, scan);
        try {
            return this.execute(tableName, table -> {
                long startIndex = 0L;
                long length = Long.MAX_VALUE;

                if (queryExtInfo.isLimitSet()) {
                    startIndex = queryExtInfo.getStartIndex();
                    length = queryExtInfo.getLength();
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
                        List<HBaseCellResult> temp = convertToHBaseCellResultList(result);
                        if (!temp.isEmpty()) {
                            resultList.add(temp);
                            if (++resultCounter >= length) {
                                break;
                            }
                        }
                    }

                    return resultList;
                }
            });
        } catch (Exception e) {
            throw new HBaseOperationsException("select. hql=" + hql, e);
        }
    }

    @Override
    public void insert(String hql) {
        Util.checkEmptyString(hql);

        HBaseSQLParser.ProgContext progContext = TreeUtil.parseProgContext(hql);
        HBaseSQLParser.InserthqlcContext context = HBaseSQLContextUtil.parseInserthqlcContext(progContext);

        Util.checkNull(context);

        String tableName = TreeUtil.parseTableName(progContext);
        checkTableName(tableName);

        List<HBaseColumnSchema> insertHbaseColumnSchemaList = HBaseSQLContextUtil
                .parseHBaseColumnSchemaList(hBaseTableConfig, context.cidList());

        final List<HBaseSQLParser.InsertValueContext> insertValueContextList = context.insertValueList().insertValue();

        Util.check(insertHbaseColumnSchemaList.size() == insertValueContextList.size());

        final HBaseSQLParser.RowKeyExpContext rowKeyExpContext = context.rowKeyExp();
        RowKey rowKey = HBaseSQLContextUtil.parseRowKey(rowKeyExpContext, runtimeSetting);

        Util.checkRowKey(rowKey);

        Date ts = null;
        HBaseSQLParser.TsexpContext tsexpContext = context.tsexp();
        if (tsexpContext != null) {
            ts = HBaseSQLContextUtil.parseTimeStampDate(tsexpContext, runtimeSetting);
        }

        Put put = new Put(rowKey.toBytes());

        for (int i = 0; i < insertHbaseColumnSchemaList.size(); i++) {
            HBaseColumnSchema hbaseColumnSchema = insertHbaseColumnSchemaList.get(i);

            HBaseSQLParser.InsertValueContext insertValueContext = insertValueContextList.get(i);
            Object value = HBaseSQLContextUtil.parseInsertConstantValue(hbaseColumnSchema, insertValueContext, runtimeSetting);

            byte[] data = convertValueToBytes(value, hbaseColumnSchema);
            if (ts == null) {
                put.addColumn(Bytes.toBytes(hbaseColumnSchema.getFamily()),
                        Bytes.toBytes(hbaseColumnSchema.getQualifier()), data);
            } else {
                put.addColumn(Bytes.toBytes(hbaseColumnSchema.getFamily()),
                        Bytes.toBytes(hbaseColumnSchema.getQualifier()), ts.getTime(), data);
            }
        }

        this.execute(tableName, mutator -> {
            mutator.mutate(put);
        });

    }

    @Override
    public void delete(String hql) {
        Util.checkEmptyString(hql);

        HBaseSQLParser.ProgContext progContext = TreeUtil.parseProgContext(hql);
        HBaseSQLParser.DeletehqlcContext context = HBaseSQLContextUtil.parseDeletehqlcContext(progContext);
        Util.checkNull(context);

        String tableName = TreeUtil.parseTableName(progContext);
        checkTableName(tableName);

        //cid list
        HBaseSQLParser.SelectCidListContext selectCidListContext = context.selectCidList();
        List<HBaseColumnSchema> deleteHbaseColumnSchemaList = HBaseSQLContextUtil
                .parseHBaseColumnSchemaList(hBaseTableConfig, selectCidListContext);
        Util.check(!deleteHbaseColumnSchemaList.isEmpty());

        //filter
        Filter filter = HBaseSQLExtendContextUtil.parseFilter(context.wherec(),
                hBaseTableConfig, runtimeSetting);

        //row keys.
        RowKeyRange rowKeyRange = HBaseSQLContextUtil.parseRowKeyRange(context.rowKeyRange(), runtimeSetting);

        RowKey startRowKey = rowKeyRange.getStart();
        RowKey endRowKey = rowKeyRange.getEnd();

        Util.checkRowKey(startRowKey);
        Util.checkRowKey(endRowKey);

        Date ts = null;
        HBaseSQLParser.TsexpContext tsexpContext = context.tsexp();
        if (tsexpContext != null) {
            ts = HBaseSQLContextUtil.parseTimeStampDate(tsexpContext, runtimeSetting);
        }

        deleteInternalWithScanFirst(tableName, startRowKey, endRowKey, filter, deleteHbaseColumnSchemaList, ts);

    }

    private void deleteInternalWithScanFirst(String tableName, RowKey startRowKey, RowKey endRowKey,
                                             Filter filter, List<HBaseColumnSchema> hbaseColumnSchemaList, Date ts) {
        Util.check((hbaseColumnSchemaList != null && !hbaseColumnSchemaList.isEmpty()));

        final int deleteBatch = getDeleteBatch();

        while (true) {
            Scan tempScan = constructScan(startRowKey, endRowKey, filter, null);

            List<Delete> deletes = new LinkedList<>();

            try {
                this.execute(tableName, table -> {
                    try (ResultScanner scanner = table.getScanner(tempScan)) {
                        Result result;
                        while ((result = scanner.next()) != null) {
                            Delete delete = new Delete(result.getRow());

                            for (HBaseColumnSchema hBaseColumnSchema : hbaseColumnSchemaList) {
                                byte[] familyBytes = Bytes.toBytes(hBaseColumnSchema.getFamily());
                                byte[] qualifierBytes = Bytes.toBytes(hBaseColumnSchema.getQualifier());
                                if (ts == null) {
                                    //delete all versions.
                                    delete.addColumn(familyBytes, qualifierBytes);
                                } else {
                                    delete.addColumn(familyBytes, qualifierBytes, ts.getTime());
                                }
                            }

                            deletes.add(delete);
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
