package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.callback.TableCallback;
import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseMetaDataException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseThriftException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.query.ScanQueryParamsBuilder;
import com.github.CCweixiao.hbase.sdk.common.reflect.FieldStruct;
import com.github.CCweixiao.hbase.sdk.common.reflect.HBaseTableMeta;
import com.github.CCweixiao.hbase.sdk.common.reflect.ReflectFactory;
import com.github.CCweixiao.hbase.sdk.common.type.TypeHandlerFactory;
import com.github.CCweixiao.hbase.sdk.common.util.ByteBufferUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.hbase.thrift.generated.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author leojie 2022/11/24 22:18
 */
public abstract class BaseHBaseThriftClient extends HBaseThriftConnection {
    protected Hbase.Client hbaseClient;

    public BaseHBaseThriftClient(final IHBaseThriftTSocket hbaseThriftTSocket) {
        super(hbaseThriftTSocket);
    }

    @Override
    public void connect() {
        super.connect();
        TSocket socket = getSocket();
        TProtocol protocol = new TBinaryProtocol(socket, true, true);
        hbaseClient = new Hbase.Client(protocol);
    }

    abstract boolean ping();

    protected <T> Optional<T> execute(String tableName, TableCallback<T, Hbase.Client> action) {
        try {
            return Optional.ofNullable(action.doInTable(this.hbaseClient));
        } catch (Throwable throwable) {
            throw new HBaseThriftException(throwable);
        }
    }

    protected void save(String tableName, Object rowKey, List<Mutation> mutations) {
        this.execute(tableName, thriftClient -> {
            thriftClient.mutateRow(ByteBufferUtil.toByterBufferFromStr(tableName),
                    ByteBufferUtil.toByteBuffer(rowKey), mutations,
                    getAttributesMap(new HashMap<>(0)));
            return null;
        });
    }

    protected int saveBatch(String tableName, List<BatchMutation> batchMutations) {
        return this.execute(tableName, thriftClient -> {
            thriftClient.mutateRows(ByteBufferUtil.toByterBufferFromStr(tableName), batchMutations,
                    getAttributesMap(new HashMap<>(0)));
            return batchMutations.size();
        }).orElse(0);
    }

