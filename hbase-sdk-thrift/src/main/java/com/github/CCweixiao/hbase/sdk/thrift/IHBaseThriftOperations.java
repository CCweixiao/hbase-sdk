package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.IHBaseTableOperations;
import com.github.CCweixiao.hbase.sdk.common.callback.TableCallback;
import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseMetaDataException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseThriftException;
import com.github.CCweixiao.hbase.sdk.common.lang.Assert;
import com.github.CCweixiao.hbase.sdk.common.reflect.FieldStruct;
import com.github.CCweixiao.hbase.sdk.common.reflect.HBaseTableMeta;
import com.github.CCweixiao.hbase.sdk.common.reflect.ReflectFactory;
import com.github.CCweixiao.hbase.sdk.common.util.ByteBufferUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;
import org.apache.hadoop.hbase.thrift.generated.*;
import org.apache.thrift.TException;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>定义HBase Thrift 的操作接口</p>
 *
 * @author leojie 2022/11/22 22:42
 */
public interface IHBaseThriftOperations extends IHBaseTableOperations {
    /**
     * get hbase thrift client
     *
     * @return hbase thrift client
     */
    Hbase.Client getHbaseThriftClient();

    default <T> Optional<T> execute(String tableName, TableCallback<T, Hbase.Client> action) {
        try {
            return Optional.ofNullable(action.doInTable(this.getHbaseThriftClient()));
        } catch (Throwable throwable) {
            throw new HBaseThriftException(throwable);
        }
    }

    default void save(String tableName, Object rowKey, List<Mutation> mutations) {
        this.execute(tableName, thriftClient -> {
            thriftClient.mutateRow(ByteBufferUtil.toByterBufferFromStr(tableName),
                    ByteBufferUtil.toByteBuffer(rowKey), mutations,
                    getAttributesMap(new HashMap<>(0)));
            return null;
        });
    }

    default int saveBatch(String tableName, List<BatchMutation> batchMutations) {
        return this.execute(tableName, thriftClient -> {
            thriftClient.mutateRows(ByteBufferUtil.toByterBufferFromStr(tableName), batchMutations,
                    getAttributesMap(new HashMap<>(0)));
            return batchMutations.size();
        }).orElse(0);
    }

    default Map<ByteBuffer, ByteBuffer> getAttributesMap(Map<String, String> attributes) {
        if (attributes != null && !attributes.isEmpty()) {
            Map<ByteBuffer, ByteBuffer> attributesMap = new HashMap<>(attributes.size());
            attributes.forEach((key, value) -> attributesMap.put(ByteBufferUtil.toByteBuffer(key),
                    ByteBufferUtil.toByteBuffer(value)));
            return attributesMap;
        } else {
            return new HashMap<>(0);
        }
    }

    default <T> List<Mutation> createMutationList(T t, HBaseTableMeta tableMeta) throws HBaseMetaDataException {
        if (t == null) {
            return new ArrayList<>(0);
        }
        List<FieldStruct> fieldStructList = tableMeta.getFieldStructList();
        if (fieldStructList == null || fieldStructList.isEmpty()) {
            return new ArrayList<>(0);
        }
        List<Mutation> mutations = new ArrayList<>(fieldStructList.size());
        fieldStructList.forEach(fieldStruct -> {
            if (!fieldStruct.isRowKey()) {
                Object fieldValue = tableMeta.getMethodAccess().invoke(t, fieldStruct.getGetterMethodIndex());
                mutations.add(new Mutation(false, ByteBufferUtil.toByterBufferFromStr(fieldStruct.getFamilyAndQualifier()),
                        ByteBufferUtil.toByteBuffer(fieldValue), true));
            }
        });
        return mutations;
    }

    default <T> BatchMutation createBatchMutation(T t, HBaseTableMeta tableMeta) {
        Object rowKeyVal = createRowKeyVal(tableMeta, t);
        List<Mutation> mutations = createMutationList(t, tableMeta);
        return new BatchMutation(ByteBufferUtil.toByteBuffer(rowKeyVal), mutations);
    }

    default <T> List<BatchMutation> createBatchMutationList(List<T> lst, HBaseTableMeta tableMeta) throws HBaseMetaDataException {
        if (lst == null || lst.isEmpty()) {
            return new ArrayList<>(0);
        }
        List<BatchMutation> batchMutations = new ArrayList<>(lst.size());
        for (T t : lst) {
            batchMutations.add(createBatchMutation(t, tableMeta));
        }
        return batchMutations;
    }

