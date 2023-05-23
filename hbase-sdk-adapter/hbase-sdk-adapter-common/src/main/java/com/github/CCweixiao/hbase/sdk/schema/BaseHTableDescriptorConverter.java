package com.github.CCweixiao.hbase.sdk.schema;

import com.google.common.base.Converter;
import org.apache.yetus.audience.InterfaceAudience;

import java.util.Objects;

/**
 * @author leojie 2023/5/16 23:17
 */
@InterfaceAudience.Private
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseHTableDescriptorConverter<?, ?> that = (BaseHTableDescriptorConverter<?, ?>) o;
        return hTableDesc.equals(that.hTableDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hTableDesc);
    }
}
