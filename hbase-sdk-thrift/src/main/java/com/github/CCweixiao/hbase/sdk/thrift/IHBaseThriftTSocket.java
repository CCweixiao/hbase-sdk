package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseThriftTSocketException;
import org.apache.thrift.transport.TSocket;

/**
 * <p>HBase thrift TSocket的初始化工厂接口</p>
 *
 * @author leojie 2020/12/27 2:38 下午
 */
public interface IHBaseThriftTSocket {
    /**
     * 创建连接HBase thrift 的TSocket对象
     *
     * @return TSocket对象
     * @throws HBaseThriftTSocketException 抛出HBase Thrift TSocket相关的异常
     */
    TSocket createTSocket() throws HBaseThriftTSocketException;

    /**
     * description
     *
     * @return description
     */
    String getDescription();

    /**
     * HBase thrift host
     *
     * @return host
     */
    String getHost();

    /**
     * HBase thrift port
     *
     * @return HBase thrift port
     */
    int getPort();

    /**
     * 获取连接HBase thrift server的超时时间
     *
     * @return 连接的超时时间
     */
    int getConnectionTimeout();

    /**
     * 获取TSocket的超时时间
     *
     * @return TSocket的超时时间
     */
    int getSocketTimeout();
}
