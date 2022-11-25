package com.github.CCweixiao.hbase.sdk.common.type.handler;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author leojie 2022/11/25 23:12
 */
public class BigIntegerHandlerTest {
    @Test
    public void testToBigInteger() {
        BigIntegerHandler bigIntegerHandler = new BigIntegerHandler();
        BigDecimal decimal = new BigDecimal("1000.11");
        String s = decimal.toString();
        Assert.assertEquals(1000, bigIntegerHandler.toBigInteger(s).intValue());
        Assert.assertEquals(1000, bigIntegerHandler.toBigInteger("1000.1").intValue());
        BigInteger bigInteger = new BigInteger("1234567890000");
        Assert.assertEquals(bigInteger, bigIntegerHandler.toBigInteger(bigInteger.toString()));
    }
}
