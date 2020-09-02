package com.github.CCweixiao;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.util.StrUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.protobuf.generated.HBaseProtos;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
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
    public boolean tableExists(String tableName) {
        return this.execute(admin -> admin.tableExists(TableName.valueOf(tableName)));
    }

    @Override
    public HTableDescriptor[] listTables() {
        return this.execute(Admin::listTables);
    }

    @Override
    public HTableDescriptor[] listTables(String regex, boolean includeSysTables) {
        return this.execute(admin -> admin.listTables(regex, includeSysTables));
    }

    @Override
    public TableName[] listTableNames() {
        return this.execute(Admin::listTableNames);
    }

    @Override
    public TableName[] listTableNames(String regex, boolean includeSysTables) {
        return this.execute(admin -> admin.listTableNames(regex, includeSysTables));
    }

    @Override
    public HTableDescriptor getTableDescriptor(String tableName) {
        return this.execute(admin -> admin.getTableDescriptor(TableName.valueOf(tableName)));
    }

    @Override
    public boolean createTable(HTableDescriptor desc) {
        return this.execute(admin -> {
            admin.createTable(desc);
            return true;
        });
    }

    @Override
    public boolean createTable(HTableDescriptor desc, byte[] startKey, byte[] endKey, int numRegions) {
        return this.execute(admin -> {
            admin.createTable(desc, startKey, endKey, numRegions);
            return true;
        });
    }

    @Override
    public boolean createTable(HTableDescriptor desc, byte[][] splitKeys) {
        return this.execute(admin -> {
            admin.createTable(desc, splitKeys);
            return true;
        });
    }

    @Override
    public boolean createTableAsync(HTableDescriptor desc, byte[][] splitKeys) {
        return this.execute(admin -> {
            admin.createTableAsync(desc, splitKeys);
            return true;
        });
    }

    @Override
    public boolean modifyTable(String tableName, HTableDescriptor tableDescriptor) {
        return this.execute(admin -> {
            admin.modifyTable(TableName.valueOf(tableName), tableDescriptor);
            return true;
        });
    }

    @Override
    public boolean renameTable(String oldTableName, String newTableName, boolean deleteOldTable) {
        checkTable(oldTableName);

        if (StrUtil.isBlank(newTableName)) {
            throw new HBaseOperationsException("new table name is not empty.");
        }

        if (this.tableExists(newTableName)) {
            throw new HBaseOperationsException("new table " + newTableName + " is exists.");
        }

        return this.execute(admin -> {
            if (admin.isTableEnabled(TableName.valueOf(oldTableName))) {
                admin.disableTable(TableName.valueOf(oldTableName));
            }
            final String tempSnapShotName = oldTableName.replace(":", "_") +
                    "_SNAPSHOT_RENAME_" + UUID.randomUUID().toString().replaceAll("-", "");
            admin.snapshot(tempSnapShotName, TableName.valueOf(oldTableName));
            admin.cloneSnapshot(tempSnapShotName, TableName.valueOf(newTableName));
            admin.deleteSnapshot(tempSnapShotName);
            if (deleteOldTable) {
                admin.deleteTable(TableName.valueOf(oldTableName));
            }
            return true;
        });
    }

    @Override
    public boolean deleteTable(String tableName) {
        return this.execute(admin -> {
            admin.deleteTable(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public HTableDescriptor[] deleteTables(String regex) {
        return this.execute(admin -> admin.deleteTables(regex));
    }

    @Override
    public boolean truncateTable(String tableName, boolean preserveSplits) {
        return this.execute(admin -> {
            admin.truncateTable(TableName.valueOf(tableName), preserveSplits);
            return true;
        });
    }

    @Override
    public boolean enableTable(String tableName) {
        return this.execute(admin -> {
            admin.enableTable(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean enableTableAsync(String tableName) {
        return this.execute(admin -> {
            admin.enableTableAsync(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean disableTable(String tableName) {
        return this.execute(admin -> {
            admin.disableTable(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean disableTableAsync(String tableName) {
        return this.execute(admin -> {
            admin.disableTableAsync(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean isTableEnabled(String tableName) {
        return this.execute(admin -> admin.isTableEnabled(TableName.valueOf(tableName)));
    }

    @Override
    public boolean isTableDisabled(String tableName) {
        return this.execute(admin -> admin.isTableDisabled(TableName.valueOf(tableName)));
    }

    @Override
    public boolean isTableAvailable(String tableName) {
        return this.execute(admin -> admin.isTableAvailable(TableName.valueOf(tableName)));
    }

    @Override
    public Pair<Integer, Integer> getAlterStatus(String tableName) {
        return this.execute(admin -> admin.getAlterStatus(TableName.valueOf(tableName)));
    }

    @Override
    public boolean addColumn(String tableName, HColumnDescriptor column) {
        return this.execute(admin -> {
            admin.addColumn(TableName.valueOf(tableName), column);
            return true;
        });
    }

    @Override
    public boolean deleteColumn(String tableName, String columnName) {
        return this.execute(admin -> {
            admin.deleteColumn(TableName.valueOf(tableName), Bytes.toBytes(columnName));
            return true;
        });
    }

    @Override
    public boolean modifyColumn(String tableName, HColumnDescriptor descriptor) {
        return this.execute(admin -> {
            admin.modifyColumn(TableName.valueOf(tableName), descriptor);
            return true;
        });
    }

    @Override
    public boolean enableReplicationScope(String tableName, List<String> families) {
        return modifyTableReplicationScope(tableName, REPLICATION_SCOPE_1, families);
    }

    @Override
    public boolean disableReplicationScope(String tableName, List<String> families) {
        return modifyTableReplicationScope(tableName, REPLICATION_SCOPE_0, families);
    }

    @Override
    public boolean flush(String tableName) {
        return this.execute(admin -> {
            admin.flush(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean compact(String tableName) {
        return this.execute(admin -> {
            admin.compact(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean majorCompact(String tableName) {
        return this.execute(admin -> {
            admin.majorCompact(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public ClusterStatus getClusterStatus() {
        return this.execute(Admin::getClusterStatus);
    }

    @Override
    public boolean createNamespace(NamespaceDescriptor descriptor) {
        return this.execute(admin -> {
            admin.createNamespace(descriptor);
            return true;
        });
    }

    @Override
    public boolean modifyNamespace(NamespaceDescriptor descriptor) {
        return this.execute(admin -> {
            admin.modifyNamespace(descriptor);
            return true;
        });
    }

    @Override
    public boolean deleteNamespace(String name) {
        return this.execute(admin -> {
            admin.deleteNamespace(name);
            return true;
        });
    }

    @Override
    public NamespaceDescriptor getNamespaceDescriptor(String name) {
        return this.execute(admin -> admin.getNamespaceDescriptor(name));
    }

    @Override
    public NamespaceDescriptor[] listNamespaceDescriptors() {
        return this.execute(Admin::listNamespaceDescriptors);
    }

    @Override
    public List<String> listNamespaces() {
        return Arrays.stream(listNamespaceDescriptors()).map(NamespaceDescriptor::getName).collect(Collectors.toList());
    }

    @Override
    public HTableDescriptor[] listTableDescriptorsByNamespace(String name) {
        return this.execute(admin -> admin.listTableDescriptorsByNamespace(name));
    }

    @Override
    public List<String> listTableNamesByNamespace(String name) {
        return Arrays.stream(listTableDescriptorsByNamespace(name)).map(HTableDescriptor::getNameAsString).collect(Collectors.toList());
    }


    @Override
    public List<HRegionInfo> getTableRegions(String tableName) {
        return this.execute(admin -> admin.getTableRegions(TableName.valueOf(tableName)));
    }

    @Override
    public long getLastMajorCompactionTimestamp(String tableName) {
        return this.execute(admin -> admin.getLastMajorCompactionTimestamp(TableName.valueOf(tableName)));
    }

    @Override
    public long getLastMajorCompactionTimestampForRegion(String regionName) {
        return this.execute(admin -> admin.getLastMajorCompactionTimestampForRegion(Bytes.toBytes(regionName)));
    }

    @Override
    public List<HBaseProtos.SnapshotDescription> listSnapshots() {
        return this.execute(Admin::listSnapshots);
    }

    @Override
    public List<HBaseProtos.SnapshotDescription> listSnapshots(String regex) {
        return this.execute(admin -> admin.listSnapshots(regex));
    }

    @Override
    public boolean snapshot(String snapshotName, String tableName) {
        return this.execute(admin -> {
            admin.snapshot(snapshotName, TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean restoreSnapshot(String snapshotName) {
        return this.execute(admin -> {
            admin.restoreSnapshot(snapshotName);
            return true;
        });
    }

    @Override
    public boolean cloneSnapshot(String snapshotName, String tableName) {
        return this.execute(admin -> {
            admin.cloneSnapshot(snapshotName, TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean deleteSnapshot(String snapshotName) {
        return this.execute(admin -> {
            admin.deleteSnapshot(snapshotName);
            return true;
        });
    }

    @Override
    public boolean deleteSnapshots(String regex) {
        return this.execute(admin -> {
            admin.deleteSnapshot(regex);
            return true;
        });
    }

    /**
     * 修改某张表某些列簇的
     *
     * @param tableName        表名
     * @param replicationScope 0 或 1，1 表示禁用replication
     * @param families         列簇
     * @return 修改replication scope是否成功
     */
    private boolean modifyTableReplicationScope(String tableName, int replicationScope, List<String> families) {
        checkTable(tableName);

        if (families == null || families.isEmpty()) {
            throw new HBaseOperationsException("the family names of table are not empty.");
        }

        return this.execute(admin -> {
            HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));

            for (String family : families) {
                final HColumnDescriptor hd = tableDescriptor.getFamily(Bytes.toBytes(family));
                final int currentScope = hd.getScope();
                if (currentScope != replicationScope) {
                    hd.setScope(replicationScope);
                }
            }
            admin.modifyTable(TableName.valueOf(tableName), tableDescriptor);
            return true;
        });
    }

    /**
     * 检查表是否存在
     *
     * @param tableName 表名
     */
    public void checkTable(String tableName) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseOperationsException("table name is not empty.");
        }

        boolean tableIsCreated = this.tableExists(tableName);
        if (!tableIsCreated) {
            throw new HBaseOperationsException(tableName + " is not exists.");
        }
    }
}
