package com.github.CCweixiao.hbase.sdk.schema;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;

import java.util.List;

/**
 * @author leojie 2023/5/17 21:48
 */
public class HTableDescriptorConverter extends BaseHTableDescriptorConverter<HTableDesc, HTableDescriptor> {
    public HTableDescriptorConverter(HTableDesc tableDesc) {
        super(tableDesc);
    }

    @Override
    protected HTableDescriptor doForward(HTableDesc tableDesc) {
        TableName tableName = TableName.valueOf(tableDesc.getTableName());
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        tableDescriptor.setMaxFileSize(tableDesc.getMaxFileSize());
        tableDescriptor.setMemStoreFlushSize(tableDesc.getMemStoreFlushSize());
        tableDescriptor.setReadOnly(tableDesc.isReadOnly());
        tableDescriptor.setCompactionEnabled(tableDesc.isCompactionEnabled());
        List<BaseColumnFamilyDesc> familyDescList = tableDesc.getColumnFamilyDescList();
        if (familyDescList != null && !familyDescList.isEmpty()) {
            for (BaseColumnFamilyDesc familyDesc : familyDescList) {
                tableDescriptor.addFamily(((ColumnFamilyDesc) familyDesc).convertFor());
            }
        }
        return tableDescriptor;
    }

    @Override
    protected HTableDesc doBackward(HTableDescriptor tableDescriptor) {
        HTableDesc tableDesc =  new HTableDesc.Builder()
                .tableName(tableDescriptor.getTableName().getNameAsString())
                .maxFileSize(tableDescriptor.getMaxFileSize())
                .memStoreFlushSize(tableDescriptor.getMemStoreFlushSize())
                .readOnly(tableDescriptor.isReadOnly())
                .compactionEnabled(tableDescriptor.isCompactionEnabled())
                .build();
        for (HColumnDescriptor columnFamily : tableDescriptor.getColumnFamilies()) {
            ColumnFamilyDesc columnFamilyDesc = new ColumnFamilyDesc().convertTo(columnFamily);
            tableDesc.addColumnFamily(columnFamilyDesc);
        }
        return tableDesc;
    }

}
