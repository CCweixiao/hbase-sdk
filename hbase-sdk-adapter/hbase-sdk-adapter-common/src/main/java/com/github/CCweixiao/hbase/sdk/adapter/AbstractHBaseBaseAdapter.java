package com.github.CCweixiao.hbase.sdk.adapter;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkTableIsExistsException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkTableIsNotDisabledException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkTableIsNotExistsException;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.connection.HBaseConnectionManager;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.yetus.audience.InterfaceAudience;

import static com.github.CCweixiao.hbase.sdk.common.constants.HBaseConfigKeys.*;

/**
 * @author leojie 2020/11/13 11:52 下午
 */
@InterfaceAudience.Private
public abstract class AbstractHBaseBaseAdapter implements IHBaseBaseAdapter {
    private final Configuration configuration;
    private Configuration hedgedConfiguration = null;

    public AbstractHBaseBaseAdapter(Configuration configuration) {
        this.configuration = configuration;
        if (hedgedReadIsOpen()) {
            String zkQuorum = this.configuration.get(HEDGED_READ_ZOOKEEPER_QUORUM);
            if (StringUtil.isBlank(zkQuorum)) {
                throw new IllegalArgumentException(String.format("When the configuration %s is true, " +
                        "you need to specify the value of configuration %s.", HBASE_CLIENT_HEDGED_READ_SWITCH,
                        HEDGED_READ_ZOOKEEPER_QUORUM));
            }
            String zkClientPort = this.configuration.get(HEDGED_READ_ZOOKEEPER_CLIENT_PORT, "2181");
            this.hedgedConfiguration = HBaseConfiguration.create(this.configuration);
            this.hedgedConfiguration.set(ZOOKEEPER_QUORUM, zkQuorum);
            this.hedgedConfiguration.set(ZOOKEEPER_CLIENT_PORT, zkClientPort);
        }
    }

    @Override
    public Connection getConnection() {
        return HBaseConnectionManager.getInstance().getConnection(this.getConfiguration());
    }

    @Override
    public BufferedMutator getBufferedMutator(String tableName) {
        return HBaseConnectionManager.getInstance().getBufferedMutator(tableName, this.getConfiguration());
    }

    @Override
    public Connection getHedgedReadClusterConnection() {
        return HBaseConnectionManager.getInstance().getConnection(this.getHedgedConfiguration());
    }

    @Override
    public BufferedMutator getHedgedReadClusterBufferedMutator(String tableName) {
        return HBaseConnectionManager.getInstance().getBufferedMutator(tableName, this.getHedgedConfiguration());
    }


    protected void tableIsNotExistsThrowError(boolean tableIsNotExists, String msg) {
        if (tableIsNotExists) {
            throw new HBaseSdkTableIsNotExistsException(msg);
        }
    }

    protected void tableIsExistsThrowError(boolean tableIsExists, String msg) {
        if (tableIsExists) {
            throw new HBaseSdkTableIsExistsException(msg);
        }
    }

    protected void tableIsNotDisableThrowError(boolean tableIsDisabled, String msg) {
        if (!tableIsDisabled) {
            throw new HBaseSdkTableIsNotDisabledException(msg);
        }
    }

    @Override
    public boolean hedgedReadIsOpen() {
        return this.getConfiguration().getBoolean(HBASE_CLIENT_HEDGED_READ_SWITCH,
                HBASE_CLIENT_HEDGED_READ_SWITCH_DEFAULT);
    }

    @Override
    public boolean hedgedReadWriteDisable() {
        return this.getConfiguration().getBoolean(HEDGED_READ_WRITE_DISABLE,
                HBASE_CLIENT_HEDGED_READ_WRITE_DISABLE);
    }

    @Override
    public long hedgedReadThresholdMillis() {
        return this.getConfiguration().getLong(HBASE_CLIENT_HEDGED_READ_TIME_OUT,
                HBASE_CLIENT_HEDGED_READ_TIME_OUT_DEFAULT_MS);
    }

    @Override
    public int initHedgedReadPoolSize() {
        return this.getConfiguration().getInt(HBASE_CLIENT_HEDGED_READ_POOL_SIZE,
                HBASE_CLIENT_HEDGED_READ_POOL_DEFAULT_SIZE);
    }

    protected int getClientScannerCaching() {
        Configuration configuration = this.getConfiguration();
        if (configuration == null) {
            return HBASE_CLIENT_DEFAULT_SCANNER_CACHING;
        }
        return configuration.getInt(HBASE_CLIENT_SCANNER_CACHING, HBASE_CLIENT_DEFAULT_SCANNER_CACHING);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Configuration getHedgedConfiguration() {
        return hedgedConfiguration;
    }
}
