package com.github.CCweixiao.hbase.sdk.common.constants;

import org.apache.hadoop.hbase.HConstants;

/**
 * @author leojie 2023/7/14 23:08
 */
public class HBaseConfigKeys {
    public static final String FILTER_NAMESPACE_PREFIX = "filter.namespace.prefix";
    public static final String FILTER_TABLE_NAME_PREFIX = "filter.table.name.prefix";
    public static final String KERBEROS_PRINCIPAL = "kerberos.principal";
    public static final String KERBEROS_KEYTAB_FILE = "keytab.file";
    public static final String JAVA_SECURITY_PREFIX = "java.security";

    public static final String ZOOKEEPER_QUORUM = HConstants.ZOOKEEPER_QUORUM;
    public static final String ZOOKEEPER_CLIENT_PORT = HConstants.ZOOKEEPER_CLIENT_PORT;
    public static final String ZOOKEEPER_NODE_PARENT = HConstants.ZOOKEEPER_ZNODE_PARENT;
    public static final String HBASE_DFS_ROOT_DIR = HConstants.HBASE_DIR;
    public static final String HBASE_SECURITY_AUTH = "hbase.security.authentication";
    public static final String HBASE_REGION_SERVER_KERBEROS_PRINCIPAL = "hbase.regionserver.kerberos.principal";
    public static final String HBASE_MASTER_KERBEROS_PRINCIPAL = "hbase.master.kerberos.principal";
    public static final String KRB5_CONF_PATH = "java.security.krb5.conf";
    public static final String KRB5_REALM = "java.security.krb5.realm";
    public static final String KRB5_KDC_SERVER_ADDR = "java.security.krb5.kdc";
}
