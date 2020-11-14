package com.github.CCweixiao;

import org.apache.hadoop.conf.Configuration;

import java.util.Properties;

/**
 * @author leojie 2020/11/14 2:26 下午
 */
public abstract class AbstractHBaseAdminTemplate extends AbstractHBaseConfig implements HBaseAdminOperations {
    public AbstractHBaseAdminTemplate(Configuration configuration) {
        super(configuration);
    }

    public AbstractHBaseAdminTemplate(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public AbstractHBaseAdminTemplate(Properties properties) {
        super(properties);
    }
}
