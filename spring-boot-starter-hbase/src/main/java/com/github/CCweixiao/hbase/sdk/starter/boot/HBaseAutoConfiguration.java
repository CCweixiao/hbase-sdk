package com.github.CCweixiao.hbase.sdk.starter.boot;

import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;
import com.github.CCweixiao.hbase.sdk.service.IHBaseAdminTemplate;
import com.github.CCweixiao.hbase.sdk.service.IHBaseTableTemplate;
import com.github.CCweixiao.hbase.sdk.service.impl.HBaseAdminTemplateBuilder;
import com.github.CCweixiao.hbase.sdk.service.impl.HBaseTableTemplateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Properties;

/**
 * <p>HBaseTemplate自动配置</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
@Configuration
@EnableConfigurationProperties(HBaseProperties.class)
@ConditionalOnClass({IHBaseAdminTemplate.class, IHBaseTableTemplate.class})
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
    @ConditionalOnMissingBean(IHBaseTableTemplate.class)
    public IHBaseTableTemplate hbaseTableTemplate() {
        return new HBaseTableTemplateBuilder.Builder()
                .sdkAdapterVersion(this.hBaseProperties.getHbaseAdapterVersion())
                .properties(createHBaseProperties()).build();
    }

    @Bean
    @ConditionalOnMissingBean(IHBaseAdminTemplate.class)
    public IHBaseAdminTemplate hbaseAdminTemplate() {
        return new HBaseAdminTemplateBuilder.Builder()
                .sdkAdapterVersion(this.hBaseProperties.getHbaseAdapterVersion())
                .properties(createHBaseProperties()).build();
    }

    private Properties createHBaseProperties() {
        Properties properties = new Properties();
        properties.setProperty(HBASE_QUORUM, this.hBaseProperties.getQuorum());
        LOG.info("start loading hbase configuration: {}={}", HBASE_QUORUM, this.hBaseProperties.getQuorum());
        properties.setProperty(HBASE_CONF_ZK_PORT, this.hBaseProperties.getZkClientPort());
        LOG.info("start loading hbase configuration: {}={}", HBASE_CONF_ZK_PORT, this.hBaseProperties.getZkClientPort());
        properties.setProperty(HBASE_ROOTDIR, hBaseProperties.getRootDir());
        LOG.info("start loading hbase configuration: {}={}", HBASE_ROOTDIR, this.hBaseProperties.getRootDir());
        properties.setProperty(HBASE_ZNODE_PARENT, hBaseProperties.getNodeParent());
        LOG.info("start loading hbase configuration: {}={}", HBASE_ZNODE_PARENT, this.hBaseProperties.getNodeParent());
        Map<String, String> otherProperties = StrUtil.parsePropertyToMapFromStr(hBaseProperties.getClientProperties());
        if (!otherProperties.isEmpty()) {
            otherProperties.forEach((k, v) -> {
                properties.setProperty(k, v);
                LOG.info("start loading hbase configuration: {}={}", k, v);
            });
        }
        return properties;
    }


}
