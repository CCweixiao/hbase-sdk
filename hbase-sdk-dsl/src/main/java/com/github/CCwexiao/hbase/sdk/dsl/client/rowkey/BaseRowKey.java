package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.RowKeyFunc;

/**
 *
 * @author leojie 2022/12/3 18:21
 */
public abstract class BaseRowKey<T> implements RowKey<T> {
    protected String value;
    protected byte[] valueBytes;
    protected RowKeyFunc<T> rowKeyFunc;

    public BaseRowKey(String value, RowKeyFunc<T> rowKeyFunc) {
        this.value = value;
        this.rowKeyFunc = rowKeyFunc;
    }

    @Override
    public byte[] toBytes() {
        // set order 1
        if (this.valueBytes != null) {
            return this.valueBytes;
        }
        // convert order 2
        T v = this.computeRowValue();
        if (v == null) {
            this.valueBytes = null;
        } else {
            this.valueBytes = this.columnType().getTypeHandler().toBytes(this.columnType().getTypeClass(), v);
        }
        return this.valueBytes;
    }

    @Override
    public void setValueBytes(byte[] valueBytes) {
        this.valueBytes = valueBytes;
    }

    @Override
    public String getOriValue() {
        return value;
    }

    public RowKeyFunc<T> getRowKeyFunc() {
        return rowKeyFunc;
    }

    @Override
    public T computeRowValue() {
        return this.getRowKeyFunc().evalFuncReturnRowValue(this);
    }

    public void setRowKeyFunc(RowKeyFunc<T> rowKeyFunc) {
        this.rowKeyFunc = rowKeyFunc;
    }
}