    default <T> T pSave(T t) {
        Class<?> clazz = t.getClass();
        HBaseTableMeta tableMeta = ReflectFactory.getHBaseTableMeta(clazz);
        Object rowKeyVal = createRowKeyVal(tableMeta, t);
        List<Mutation> mutations = createMutationList(t, tableMeta);
        this.save(tableMeta.getTableName(), rowKeyVal, mutations);
        return t;
    }

    default <T> Object createRowKeyVal (HBaseTableMeta tableMeta, T t) {
        List<FieldStruct> fieldStructList = tableMeta.getFieldStructList();
        FieldStruct rowFieldStruct = fieldStructList.get(0);
        Assert.checkArgument(rowFieldStruct.isRowKey(), "The first field is not row key, please check hbase table mata data.");
        Object rowKeyVal = tableMeta.getMethodAccess().invoke(t, rowFieldStruct.getGetterMethodIndex());
        Assert.checkArgument(rowKeyVal != null, "The value of row key must not be null.");
        return rowKeyVal;
    }

    default <T> List<TRowResult> getTRowResultList(Hbase.Client thriftClient, String tableName, String rowKey, String familyName, List<String> qualifiers) {
        Assert.checkArgument(StrUtil.isNotBlank(tableName), "The table name must not be null.");
        Assert.checkArgument(StrUtil.isNotBlank(rowKey), "The value of row key must not be null.");
        List<TRowResult> results;
        try {
            if (StrUtil.isNotBlank(familyName)) {
                if (qualifiers != null && !qualifiers.isEmpty()) {
                    List<ByteBuffer> colNames = qualifiers.stream().map(q -> ByteBufferUtil.toByterBufferFromStr(
                                    familyName + HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR + q))
                            .collect(Collectors.toList());
                    results = thriftClient.getRowsWithColumns(ByteBufferUtil.toByterBufferFromStr(tableName),
                            Collections.singletonList(ByteBufferUtil.toByterBufferFromStr(rowKey)), colNames,
                            getAttributesMap(new HashMap<>(0)));
                } else {
                    results = thriftClient.getRowsWithColumns(ByteBufferUtil.toByterBufferFromStr(tableName),
                            Collections.singletonList(ByteBufferUtil.toByterBufferFromStr(rowKey)),
                            Collections.singletonList(ByteBufferUtil.toByterBufferFromStr(familyName)),
                            getAttributesMap(new HashMap<>(0)));
                }

            } else {
                results = thriftClient.getRow(ByteBufferUtil.toByterBufferFromStr(tableName),
                        ByteBufferUtil.toByterBufferFromStr(rowKey), getAttributesMap(new HashMap<>(0)));
            }
        } catch (TException e) {
            throw new HBaseThriftException(e);
        }
        return results;
    }

    default <T> T mapperRowToT(List<TRowResult> results, Class<T> clazz) throws Exception {
        T t = clazz.getDeclaredConstructor().newInstance();
        if (results == null || results.isEmpty()) {
            return t;
        }
        HBaseTableMeta hBaseTableMeta = ReflectFactory.getHBaseTableMeta(clazz);
        List<FieldStruct> fieldColStructMap = hBaseTableMeta.getFieldStructList();
        Map<String, ByteBuffer> resultDataMap = new HashMap<>(results.size());
        results.forEach(result -> {
            for (Map.Entry<ByteBuffer, TCell> entry : result.columns.entrySet()) {
                if (entry.getValue().value == null) {
                    resultDataMap.put(ByteBufferUtil.byteBufferToString(entry.getKey()), null);
                    continue;
                }
                resultDataMap.put(ByteBufferUtil.byteBufferToString(entry.getKey()), entry.getValue().value);
            }
        });
        fieldColStructMap.forEach(fieldStruct -> {
            Object value = fieldStruct.getTypeHandler().toObject(fieldStruct.getType(), resultDataMap.get(fieldStruct.getFamilyAndQualifier()));
            if (fieldStruct.isRowKey()) {
                Assert.checkArgument(value != null, "The value of row key must not be null.");
            }
            hBaseTableMeta.getMethodAccess().invoke(t, fieldStruct.getSetterMethodIndex(), value);
        });
        return t;
    }

    default Map<String, String> parseResultsToMap(List<TRowResult> results) {
        if (results == null || results.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<String, String> res = new HashMap<>(results.size());
        results.forEach(result -> {
            for (Map.Entry<ByteBuffer, TCell> entry : result.columns.entrySet()) {
                res.put(ByteBufferUtil.byteBufferToString(entry.getKey()),
                        ByteBufferUtil.byteBufferToString(entry.getValue().value));
            }
        });
        return res;
    }

}
