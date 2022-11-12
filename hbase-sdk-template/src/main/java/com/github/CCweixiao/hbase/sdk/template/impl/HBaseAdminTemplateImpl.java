package com.github.CCweixiao.hbase.sdk.template.impl;

import com.github.CCweixiao.hbase.sdk.HBaseAdminTemplate;
import com.github.CCweixiao.hbase.sdk.common.IHBaseAdminOperations;
import com.github.CCweixiao.hbase.sdk.common.model.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.common.model.HTableDesc;
import com.github.CCweixiao.hbase.sdk.common.model.NamespaceDesc;
import com.github.CCweixiao.hbase.sdk.common.model.SnapshotDesc;
import com.github.CCweixiao.hbase.sdk.common.util.SplitGoEnum;
import com.github.CCweixiao.hbase.sdk.template.IHBaseAdminTemplate;
import org.apache.hadoop.hbase.HConstants;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author leojie 2022/10/22 18:57
 */
public class HBaseAdminTemplateImpl implements IHBaseAdminTemplate {
    private final Properties properties;
    private final IHBaseAdminOperations adminOperations;

    private HBaseAdminTemplateImpl(Builder builder) {
        this.properties = builder.properties;
        this.adminOperations = new HBaseAdminTemplate(properties);
    }

    @Override
    public boolean tableExists(String tableName) {
        return adminOperations.tableExists(tableName);
    }

    @Override
    public List<HTableDesc> listTableDesc() {
        return adminOperations.listTableDesc();
    }

    @Override
    public List<HTableDesc> listTableDesc(boolean includeSysTables) {
        return adminOperations.listTableDesc(includeSysTables);
    }

    @Override
    public List<HTableDesc> listTableDesc(String regex, boolean includeSysTables) {
        return adminOperations.listTableDesc(regex, includeSysTables);
    }

    @Override
    public List<HTableDesc> listTableDescByNamespace(String namespaceName) {
        return adminOperations.listTableDescByNamespace(namespaceName);
    }

    @Override
    public List<String> listTableNames() {
        return adminOperations.listTableNames();
    }

    @Override
    public List<String> listTableNames(boolean includeSysTables) {
        return adminOperations.listTableNames(includeSysTables);
    }

    @Override
    public List<String> listTableNames(String regex, boolean includeSysTables) {
        return adminOperations.listTableNames(regex, includeSysTables);
    }

    @Override
    public List<String> listTableNamesByNamespace(String namespaceName) {
        return adminOperations.listTableNamesByNamespace(namespaceName);
    }

    @Override
    public List<ColumnFamilyDesc> listFamilyDesc(String tableName) {
        return adminOperations.listFamilyDesc(tableName);
    }

    @Override
    public HTableDesc getTableDesc(String tableName) {
        return adminOperations.getTableDesc(tableName);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc) {
        return adminOperations.createTable(tableDesc);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync) {
        return adminOperations.createTable(tableDesc, startKey, endKey, numRegions, isAsync);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, String[] splitKeys, boolean isAsync) {
        return adminOperations.createTable(tableDesc, splitKeys, isAsync);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, SplitGoEnum splitGoEnum, int numRegions, boolean isAsync) {
        return adminOperations.createTable(tableDesc, splitGoEnum, numRegions, isAsync);
    }

    @Override
    public boolean modifyTableProps(String tableName, Map<String, String> props, boolean isAsync) {
        return adminOperations.modifyTableProps(tableName, props, isAsync);
    }

    @Override
    public boolean modifyTablePropsAsync(String tableName, Map<String, String> props) {
        return adminOperations.modifyTablePropsAsync(tableName, props);
    }

    @Override
    public boolean renameTable(String oldTableName, String newTableName, boolean deleteOldTable, boolean isAsync) {
        return adminOperations.renameTable(oldTableName, newTableName, deleteOldTable, isAsync);
    }

    @Override
    public boolean deleteTable(String tableName, boolean isAsync) {
        return adminOperations.deleteTable(tableName, isAsync);
    }

