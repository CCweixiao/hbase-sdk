package com.github.CCweixiao.hbase.sdk.common.constants;

/**
 * @author leojie 2023/7/14 23:08
 */
public class HBaseConfigKeys {
    public static final String FILTER_NAMESPACE_PREFIX = "filter.namespace.prefix";
    public static final String FILTER_TABLE_NAME_PREFIX = "filter.table.name.prefix";
    public static final String KERBEROS_PROXY_USER = "kerberos.proxy.user";
    public static final String KERBEROS_PRINCIPAL = "kerberos.principal";
    public static final String KERBEROS_KEYTAB_FILE = "keytab.file";
    public static final String JAVA_SECURITY_PREFIX = "java.security";
    public static final String ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
    public static final String LOCAL_HOST_ZOOKEEPER_QUORUM = "localhost";
    public static final String ZOOKEEPER_CLIENT_PORT = "hbase.zookeeper.property.clientPort";
    public static final String ZOOKEEPER_NODE_PARENT = "zookeeper.znode.parent";
    public static final String HBASE_DFS_ROOT_DIR = "hbase.rootdir";
    public static final String HBASE_SECURITY_AUTH = "hbase.security.authentication";
    public static final String HBASE_REGION_SERVER_KERBEROS_PRINCIPAL = "hbase.regionserver.kerberos.principal";
    public static final String HBASE_MASTER_KERBEROS_PRINCIPAL = "hbase.master.kerberos.principal";
    public static final String KRB5_CONF_PATH = "java.security.krb5.conf";
    public static final String KRB5_REALM = "java.security.krb5.realm";
    public static final String KRB5_KDC_SERVER_ADDR = "java.security.krb5.kdc";

    public static final String HEDGED_READ_WRITE_DISABLE = "hbase.client.hedged.read.write.disable";
    public static final String HBASE_CLIENT_HEDGED_READ_WRITE_DISABLE = "true";
    public static final String HBASE_CLIENT_HEDGED_READ_SWITCH = "hbase.client.hedged.read.open";
    public static final String HBASE_CLIENT_HEDGED_READ_SWITCH_DEFAULT = "false";
    public static final String HBASE_CLIENT_HEDGED_READ_TIME_OUT = "hbase.client.hedged.read.timeout";
    public static final String HBASE_CLIENT_HEDGED_READ_TIME_OUT_DEFAULT_MS = "100";
    public static final String HBASE_CLIENT_HEDGED_READ_POOL_SIZE = "hbase.client.hedged.thread.pool.size";
    public static final String HBASE_CLIENT_HEDGED_READ_POOL_DEFAULT_SIZE = "10";
    public static final String HEDGED_READ_CONF_SUFFIX = ".hedged.read";

    public static final String HBASE_CLIENT_SCANNER_CACHING = "hbase.client.scanner.caching";
    public static final String HBASE_CLIENT_BLOCK_CACHE = "hbase.client.block.cache";
    public static final int HBASE_CLIENT_DEFAULT_SCANNER_CACHING = 1000;
}
