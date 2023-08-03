package com.github.CCweixiao.hbase.sdk.adapter;

import com.github.CCweixiao.hbase.sdk.common.model.NamespaceDesc;
import com.github.CCweixiao.hbase.sdk.common.model.SnapshotDesc;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.yetus.audience.InterfaceAudience;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/11/14 2:26 下午
 */
@InterfaceAudience.Private
public abstract class AbstractHBaseAdminAdapter extends AbstractHBaseBaseAdapter implements IHBaseAdminAdapter {
    public AbstractHBaseAdminAdapter(Configuration configuration) {
        super(configuration);
    }

    @Override
    public boolean tableExists(String tableName) {
        return this.execute(admin -> admin.tableExists(TableName.valueOf(tableName)));
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
            if (StringUtil.isBlank(regex)) {
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
    public boolean modifyTableConfigurationAsync(final String tableName, Map<String, String> configuration) {
        return modifyTableConfiguration(tableName, configuration, true);
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
    public boolean deleteFamilyAsync(String tableName, String familyName) {
        return deleteFamily(tableName, familyName, true);
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

    protected void tableIsNotExistsThrowError(Admin admin, String tableName) throws IOException {
        tableIsNotExistsThrowError(!admin.tableExists(TableName.valueOf(tableName)),
                String.format("The table %s is not exists!", tableName));
    }

    protected void tableIsExistsThrowError(Admin admin, String tableName) throws IOException {
        tableIsExistsThrowError(admin.tableExists(TableName.valueOf(tableName)),
                String.format("The table %s is exists!", tableName));
    }

    protected void tableIsNotDisableThrowError(Admin admin, String tableName) throws IOException {
        tableIsNotDisableThrowError(admin.isTableDisabled(TableName.valueOf(tableName)),
                String.format("The table %s is not disabled!", tableName));
    }


}
