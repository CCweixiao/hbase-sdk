package com.github.CCweixiao.hbase.sdk.schema;

import com.google.common.base.Converter;

/**
 * @author leojie 2023/5/16 23:17
 */
public abstract class BaseHTableDescriptorConverter<D extends BaseHTableDesc, S> extends Converter<D, S> {
    private final D hTableDesc;
    public BaseHTableDescriptorConverter(D hTableDesc) {
        this.hTableDesc = hTableDesc;
    }

    protected S convertFor() {
        return this.convert(this.hTableDesc);
    }

    protected D convertTo(S htableDescriptor) {
        return this.reverse().convert(htableDescriptor);
    }
}
