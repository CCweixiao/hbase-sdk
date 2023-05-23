package com.github.CCweixiao.hbase.sdk.schema;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.yetus.audience.InterfaceAudience;

import java.util.List;
import java.util.Map;

/**
 * @author leojie 2023/5/17 21:48
 */
@InterfaceAudience.Private
public class HTableDescriptorConverter extends BaseHTableDescriptorConverter<HTableDesc, HTableDescriptor> {
    public HTableDescriptorConverter(HTableDesc tableDesc) {
        super(tableDesc);
    }

    @Override
    protected HTableDescriptor doForward(HTableDesc tableDesc) {
        TableName tableName = TableName.valueOf(tableDesc.getTableNameAsString());
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
        Map<String, String> configuration = tableDesc.getConfiguration();
        if (configuration != null && !configuration.isEmpty()) {
            configuration.forEach(tableDescriptor::setConfiguration);
        }
        Map<String, String> values = tableDesc.getValues();
        if (values != null && !values.isEmpty()) {
            values.forEach(tableDescriptor::setValue);
        }
        return tableDescriptor;
    }

    @Override
    protected HTableDesc doBackward(HTableDescriptor tableDescriptor) {
        HTableDesc tableDesc = HTableDesc.newBuilder()
                .name(tableDescriptor.getTableName().getNameAsString())
                .maxFileSize(tableDescriptor.getMaxFileSize())
                .memStoreFlushSize(tableDescriptor.getMemStoreFlushSize())
                .readOnly(tableDescriptor.isReadOnly())
                .compactionEnabled(tableDescriptor.isCompactionEnabled())
                .build();
        for (HColumnDescriptor columnFamily : tableDescriptor.getColumnFamilies()) {
            ColumnFamilyDesc columnFamilyDesc = new ColumnFamilyDesc().convertTo(columnFamily);
            tableDesc.addColumnFamily(columnFamilyDesc);
        }
        Map<String, String> configuration = tableDescriptor.getConfiguration();
        if (configuration != null && !configuration.isEmpty()) {
            configuration.forEach(tableDesc::setConfiguration);
        }
        Map<ImmutableBytesWritable, ImmutableBytesWritable> values = tableDescriptor.getValues();
        if (values != null && !values.isEmpty()) {
            values.forEach((key, value) ->
                    tableDesc.setValue(Bytes.toString(key.get()), Bytes.toString(value.get())));
        }
        return tableDesc;
    }

}
