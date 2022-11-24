package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseThriftTSocketException;
import com.github.CCweixiao.hbase.sdk.common.util.HBaseThriftProtocol;
import org.apache.thrift.transport.TSocket;

import java.io.Closeable;

/**
 * <p>HBase thrift connection</p>
 *
 * @author leojie 2020/12/27 2:45 下午
 */
public class HBaseThriftConnection implements Closeable {
    private final IHBaseThriftTSocket hbaseThriftTSocket;
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
        this(new HBaseThriftTSocketImpl.Builder(host, port)
                .connectionTimeout(connectionTimeout)
                .socketTimeout(socketTimeout)
                .build());
    }

    public HBaseThriftConnection(final IHBaseThriftTSocket hbaseThriftTSocket) {
        this.hbaseThriftTSocket = hbaseThriftTSocket;
    }

    public TSocket getSocket() {
        return socket;
    }

    public int getConnectionTimeout() {
        return hbaseThriftTSocket.getConnectionTimeout();
    }

    public int getSocketTimeout() {
        return hbaseThriftTSocket.getSocketTimeout();
    }

    public String getHost() {
        return hbaseThriftTSocket.getHost();
    }

    public int getPort() {
        return hbaseThriftTSocket.getPort();
    }


    public void connect() {
        if (!isConnected()) {
            try {
                socket = hbaseThriftTSocket.createTSocket();
            } catch (HBaseThriftTSocketException e) {
                broken = true;
                throw new HBaseThriftTSocketException("Failed connecting to " + hbaseThriftTSocket.getDescription());
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