    protected <T> List<Mutation> createMutationList(T t, HBaseTableMeta tableMeta) throws HBaseMetaDataException {
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

    protected <T> BatchMutation createBatchMutation(T t, HBaseTableMeta tableMeta) {
        Object rowKeyVal = createRowKeyVal(tableMeta, t);
        List<Mutation> mutations = createMutationList(t, tableMeta);
        return new BatchMutation(ByteBufferUtil.toByteBuffer(rowKeyVal), mutations);
    }

    protected <T> List<BatchMutation> createBatchMutationList(List<T> lst, HBaseTableMeta tableMeta) throws HBaseMetaDataException {
        if (lst == null || lst.isEmpty()) {
            return new ArrayList<>(0);
        }
        List<BatchMutation> batchMutations = new ArrayList<>(lst.size());
        for (T t : lst) {
            batchMutations.add(createBatchMutation(t, tableMeta));
        }
        return batchMutations;
    }

    protected <T> T pSave(T t) {
        Class<?> clazz = t.getClass();
        HBaseTableMeta tableMeta = ReflectFactory.getHBaseTableMeta(clazz);
        Object rowKeyVal = createRowKeyVal(tableMeta, t);
        List<Mutation> mutations = createMutationList(t, tableMeta);
        this.save(tableMeta.getTableName(), rowKeyVal, mutations);
        return t;
    }

    protected List<TRowResult> getToRowResultList(Hbase.Client thriftClient, String tableName, String rowKey, String familyName, List<String> qualifiers) {
        MyAssert.checkArgument(StringUtil.isNotBlank(tableName), "The table name must not be null.");
        MyAssert.checkArgument(StringUtil.isNotBlank(rowKey), "The value of row key must not be null.");
        ByteBuffer rowByteBuffer = ByteBufferUtil.toByterBufferFromStr(rowKey);
        List<ByteBuffer> familyQualifiers = createFamilyQualifiesBuffer(familyName, qualifiers);
        List<TRowResult> results;
        try {
            if (familyQualifiers != null && !familyQualifiers.isEmpty()) {
                results = thriftClient.getRowWithColumns(ByteBufferUtil.toByterBufferFromStr(tableName),
                        rowByteBuffer, familyQualifiers, getAttributesMap(new HashMap<>(0)));
            } else {
                results = thriftClient.getRow(ByteBufferUtil.toByterBufferFromStr(tableName), rowByteBuffer,
                        getAttributesMap(new HashMap<>(0)));
            }
        } catch (TException e) {
            throw new HBaseThriftException(e);
        }
        return results;
    }

    protected List<TRowResult> getToRowsResultList(Hbase.Client thriftClient, String tableName, List<String> rowKeyList, String familyName, List<String> qualifiers) {
        MyAssert.checkArgument(StringUtil.isNotBlank(tableName), "The table name must not be null.");
        MyAssert.checkArgument((rowKeyList != null && !rowKeyList.isEmpty()), "The row key(s) must not be empty.");
        if (rowKeyList.size() == 1) {
            return getToRowResultList(thriftClient, tableName, rowKeyList.get(0), familyName, qualifiers);
        }
        List<ByteBuffer> rowByteBuffers = rowKeyList.stream().map(ByteBufferUtil::toByterBufferFromStr).collect(Collectors.toList());
        List<ByteBuffer> familyQualifiers = createFamilyQualifiesBuffer(familyName, qualifiers);

        List<TRowResult> results;
        try {
            if (familyQualifiers != null && !familyQualifiers.isEmpty()) {
                results = thriftClient.getRowsWithColumns(ByteBufferUtil.toByterBufferFromStr(tableName),
                        rowByteBuffers, familyQualifiers, getAttributesMap(new HashMap<>(0)));
            } else {
                results = thriftClient.getRows(ByteBufferUtil.toByterBufferFromStr(tableName),
                        rowByteBuffers, getAttributesMap(new HashMap<>(0)));
            }
        } catch (TException e) {
            throw new HBaseThriftException(e);
        }
        return results;
    }

    protected  <T> T mapperRowToT(TRowResult result, Class<T> clazz) throws Exception {
        //TODO 这里的反射调用构造函数是否可以再优化
        T t = clazz.getDeclaredConstructor().newInstance();
        if (result == null) {
            return t;
        }
        HBaseTableMeta hBaseTableMeta = ReflectFactory.getHBaseTableMeta(clazz);
        List<FieldStruct> fieldColStructMap = hBaseTableMeta.getFieldStructList();

        fieldColStructMap.forEach(fieldStruct -> {
            if (fieldStruct.isRowKey()) {
                Object rowVal = TypeHandlerFactory.toObject(String.class, result.getRow());
                hBaseTableMeta.getMethodAccess().invoke(t, fieldStruct.getSetterMethodIndex(), rowVal);
            } else {
                Object fieldValue = result.getFieldValue(TRowResult._Fields.findByName(fieldStruct.getFamilyAndQualifier()));
                hBaseTableMeta.getMethodAccess().invoke(t, fieldStruct.getSetterMethodIndex(), fieldValue);
            }
        });
        return t;
    }

    protected  <T> List<T> mapperRowToTList(List<TRowResult> results, Class<T> clazz) {
        if (results == null || results.isEmpty()) {
            return new ArrayList<>(0);
        }
        List<T> list = new ArrayList<>(results.size());
        try {
            for (TRowResult result : results) {
                list.add(mapperRowToT(result, clazz));
            }
        } catch (Exception e) {
            throw new HBaseThriftException(e);
        }
        return list;
    }

    protected Map<String, String> parseResultsToMap(TRowResult result) {
        if (result == null) {
            return new HashMap<>(0);
        }
        Map<String, String> res = new HashMap<>(result.getColumnsSize());
        for (Map.Entry<ByteBuffer, TCell> entry : result.getColumns().entrySet()) {
            res.put(ByteBufferUtil.byteBufferToString(entry.getKey()),
                    ByteBufferUtil.byteBufferToString(entry.getValue().value));
        }
        return res;
    }

    protected Map<String, Map<String, String>> parseResultsToMap(List<TRowResult> results) {
        if (results == null || results.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<String, Map<String, String>> res = new HashMap<>(results.size());
        results.forEach(result -> {
            String rowVal = TypeHandlerFactory.toObject(String.class, result.getRow()).toString();
            res.put(rowVal, parseResultsToMap(result));
        });
        return res;
    }

    protected TScan buildScan(ScanQueryParamsBuilder scanQueryParams) {
        TScan scan = new TScan();
        if (StringUtil.isNotBlank(scanQueryParams.getStartRow())) {
            scan.setStartRow(ByteBufferUtil.toByterBufferFromStr(scanQueryParams.getStartRow()));
        }
        if (StringUtil.isNotBlank(scanQueryParams.getStopRow())) {
            scan.setStopRow(ByteBufferUtil.toByterBufferFromStr(scanQueryParams.getStopRow()));
        }

        if (StringUtil.isNotBlank(scanQueryParams.getFamilyName())) {
            if (scanQueryParams.getColumnNames() != null && !scanQueryParams.getColumnNames().isEmpty()) {
                final List<ByteBuffer> columns = scanQueryParams.getColumnNames().stream()
                        .filter(StringUtil::isNotBlank)
                        .map(qualifier -> ByteBufferUtil.toByterBufferFromStr(scanQueryParams.getFamilyName() + ":" + qualifier))
                        .collect(Collectors.toList());
                scan.setColumns(columns);
            } else {
                scan.setColumns(Collections.singletonList(ByteBufferUtil.toByterBufferFromStr(scanQueryParams.getFamilyName())));
            }
        }

        if (scanQueryParams.getFilter() != null && scanQueryParams.getFilter().customFilter() instanceof String) {
            scan.setFilterString(ByteBufferUtil.toStrByteBuffer(scanQueryParams.getFilter().customFilter()));
        }

        if (scanQueryParams.getTimestamp() > 0) {
            scan.setTimestamp(scanQueryParams.getTimestamp());
        }

        if (scanQueryParams.getCaching() > 0) {
            scan.setCaching(scanQueryParams.getCaching());
        }

        if (scanQueryParams.getBatch() > 0) {
            scan.setBatchSize(scanQueryParams.getBatch());
        }

        if (scanQueryParams.isReversed()) {
            scan.setReversed(true);
        }
        return scan;
    }

    protected Map<ByteBuffer, ByteBuffer> getAttributesMap(Map<String, String> attributes) {
        if (attributes != null && !attributes.isEmpty()) {
            Map<ByteBuffer, ByteBuffer> attributesMap = new HashMap<>(attributes.size());
            attributes.forEach((key, value) -> attributesMap.put(ByteBufferUtil.toByteBuffer(key),
                    ByteBufferUtil.toByteBuffer(value)));
            return attributesMap;
        } else {
            return new HashMap<>(0);
        }
    }

    private <T> Object createRowKeyVal(HBaseTableMeta tableMeta, T t) {
        List<FieldStruct> fieldStructList = tableMeta.getFieldStructList();
        FieldStruct rowFieldStruct = fieldStructList.get(0);
        MyAssert.checkArgument(rowFieldStruct.isRowKey(), "The first field is not row key, please check hbase table mata data.");
        Object rowKeyVal = tableMeta.getMethodAccess().invoke(t, rowFieldStruct.getGetterMethodIndex());
        MyAssert.checkArgument(rowKeyVal != null, "The value of row key must not be null.");
        return rowKeyVal;
    }

    private List<ByteBuffer> createFamilyQualifiesBuffer(String familyName, List<String> qualifiers) {
        List<ByteBuffer> familyQualifiers = null;
        if (StringUtil.isNotBlank(familyName)) {
            if (qualifiers != null && !qualifiers.isEmpty()) {
                familyQualifiers = qualifiers.stream().map(q -> ByteBufferUtil.toByterBufferFromStr(
                                familyName + HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR + q))
                        .collect(Collectors.toList());
            } else {
                familyQualifiers = Collections.singletonList(ByteBufferUtil.toByterBufferFromStr(familyName));
            }
        }
        return familyQualifiers;
    }

}