    @Override
    public boolean deleteTableAsync(String tableName) {
        return adminOperations.deleteTableAsync(tableName);
    }

    @Override
    public boolean truncateTable(String tableName, boolean preserveSplits, boolean isAsync) {
        return adminOperations.truncateTable(tableName, preserveSplits, isAsync);
    }

    @Override
    public boolean truncateTableAsync(String tableName, boolean preserveSplits) {
        return adminOperations.truncateTableAsync(tableName, preserveSplits);
    }

    @Override
    public boolean enableTable(String tableName, boolean isAsync) {
        return adminOperations.enableTable(tableName, isAsync);
    }

    @Override
    public boolean enableTableAsync(String tableName) {
        return adminOperations.enableTableAsync(tableName);
    }

    @Override
    public boolean disableTable(String tableName, boolean isAsync) {
        return adminOperations.disableTable(tableName, isAsync);
    }

    @Override
    public boolean disableTableAsync(String tableName) {
        return adminOperations.disableTableAsync(tableName);
    }

    @Override
    public boolean isTableEnabled(String tableName) {
        return adminOperations.isTableEnabled(tableName);
    }

    @Override
    public boolean isTableDisabled(String tableName) {
        return adminOperations.isTableDisabled(tableName);
    }

    @Override
    public boolean isTableAvailable(String tableName) {
        return adminOperations.isTableAvailable(tableName);
    }

    @Override
    public boolean addFamily(String tableName, ColumnFamilyDesc familyDesc, boolean isAsync) {
        return adminOperations.addFamily(tableName, familyDesc, isAsync);
    }

    @Override
    public boolean addFamilyAsync(String tableName, ColumnFamilyDesc familyDesc) {
        return adminOperations.addFamilyAsync(tableName, familyDesc);
    }

    @Override
    public boolean deleteFamily(String tableName, String familyName, boolean isAsync) {
        return adminOperations.deleteFamily(tableName, familyName, isAsync);
    }

    @Override
    public boolean deleteFamilyAsync(String tableName, String familyName) {
        return adminOperations.deleteFamilyAsync(tableName, familyName);
    }

    @Override
    public boolean modifyFamily(String tableName, ColumnFamilyDesc familyDesc, boolean isAsync) {
        return adminOperations.modifyFamily(tableName, familyDesc, isAsync);
    }

    @Override
    public boolean modifyFamilyAsync(String tableName, ColumnFamilyDesc familyDesc) {
        return adminOperations.modifyFamilyAsync(tableName, familyDesc);
    }

    @Override
    public boolean enableReplicationScope(String tableName, List<String> families, boolean isAsync) {
        return adminOperations.enableReplicationScope(tableName, families, isAsync);
    }

    @Override
    public boolean enableReplicationScopeAsync(String tableName, List<String> families) {
        return adminOperations.enableReplicationScopeAsync(tableName, families);
    }

    @Override
    public boolean disableReplicationScope(String tableName, List<String> families, boolean isAsync) {
        return adminOperations.disableReplicationScope(tableName, families, isAsync);
    }

    @Override
    public boolean disableReplicationScopeAsync(String tableName, List<String> families) {
        return adminOperations.disableReplicationScopeAsync(tableName, families);
    }

    @Override
    public boolean flush(String tableName) {
        return adminOperations.flush(tableName);
    }

    @Override
    public boolean compact(String tableName) {
        return adminOperations.compact(tableName);
    }

    @Override
    public boolean majorCompact(String tableName) {
        return adminOperations.majorCompact(tableName);
    }

    @Override
    public boolean createNamespace(NamespaceDesc namespaceDesc, boolean isAsync) {
        return adminOperations.createNamespace(namespaceDesc, isAsync);
    }

    @Override
    public boolean createNamespaceAsync(NamespaceDesc namespaceDesc) {
        return adminOperations.createNamespaceAsync(namespaceDesc);
    }

