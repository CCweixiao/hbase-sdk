package com.github.CCweixiao.hbase.sdk.dsl.antlr.data;

/**
 * @author leojie 2023/7/28 19:58
 */
public class InsertColData {
    private final byte[] familyByte;
    private final byte[] qualifierByte;
    private final byte[] dataByte;
    private final long ts;

    public InsertColData(byte[] familyByte, byte[] qualifierByte, byte[] dataByte, long ts) {
        this.familyByte = familyByte;
        this.qualifierByte = qualifierByte;
        this.dataByte = dataByte;
        this.ts = ts;
    }

    public InsertColData(byte[] familyByte, byte[] qualifierByte, byte[] dataByte) {
        this(familyByte, qualifierByte, dataByte, 0);
    }

    public byte[] getFamilyByte() {
        return familyByte;
    }

    public byte[] getQualifierByte() {
        return qualifierByte;
    }

    public byte[] getDataByte() {
        return dataByte;
    }

    public long getTs() {
        return ts;
    }
}
