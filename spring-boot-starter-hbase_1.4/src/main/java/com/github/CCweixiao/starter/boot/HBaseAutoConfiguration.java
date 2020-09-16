package com.github.CCweixiao.starter.boot;

import com.github.CCweixiao.HBaseAdminTemplate;
import com.github.CCweixiao.HBaseTemplate;
import com.github.CCweixiao.util.StrUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * <p>HBaseTemplate自动配置</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
@Configuration
@EnableConfigurationProperties(HBaseProperties.class)
@ConditionalOnClass({HBaseTemplate.class, HBaseAdminTemplate.class})
public class HBaseAutoConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(HBaseAutoConfiguration.class);

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
        LOG.info("start loading hbase configuration: {}={}", HBASE_QUORUM, this.hBaseProperties.getQuorum());
        configuration.set(HBASE_CONF_ZK_PORT, this.hBaseProperties.getZkClientPort());
        LOG.info("start loading hbase configuration: {}={}", HBASE_CONF_ZK_PORT, this.hBaseProperties.getZkClientPort());
        configuration.set(HBASE_ROOTDIR, hBaseProperties.getRootDir());
        LOG.info("start loading hbase configuration: {}={}", HBASE_ROOTDIR, this.hBaseProperties.getRootDir());
        configuration.set(HBASE_ZNODE_PARENT, hBaseProperties.getNodeParent());
        LOG.info("start loading hbase configuration: {}={}", HBASE_ZNODE_PARENT, this.hBaseProperties.getNodeParent());
        Map<String, String> otherProperties = StrUtil.parsePropertyToMapFromStr(hBaseProperties.getClientProperties());
        if (!otherProperties.isEmpty()) {
            otherProperties.forEach((k, v) -> {
                configuration.set(k, v);
                LOG.info("start loading hbase configuration: {}={}", k, v);
            });
        }
        return configuration;
    }


}
