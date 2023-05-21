package com.github.CCweixiao.hbase.sdk.schema;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseFamilyNotEmptyException;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

/**
 * @author leojie 2023/5/17 22:43
 */
public class ColumnFamilyDescriptorConverter extends BaseColumnFamilyDescriptorConverter<ColumnFamilyDesc, HColumnDescriptor> {
    public ColumnFamilyDescriptorConverter(ColumnFamilyDesc columnFamilyDesc) {
        super(columnFamilyDesc);
    }

    @Override
    protected HColumnDescriptor doForward(ColumnFamilyDesc columnFamilyDesc) {
        if (StringUtil.isBlank(columnFamilyDesc.getName())) {
            throw new HBaseFamilyNotEmptyException("The family name is not empty.");
        }
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(columnFamilyDesc.getName());
        columnDescriptor.setScope(columnFamilyDesc.getReplicationScope());
        columnDescriptor.setMaxVersions(columnFamilyDesc.getMaxVersions());
        columnDescriptor.setMinVersions(columnFamilyDesc.getMinVersions());
        columnDescriptor.setCompressionType(getCompression(columnFamilyDesc.getCompressionType()));
        columnDescriptor.setBloomFilterType(getBloomType(columnFamilyDesc.getBloomFilterType()));
        columnDescriptor.setTimeToLive(columnFamilyDesc.getTimeToLive());
        columnDescriptor.setBlocksize(columnFamilyDesc.getBlockSize());
        columnDescriptor.setBlockCacheEnabled(columnFamilyDesc.isBlockCacheEnabled());
        columnDescriptor.setInMemory(columnFamilyDesc.isInMemory());
        columnDescriptor.setKeepDeletedCells(getKeepDeletedCells(columnFamilyDesc.getKeepDeletedCells()));
        columnDescriptor.setDataBlockEncoding(getDataBlockEncoding(columnFamilyDesc.getDataBlockEncoding()));
        columnDescriptor.setCacheDataOnWrite(columnFamilyDesc.isCacheDataOnWrite());
        columnDescriptor.setCacheDataInL1(columnFamilyDesc.isCacheDataInL1());
        columnDescriptor.setCacheIndexesOnWrite(columnFamilyDesc.isCacheIndexesOnWrite());
        columnDescriptor.setCacheBloomsOnWrite(columnFamilyDesc.isCacheBloomsOnWrite());
        columnDescriptor.setEvictBlocksOnClose(columnFamilyDesc.isEvictBlocksOnClose());
        columnDescriptor.setPrefetchBlocksOnOpen(columnFamilyDesc.isPrefetchBlocksOnOpen());
        Map<String, String> configuration = columnFamilyDesc.getConfiguration();
        if (configuration != null && !configuration.isEmpty()) {
            configuration.forEach(columnDescriptor::setConfiguration);
        }
        Map<String, String> values = columnFamilyDesc.getValues();
        if (values != null && !values.isEmpty()) {
            values.forEach(columnDescriptor::setValue);
        }
        return columnDescriptor;
    }

    @Override
    protected ColumnFamilyDesc doBackward(HColumnDescriptor columnDescriptor) {
        ColumnFamilyDesc columnFamilyDesc = ColumnFamilyDesc.newBuilder()
                .name(columnDescriptor.getNameAsString())
                .replicationScope(columnDescriptor.getScope())
                .maxVersions(columnDescriptor.getMaxVersions())
                .minVersions(columnDescriptor.getMinVersions())
                .compressionType(columnDescriptor.getCompressionType().getName())
                .bloomFilterType(columnDescriptor.getBloomFilterType().name())
                .timeToLive(columnDescriptor.getTimeToLive())
                .blockSize(columnDescriptor.getBlocksize())
                .blockCacheEnabled(columnDescriptor.isBlockCacheEnabled())
                .inMemory(columnDescriptor.isInMemory())
                .keepDeletedCells(columnDescriptor.getKeepDeletedCells().name())
                .dataBlockEncoding(columnDescriptor.getDataBlockEncoding().name())
                .cacheDataOnWrite(columnDescriptor.isCacheDataOnWrite())
                .cacheDataInL1(columnDescriptor.isCacheDataInL1())
                .cacheIndexesOnWrite(columnDescriptor.isCacheIndexesOnWrite())
                .cacheBloomsOnWrite(columnDescriptor.isCacheBloomsOnWrite())
                .evictBlocksOnClose(columnDescriptor.isEvictBlocksOnClose())
                .prefetchBlocksOnOpen(columnDescriptor.isPrefetchBlocksOnOpen())
                .setConfiguration(columnDescriptor.getConfiguration())
                .build();
        columnDescriptor.getValues().forEach((key, value) ->
                columnFamilyDesc.setValue(Bytes.toString(key.get()), Bytes.toString(value.get())));
        return columnFamilyDesc;
    }
}
