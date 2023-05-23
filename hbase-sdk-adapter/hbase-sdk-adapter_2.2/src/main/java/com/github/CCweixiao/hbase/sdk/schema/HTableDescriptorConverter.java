package com.github.CCweixiao.hbase.sdk.schema;

import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.yetus.audience.InterfaceAudience;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author leojie 2023/5/17 21:48
 */
@InterfaceAudience.Private
public class HTableDescriptorConverter extends BaseHTableDescriptorConverter<HTableDesc, TableDescriptor> {
    public HTableDescriptorConverter(HTableDesc tableDesc) {
        super(tableDesc);
    }

    @Override
    protected TableDescriptor doForward(HTableDesc tableDesc) {
        TableName tableName = TableName.valueOf(tableDesc.getTableNameAsString());
        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableName);
        tableDescriptorBuilder.setMaxFileSize(tableDesc.getMaxFileSize());
        tableDescriptorBuilder.setMemStoreFlushSize(tableDesc.getMemStoreFlushSize());
        tableDescriptorBuilder.setReadOnly(tableDesc.isReadOnly());
        tableDescriptorBuilder.setCompactionEnabled(tableDesc.isCompactionEnabled());
        if (StringUtil.isNotBlank(tableDesc.getRegionSplitPolicyClassName())) {
            tableDescriptorBuilder.setRegionSplitPolicyClassName(tableDesc.getRegionSplitPolicyClassName());
        }
        List<BaseColumnFamilyDesc> familyDescList = tableDesc.getColumnFamilyDescList();
        if (familyDescList != null && !familyDescList.isEmpty()) {
            for (BaseColumnFamilyDesc familyDesc : familyDescList) {
                tableDescriptorBuilder.setColumnFamily(((ColumnFamilyDesc) familyDesc).convertFor());
            }
        }

        Map<String, String> values = tableDesc.getValues();
        if (values != null && !values.isEmpty()) {
            values.forEach(tableDescriptorBuilder::setValue);
        }
        return tableDescriptorBuilder.build();
    }

    @Override
    protected HTableDesc doBackward(TableDescriptor tableDescriptor) {
        List<ColumnFamilyDesc> columnFamilyDescList =
                Arrays.stream(tableDescriptor.getColumnFamilies())
                        .map(cd -> new ColumnFamilyDesc().convertTo(cd)).collect(Collectors.toList());
        HTableDesc tableDesc = HTableDesc.newBuilder()
                .name(tableDescriptor.getTableName().getNameAsString())
                .maxFileSize(tableDescriptor.getMaxFileSize())
                .memStoreFlushSize(tableDescriptor.getMemStoreFlushSize())
                .readOnly(tableDescriptor.isReadOnly())
                .regionSplitPolicyClassName(tableDescriptor.getRegionSplitPolicyClassName())
                .compactionEnabled(tableDescriptor.isCompactionEnabled())
                .build();
        for (ColumnFamilyDesc columnFamilyDesc : columnFamilyDescList) {
            tableDesc.addColumnFamily(columnFamilyDesc);
        }
        Map<Bytes, Bytes> values = tableDescriptor.getValues();
        if (values != null && !values.isEmpty()) {
            values.forEach((key, value) ->
                    tableDesc.setValue(Bytes.toString(key.get()), Bytes.toString(value.get())));
        }
        return tableDesc;
    }

}
