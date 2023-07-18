package com.github.CCweixiao.hbase.sdk.starter.boot;

import com.github.CCweixiao.hbase.sdk.common.constants.HBaseConfigKeys;
import com.github.CCweixiao.hbase.sdk.common.security.AuthType;
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
        if (StringUtil.isBlank(this.hBaseProperties.getZkHostList())) {
            throw new IllegalArgumentException("The zookeeper address list must not be empty.");
        }
        properties.setProperty(HBaseConfigKeys.ZOOKEEPER_QUORUM, this.hBaseProperties.getZkHostList());
        if (StringUtil.isNotBlank(this.hBaseProperties.getZkHostList())) {
            properties.setProperty(HBaseConfigKeys.ZOOKEEPER_CLIENT_PORT, this.hBaseProperties.getZkClientPort());
        }
        if (StringUtil.isNotBlank(this.hBaseProperties.getDfsRootDir())) {
            properties.setProperty(HBaseConfigKeys.HBASE_DFS_ROOT_DIR, this.hBaseProperties.getDfsRootDir());
        }
        if (StringUtil.isNotBlank(this.hBaseProperties.getZkNodeParent())) {
            properties.setProperty(HBaseConfigKeys.ZOOKEEPER_NODE_PARENT, this.hBaseProperties.getZkNodeParent());
        }
        AuthType defaultAuthType = AuthType.findAuthType(this.hBaseProperties.getSecurityAuthWay());
        properties.setProperty(HBaseConfigKeys.HBASE_SECURITY_AUTH, defaultAuthType.getAuthType());

        if (defaultAuthType == AuthType.KERBEROS) {
            if (StringUtil.isBlank(this.hBaseProperties.getKerberosPrincipal())) {
                throw new IllegalArgumentException("The client kerberos principal must not be empty.");
            }
            properties.setProperty(HBaseConfigKeys.KERBEROS_PRINCIPAL, this.hBaseProperties.getKerberosPrincipal());

            if (StringUtil.isBlank(this.hBaseProperties.getKeytabFilePath())) {
                throw new IllegalArgumentException("The client kerberos keytab file path must not be empty.");
            }
            properties.setProperty(HBaseConfigKeys.KERBEROS_KEYTAB_FILE, this.hBaseProperties.getKeytabFilePath());

            if (StringUtil.isBlank(this.hBaseProperties.getRsKerberosPrincipal())) {
                throw new IllegalArgumentException("The region server kerberos principal must not be empty.");
            }
            properties.setProperty(HBaseConfigKeys.HBASE_REGION_SERVER_KERBEROS_PRINCIPAL, this.hBaseProperties.getRsKerberosPrincipal());

            if (StringUtil.isBlank(this.hBaseProperties.getMasterKerberosPrincipal())) {
                throw new IllegalArgumentException("The master server kerberos principal must not be empty.");
            }
            properties.setProperty(HBaseConfigKeys.HBASE_MASTER_KERBEROS_PRINCIPAL, this.hBaseProperties.getMasterKerberosPrincipal());

            if (StringUtil.isNotBlank(this.hBaseProperties.getKrb5ConfPath())) {
                properties.setProperty(HBaseConfigKeys.KRB5_CONF_PATH, this.hBaseProperties.getKrb5ConfPath());
            } else {
                properties.setProperty(HBaseConfigKeys.KRB5_REALM, this.hBaseProperties.getKrb5Realm());
                properties.setProperty(HBaseConfigKeys.KRB5_KDC_SERVER_ADDR, this.hBaseProperties.getKrb5KdcServerAddr());
            }
        }

        Map<String, String> otherProperties = StringUtil.parsePropertyToMapFromStr(this.hBaseProperties.getClientProperties());
        if (!otherProperties.isEmpty()) {
            otherProperties.forEach(properties::setProperty);
        }
        return properties;
    }


}
