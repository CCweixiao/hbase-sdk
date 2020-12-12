package com.github.CCweixiao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

/**
 * @author leojie 2020/12/12 11:00 上午
 */
public class HBaseKerberosAuthTest {
    public static void main(String[] args) throws IOException {
        System.setProperty("java.security.krb5.conf", "/Users/mac/leo_project/hbase-sdk/hbase-sdk-core_1.4/src/test/resources/conf/krb5.conf");

        Configuration conf = HBaseConfiguration.create();
        //conf.set("hbase.zookeeper.quorum", "node1");
        //conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hadoop.security.authentication", "kerberos");


        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromKeytab("hadoop@LEO.COM", "/Users/mac/leo_project/hbase-sdk/hbase-sdk-core_1.4/src/test/resources/conf/hadoop.keytab");
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();
        for (TableName tableName : admin.listTableNames()) {
            System.out.println(tableName);
        }

        HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf("USER2"));
        descriptor.addFamily(new HColumnDescriptor("INFO"));
        //admin.createTable(descriptor);
        final Table table = connection.getTable(TableName.valueOf("USER2"));
        Put put = new Put(Bytes.toBytes("10001"));
        put.addColumn(Bytes.toBytes("INFO"), Bytes.toBytes("NAME"), Bytes.toBytes("LEO_JIE"));
        table.put(put);
        admin.flush(TableName.valueOf("USER2"));
        table.close();
        connection.close();
    }
}
