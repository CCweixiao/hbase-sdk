package com.github.CCweixiao.hbase.sdk.starter.boot;

import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.template.IHBaseAdminTemplate;
import com.github.CCweixiao.hbase.sdk.template.IHBaseSqlTemplate;
import com.github.CCweixiao.hbase.sdk.template.IHBaseTableTemplate;
import com.github.CCweixiao.hbase.sdk.template.impl.HBaseAdminTemplateImpl;
import com.github.CCweixiao.hbase.sdk.template.impl.HBaseSqlTemplateImpl;
import com.github.CCweixiao.hbase.sdk.template.impl.HBaseTableTemplateImpl;
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
        return new HBaseTableTemplateImpl.Builder()
                .properties(createHBaseProperties()).build();
    }

    @Bean
    @ConditionalOnMissingBean(IHBaseSqlTemplate.class)
    public IHBaseSqlTemplate hbaseSqlTemplate() {
        return new HBaseSqlTemplateImpl();
    }

    @Bean
    @ConditionalOnMissingBean(IHBaseAdminTemplate.class)
    public IHBaseAdminTemplate hbaseAdminTemplate() {
        return new HBaseAdminTemplateImpl.Builder()
                .properties(createHBaseProperties()).build();
    }

    private Properties createHBaseProperties() {
        Properties properties = new Properties();
        properties.setProperty(HBASE_QUORUM, this.hBaseProperties.getQuorum());
        properties.setProperty(HBASE_CONF_ZK_PORT, this.hBaseProperties.getZkClientPort());
        properties.setProperty(HBASE_ROOTDIR, hBaseProperties.getRootDir());
        properties.setProperty(HBASE_ZNODE_PARENT, hBaseProperties.getNodeParent());
        Map<String, String> otherProperties = StringUtil.parsePropertyToMapFromStr(hBaseProperties.getClientProperties());
        if (!otherProperties.isEmpty()) {
            otherProperties.forEach(properties::setProperty);
        }
        return properties;
    }


}
