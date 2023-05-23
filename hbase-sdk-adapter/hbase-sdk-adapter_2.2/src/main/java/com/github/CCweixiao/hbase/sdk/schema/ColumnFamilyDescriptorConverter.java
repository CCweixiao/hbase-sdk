package com.github.CCweixiao.hbase.sdk.schema;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseFamilyNotEmptyException;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.yetus.audience.InterfaceAudience;

import java.util.Map;

/**
 * @author leojie 2023/5/17 22:43
 */
@InterfaceAudience.Private
public class ColumnFamilyDescriptorConverter extends BaseColumnFamilyDescriptorConverter<ColumnFamilyDesc, ColumnFamilyDescriptor> {
    public ColumnFamilyDescriptorConverter(ColumnFamilyDesc columnFamilyDesc) {
        super(columnFamilyDesc);
    }

    @Override
    protected ColumnFamilyDescriptor doForward(ColumnFamilyDesc columnFamilyDesc) {
        if (StringUtil.isBlank(columnFamilyDesc.getNameAsString())) {
            throw new HBaseFamilyNotEmptyException("The family name is not empty.");
        }
        ColumnFamilyDescriptorBuilder familyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(columnFamilyDesc.getNameAsString()));
        familyDescriptorBuilder.setScope(columnFamilyDesc.getReplicationScope());
        familyDescriptorBuilder.setMaxVersions(columnFamilyDesc.getMaxVersions());
        familyDescriptorBuilder.setMinVersions(columnFamilyDesc.getMinVersions());
        familyDescriptorBuilder.setCompressionType(getCompression(columnFamilyDesc.getCompressionType()));
        familyDescriptorBuilder.setBloomFilterType(getBloomType(columnFamilyDesc.getBloomFilterType()));
        familyDescriptorBuilder.setTimeToLive(columnFamilyDesc.getTimeToLive());
        familyDescriptorBuilder.setBlocksize(columnFamilyDesc.getBlockSize());
        familyDescriptorBuilder.setBlockCacheEnabled(columnFamilyDesc.isBlockCacheEnabled());
        familyDescriptorBuilder.setInMemory(columnFamilyDesc.isInMemory());
        familyDescriptorBuilder.setKeepDeletedCells(getKeepDeletedCells(columnFamilyDesc.getKeepDeletedCells()));
        familyDescriptorBuilder.setDataBlockEncoding(getDataBlockEncoding(columnFamilyDesc.getDataBlockEncoding()));
        familyDescriptorBuilder.setCacheDataOnWrite(columnFamilyDesc.isCacheDataOnWrite());
        familyDescriptorBuilder.setCacheIndexesOnWrite(columnFamilyDesc.isCacheIndexesOnWrite());
        familyDescriptorBuilder.setCacheBloomsOnWrite(columnFamilyDesc.isCacheBloomsOnWrite());
        familyDescriptorBuilder.setEvictBlocksOnClose(columnFamilyDesc.isEvictBlocksOnClose());
        familyDescriptorBuilder.setPrefetchBlocksOnOpen(columnFamilyDesc.isPrefetchBlocksOnOpen());
        if (StringUtil.isNotBlank(columnFamilyDesc.getStoragePolicy())) {
            familyDescriptorBuilder.setStoragePolicy(columnFamilyDesc.getStoragePolicy());
        }
        familyDescriptorBuilder.setMobEnabled(columnFamilyDesc.isMobEnabled());
        familyDescriptorBuilder.setMobThreshold(columnFamilyDesc.getMobThreshold());

        Map<String, String> configuration = columnFamilyDesc.getConfiguration();
        if (configuration != null && !configuration.isEmpty()) {
            configuration.forEach(familyDescriptorBuilder::setConfiguration);
        }

        Map<String, String> values = columnFamilyDesc.getValues();
        if (values != null && !values.isEmpty()) {
            values.forEach(familyDescriptorBuilder::setValue);
        }

        return familyDescriptorBuilder.build();
    }

    @Override
    protected ColumnFamilyDesc doBackward(ColumnFamilyDescriptor columnDescriptor) {
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
                .cacheIndexesOnWrite(columnDescriptor.isCacheIndexesOnWrite())
                .cacheBloomsOnWrite(columnDescriptor.isCacheBloomsOnWrite())
                .evictBlocksOnClose(columnDescriptor.isEvictBlocksOnClose())
                .prefetchBlocksOnOpen(columnDescriptor.isPrefetchBlocksOnOpen())
                .mobEnabled(columnDescriptor.isMobEnabled())
                .mobThreshold(columnDescriptor.getMobThreshold())
                .build();

        columnDescriptor.getValues().forEach((key, value) ->
                columnFamilyDesc.setValue(Bytes.toString(key.get()), Bytes.toString(value.get())));
        return columnFamilyDesc;
    }
}
