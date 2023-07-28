package com.github.CCweixiao.hbase.sdk.dsl.antlr.data;

/**
 * @author leojie 2023/7/28 19:58
 */
public class InsertColData {
    private final byte[] family;
    private final byte[] qualifier;
    private final byte[] value;

    public InsertColData(byte[] family, byte[] qualifier, byte[] value) {
        this.family = family;
        this.qualifier = qualifier;
        this.value = value;
    }

    public byte[] getFamily() {
        return family;
    }

    public byte[] getQualifier() {
        return qualifier;
    }

    public byte[] getValue() {
        return value;
    }
}
