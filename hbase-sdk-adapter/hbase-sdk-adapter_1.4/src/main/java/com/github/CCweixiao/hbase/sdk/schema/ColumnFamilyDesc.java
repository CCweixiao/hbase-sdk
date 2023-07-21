package com.github.CCweixiao.hbase.sdk.schema;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.yetus.audience.InterfaceAudience;

/**
 * @author leojie 2020/9/9 10:25 下午
 */
@InterfaceAudience.Public
public class ColumnFamilyDesc extends BaseColumnFamilyDesc implements Comparable<ColumnFamilyDesc>{

    private final BaseColumnFamilyDescriptorConverter<ColumnFamilyDesc, HColumnDescriptor>
            familyDescriptorConverter;

    public ColumnFamilyDesc() {
        this.familyDescriptorConverter = new ColumnFamilyDescriptorConverter(this);
    }

    private ColumnFamilyDesc(BaseColumnFamilyDesc.Builder<ColumnFamilyDesc> builder) {
        super(builder);
        this.familyDescriptorConverter = new ColumnFamilyDescriptorConverter(this);
    }

    public static class Builder extends BaseColumnFamilyDesc.Builder<ColumnFamilyDesc> {
        private Builder() {

        }

        @Override
        public ColumnFamilyDesc build() {
            return new ColumnFamilyDesc(this);
        }
    }

    public static Builder newBuilder() {
        return new ColumnFamilyDesc.Builder();
    }

    public HColumnDescriptor convertFor() {
        return this.familyDescriptorConverter.convertFor();
    }

    public ColumnFamilyDesc convertTo(HColumnDescriptor columnDescriptor) {
        return this.familyDescriptorConverter.convertTo(columnDescriptor);
    }

    @Override
    public int compareTo(ColumnFamilyDesc o) {
        return this.convertFor().compareTo(o.convertFor());
    }

    @Override
    public int hashCode() {
        return this.convertFor().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean res = super.equals(obj);
        if (!res) {
            return false;
        }
        return this.convertFor().equals(((ColumnFamilyDesc) obj).convertFor());
    }

    @Override
    public String toString() {
        return this.convertFor().toString();
    }
}
