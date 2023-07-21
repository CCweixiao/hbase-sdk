package com.github.CCweixiao.hbase.sdk.schema;

import com.github.CCweixiao.hbase.sdk.common.lang.Converter;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.hbase.KeepDeletedCells;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.io.encoding.DataBlockEncoding;
import org.apache.hadoop.hbase.regionserver.BloomType;
import org.apache.yetus.audience.InterfaceAudience;

import java.util.Objects;

/**
 * @author leojie 2023/5/17 22:39
 */
@InterfaceAudience.Private
public abstract class BaseColumnFamilyDescriptorConverter<CF extends BaseColumnFamilyDesc, CD> extends Converter<CF, CD> {
    private final CF columnFamilyDesc;
    public BaseColumnFamilyDescriptorConverter(CF columnFamilyDesc) {
        this.columnFamilyDesc = columnFamilyDesc;
    }

    protected CD convertFor() {
        return this.convert(this.columnFamilyDesc);
    }

    protected CF convertTo(CD columnFamilyDescriptor) {
        return this.reverse().convert(columnFamilyDescriptor);
    }

    protected Compression.Algorithm getCompression(String compression) {
        if (StringUtil.isBlank(compression)) {
            return Compression.Algorithm.NONE;
        }
        for (Compression.Algorithm value : Compression.Algorithm.values()) {
            if (compression.equalsIgnoreCase(value.getName())) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unsupported compression type " + compression);
    }

    protected BloomType getBloomType(String bloomType) {
        if (StringUtil.isBlank(bloomType)) {
            // return default value
            return BloomType.ROW;
        }
        for (BloomType value : BloomType.values()) {
            if (bloomType.equals(value.name())) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unsupported bloom type " + bloomType);
    }

    protected KeepDeletedCells getKeepDeletedCells(String keepDeletedCells) {
        if (StringUtil.isBlank(keepDeletedCells)) {
            // return default value
            return KeepDeletedCells.FALSE;
        }
        for (KeepDeletedCells value : KeepDeletedCells.values()) {
            if (keepDeletedCells.equals(value.name())) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unsupported keepDeletedCells " + keepDeletedCells);
    }

    protected DataBlockEncoding getDataBlockEncoding(String dataBlockEncoding) {
        if (StringUtil.isBlank(dataBlockEncoding)) {
            // return default value
            return DataBlockEncoding.NONE;
        }
        for (DataBlockEncoding value : DataBlockEncoding.values()) {
            if (dataBlockEncoding.equals(value.name())) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unsupported dataBlockEncoding " + dataBlockEncoding);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BaseColumnFamilyDescriptorConverter<?, ?> that = (BaseColumnFamilyDescriptorConverter<?, ?>) o;
        return columnFamilyDesc.equals(that.columnFamilyDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnFamilyDesc);
    }
}
