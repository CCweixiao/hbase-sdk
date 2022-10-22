package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseThriftException;
import com.github.CCweixiao.hbase.sdk.common.util.HBaseThriftProtocol;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * <p>HBase thrift server 连接池的具体实现</p>
 *
 * @author leojie 2020/12/27 2:50 下午
 */
public class HBaseThriftPool extends HBaseThriftPoolAbstract {
    public HBaseThriftPool() {
        this(HBaseThriftProtocol.DEFAULT_HOST, HBaseThriftProtocol.DEFAULT_PORT);
    }

    public HBaseThriftPool(String host) {
        this(host, HBaseThriftProtocol.DEFAULT_PORT);
    }

    public HBaseThriftPool(String host, int port) {
        this(new GenericObjectPoolConfig(), host, port, HBaseThriftProtocol.DEFAULT_TIMEOUT);
    }

    public HBaseThriftPool(final GenericObjectPoolConfig poolConfig, final String host, int port) {
        this(poolConfig, host, port, HBaseThriftProtocol.DEFAULT_TIMEOUT, HBaseThriftProtocol.DEFAULT_TIMEOUT);
    }

    public HBaseThriftPool(final GenericObjectPoolConfig poolConfig, final String host, int port, int timeout) {
        this(poolConfig, host, port, timeout, timeout);
    }

    public HBaseThriftPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                           final int connectionTimeout, final int soTimeout) {
        super(poolConfig, new HBaseThriftFactory(host, port, connectionTimeout, soTimeout));
    }


    @Override
    public HBaseThrift getResource() {
        HBaseThrift hBaseThrift = super.getResource();
        hBaseThrift.setDataSource(this);
        return hBaseThrift;
    }

    @Override
    protected void returnBrokenResource(HBaseThrift resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
        }
    }

    @Override
    protected void returnResource(HBaseThrift resource) {
        if (resource != null) {
            try {
                returnResourceObject(resource);
            } catch (Exception e) {
                returnBrokenResourceObject(resource);
                throw new HBaseThriftException("Resource is returned to the pool as broken", e);
            }
        }
    }
}

