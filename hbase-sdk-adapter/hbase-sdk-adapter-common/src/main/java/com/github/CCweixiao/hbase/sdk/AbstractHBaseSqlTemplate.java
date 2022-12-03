package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.type.TypeHandler;
import com.github.CCweixiao.hbase.sdk.common.model.HBaseCellResult;
import com.github.CCweixiao.hbase.sdk.connection.ConnectionFactory;
import com.github.CCwexiao.hbase.sdk.dsl.client.QueryExtInfo;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseSqlContext;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.util.Util;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author leojie 2020/11/28 8:34 下午
 */
public abstract class AbstractHBaseSqlTemplate implements IHBaseOperations {

    abstract List<List<HBaseCellResult>> select(String hsql);

    abstract void insert(String hql);

    abstract void delete(String hql);

    @Override
    public Connection getConnection() {
        return ConnectionFactory.getConnection(HBaseSqlContext.getConnProperties());
    }

    protected HBaseColumn findColumnSchema(String tableName, String family, String qualifier) {
        return HBaseSqlContext.getTableSchema(tableName).findColumn(family, qualifier);
    }

    protected int getScanCaching(String tableName) {
        return HBaseSqlContext.getTableSchema(tableName).getTableQuerySetting().getScanCaching();
    }

    protected int getScanBatch(String tableName) {
        return HBaseSqlContext.getTableSchema(tableName).getTableQuerySetting().getScanBatch();
    }

    protected int getDeleteBatch(String tableName) {
        return HBaseSqlContext.getTableSchema(tableName).getTableQuerySetting().getDeleteBatch();
    }

    protected boolean scanCacheBlocks(String tableName) {
        return HBaseSqlContext.getTableSchema(tableName).getTableQuerySetting().isScanCacheBlocks();
    }

    protected Get constructGet(RowKey<?> rowKey, QueryExtInfo queryExtInfo, List<HBaseColumn> hbaseColumnSchemaList) {
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
        if (hbaseColumnSchemaList != null && !hbaseColumnSchemaList.isEmpty()) {
            hbaseColumnSchemaList.forEach(hBaseColumnSchema -> {
                get.addColumn(Bytes.toBytes(hBaseColumnSchema.getFamilyName()),
                        Bytes.toBytes(hBaseColumnSchema.getColumnName()));
            });
        }
        return get;
    }

    protected abstract Scan constructScan(RowKey<?> startRowKey, RowKey<?> endRowKey, Filter filter, QueryExtInfo queryExtInfo);


    protected Get constructGet(RowKey<?> rowKey) {
        Util.checkRowKey(rowKey);
        return new Get(rowKey.toBytes());
    }


    protected Delete constructDelete(Result result, List<HBaseColumn> hbaseColumnSchemaList, Date ts) {
        Delete delete = new Delete(result.getRow());
        for (HBaseColumn hBaseColumnSchema : hbaseColumnSchemaList) {
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

    protected List<HBaseCellResult> convertToHBaseCellResultList(String tableName, Result result, RowKey<?> rowKey) {
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
                cellResult.setRowKey(rowKey.toBytes());

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
            scan.addColumn(Bytes.toBytes(hbaseColumnSchema.getFamilyName()),
                    Bytes.toBytes(hbaseColumnSchema.getColumnName()));
        }
    }

    protected byte[] convertValueToBytes(Object value, HBaseColumn column) {
        TypeHandler<?> typeHandler = column.getColumnType().getTypeHandler();
        return typeHandler.toBytes(column.getColumnType().getTypeClass(), value);
    }
}
