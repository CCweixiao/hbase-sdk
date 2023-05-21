package com.github.CCweixiao.hbase.sdk.schema;

import org.apache.hadoop.hbase.HTableDescriptor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author leojie 2023/5/17 22:09
 */
public class HTableDescTest {
    @Test
    public void testConvertFor() {
        ColumnFamilyDesc familyDesc =
                new ColumnFamilyDesc.Builder().familyName("f").build();
        HTableDesc tableDesc = new HTableDesc.Builder()
                .tableName("test")
                .addFamilyDesc(familyDesc)
                .build();

        HTableDescriptor tableDescriptor = tableDesc.convertFor();
        System.out.println(tableDescriptor);
        System.out.println(tableDesc);
    }

    @Test
    public void testCompareTo() {
        ColumnFamilyDesc familyDesc =
                new ColumnFamilyDesc.Builder().familyName("f").build();
        HTableDesc tableDesc = new HTableDesc.Builder()
                .tableName("test")
                .addFamilyDesc(familyDesc)
                .build();

        ColumnFamilyDesc familyDesc1 =
                new ColumnFamilyDesc.Builder().familyName("f").build();
        HTableDesc tableDesc1 = new HTableDesc.Builder()
                .tableName("test")
                .addFamilyDesc(familyDesc1)
                .build();
        boolean equals = tableDesc.equals(tableDesc1);
        Assert.assertTrue(equals);
    }
}
