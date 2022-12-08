package com.github.CCwexiao.hbase.sdk.dsl.model;

import java.util.Arrays;

/**
 * @author leojie 2022/12/8 23:28
 */
public class KeyValue {
    private byte[] key;
    private byte[] value;

    public KeyValue(byte[] key, byte[] value) {
        this.key = key;
        this.value = value;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyValue)) {
            return false;
        }
        KeyValue keyValue = (KeyValue) o;
        return Arrays.equals(key, keyValue.key) && Arrays.equals(value, keyValue.value);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(key);
        result = 31 * result + Arrays.hashCode(value);
        return result;
    }
}
