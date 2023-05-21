package com.github.CCweixiao.hbase.sdk.schema;

import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;

/**
 * @author leojie 2020/9/9 10:25 下午
 */
public class ColumnFamilyDesc extends BaseColumnFamilyDesc {

    private final BaseColumnFamilyDescriptorConverter<ColumnFamilyDesc, ColumnFamilyDescriptor>
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

    public ColumnFamilyDescriptor convertFor() {
        return this.familyDescriptorConverter.convertFor();
    }

    public ColumnFamilyDesc convertTo(ColumnFamilyDescriptor columnDescriptor) {
        return this.familyDescriptorConverter.convertTo(columnDescriptor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof BaseColumnFamilyDesc)) {
            return false;
        } else {
            ColumnFamilyDescriptor thisCD = this.convertFor();
            ColumnFamilyDescriptor thatCD = ((ColumnFamilyDesc) obj).convertFor();
            return ColumnFamilyDescriptor.COMPARATOR.compare(thisCD, thatCD) == 0;
        }
    }

    @Override
    public String toString() {
        return this.convertFor().toString();
    }
}
