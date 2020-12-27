package com.github.CCweixiao.thrift;

import com.github.CCweixiao.exception.HBaseThriftTSocketException;
import com.github.CCweixiao.util.HBaseThriftProtocol;
import org.apache.thrift.transport.TSocket;

import java.io.Closeable;

/**
 * <p>HBase thrift connection</p>
 *
 * @author leojie 2020/12/27 2:45 下午
 */
public class HBaseThriftConnection implements Closeable {
    private final HBaseThriftTSocketFactory thriftTSocketFactory;
    private TSocket socket = null;
    private boolean broken = false;

    public HBaseThriftConnection() {
        this(HBaseThriftProtocol.DEFAULT_HOST);
    }

    public HBaseThriftConnection(final String host) {
        this(host, HBaseThriftProtocol.DEFAULT_PORT);
    }

    public HBaseThriftConnection(final String host, final int port) {
        this(host, port, HBaseThriftProtocol.DEFAULT_TIMEOUT, HBaseThriftProtocol.DEFAULT_TIMEOUT);
    }

    public HBaseThriftConnection(final String host, final int port, final int connectionTimeout, final int socketTimeout) {
        this(new DefaultHBaseThriftTSocketFactory(host, port, connectionTimeout, socketTimeout));
    }

    public HBaseThriftConnection(final HBaseThriftTSocketFactory thriftTSocketFactory) {
        this.thriftTSocketFactory = thriftTSocketFactory;
    }

    public TSocket getSocket() {
        return socket;
    }

    public int getConnectionTimeout() {
        return thriftTSocketFactory.getConnectionTimeout();
    }

    public void setConnectionTimeout(int connectionTimeout) {
        thriftTSocketFactory.setConnectionTimeout(connectionTimeout);
    }

    public int getSocketTimeout() {
        return thriftTSocketFactory.getSocketTimeout();
    }

    public void setSocketTimeout(int soTimeout) {
        thriftTSocketFactory.setSocketTimeout(soTimeout);
    }

    public String getHost() {
        return thriftTSocketFactory.getHost();
    }

    public void setHost(final String host) {
        thriftTSocketFactory.setHost(host);
    }

    public int getPort() {
        return thriftTSocketFactory.getPort();
    }

    public void setPort(final int port) {
        thriftTSocketFactory.setPort(port);
    }


    public void connect() {
        if (!isConnected()) {
            try {
                socket = thriftTSocketFactory.createTSocket();
            } catch (HBaseThriftTSocketException e) {
                broken = true;
                throw new HBaseThriftTSocketException("Failed connecting to " + thriftTSocketFactory.getDescription());
            }
        }
    }

    public boolean isBroken() {
        return broken;
    }

    public boolean isConnected() {
        return socket != null && socket.isOpen();
    }

    public void disconnect() {
        if (isConnected()) {
            socket.close();
        }
    }

    @Override
    public void close() {
        disconnect();
    }

}

