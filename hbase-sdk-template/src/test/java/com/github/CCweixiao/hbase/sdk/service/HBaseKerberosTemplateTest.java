package com.github.CCweixiao.hbase.sdk.service;

import com.github.CCweixiao.hbase.sdk.template.BaseHBaseAdminTemplate;
import com.github.CCweixiao.hbase.sdk.template.HBaseAdminTemplate;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

/**
 * @author leojie 2022/12/9 22:40
 */
public class HBaseKerberosTemplateTest {
    private BaseHBaseAdminTemplate adminTemplate;

    @Before
    public void init() {
        Properties properties = new Properties();
        properties.put("hbase.zookeeper.quorum", "myhbase");
        properties.put("hbase.zookeeper.property.clientPort", "2181");
        properties.put("hbase.security.authentication", "simple");
        // 下面配置是kerberos认证方式所需
        properties.put("kerberos.principal", "hbase@HADOOP.LEO.COM");
        properties.put("keytab.file", "/etc/hbase/conf/hbase.keytab");
        properties.put("hbase.regionserver.kerberos.principal", "hbase/_HOST@HADOOP.LEO.COM");
        properties.put("hbase.master.kerberos.principal", "hbase/_HOST@HADOOP.LEO.COM");
        // 指定kdc服务相关的配置方式有如下两种：
        // 方式一：指定krb5.conf路径
        properties.put("java.security.krb5.conf", "/etc/krb5.conf");
        // 方式二：指定java.security.krb5.realm和java.security.krb5.kdc
        properties.put("java.security.krb5.realm", "HADOOP.LEO.COM");
        properties.put("java.security.krb5.kdc", "你自己的kdc服务地址");
        // 一些额外的客户端参数
        properties.put("hbase.client.retries.number", "3");
        adminTemplate = HBaseAdminTemplate.of(properties);
    }


    @Test
    public void getTest() {
        System.out.println(adminTemplate.listTableNames());
    }
}
