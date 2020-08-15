package com.leo.hbase.sdk.starter.boot;

import com.leo.hbase.sdk.core.HBaseAdminTemplate;
import com.leo.hbase.sdk.core.HBaseTemplate;
import org.apache.hadoop.hbase.HBaseConfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>HBaseTemplate自动配置</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
@Configuration
@EnableConfigurationProperties(HBaseProperties.class)
@ConditionalOnClass({HBaseTemplate.class, HBaseAdminTemplate.class})
public class HBaseAutoConfiguration {
    private static final String HBASE_QUORUM = "hbase.zookeeper.quorum";
    private static final String HBASE_ROOTDIR = "hbase.rootdir";
    private static final String HBASE_ZNODE_PARENT = "zookeeper.znode.parent";
    private static final String HBASE_CONF_ZK_PORT = "hbase.zookeeper.property.clientPort";


    private final HBaseProperties hBaseProperties;

    public HBaseAutoConfiguration(HBaseProperties hBaseProperties) {
        this.hBaseProperties = hBaseProperties;
    }

    @Bean
    @ConditionalOnMissingBean(HBaseTemplate.class)
    public HBaseTemplate hBaseTemplate() {
        return new HBaseTemplate(createHBaseConfiguration());
    }

    @Bean
    @ConditionalOnMissingBean(HBaseAdminTemplate.class)
    public HBaseAdminTemplate hBaseAdminTemplate() {
        return new HBaseAdminTemplate(createHBaseConfiguration());
    }

    private org.apache.hadoop.conf.Configuration createHBaseConfiguration() {
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set(HBASE_QUORUM, this.hBaseProperties.getQuorum());
        configuration.set(HBASE_CONF_ZK_PORT, this.hBaseProperties.getZkClientPort());
        configuration.set(HBASE_ROOTDIR, hBaseProperties.getRootDir());
        configuration.set(HBASE_ZNODE_PARENT, hBaseProperties.getNodeParent());
        return configuration;
    }
}
