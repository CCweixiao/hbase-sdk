package com.github.CCweixiao;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.model.ColumnFamilyDesc;
import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.HTableDesc;
import com.github.CCweixiao.model.TableDesc;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

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

    /**
     * 转换TableDesc得到HTableDescriptor
     *
     * @param tableDesc TableDesc
     * @return HTableDescriptor
     */
    @Deprecated
    protected HTableDescriptor parseTableDescToHTableDescriptor(final TableDesc tableDesc) {
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableDesc.getTableName()));

        final List<FamilyDesc> familyDescList = tableDesc.getFamilyDescList();

        if (familyDescList == null || familyDescList.isEmpty()) {
            throw new HBaseOperationsException("请为表[" + tableDesc.getTableName() + "]指定一个或多个列簇！");
        }

        final Map<String, Long> familyCountMap = familyDescList.stream().collect(Collectors.groupingBy(FamilyDesc::getFamilyName, Collectors.counting()));
        familyCountMap.forEach((familyName, count) -> {
            if (count > 1) {
                throw new HBaseOperationsException("同一张表中的列簇名[" + familyName + "]应该是唯一的");
            }
        });

        if (tableDesc.getTableProps() != null && !tableDesc.getTableProps().isEmpty()) {
            tableDesc.getTableProps().forEach(tableDescriptor::setValue);
        }

        for (FamilyDesc familyDesc : familyDescList) {
            tableDescriptor.addFamily(parseFamilyDescToHColumnDescriptor(familyDesc));
        }

        return tableDescriptor;
    }

    protected HTableDescriptor parseHTableDescToTableDescriptor(final HTableDesc tableDesc) {
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableDesc.getTableName()));
        tableDescriptor.setMaxFileSize(tableDesc.getMaxFileSize());
        tableDescriptor.setReadOnly(tableDesc.isReadOnly());
        tableDescriptor.setMemStoreFlushSize(tableDesc.getMemStoreFlushSize());
        tableDescriptor.setCompactionEnabled(tableDesc.isCompactionEnabled());
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

    /**
     * HTableDescriptor批量转换为TableDesc
     *
     * @param tableDescriptors HTableDescriptor
     * @return TableDesc
     */
    protected List<TableDesc> parseHTableDescriptorsToTableDescList(HTableDescriptor[] tableDescriptors) {
        return Arrays.stream(tableDescriptors).map(this::parseHTableDescriptorToTableDesc).collect(Collectors.toList());
    }

    protected List<HTableDesc> parseHTableDescriptorsToHTableDescList(HTableDescriptor[] tableDescriptors) {
        return Arrays.stream(tableDescriptors).map(this::parseHTableDescriptorToHTableDesc).collect(Collectors.toList());
    }

    /**
     * HTableDescriptor 转换为TableDesc
     *
     * @param tableDescriptor 表定义
     * @return TableDesc
     */
    @Deprecated
    protected TableDesc parseHTableDescriptorToTableDesc(HTableDescriptor tableDescriptor) {
        TableDesc tableDesc = new TableDesc();
        String tableName = tableDescriptor.getNameAsString();
        tableDesc.setNamespaceName(HMHBaseConstant.getNamespaceName(tableName));
        tableDesc.setTableName(tableName);
        tableDesc.setDisabled(isTableDisabled(tableName));
        tableDesc.setMetaTable(tableDescriptor.isMetaTable());

        final Map<ImmutableBytesWritable, ImmutableBytesWritable> values = tableDescriptor.getValues();
        if (values != null && !values.isEmpty()) {
            values.forEach((key, value) -> tableDesc.addProp(Bytes.toString(key.get()), Bytes.toString(value.get())));
        }

        tableDesc.setTableDesc(tableDescriptor.toString());
        tableDesc.setFamilyDescList(parseFamilyDescriptorToFamilyDescList(tableDescriptor.getFamilies()));
        return tableDesc;
    }

    protected HTableDesc parseHTableDescriptorToHTableDesc(HTableDescriptor tableDescriptor){
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
                .metaTable(tableDescriptor.isMetaTable())
                .build();
    }



    /**
     * HColumnDescriptor列表转换为FamilyDesc列表
     *
     * @param families HColumnDescriptor 列表
     * @return FamilyDesc列表
     */
    @Deprecated
    protected List<FamilyDesc> parseFamilyDescriptorToFamilyDescList(Collection<HColumnDescriptor> families) {
        return families.stream().map(family -> new FamilyDesc.Builder()
                .familyName(family.getNameAsString())
                .maxVersions(family.getMaxVersions())
                .timeToLive(family.getTimeToLive())
                .compressionType(family.getCompressionType().getName().toUpperCase())
                .replicationScope(family.getScope())
                .build()).collect(Collectors.toList());
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

    /**
     * FamilyDesc HColumnDescriptor
     *
     * @param familyDesc 自定义列簇描述
     * @return HColumnDescriptor
     */
    @Deprecated
    protected HColumnDescriptor parseFamilyDescToHColumnDescriptor(FamilyDesc familyDesc) {
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(familyDesc.getFamilyName());
        columnDescriptor.setMaxVersions(familyDesc.getMaxVersions());
        columnDescriptor.setTimeToLive(familyDesc.getTimeToLive());
        columnDescriptor.setCompressionType(Compression.Algorithm.valueOf(familyDesc.getCompressionType()));
        return columnDescriptor;
    }

    protected HColumnDescriptor parseColumnFamilyDescToHColumnDescriptor(ColumnFamilyDesc familyDesc) {
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(familyDesc.getFamilyName());
        columnDescriptor.setScope(familyDesc.getReplicationScope());
        columnDescriptor.setMaxVersions(familyDesc.getVersions());
        columnDescriptor.setMinVersions(familyDesc.getMinVersions());
        columnDescriptor.setTimeToLive(familyDesc.getTimeToLive());
        columnDescriptor.setCompressionType(Compression.Algorithm.valueOf(familyDesc.getCompressionType()));
        columnDescriptor.setBlocksize(familyDesc.getBlockSize());
        columnDescriptor.setBlockCacheEnabled(familyDesc.isBlockCache());
        columnDescriptor.setInMemory(familyDesc.isInMemory());
        return columnDescriptor;
    }



    protected void tableIsNotExistsError(String tableName) {
        String fullTableName = HMHBaseConstant.getFullTableName(tableName);
        if (!tableExists(fullTableName)) {
            throw new HBaseOperationsException("表[" + tableName + "]不存在！");
        }
    }

    protected void tableIsExistsError(String tableName) {
        String fullTableName = HMHBaseConstant.getFullTableName(tableName);
        if (tableExists(fullTableName)) {
            throw new HBaseOperationsException("表[" + tableName + "]已经存在！");
        }
    }
}
