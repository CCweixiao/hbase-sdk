package com.github.CCweixiao.hbase.sdk.schema;

import org.apache.hadoop.hbase.client.TableDescriptor;

/**
 * @author leojie 2021/6/23 9:48 下午
 */
public class HTableDesc extends BaseHTableDesc {

    private final BaseHTableDescriptorConverter<HTableDesc, TableDescriptor> tableDescriptorConverter;

    public HTableDesc() {
        this.tableDescriptorConverter = new HTableDescriptorConverter(this);
    }
    public HTableDesc(BaseHTableDesc.Builder<HTableDesc> builder) {
        super(builder);
        this.tableDescriptorConverter = new HTableDescriptorConverter(this);
    }

    public static class Builder extends BaseHTableDesc.Builder<HTableDesc> {
        @Override
        public HTableDesc build() {
            return new HTableDesc(this);
        }
    }

    public TableDescriptor convertFor() {
        return this.tableDescriptorConverter.convertFor();
    }

    public HTableDesc convertTo(TableDescriptor tableDescriptor) {
        return this.tableDescriptorConverter.convertTo(tableDescriptor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof HTableDesc)) {
            return false;
        } else {
            TableDescriptor thisTS = this.convertFor();
            TableDescriptor thatTS = ((HTableDesc) obj).convertFor();
            return TableDescriptor.COMPARATOR.compare(thisTS, thatTS) == 0;
        }
    }

    @Override
    public String toString() {
        return this.convertFor().toString();
    }
}
