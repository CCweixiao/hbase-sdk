package com.github.CCweixiao.hbase.sdk.adapter_14;

import com.github.CCweixiao.hbase.sdk.common.HBaseAdminOperations;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.model.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.common.model.HTableDesc;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>the abstract class of HBaseTemplate,</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
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

    protected HTableDescriptor parseHTableDescToTableDescriptor(final HTableDesc tableDesc) {
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableDesc.getTableName()));
        if (tableDesc.getMaxFileSize() != null) {
            tableDescriptor.setMaxFileSize(tableDesc.getMaxFileSize());
        }
        if (tableDesc.isReadOnly() != null) {
            tableDescriptor.setReadOnly(tableDesc.isReadOnly());
        }
        if (tableDesc.getMemStoreFlushSize() != null) {
            tableDescriptor.setMemStoreFlushSize(tableDesc.getMemStoreFlushSize());
        }
        if (tableDesc.isCompactionEnabled() != null) {
            tableDescriptor.setCompactionEnabled(tableDesc.isCompactionEnabled());
        }
        if (tableDesc.columnFamilyIsEmpty()) {
            throw new HBaseOperationsException("请为表[" + tableDesc.getTableName() + "]指定一个或多个列簇");
        }
        tableDesc.checkHasSameColumnFamily();
        if (tableDesc.getTableProps() != null && !tableDesc.getTableProps().isEmpty()) {
            tableDesc.getTableProps().forEach(tableDescriptor::setValue);
        }

        for (ColumnFamilyDesc familyDesc : tableDesc.getColumnFamilyDescList()) {
            tableDescriptor.addFamily(parseColumnFamilyDescToHColumnDescriptor(familyDesc));
        }
        return tableDescriptor;
    }

    protected List<HTableDesc> parseHTableDescriptorsToHTableDescList(HTableDescriptor[] tableDescriptors) {
        return Arrays.stream(tableDescriptors).map(this::parseHTableDescriptorToHTableDesc).collect(Collectors.toList());
    }

    protected HTableDesc parseHTableDescriptorToHTableDesc(HTableDescriptor tableDescriptor) {
        final Map<ImmutableBytesWritable, ImmutableBytesWritable> values = tableDescriptor.getValues();
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
                .columnFamilyDescList(parseFamilyDescriptorToColumnFamilyDescList(tableDescriptor.getFamilies()))
                .build();
    }

    protected List<ColumnFamilyDesc> parseFamilyDescriptorToColumnFamilyDescList(Collection<HColumnDescriptor> families) {
        return families.stream().map(family -> new ColumnFamilyDesc.Builder()
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

    protected HColumnDescriptor parseColumnFamilyDescToHColumnDescriptor(ColumnFamilyDesc familyDesc) {
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(familyDesc.getFamilyName());
        if (familyDesc.getReplicationScope() != null) {
            columnDescriptor.setScope(familyDesc.getReplicationScope());
        }
        if (familyDesc.getVersions() != null) {
            columnDescriptor.setMaxVersions(familyDesc.getVersions());
        }
        if (familyDesc.getMinVersions() != null) {
            columnDescriptor.setMinVersions(familyDesc.getMinVersions());
        }
        if (familyDesc.getTimeToLive() != null) {
            columnDescriptor.setTimeToLive(familyDesc.getTimeToLive());
        }
        if (familyDesc.getCompressionType() != null) {
            columnDescriptor.setCompressionType(Compression.Algorithm.valueOf(familyDesc.getCompressionType()));
        }
        if (familyDesc.getBlockSize() != null) {
            columnDescriptor.setBlocksize(familyDesc.getBlockSize());
        }
        if (familyDesc.isBlockCache() != null) {
            columnDescriptor.setBlockCacheEnabled(familyDesc.isBlockCache());
        }
        if (familyDesc.isInMemory() != null) {
            columnDescriptor.setInMemory(familyDesc.isInMemory());
        }
        return columnDescriptor;
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
