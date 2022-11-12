package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.IHBaseAdminOperations;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.model.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.common.model.HTableDesc;
import com.github.CCweixiao.hbase.sdk.common.model.NamespaceDesc;
import com.github.CCweixiao.hbase.sdk.common.model.SnapshotDesc;
import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/11/14 2:26 下午
 */
public abstract class AbstractHBaseAdminTemplate extends AbstractHBaseOperations implements IHBaseAdminOperations {
    public AbstractHBaseAdminTemplate(Configuration configuration) {
        super(configuration);
    }

    public AbstractHBaseAdminTemplate(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public AbstractHBaseAdminTemplate(Properties properties) {
        super(properties);
    }

    @Override
    public boolean tableExists(String tableName) {
        return this.execute(admin -> admin.tableExists(TableName.valueOf(tableName)));
    }

    @Override
    public List<HTableDesc> listTableDesc() {
        return listTableDesc("", false);
    }

    @Override
    public List<HTableDesc> listTableDesc(boolean includeSysTables) {
        return listTableDesc("", includeSysTables);
    }

    @Override
    public List<String> listTableNames() {
        return listTableNames("", false);
    }

    @Override
    public List<String> listTableNames(boolean includeSysTables) {
        return listTableNames("", includeSysTables);
    }

    @Override
    public List<String> listTableNames(String regex, boolean includeSysTables) {
        return this.execute(admin -> {
            TableName[] tableNames;
            if (StrUtil.isBlank(regex)) {
                tableNames = admin.listTableNames((Pattern) null, includeSysTables);
            } else {
                tableNames = admin.listTableNames(Pattern.compile(regex), includeSysTables);
            }
            if (tableNames == null || tableNames.length == 0) {
                return new ArrayList<>();
            }
            return Arrays.stream(tableNames).map(TableName::getNameAsString).collect(Collectors.toList());
        });
    }

    @Override
    public List<String> listTableNamesByNamespace(String namespaceName) {
        List<HTableDesc> tableDescList = listTableDescByNamespace(namespaceName);
        return tableDescList.stream().map(HTableDesc::getTableName).collect(Collectors.toList());
    }

    @Override
    public boolean modifyTablePropsAsync(final String tableName, Map<String, String> props) {
        return modifyTableProps(tableName, props, true);
    }

    @Override
    public boolean deleteTableAsync(String tableName) {
        return deleteTable(tableName, true);
    }

    @Override
    public boolean truncateTableAsync(String tableName, boolean preserveSplits) {
        return truncateTable(tableName, preserveSplits, true);
    }

    @Override
    public boolean enableTable(String tableName, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);

            if (admin.isTableEnabled(TableName.valueOf(tableName))) {
                return true;
            }
            if (isAsync) {
                admin.enableTableAsync(TableName.valueOf(tableName));
            } else {
                admin.enableTable(TableName.valueOf(tableName));
            }
            return true;
        });
    }

    @Override
    public boolean enableTableAsync(String tableName) {
        return enableTable(tableName, true);
    }

    @Override
    public boolean disableTable(String tableName, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);

            if (admin.isTableDisabled(TableName.valueOf(tableName))) {
                return true;
            }
            if (isAsync) {
                admin.disableTableAsync(TableName.valueOf(tableName));
            } else {
                admin.disableTable(TableName.valueOf(tableName));
            }
            return true;
        });
    }

    @Override
    public boolean disableTableAsync(String tableName) {
        return disableTable(tableName, true);
    }

    @Override
    public boolean isTableEnabled(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            return admin.isTableEnabled(TableName.valueOf(tableName));
        });
    }

    @Override
    public boolean isTableDisabled(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            return admin.isTableDisabled(TableName.valueOf(tableName));
        });
    }

    @Override
    public boolean isTableAvailable(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            return admin.isTableAvailable(TableName.valueOf(tableName));
        });
    }

    @Override
    public boolean addFamilyAsync(String tableName, ColumnFamilyDesc familyDesc) {
        return addFamily(tableName, familyDesc, true);
    }

    @Override
    public boolean deleteFamilyAsync(String tableName, String familyName) {
        return deleteFamily(tableName, familyName, true);
    }

    @Override
    public boolean modifyFamilyAsync(String tableName, ColumnFamilyDesc familyDesc) {
        return modifyFamily(tableName, familyDesc, true);
    }

    @Override
    public boolean enableReplicationScopeAsync(String tableName, List<String> families) {
        return enableReplicationScope(tableName, families, true);
    }

    @Override
    public boolean disableReplicationScopeAsync(String tableName, List<String> families) {
        return disableReplicationScope(tableName, families, true);
    }

    @Override
    public boolean flush(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            admin.flush(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean compact(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            admin.compact(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean majorCompact(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            admin.majorCompact(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean createNamespaceAsync(NamespaceDesc namespaceDesc) {
        return createNamespace(namespaceDesc, true);
    }

    @Override
    public boolean namespaceIsExists(String namespaceName) {
        final List<String> namespaces = listNamespaceNames();
        if (namespaces == null || namespaces.isEmpty()) {
            return false;
        }
        return namespaces.contains(namespaceName);
    }

    @Override
    public boolean deleteNamespaceAsync(String namespaceName) {
        return deleteNamespace(namespaceName, true);
    }

    @Override
    public NamespaceDesc getNamespaceDesc(String namespaceName) {
        return this.execute(admin -> {
            final NamespaceDescriptor namespaceDescriptor = admin.getNamespaceDescriptor(namespaceName);
            NamespaceDesc namespaceDesc = new NamespaceDesc();
            namespaceDesc.setNamespaceName(namespaceDescriptor.getName());
            namespaceDesc.setNamespaceProps(namespaceDescriptor.getConfiguration());
            return namespaceDesc;
        });
    }

    @Override
    public List<NamespaceDesc> listNamespaceDesc() {
        return this.execute(admin -> Arrays.stream(admin.listNamespaceDescriptors()).map(namespaceDescriptor -> {
            NamespaceDesc namespaceDesc = new NamespaceDesc();
            namespaceDesc.setNamespaceName(namespaceDescriptor.getName());
            namespaceDesc.setNamespaceProps(namespaceDescriptor.getConfiguration());
            return namespaceDesc;
        }).collect(Collectors.toList()));
    }

    @Override
    public List<String> listNamespaceNames() {
        return listNamespaceDesc().stream().map(NamespaceDesc::getNamespaceName).collect(Collectors.toList());

    }

    @Override
    public long getLastMajorCompactionTimestamp(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            return admin.getLastMajorCompactionTimestamp(TableName.valueOf(tableName));
        });
    }

    @Override
    public long getLastMajorCompactionTimestampForRegion(String regionName) {
        return this.execute(admin -> admin.getLastMajorCompactionTimestampForRegion(Bytes.toBytes(regionName)));
    }

    @Override
    public boolean snapshotAsync(SnapshotDesc snapshotDesc) {
        return snapshot(snapshotDesc, true);
    }

    @Override
    public boolean restoreSnapshotAsync(String snapshotName) {
        return restoreSnapshot(snapshotName, true);
    }

    @Override
    public boolean cloneSnapshotAsync(String snapshotName, String tableName) {
        return cloneSnapshot(snapshotName, tableName, true);
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

    protected abstract <T> T convertToTableDescriptor(final HTableDesc tableDesc);

    protected abstract <T> List<HTableDesc> convertToTableDescList(List<T> tList);

    protected abstract <T> HTableDesc convertToTableDesc(T t);

    protected abstract <CF> List<ColumnFamilyDesc> convertToColumnFamilyDescList(List<CF> fList);

    protected abstract <CF> CF convertToColumnFamilyDescriptor(ColumnFamilyDesc familyDesc);

    protected void tableIsNotExistsThrowError(Admin admin, String tableName) throws IOException {
        if (!admin.tableExists(TableName.valueOf(tableName))) {
            throw new HBaseOperationsException("表[" + tableName + "]不存在");
        }
    }

    protected void tableIsExistsThrowError(Admin admin, String tableName) throws IOException {
        if (admin.tableExists(TableName.valueOf(tableName))) {
            throw new HBaseOperationsException("表[" + tableName + "]已经存在");
        }
    }

    protected void tableIsNotDisableThrowError(Admin admin, String tableName) throws IOException {
        if (!admin.isTableDisabled(TableName.valueOf(tableName))) {
            throw new HBaseOperationsException("非禁用状态的表不可被操作");
        }
    }


}
