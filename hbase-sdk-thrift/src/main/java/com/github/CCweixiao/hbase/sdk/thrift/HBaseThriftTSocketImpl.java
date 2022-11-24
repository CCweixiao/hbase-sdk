package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseThriftTSocketException;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * <p>默认的thrift tSocket的创建工厂</p>
 *
 * @author leojie 2020/12/27 2:44 下午
 */
public class HBaseThriftTSocketImpl implements IHBaseThriftTSocket {
    private final String host;
    private final int port;
    private final int connectionTimeout;
    private final int socketTimeout;

    private HBaseThriftTSocketImpl(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.connectionTimeout = builder.connectionTimeout;
        this.socketTimeout = builder.socketTimeout;
    }

    public static class Builder {
        private final String host;
        private final int port;
        private int connectionTimeout;
        private int socketTimeout;

        public Builder(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public Builder connectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public Builder socketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
            return this;
        }

        public HBaseThriftTSocketImpl build() {
            return new HBaseThriftTSocketImpl(this);
        }
    }

    @Override
    public TSocket createTSocket() throws HBaseThriftTSocketException {
        TSocket socket = new TSocket(this.getHost(), this.getPort());
        socket.setConnectTimeout(this.getConnectionTimeout());
        try {
            socket.open();
            socket.setSocketTimeout(this.getSocketTimeout());
            return socket;
        } catch (TTransportException ex) {
            socket.close();
            throw new HBaseThriftTSocketException("The TSocket of hbase thrift is created or opened failed!", ex);
        }
    }

    @Override
    public String getDescription() {
        return this.host + ":" + this.port;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    @Override
    public int getSocketTimeout() {
        return this.socketTimeout;
    }
}

