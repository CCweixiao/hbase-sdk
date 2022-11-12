package com.github.CCweixiao.hbase.sdk.starter.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>HBase的连接配置</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
@ConfigurationProperties(prefix = "spring.data.hbase")
public class HBaseProperties {
    private String quorum="localhost";
    private String zkClientPort = "2181";
    private String rootDir="/hbase";
    private String nodeParent="/hbase";
    private String threadPoolName="HBase-DataSource";
    private String clientProperties="";
    private int corePoolSize=2;
    private int maximumPoolSize=4;
    private long keepAliveTime=60000;

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public String getQuorum() {
        return quorum;
    }

    public void setQuorum(String quorum) {
        this.quorum = quorum;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    public String getNodeParent() {
        return nodeParent;
    }

    public void setNodeParent(String nodeParent) {
        this.nodeParent = nodeParent;
    }

    public String getZkClientPort() {
        return zkClientPort;
    }

    public void setZkClientPort(String zkClientPort) {
        this.zkClientPort = zkClientPort;
    }

    public String getClientProperties() {
        return clientProperties;
    }

    public void setClientProperties(String clientProperties) {
        this.clientProperties = clientProperties;
    }
}
