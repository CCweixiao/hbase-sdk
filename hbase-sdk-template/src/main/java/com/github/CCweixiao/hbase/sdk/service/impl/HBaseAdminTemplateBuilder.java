package com.github.CCweixiao.hbase.sdk.service.impl;

import com.github.CCweixiao.hbase.sdk.common.HBaseAdminOperations;
import com.github.CCweixiao.hbase.sdk.common.model.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.common.model.HTableDesc;
import com.github.CCweixiao.hbase.sdk.common.model.NamespaceDesc;
import com.github.CCweixiao.hbase.sdk.common.model.SnapshotDesc;
import com.github.CCweixiao.hbase.sdk.common.util.SplitGoEnum;
import com.github.CCweixiao.hbase.sdk.service.IHBaseAdminTemplate;
import com.github.CCweixiao.hbase.sdk.service.HBaseTemplateFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author leojie 2022/10/22 18:57
 */
public class HBaseAdminTemplateBuilder implements IHBaseAdminTemplate {
    private final String sdkVersion;
    private final Properties properties;

    private HBaseAdminTemplateBuilder(Builder builder) {
        this.sdkVersion = builder.sdkAdapterVersion;
        this.properties = builder.properties;
    }

    @Override
    public boolean tableExists(String tableName) {
        return getHBaseAdminTemplate().tableExists(tableName);
    }

    @Override
    public List<HTableDesc> listTableDesc() {
        return getHBaseAdminTemplate().listTableDesc();
    }

    @Override
    public List<HTableDesc> listTableDesc(boolean includeSysTables) {
        return getHBaseAdminTemplate().listTableDesc(includeSysTables);
    }

    @Override
    public List<HTableDesc> listTableDesc(String regex, boolean includeSysTables) {
        return getHBaseAdminTemplate().listTableDesc(regex, includeSysTables);
    }

    @Override
    public List<HTableDesc> listTableDescByNamespace(String namespaceName) {
        return getHBaseAdminTemplate().listTableDescByNamespace(namespaceName);
    }

    @Override
    public List<String> listTableNames() {
        return getHBaseAdminTemplate().listTableNames();
    }

    @Override
    public List<String> listTableNames(boolean includeSysTables) {
        return getHBaseAdminTemplate().listTableNames(includeSysTables);
    }

    @Override
    public List<String> listTableNames(String regex, boolean includeSysTables) {
        return getHBaseAdminTemplate().listTableNames(regex, includeSysTables);
    }

    @Override
    public List<String> listTableNamesByNamespace(String namespaceName) {
        return getHBaseAdminTemplate().listTableNamesByNamespace(namespaceName);
    }

    @Override
    public List<ColumnFamilyDesc> listFamilyDesc(String tableName) {
        return getHBaseAdminTemplate().listFamilyDesc(tableName);
    }

    @Override
    public HTableDesc getTableDesc(String tableName) {
        return getHBaseAdminTemplate().getTableDesc(tableName);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc) {
        return getHBaseAdminTemplate().createTable(tableDesc);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync) {
        return getHBaseAdminTemplate().createTable(tableDesc, startKey, endKey, numRegions, isAsync);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, String[] splitKeys, boolean isAsync) {
        return getHBaseAdminTemplate().createTable(tableDesc, splitKeys, isAsync);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, SplitGoEnum splitGoEnum, int numRegions, boolean isAsync) {
        return getHBaseAdminTemplate().createTable(tableDesc, splitGoEnum, numRegions, isAsync);
    }

    @Override
    public boolean modifyTableProps(String tableName, Map<String, String> props, boolean isAsync) {
        return getHBaseAdminTemplate().modifyTableProps(tableName, props, isAsync);
    }

    @Override
    public boolean modifyTablePropsAsync(String tableName, Map<String, String> props) {
        return getHBaseAdminTemplate().modifyTablePropsAsync(tableName, props);
    }

    @Override
    public boolean renameTable(String oldTableName, String newTableName, boolean deleteOldTable, boolean isAsync) {
        return getHBaseAdminTemplate().renameTable(oldTableName, newTableName, deleteOldTable, isAsync);
    }

    @Override
    public boolean deleteTable(String tableName, boolean isAsync) {
        return getHBaseAdminTemplate().deleteTable(tableName, isAsync);
    }