    @Override
    public boolean namespaceIsExists(String namespaceName) {
        return adminOperations.namespaceIsExists(namespaceName);
    }

    @Override
    public boolean deleteNamespace(String namespaceName, boolean isAsync) {
        return adminOperations.deleteNamespace(namespaceName, isAsync);
    }

    @Override
    public boolean deleteNamespaceAsync(String namespaceName) {
        return adminOperations.deleteNamespaceAsync(namespaceName);
    }

    @Override
    public NamespaceDesc getNamespaceDesc(String namespaceName) {
        return adminOperations.getNamespaceDesc(namespaceName);
    }

    @Override
    public List<NamespaceDesc> listNamespaceDesc() {
        return adminOperations.listNamespaceDesc();
    }

    @Override
    public List<String> listNamespaceNames() {
        return adminOperations.listNamespaceNames();
    }

    @Override
    public long getLastMajorCompactionTimestamp(String tableName) {
        return adminOperations.getLastMajorCompactionTimestamp(tableName);
    }

    @Override
    public long getLastMajorCompactionTimestampForRegion(String regionName) {
        return adminOperations.getLastMajorCompactionTimestampForRegion(regionName);
    }

    @Override
    public List<SnapshotDesc> listSnapshots() {
        return adminOperations.listSnapshots();
    }

    @Override
    public boolean snapshot(SnapshotDesc snapshotDesc, boolean isAsync) {
        return adminOperations.snapshot(snapshotDesc, isAsync);
    }

    @Override
    public boolean snapshotAsync(SnapshotDesc snapshotDesc) {
        return adminOperations.snapshotAsync(snapshotDesc);
    }

    @Override
    public boolean restoreSnapshot(String snapshotName, boolean isAsync) {
        return adminOperations.restoreSnapshot(snapshotName, isAsync);
    }

    @Override
    public boolean restoreSnapshotAsync(String snapshotName) {
        return adminOperations.restoreSnapshotAsync(snapshotName);
    }

    @Override
    public boolean cloneSnapshot(String snapshotName, String tableName, boolean isAsync) {
        return adminOperations.cloneSnapshot(snapshotName, tableName, isAsync);
    }

    @Override
    public boolean cloneSnapshotAsync(String snapshotName, String tableName) {
        return adminOperations.cloneSnapshotAsync(snapshotName, tableName);
    }

    @Override
    public boolean deleteSnapshot(String snapshotName) {
        return adminOperations.deleteSnapshots(snapshotName);
    }

    @Override
    public boolean deleteSnapshots(String regex) {
        return adminOperations.deleteSnapshots(regex);
    }

    @Override
    public boolean mergeRegions(byte[] firstRegion, byte[] secondRegion, boolean force) {
        return adminOperations.mergeRegions(firstRegion, secondRegion, force);
    }

    @Override
    public boolean mergeMultipleRegions(byte[][] regions, boolean force) {
        return adminOperations.mergeMultipleRegions(regions, force);
    }

    @Override
    public boolean mergeTableSmallRegions(String tableName, int limitRegionsNum, int limitRegionSize) {
        return adminOperations.mergeTableSmallRegions(tableName, limitRegionsNum, limitRegionSize);
    }

    public static class Builder {
        private Properties properties;

        public Builder() {}

        public Builder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public Builder addProp(String key, String value) {
            if (this.properties == null) {
                this.properties = new Properties();
            }
            this.properties.setProperty(key, value);
            return this;
        }

        public Builder zookeeperQuorum(String zookeeperQuorum) {
            addProp(HConstants.ZOOKEEPER_QUORUM, zookeeperQuorum);
            return this;
        }

        public Builder zookeeperClientPort(String zookeeperClientPort) {
            addProp(HConstants.ZOOKEEPER_CLIENT_PORT, zookeeperClientPort);
            return this;
        }
        public HBaseAdminTemplateImpl build() {
            return new HBaseAdminTemplateImpl(this);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
