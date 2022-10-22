package com.github.CCweixiao.hbase.sdk.adapter_22;

import com.github.CCweixiao.hbase.sdk.common.HBaseAdminOperations;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.model.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.common.model.HTableDesc;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/11/14 2:26 下午
 */
public abstract class AbstractHBaseAdminTemplate extends AbstractHBaseConfig implements HBaseAdminOperations {
    public AbstractHBaseAdminTemplate(Configuration configuration) {
        super(configuration);
    }

    public AbstractHBaseAdminTemplate(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public AbstractHBaseAdminTemplate(Properties properties) {
        super(properties);
    }

    protected TableDescriptor parseHTableDescToTableDescriptor(final HTableDesc tableDesc) {
        final TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableDesc.getTableName()));

        if (tableDesc.getMaxFileSize() != null) {
            tableDescriptorBuilder.setMaxFileSize(tableDesc.getMaxFileSize());
        }
        if (tableDesc.isReadOnly() != null) {
            tableDescriptorBuilder.setReadOnly(tableDesc.isReadOnly());
        }
        if (tableDesc.getMemStoreFlushSize() != null) {
            tableDescriptorBuilder.setMemStoreFlushSize(tableDesc.getMemStoreFlushSize());
        }
        if (tableDesc.isCompactionEnabled() != null) {
            tableDescriptorBuilder.setCompactionEnabled(tableDesc.isCompactionEnabled());
        }

        if (tableDesc.columnFamilyIsEmpty()) {
            throw new HBaseOperationsException("请为表[" + tableDesc.getTableName() + "]指定一个或多个列簇");
        }
        tableDesc.checkHasSameColumnFamily();

        if (tableDesc.getTableProps() != null && !tableDesc.getTableProps().isEmpty()) {
            tableDesc.getTableProps().forEach(tableDescriptorBuilder::setValue);
        }

        for (ColumnFamilyDesc familyDesc : tableDesc.getColumnFamilyDescList()) {
            tableDescriptorBuilder.setColumnFamily(parseColumnFamilyDescToColumnFamilyDescriptor(familyDesc));
        }

        return tableDescriptorBuilder.build();
    }

    protected List<HTableDesc> parseHTableDescriptorsToHTableDescList(List<TableDescriptor> tableDescriptors) {
        if (tableDescriptors == null || tableDescriptors.isEmpty()) {
            return new ArrayList<>();
        }
        return tableDescriptors.stream().map(this::parseHTableDescriptorToHTableDesc).collect(Collectors.toList());
    }

    protected HTableDesc parseHTableDescriptorToHTableDesc(TableDescriptor tableDescriptor) {
        final Map<Bytes, Bytes> values = tableDescriptor.getValues();
        final Map<String, String> props = new HashMap<>(2);

        if (values != null && !values.isEmpty()) {
            values.forEach((key, value) -> props.put(Bytes.toString(key.get()), Bytes.toString(value.get())));
        }

        return new HTableDesc.Builder()
                .tableName(tableDescriptor.getTableName().getNameAsString())
                .maxFileSize(tableDescriptor.getMaxFileSize())
                .readOnly(tableDescriptor.isReadOnly())
                .memStoreFlushSize(tableDescriptor.getMemStoreFlushSize())
                .compactionEnabled(tableDescriptor.isCompactionEnabled())
                .tableProps(props)
                .columnFamilyDescList(parseColumnFamilyDescriptorToColumnFamilyDescList(tableDescriptor.getColumnFamilies()))
                .build();
    }

    protected List<ColumnFamilyDesc> parseColumnFamilyDescriptorToColumnFamilyDescList(ColumnFamilyDescriptor[] families) {
        return Arrays.stream(families).map(family -> new ColumnFamilyDesc.Builder()
                .familyName(family.getNameAsString())
                .versions(family.getMaxVersions())
                .minVersions(family.getMinVersions())
                .timeToLive(family.getTimeToLive())
                .compressionType(family.getCompressionType().getName().toUpperCase())
                .replicationScope(family.getScope())
                .blockSize(family.getBlocksize())
                .blockCache(family.isBlockCacheEnabled())
                .inMemory(family.isInMemory())
                .build()).collect(Collectors.toList());
    }

    protected ColumnFamilyDescriptor parseColumnFamilyDescToColumnFamilyDescriptor(ColumnFamilyDesc familyDesc) {
        ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(familyDesc.getFamilyName()));
        if (familyDesc.getReplicationScope() != null) {
            columnFamilyDescriptorBuilder.setScope(familyDesc.getReplicationScope());
        }
        if (familyDesc.getVersions() != null) {
            columnFamilyDescriptorBuilder.setMaxVersions(familyDesc.getVersions());
        }
        if (familyDesc.getMinVersions() != null) {
            columnFamilyDescriptorBuilder.setMinVersions(familyDesc.getMinVersions());
        }
        if (familyDesc.getTimeToLive() != null) {
            columnFamilyDescriptorBuilder.setTimeToLive(familyDesc.getTimeToLive());
        }
        if (familyDesc.getCompressionType() != null) {
            columnFamilyDescriptorBuilder.setCompressionType(Compression.Algorithm.valueOf(familyDesc.getCompressionType()));
        }
        if (familyDesc.getBlockSize() != null) {
            columnFamilyDescriptorBuilder.setBlocksize(familyDesc.getBlockSize());
        }
        if (familyDesc.isBlockCache() != null) {
            columnFamilyDescriptorBuilder.setBlockCacheEnabled(familyDesc.isBlockCache());
        }
        if (familyDesc.isInMemory() != null) {
            columnFamilyDescriptorBuilder.setInMemory(familyDesc.isInMemory());
        }
        return columnFamilyDescriptorBuilder.build();
    }

    protected void tableIsNotExistsError(Admin admin, String tableName) throws IOException {
        if (!admin.tableExists(TableName.valueOf(tableName))) {
            throw new HBaseOperationsException("表[" + tableName + "]不存在");
        }
    }

    protected void tableIsNotDisableError(Admin admin, String tableName) throws IOException {
        if (!admin.isTableDisabled(TableName.valueOf(tableName))) {
            throw new HBaseOperationsException("非禁用状态的表不可被操作");
        }
    }

    protected void tableIsExistsError(Admin admin, String tableName) throws IOException {
        if (admin.tableExists(TableName.valueOf(tableName))) {
            throw new HBaseOperationsException("表[" + tableName + "]已经存在");
        }
    }
}
