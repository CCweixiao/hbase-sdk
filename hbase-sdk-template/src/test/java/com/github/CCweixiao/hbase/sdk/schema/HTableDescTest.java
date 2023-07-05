package com.github.CCweixiao.hbase.sdk.schema;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author leojie 2023/5/17 22:09
 */
public class HTableDescTest {
    @Test
    public void testConvertFor() {
        ColumnFamilyDesc familyDesc =
                ColumnFamilyDesc.newBuilder().name("f").build();
        HTableDesc tableDesc = HTableDesc.newBuilder()
                .name("test")
                .addFamilyDesc(familyDesc)
                .build();
        System.out.println(tableDesc);
    }

    @Test
    public void testCompareTo() {
        ColumnFamilyDesc familyDesc =
                ColumnFamilyDesc.newBuilder().name("f").build();
        HTableDesc tableDesc = HTableDesc.newBuilder()
                .name("test")
                .addFamilyDesc(familyDesc)
                .build();

        ColumnFamilyDesc familyDesc1 =
                ColumnFamilyDesc.newBuilder().name("f").build();
        HTableDesc tableDesc1 = HTableDesc.newBuilder()
                .name("test")
                .addFamilyDesc(familyDesc1)
                .build();
        boolean equals = tableDesc.equals(tableDesc1);
        Assert.assertTrue(equals);
    }
}
