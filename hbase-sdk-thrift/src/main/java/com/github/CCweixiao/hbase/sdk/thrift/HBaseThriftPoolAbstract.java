package com.github.CCweixiao.hbase.sdk.thrift;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author leojie 2020/12/27 2:48 下午
 */
public class HBaseThriftPoolAbstract extends Pool<HBaseThrift>{
    public HBaseThriftPoolAbstract() {
        super();
    }

    public HBaseThriftPoolAbstract(GenericObjectPoolConfig poolConfig, PooledObjectFactory<HBaseThrift> factory) {
        super(poolConfig, factory);
    }

    @Override
    protected void returnBrokenResource(HBaseThrift resource) {
        super.returnBrokenResource(resource);
    }

    @Override
    protected void returnResource(HBaseThrift resource) {
        super.returnResource(resource);
    }
}
