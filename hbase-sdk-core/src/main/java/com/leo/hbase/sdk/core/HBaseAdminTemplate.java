package com.leo.hbase.sdk.core;

import com.leo.hbase.sdk.core.exception.HBaseOperationsException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;


import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 */
public class HBaseAdminTemplate extends AbstractHBaseTemplate implements HBaseAdminOperations {
    public HBaseAdminTemplate(Configuration configuration) {
        super(configuration);
    }

    public HBaseAdminTemplate(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public HBaseAdminTemplate(Properties properties) {
        super(properties);
    }

    @Override
    public List<String> listNamespaces() {
        return this.execute(admin -> Arrays.stream(admin.listNamespaceDescriptors()).
                map(NamespaceDescriptor::getName).collect(Collectors.toList()));
    }

    @Override
    public List<String> listTableNames() {
        return this.execute(admin -> {
            final TableName[] tableNames = admin.listTableNames();
            return Arrays.stream(tableNames).map(TableName::getNameAsString).collect(Collectors.toList());
        });
    }

    @Override
    public boolean createNamespace(String namespace) {
        return this.execute(admin -> {
            List<String> namespaces = Arrays.stream(admin.listNamespaceDescriptors())
                    .map(NamespaceDescriptor::getName).collect(Collectors.toList());
            if (namespaces.contains(namespace)) {
                throw new HBaseOperationsException(namespace + " is exists.");
            }
            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespace).build();
            admin.createNamespace(namespaceDescriptor);
            return true;
        });
    }

    @Override
    public boolean createTable(HTableDescriptor tableDescriptor, byte[][] splits) {
        return this.execute(admin -> {
            boolean tableIsCreated = admin.tableExists(tableDescriptor.getTableName());
            if (tableIsCreated) {
                throw new HBaseOperationsException(tableDescriptor.getTableName().getNameAsString() + " is exists.");
            }
            if (splits != null) {
                admin.createTable(tableDescriptor, splits);
            } else {
                admin.createTable(tableDescriptor);
            }
            return true;
        });
    }
}
