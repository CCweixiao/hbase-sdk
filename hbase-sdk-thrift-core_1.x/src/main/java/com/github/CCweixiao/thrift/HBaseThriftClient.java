package com.github.CCweixiao.thrift;

import com.github.CCweixiao.HBaseThriftOperations;
import com.github.CCweixiao.exception.HBaseThriftException;
import com.github.CCweixiao.util.ByteBufferUtil;
import com.github.CCweixiao.util.HBaseThriftProtocol;
import com.github.CCweixiao.util.StrUtil;
import org.apache.hadoop.hbase.thrift.generated.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>HBase thrift client</p>
 *
 * @author leojie 2020/12/27 2:46 下午
 */
public class HBaseThriftClient extends HBaseThriftConnection implements HBaseThriftOperations {
    private static final Logger LOG = LoggerFactory.getLogger(HBaseThriftClient.class);

    private Hbase.Client hbaseClient;

    public HBaseThriftClient() {
        this(HBaseThriftProtocol.DEFAULT_HOST);
    }

    public HBaseThriftClient(final String host) {
        this(host, HBaseThriftProtocol.DEFAULT_PORT);
    }

    public HBaseThriftClient(final String host, final int port) {
        this(host, port, HBaseThriftProtocol.DEFAULT_TIMEOUT, HBaseThriftProtocol.DEFAULT_TIMEOUT);
    }

    public HBaseThriftClient(final String host, final int port, final int connectionTimeout, int socketTimeout) {
        this(new DefaultHBaseThriftTSocketFactory(host, port, connectionTimeout, socketTimeout));
    }

    public HBaseThriftClient(final HBaseThriftTSocketFactory thriftTSocketFactory) {
        super(thriftTSocketFactory);
    }

    @Override
    public void connect() {
        super.connect();
        TSocket socket = getSocket();
        LOG.info("connecting hbase thrift server {}:{}, and local port is {} ", getHost(), getPort(), socket.getSocket().getLocalPort());
        TProtocol protocol = new TBinaryProtocol(socket, true, true);
        hbaseClient = new Hbase.Client(protocol);
    }

    public Hbase.Client hbaseThriftClient() {
        return hbaseClient;
    }

