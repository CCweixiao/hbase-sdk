package com.github.CCweixiao;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.model.ColumnFamilyDesc;
import com.github.CCweixiao.model.HTableDesc;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

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
        tableDescriptorBuilder.setMaxFileSize(tableDesc.getMaxFileSize());
        tableDescriptorBuilder.setReadOnly(tableDesc.isReadOnly());
        tableDescriptorBuilder.setMemStoreFlushSize(tableDesc.getMemStoreFlushSize());
        tableDescriptorBuilder.setCompactionEnabled(tableDesc.isCompactionEnabled());

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

    protected HTableDesc parseHTableDescriptorToHTableDesc(TableDescriptor tableDescriptor){
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
                .metaTable(tableDescriptor.isMetaTable())
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
        columnFamilyDescriptorBuilder.setScope(familyDesc.getReplicationScope());
        columnFamilyDescriptorBuilder.setMaxVersions(familyDesc.getVersions());
        columnFamilyDescriptorBuilder.setMinVersions(familyDesc.getMinVersions());
        columnFamilyDescriptorBuilder.setTimeToLive(familyDesc.getTimeToLive());
        columnFamilyDescriptorBuilder.setCompressionType(Compression.Algorithm.valueOf(familyDesc.getCompressionType()));
        columnFamilyDescriptorBuilder.setBlocksize(familyDesc.getBlockSize());
        columnFamilyDescriptorBuilder.setBlockCacheEnabled(familyDesc.isBlockCache());
        columnFamilyDescriptorBuilder.setInMemory(familyDesc.isInMemory());
        return columnFamilyDescriptorBuilder.build();
    }

    protected void tableIsNotExistsError(String tableName) {
        String fullTableName = HMHBaseConstant.getFullTableName(tableName);

        if (!tableExists(fullTableName)) {
            throw new HBaseOperationsException("表[" + tableName + "]不存在");
        }
    }

    protected void tableIsExistsError(String tableName) {
        String fullTableName = HMHBaseConstant.getFullTableName(tableName);
        if (tableExists(fullTableName)) {
            throw new HBaseOperationsException("表[" + tableName + "]已经存在");
        }
    }
}
