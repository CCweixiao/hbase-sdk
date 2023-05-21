package com.github.CCweixiao.hbase.sdk.schema;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @author leojie 2020/9/9 10:25 下午
 */
public class ColumnFamilyDesc extends BaseColumnFamilyDesc {

    private final BaseColumnFamilyDescriptorConverter<ColumnFamilyDesc, HColumnDescriptor>
            familyDescriptorConverter;

    public ColumnFamilyDesc() {
        this.familyDescriptorConverter = new ColumnFamilyDescriptorConverter(this);
    }

    public ColumnFamilyDesc(BaseColumnFamilyDesc.Builder<ColumnFamilyDesc> builder) {
        super(builder);
        this.familyDescriptorConverter = new ColumnFamilyDescriptorConverter(this);
    }

    public static class Builder extends BaseColumnFamilyDesc.Builder<ColumnFamilyDesc> {
        @Override
        public ColumnFamilyDesc build() {
            return new ColumnFamilyDesc(this);
        }
    }

    public HColumnDescriptor convertFor() {
        return this.familyDescriptorConverter.convertFor();
    }

    public ColumnFamilyDesc convertTo(HColumnDescriptor columnDescriptor) {
        return this.familyDescriptorConverter.convertTo(columnDescriptor);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof BaseColumnFamilyDesc)) {
            return false;
        } else {
            HColumnDescriptor thisCD = this.convertFor();
            HColumnDescriptor thatCD = ((ColumnFamilyDesc) obj).convertFor();
            return thisCD.compareTo(thatCD) == 0;
        }
    }

    @Override
    public String toString() {
        return this.convertFor().toString();
    }
}