    @Override
    public boolean deleteTableAsync(String tableName) {
        return getHBaseAdminTemplate().deleteTableAsync(tableName);
    }

    @Override
    public boolean truncateTable(String tableName, boolean preserveSplits, boolean isAsync) {
        return getHBaseAdminTemplate().truncateTable(tableName, preserveSplits, isAsync);
    }

    @Override
    public boolean truncateTableAsync(String tableName, boolean preserveSplits) {
        return getHBaseAdminTemplate().truncateTableAsync(tableName, preserveSplits);
    }

    @Override
    public boolean enableTable(String tableName, boolean isAsync) {
        return getHBaseAdminTemplate().enableTable(tableName, isAsync);
    }

    @Override
    public boolean enableTableAsync(String tableName) {
        return getHBaseAdminTemplate().enableTableAsync(tableName);
    }

    @Override
    public boolean disableTable(String tableName, boolean isAsync) {
        return getHBaseAdminTemplate().disableTable(tableName, isAsync);
    }

    @Override
    public boolean disableTableAsync(String tableName) {
        return getHBaseAdminTemplate().disableTableAsync(tableName);
    }

    @Override
    public boolean isTableEnabled(String tableName) {
        return getHBaseAdminTemplate().isTableEnabled(tableName);
    }

    @Override
    public boolean isTableDisabled(String tableName) {
        return getHBaseAdminTemplate().isTableDisabled(tableName);
    }

    @Override
    public boolean isTableAvailable(String tableName) {
        return getHBaseAdminTemplate().isTableAvailable(tableName);
    }

    @Override
    public boolean addFamily(String tableName, ColumnFamilyDesc familyDesc, boolean isAsync) {
        return getHBaseAdminTemplate().addFamily(tableName, familyDesc, isAsync);
    }

    @Override
    public boolean addFamilyAsync(String tableName, ColumnFamilyDesc familyDesc) {
        return getHBaseAdminTemplate().addFamilyAsync(tableName, familyDesc);
    }

    @Override
    public boolean deleteFamily(String tableName, String familyName, boolean isAsync) {
        return getHBaseAdminTemplate().deleteFamily(tableName, familyName, isAsync);
    }

    @Override
    public boolean deleteFamilyAsync(String tableName, String familyName) {
        return getHBaseAdminTemplate().deleteFamilyAsync(tableName, familyName);
    }

    @Override
    public boolean modifyFamily(String tableName, ColumnFamilyDesc familyDesc, boolean isAsync) {
        return getHBaseAdminTemplate().modifyFamily(tableName, familyDesc, isAsync);
    }

    @Override
    public boolean modifyFamilyAsync(String tableName, ColumnFamilyDesc familyDesc) {
        return getHBaseAdminTemplate().modifyFamilyAsync(tableName, familyDesc);
    }

    @Override
    public boolean enableReplicationScope(String tableName, List<String> families, boolean isAsync) {
        return getHBaseAdminTemplate().enableReplicationScope(tableName, families, isAsync);
    }

    @Override
    public boolean enableReplicationScopeAsync(String tableName, List<String> families) {
        return getHBaseAdminTemplate().enableReplicationScopeAsync(tableName, families);
    }

    @Override
    public boolean disableReplicationScope(String tableName, List<String> families, boolean isAsync) {
        return getHBaseAdminTemplate().disableReplicationScope(tableName, families, isAsync);
    }

    @Override
    public boolean disableReplicationScopeAsync(String tableName, List<String> families) {
        return getHBaseAdminTemplate().disableReplicationScopeAsync(tableName, families);
    }

    @Override
    public boolean flush(String tableName) {
        return getHBaseAdminTemplate().flush(tableName);
    }

    @Override
    public boolean compact(String tableName) {
        return getHBaseAdminTemplate().compact(tableName);
    }

    @Override
    public boolean majorCompact(String tableName) {
        return getHBaseAdminTemplate().majorCompact(tableName);
    }

    @Override
    public boolean createNamespace(NamespaceDesc namespaceDesc, boolean isAsync) {
        return getHBaseAdminTemplate().createNamespace(namespaceDesc, isAsync);
    }

    @Override
    public boolean createNamespaceAsync(NamespaceDesc namespaceDesc) {
        return getHBaseAdminTemplate().createNamespaceAsync(namespaceDesc);
    }

