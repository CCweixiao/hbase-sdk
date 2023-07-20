package com.github.CCweixiao.hbase.sdk.starter.boot;

import com.github.CCweixiao.hbase.sdk.common.constants.HBaseConfigKeys;
import com.github.CCweixiao.hbase.sdk.common.security.AuthType;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.template.BaseHBaseTableTemplate;
import com.github.CCweixiao.hbase.sdk.template.HBaseTableTemplate;
import com.github.CCweixiao.hbase.sdk.template.IHBaseAdminTemplate;
import com.github.CCweixiao.hbase.sdk.template.IHBaseSqlTemplate;
import com.github.CCweixiao.hbase.sdk.template.impl.HBaseAdminTemplateImpl;
import com.github.CCweixiao.hbase.sdk.template.impl.HBaseSqlTemplateImpl;
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
@ConditionalOnClass({IHBaseAdminTemplate.class, BaseHBaseTableTemplate.class})
public class HBaseAutoConfiguration {

    private final HBaseProperties properties;

    public HBaseAutoConfiguration(HBaseProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(BaseHBaseTableTemplate.class)
    public BaseHBaseTableTemplate hbaseTableTemplate() {
        return HBaseTableTemplate.of(createHBaseProperties());
    }

    @Bean
    @ConditionalOnMissingBean(IHBaseSqlTemplate.class)
    public IHBaseSqlTemplate hbaseSqlTemplate() {
        return new HBaseSqlTemplateImpl.Builder()
                .properties(createHBaseProperties()).build();
    }

    @Bean
    @ConditionalOnMissingBean(IHBaseAdminTemplate.class)
    public IHBaseAdminTemplate hbaseAdminTemplate() {
        return new HBaseAdminTemplateImpl.Builder()
                .properties(createHBaseProperties()).build();
    }

    private Properties createHBaseProperties() {
        Properties properties = new Properties();
        if (StringUtil.isBlank(this.properties.getZkQuorum())) {
            properties.setProperty(HBaseConfigKeys.ZOOKEEPER_QUORUM, HBaseConfigKeys.LOCAL_HOST_ZOOKEEPER_QUORUM);
        } else {
            properties.setProperty(HBaseConfigKeys.ZOOKEEPER_QUORUM, this.properties.getZkQuorum());
        }
        if (StringUtil.isNotBlank(this.properties.getZkClientPort())) {
            properties.setProperty(HBaseConfigKeys.ZOOKEEPER_CLIENT_PORT, this.properties.getZkClientPort());
        }
        if (StringUtil.isNotBlank(this.properties.getDfsRootDir())) {
            properties.setProperty(HBaseConfigKeys.HBASE_DFS_ROOT_DIR, this.properties.getDfsRootDir());
        }
        if (StringUtil.isNotBlank(this.properties.getZkNodeParent())) {
            properties.setProperty(HBaseConfigKeys.ZOOKEEPER_NODE_PARENT, this.properties.getZkNodeParent());
        }
        AuthType defaultAuthType = AuthType.findAuthType(this.properties.getSecurityAuthWay());
        properties.setProperty(HBaseConfigKeys.HBASE_SECURITY_AUTH, defaultAuthType.getAuthType());

        if (defaultAuthType == AuthType.KERBEROS) {
            if (StringUtil.isBlank(this.properties.getKerberosPrincipal())) {
                throw new IllegalArgumentException("The client kerberos principal must not be empty.");
            }
            properties.setProperty(HBaseConfigKeys.KERBEROS_PRINCIPAL, this.properties.getKerberosPrincipal());

            if (StringUtil.isBlank(this.properties.getKeytabFilePath())) {
                throw new IllegalArgumentException("The client kerberos keytab file path must not be empty.");
            }
            properties.setProperty(HBaseConfigKeys.KERBEROS_KEYTAB_FILE, this.properties.getKeytabFilePath());
            properties.setProperty(HBaseConfigKeys.KERBEROS_PROXY_USER, this.properties.getKerberosProxyUser());

            if (StringUtil.isBlank(this.properties.getRsKerberosPrincipal())) {
                throw new IllegalArgumentException("The region server kerberos principal must not be empty.");
            }
            properties.setProperty(HBaseConfigKeys.HBASE_REGION_SERVER_KERBEROS_PRINCIPAL, this.properties.getRsKerberosPrincipal());

            if (StringUtil.isBlank(this.properties.getMasterKerberosPrincipal())) {
                throw new IllegalArgumentException("The master server kerberos principal must not be empty.");
            }
            properties.setProperty(HBaseConfigKeys.HBASE_MASTER_KERBEROS_PRINCIPAL, this.properties.getMasterKerberosPrincipal());

            if (StringUtil.isNotBlank(this.properties.getKrb5ConfPath())) {
                properties.setProperty(HBaseConfigKeys.KRB5_CONF_PATH, this.properties.getKrb5ConfPath());
            } else {
                properties.setProperty(HBaseConfigKeys.KRB5_REALM, this.properties.getKrb5Realm());
                properties.setProperty(HBaseConfigKeys.KRB5_KDC_SERVER_ADDR, this.properties.getKrb5KdcServerAddr());
            }
        }

        Map<String, String> otherProperties = StringUtil.parsePropertyToMapFromStr(this.properties.getClientProperties());
        if (!otherProperties.isEmpty()) {
            otherProperties.forEach(properties::setProperty);
        }
        return properties;
    }


}