    @Override
    public void save(String tableName, String rowKey, Map<String, String> data) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseThriftException("table name is blank");
        }
        if (StrUtil.isBlank(rowKey)) {
            throw new HBaseThriftException("row key is blank");
        }
        if (data == null || data.isEmpty()) {
            return;
        }
        List<Mutation> mutations = new ArrayList<>(data.size());
        data.forEach((key, value) -> mutations.add(new Mutation(false, ByteBufferUtil.getByteBufferFromString(key),
                ByteBufferUtil.getByteBufferFromString(value), true)));
        try {
            hbaseClient.mutateRow(ByteBufferUtil.getByteBufferFromString(tableName),
                    ByteBufferUtil.getByteBufferFromString(rowKey), mutations, getAttributesMap(new HashMap<>()));
        } catch (TException e) {
            throw new HBaseThriftException(e);
        }
    }

    @Override
    public void saveBatch(String tableName, Map<String, Map<String, String>> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        Map<String, String> attributes = new HashMap<>();
        Map<ByteBuffer, ByteBuffer> wrappedAttributes = getAttributesMap(attributes);

        List<BatchMutation> rowBatches = new ArrayList<>(data.size());
        data.forEach((rowKey, columnData) -> {
            if (null != columnData && !columnData.isEmpty()) {
                List<Mutation> mutations = new ArrayList<>();
                columnData.forEach((col, value) -> mutations.add(new Mutation(false,
                        ByteBufferUtil.getByteBufferFromString(col),
                        ByteBufferUtil.getByteBufferFromString(value), true)));

                rowBatches.add(new BatchMutation(ByteBufferUtil.getByteBufferFromString(rowKey), mutations));
            }
        });
        try {
            hbaseClient.mutateRows(ByteBufferUtil.getByteBufferFromString(tableName), rowBatches, wrappedAttributes);
        } catch (TException e) {
            throw new HBaseThriftException(e);
        }

    }

    @Override
    public <T> T save(T t) throws Exception {
        return null;
    }

    @Override
    public <T> T saveBatch(List<T> lst) throws Exception {
        return null;
    }

    @Override
    public <T> T getByRowKey(String rowKey, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T getByRowKeyWithFamily(String rowKey, String familyName, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T getByRowKeyWithFamilyAndQualifiers(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz) {
        return null;
    }

    @Override
    public Map<String, String> getByRowKeyToMap(String tableName, String rowKey) {
        return getByRowKeyWithFamilyToMap(tableName, rowKey, null);
    }

    @Override
    public Map<String, String> getByRowKeyWithFamilyToMap(String tableName, String rowKey, String familyName) {
        return getByRowKeyWithFamilyAndQualifiersToMap(tableName, rowKey, familyName, new ArrayList<>());
    }

    @Override
    public Map<String, String> getByRowKeyWithFamilyAndQualifiersToMap(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        Map<String, String> res = new HashMap<>();
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseThriftException("table name is blank");
        }
        if (StrUtil.isBlank(rowKey)) {
            throw new HBaseThriftException("row key is blank");
        }
        try {
            List<TRowResult> results;
            if (StrUtil.isNotBlank(familyName)) {
                if (qualifiers != null && !qualifiers.isEmpty()) {
                    List<ByteBuffer> colNames = new ArrayList<>(qualifiers.size());
                    qualifiers.forEach(qualifier -> colNames.add(ByteBufferUtil.getByteBufferFromString(familyName + ":" + qualifier)));
                    results = hbaseClient.getRowsWithColumns(ByteBufferUtil.getByteBufferFromString(tableName),
                            Collections.singletonList(ByteBufferUtil.getByteBufferFromString(rowKey)), colNames,
                            getAttributesMap(new HashMap<>()));
                } else {
                    results = hbaseClient.getRowsWithColumns(ByteBufferUtil.getByteBufferFromString(tableName),
                            Collections.singletonList(ByteBufferUtil.getByteBufferFromString(rowKey)),
                            Collections.singletonList(ByteBufferUtil.getByteBufferFromString(familyName)),
                            getAttributesMap(new HashMap<>()));
                }
            } else {
                results = hbaseClient.getRow(ByteBufferUtil.getByteBufferFromString(tableName),
                        ByteBufferUtil.getByteBufferFromString(rowKey), getAttributesMap(new HashMap<>()));
            }

            if (results == null || results.isEmpty()) {
                return res;
            }
            results.forEach(result -> {
                for (Map.Entry<ByteBuffer, TCell> entry : result.columns.entrySet()) {
                    res.put(ByteBufferUtil.byteBufferToString(entry.getKey()),
                            ByteBufferUtil.byteBufferToString(entry.getValue().value));
                }
            });
        } catch (TException e) {
            throw new HBaseThriftException(e);
        }
        return res;
    }

    @Override
    public Map<String, String> getByRowKeyWithFamilyAndQualifiersToMap(String tableName, String rowKey, String familyName, String... qualifiers) {
        if (qualifiers != null && qualifiers.length > 0) {
            return getByRowKeyWithFamilyAndQualifiersToMap(tableName, rowKey, familyName, Arrays.asList(qualifiers));
        } else {
            return getByRowKeyWithFamilyAndQualifiersToMap(tableName, rowKey, familyName, new ArrayList<>());
        }
    }

    @Override
    public Map<String, Map<String, String>> getRowsByRowKeysToMap(String tableName, List<String> rowKeyList) {
        return getRowsByRowKeysWithFamilyToMap(tableName, rowKeyList, null);
    }

    @Override
    public Map<String, Map<String, String>> getRowsByRowKeysWithFamilyToMap(String tableName, List<String> rowKeyList, String familyName) {
        return getRowsByRowKeysWithFamilyAndQualifiersToMap(tableName, rowKeyList, familyName, new ArrayList<>());
    }

    @Override
    public Map<String, Map<String, String>> getRowsByRowKeysWithFamilyAndQualifiersToMap(String tableName, List<String> rowKeyList, String familyName, List<String> qualifiers) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseThriftException("table name is blank");
        }
        if (rowKeyList == null || rowKeyList.isEmpty()) {
            throw new HBaseThriftException("row keys are empty");
        }
        final List<ByteBuffer> rows = rowKeyList.stream().map(ByteBufferUtil::getByteBufferFromString)
                .collect(Collectors.toList());
        Map<String, Map<String, String>> resMap = new HashMap<>(rowKeyList.size());

        final List<TRowResult> results;
        if (StrUtil.isNotBlank(familyName)) {
            final List<ByteBuffer> cols;
            if (qualifiers != null && !qualifiers.isEmpty()) {
                cols = qualifiers.stream().map(qualifier -> ByteBufferUtil.getByteBufferFromString(familyName + ":" + qualifier))
                        .collect(Collectors.toList());

            } else {
                cols = Collections.singletonList(ByteBufferUtil.getByteBufferFromString(familyName));
            }
            try {
                results = hbaseClient.getRowsWithColumns(ByteBufferUtil.getByteBufferFromString(tableName),
                        rows, cols, getAttributesMap(new HashMap<>()));
            } catch (TException e) {
                throw new HBaseThriftException(e);
            }
        } else {
            try {
                results = hbaseClient.getRows(ByteBufferUtil.getByteBufferFromString(tableName), rows, getAttributesMap(new HashMap<>()));
            } catch (TException e) {
                throw new HBaseThriftException(e);
            }

        }

        if (results == null || results.isEmpty()) {
            return resMap;
        }

        results.forEach(result -> {
            Map<String, String> res = new HashMap<>();
            for (Map.Entry<ByteBuffer, TCell> entry : result.columns.entrySet()) {
                res.put(ByteBufferUtil.byteBufferToString(entry.getKey()),
                        ByteBufferUtil.byteBufferToString(entry.getValue().value));
            }
            resMap.put(ByteBufferUtil.byteBufferToString(result.row), res);
        });
        return resMap;
    }

    @Override
    public Map<String, Map<String, String>> getRowsByRowKeysWithFamilyAndQualifiersToMap(String tableName, List<String> rowKeyList, String familyName, String... qualifiers) {
        if (qualifiers != null && qualifiers.length > 0) {
            return getRowsByRowKeysWithFamilyAndQualifiersToMap(tableName, rowKeyList, familyName, Arrays.asList(qualifiers));
        } else {
            return getRowsByRowKeysWithFamilyAndQualifiersToMap(tableName, rowKeyList, familyName, new ArrayList<>());
        }
    }

    @Override
    public void delete(String tableName, String rowKey) {
        delete(tableName, rowKey, null, new ArrayList<>());
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName) {
        delete(tableName, rowKey, familyName, new ArrayList<>());
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseThriftException("table name is blank");
        }
        if (StrUtil.isBlank(rowKey)) {
            throw new HBaseThriftException("row key is blank");
        }
        if (StrUtil.isNotBlank(familyName)) {
            if (qualifiers != null && !qualifiers.isEmpty()) {
                List<Mutation> mutations = new ArrayList<>(qualifiers.size());
                for (String qualifier : qualifiers) {
                    mutations.add(new Mutation(true, ByteBufferUtil.getByteBufferFromString(familyName + ":" + qualifier),
                            null, true));
                }
                try {
                    hbaseClient.mutateRow(ByteBufferUtil.getByteBufferFromString(tableName),
                            ByteBufferUtil.getByteBufferFromString(rowKey),
                            mutations, getAttributesMap(new HashMap<>()));
                } catch (TException e) {
                    throw new HBaseThriftException(e);
                }
            } else {
                try {
                    hbaseClient.deleteAll(ByteBufferUtil.getByteBufferFromString(tableName),
                            ByteBufferUtil.getByteBufferFromString(rowKey),
                            ByteBufferUtil.getByteBufferFromString(familyName),
                            getAttributesMap(new HashMap<>()));
                } catch (TException e) {
                    throw new HBaseThriftException(e);
                }

            }
        } else {
            try {
                hbaseClient.deleteAllRow(ByteBufferUtil.getByteBufferFromString(tableName),
                        ByteBufferUtil.getByteBufferFromString(rowKey),
                        getAttributesMap(new HashMap<>()));
            } catch (TException e) {
                throw new HBaseThriftException(e);
            }
        }
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, String... qualifiers) {
        if (qualifiers != null && qualifiers.length > 0) {
            delete(tableName, rowKey, familyName, Arrays.asList(qualifiers));
        } else {
            delete(tableName, rowKey, familyName, new ArrayList<>());
        }
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys) {
        deleteBatch(tableName, rowKeys, null);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName) {
        deleteBatch(tableName, rowKeys, familyName, new ArrayList<>());
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseThriftException("table name is blank");
        }
        if (rowKeys == null || rowKeys.isEmpty()) {
            throw new HBaseThriftException("row keys are empty");
        }

        List<BatchMutation> rowBatches = new ArrayList<>(rowKeys.size());

        if (StrUtil.isNotBlank(familyName)) {
            if (qualifiers != null && !qualifiers.isEmpty()) {
                rowKeys.forEach(rowKey -> {
                    List<Mutation> mutations = new ArrayList<>(rowKeys.size());
                    for (String qualifier : qualifiers) {
                        mutations.add(new Mutation(true, ByteBufferUtil.getByteBufferFromString(familyName + ":" + qualifier),
                                null, true));
                    }
                    BatchMutation batchMutation = new BatchMutation(ByteBufferUtil.getByteBufferFromString(rowKey), mutations);
                    rowBatches.add(batchMutation);
                });
            } else {
                rowKeys.forEach(rowKey -> {
                    List<Mutation> mutations = new ArrayList<>(rowKeys.size());
                    mutations.add(new Mutation(true, ByteBufferUtil.getByteBufferFromString(familyName), null, true));
                    BatchMutation batchMutation = new BatchMutation(ByteBufferUtil.getByteBufferFromString(rowKey), mutations);
                    rowBatches.add(batchMutation);
                });
            }
            try {
                hbaseClient.mutateRows(ByteBufferUtil.getByteBufferFromString(tableName), rowBatches, getAttributesMap(new HashMap<>()));
            } catch (TException e) {
                throw new HBaseThriftException(e);
            }
        } else {
            rowKeys.forEach(rowKey -> {
                try {
                    hbaseClient.deleteAllRow(ByteBufferUtil.getByteBufferFromString(tableName),
                            ByteBufferUtil.getByteBufferFromString(rowKey), getAttributesMap(new HashMap<>()));
                } catch (TException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, String... qualifiers) {
        if (qualifiers != null && qualifiers.length > 0) {
            deleteBatch(tableName, rowKeys, familyName, Arrays.asList(qualifiers));
        } else {
            deleteBatch(tableName, rowKeys, familyName, new ArrayList<>());
        }
    }

    private Map<ByteBuffer, ByteBuffer> getAttributesMap(Map<String, String> attributes) {
        Map<ByteBuffer, ByteBuffer> attributesMap = new HashMap<>();
        if (attributes != null && !attributes.isEmpty()) {
            attributes.forEach((key, value) -> attributesMap.put(ByteBufferUtil.getByteBufferFromString(key),
                    ByteBufferUtil.getByteBufferFromString(value)));
        }
        return attributesMap;
    }

    @Override
    public List<String> getTableNames() {
        ArrayList<String> tableNames = new ArrayList<>();
        try {
            for (ByteBuffer name : hbaseClient.getTableNames()) {
                tableNames.add(ByteBufferUtil.byteBufferToString(name));
            }
            return tableNames;
        } catch (TException e) {
            throw new HBaseThriftException(e);
        }
    }


    public void ping() {
        getTableNames();
    }


}