    @Override
    public boolean namespaceIsExists(String namespaceName) {
        return getHBaseAdminTemplate().namespaceIsExists(namespaceName);
    }

    @Override
    public boolean deleteNamespace(String namespaceName, boolean isAsync) {
        return getHBaseAdminTemplate().deleteNamespace(namespaceName, isAsync);
    }

    @Override
    public boolean deleteNamespaceAsync(String namespaceName) {
        return getHBaseAdminTemplate().deleteNamespaceAsync(namespaceName);
    }

    @Override
    public NamespaceDesc getNamespaceDesc(String namespaceName) {
        return getHBaseAdminTemplate().getNamespaceDesc(namespaceName);
    }

    @Override
    public List<NamespaceDesc> listNamespaceDesc() {
        return getHBaseAdminTemplate().listNamespaceDesc();
    }

    @Override
    public List<String> listNamespaceNames() {
        return getHBaseAdminTemplate().listNamespaceNames();
    }

    @Override
    public long getLastMajorCompactionTimestamp(String tableName) {
        return getHBaseAdminTemplate().getLastMajorCompactionTimestamp(tableName);
    }

    @Override
    public long getLastMajorCompactionTimestampForRegion(String regionName) {
        return getHBaseAdminTemplate().getLastMajorCompactionTimestampForRegion(regionName);
    }

    @Override
    public List<SnapshotDesc> listSnapshots() {
        return getHBaseAdminTemplate().listSnapshots();
    }

    @Override
    public boolean snapshot(SnapshotDesc snapshotDesc, boolean isAsync) {
        return getHBaseAdminTemplate().snapshot(snapshotDesc, isAsync);
    }

    @Override
    public boolean snapshotAsync(SnapshotDesc snapshotDesc) {
        return getHBaseAdminTemplate().snapshotAsync(snapshotDesc);
    }

    @Override
    public boolean restoreSnapshot(String snapshotName, boolean isAsync) {
        return getHBaseAdminTemplate().restoreSnapshot(snapshotName, isAsync);
    }

    @Override
    public boolean restoreSnapshotAsync(String snapshotName) {
        return getHBaseAdminTemplate().restoreSnapshotAsync(snapshotName);
    }

    @Override
    public boolean cloneSnapshot(String snapshotName, String tableName, boolean isAsync) {
        return getHBaseAdminTemplate().cloneSnapshot(snapshotName, tableName, isAsync);
    }

    @Override
    public boolean cloneSnapshotAsync(String snapshotName, String tableName) {
        return getHBaseAdminTemplate().cloneSnapshotAsync(snapshotName, tableName);
    }

    @Override
    public boolean deleteSnapshot(String snapshotName) {
        return getHBaseAdminTemplate().deleteSnapshots(snapshotName);
    }

    @Override
    public boolean deleteSnapshots(String regex) {
        return getHBaseAdminTemplate().deleteSnapshots(regex);
    }

    @Override
    public boolean mergeRegions(byte[] firstRegion, byte[] secondRegion, boolean force) {
        return getHBaseAdminTemplate().mergeRegions(firstRegion, secondRegion, force);
    }

    @Override
    public boolean mergeMultipleRegions(byte[][] regions, boolean force) {
        return getHBaseAdminTemplate().mergeMultipleRegions(regions, force);
    }

    @Override
    public boolean mergeTableSmallRegions(String tableName, int limitRegionsNum, int limitRegionSize) {
        return getHBaseAdminTemplate().mergeTableSmallRegions(tableName, limitRegionsNum, limitRegionSize);
    }

    public static class Builder {
        private String sdkAdapterVersion;
        private Properties properties;

        public Builder() {}

        public Builder sdkAdapterVersion(String sdkAdapterVersion) {
            this.sdkAdapterVersion = sdkAdapterVersion;
            return this;
        }

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
        public HBaseAdminTemplateBuilder build() {
            return new HBaseAdminTemplateBuilder(this);
        }
    }

    private HBaseAdminOperations getHBaseAdminTemplate() {
       return HBaseTemplateFactory.getHBaseAdminOperations(this.getSdkVersion(), this.getProperties());
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public Properties getProperties() {
        return properties;
    }
}
