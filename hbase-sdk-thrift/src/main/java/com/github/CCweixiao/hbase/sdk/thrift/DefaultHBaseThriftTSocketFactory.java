package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseThriftTSocketException;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * <p>默认的thrift tSocket的创建工厂</p>
 *
 * @author leojie 2020/12/27 2:44 下午
 */
public class DefaultHBaseThriftTSocketFactory implements HBaseThriftTSocketFactory {
    private String host;
    private int port;
    private int connectionTimeout;
    private int socketTimeout;

    public DefaultHBaseThriftTSocketFactory(String host, int port, int connectionTimeout, int socketTimeout) {
        this.host = host;
        this.port = port;
        this.connectionTimeout = connectionTimeout;
        this.socketTimeout = socketTimeout;
    }

    @Override
    public TSocket createTSocket() throws HBaseThriftTSocketException {
        TSocket socket = new TSocket(getHost(), getPort());
        socket.setConnectTimeout(getConnectionTimeout());

        try {
            socket.open();
            socket.setSocketTimeout(getSocketTimeout());
            return socket;
        } catch (TTransportException ex) {
            socket.close();
            throw new HBaseThriftTSocketException("The TSocket of HBase thrift server create or open failed", ex);
        }
    }

    @Override
    public String getDescription() {
        return host + ":" + port;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    @Override
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public int getSocketTimeout() {
        return socketTimeout;
    }

    @Override
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}

