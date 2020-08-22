package com.leo.hbase.sdk.core;

import com.leo.hbase.sdk.core.exception.HBaseOperationsException;
import com.leo.hbase.sdk.core.model.HFamilyBuilder;
import com.leo.hbase.sdk.core.model.HTableModel;
import com.leo.hbase.sdk.core.model.SnapshotModel;
import com.leo.hbase.sdk.core.util.StrUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

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
    public boolean deleteNamespace(String namespace) {
        return this.execute(admin -> {
            admin.deleteNamespace(namespace);
            return true;
        });
    }

    @Override
    public boolean tableIsExists(String tableName) {
        return this.execute(admin -> admin.tableExists(TableName.valueOf(tableName)));
    }


    @Override
    public boolean createTable(HTableModel hTableModel) {
        HTableDescriptor tableDescriptor = getHTableDescriptor(hTableModel);
        return this.execute(admin -> {
            admin.createTable(tableDescriptor);
            return true;
        });
    }

    @Override
    public boolean createTable(HTableModel hTableModel, String[] splitKeys) {
        if (splitKeys == null || splitKeys.length == 0) {
            throw new HBaseOperationsException("the pre-split keys is not empty.");
        }
        byte[][] splitBytesKeys = Arrays.stream(splitKeys).map(Bytes::toBytes).toArray(byte[][]::new);
        HTableDescriptor tableDescriptor = getHTableDescriptor(hTableModel);
        return this.execute(admin -> {
            admin.createTable(tableDescriptor, splitBytesKeys);
            return true;
        });
    }

    @Override
    public boolean createTable(HTableModel hTableModel, String startKey, String endKey, int numRegions) {
        if (StrUtil.isBlank(startKey)) {
            throw new HBaseOperationsException("Start key must not be empty.");
        }
        if (StrUtil.isBlank(endKey)) {
            throw new HBaseOperationsException("End key must not be empty.");
        }
        if (numRegions < 3) {
            throw new HBaseOperationsException("Must create at least three regions");
        } else if (Bytes.compareTo(Bytes.toBytes(startKey), Bytes.toBytes(endKey)) >= 0) {
            throw new HBaseOperationsException("Start key must be smaller than end key");
        }

        byte[][] splitKeys = Bytes.split(Bytes.toBytes(startKey), Bytes.toBytes(endKey), numRegions - 3);
        if (splitKeys == null || splitKeys.length != numRegions - 1) {
            throw new HBaseOperationsException("Unable to split key range into enough regions");
        }

        HTableDescriptor tableDescriptor = getHTableDescriptor(hTableModel);
        return this.execute(admin -> {
            admin.createTable(tableDescriptor, splitKeys);
            return true;
        });
    }

    @Override
    public String getTableDescriptor(String tableName) {
        checkTableNotExists(tableName);

        return this.execute(admin -> admin.getTableDescriptor(TableName.valueOf(tableName)).toString());
    }

    @Override
    public boolean tableIsDisabled(String tableName) {
        checkTableNotExists(tableName);

        return this.execute(admin -> admin.isTableDisabled(TableName.valueOf(tableName)));
    }

    @Override
    public boolean disableTable(String tableName) {
        checkTableNotExists(tableName);

        return this.execute(admin -> {
            if (admin.isTableDisabled(TableName.valueOf(tableName))) {
                return true;
            } else {
                admin.disableTable(TableName.valueOf(tableName));
            }
            return admin.isTableDisabled(TableName.valueOf(tableName));
        });
    }

    @Override
    public boolean disableTableAsync(String tableName) {
        checkTableNotExists(tableName);

        return this.execute(admin -> {
            if (admin.isTableDisabled(TableName.valueOf(tableName))) {
                return true;
            } else {
                admin.disableTableAsync(TableName.valueOf(tableName));
                return true;
            }
        });
    }

    @Override
    public boolean tableIsEnabled(String tableName) {
        checkTableNotExists(tableName);

        return this.execute(admin -> admin.isTableEnabled(TableName.valueOf(tableName)));
    }

    @Override
    public boolean enableTable(String tableName) {
        checkTableNotExists(tableName);

        return this.execute(admin -> {
            if (admin.isTableEnabled(TableName.valueOf(tableName))) {
                return true;
            } else {
                admin.enableTable(TableName.valueOf(tableName));
                return admin.isTableEnabled(TableName.valueOf(tableName));
            }
        });
    }

    @Override
    public boolean enableTableAsync(String tableName) {
        checkTableNotExists(tableName);

        return this.execute(admin -> {
            if (admin.isTableEnabled(TableName.valueOf(tableName))) {
                return true;
            } else {
                admin.enableTableAsync(TableName.valueOf(tableName));
                return true;
            }
        });
    }

    @Override
    public boolean deleteTable(String tableName) {
        checkTableNotExists(tableName);

        return this.execute(admin -> {
            boolean isEnabled = admin.isTableEnabled(TableName.valueOf(tableName));
            if (isEnabled) {
                throw new HBaseOperationsException(tableName + " is not disabled.");
            }
            admin.deleteTable(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean enableReplicationScope(String tableName, String... families) {
        return alterTableReplicationScope(tableName, REPLICATION_SCOPE_1, families);
    }

    @Override
    public boolean disableReplicationScope(String tableName, String... families) {
        return alterTableReplicationScope(tableName, REPLICATION_SCOPE_0, families);
    }

    @Override
    public boolean addFamily(String tableName, HFamilyBuilder... families) {
        checkTableNotExists(tableName);

        if (families == null || families.length == 0) {
            throw new HBaseOperationsException("the data model of families of table are not empty.");
        }

        return this.execute(admin -> {
            HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            for (HFamilyBuilder familyBuilder : families) {
                boolean familyExists = tableDescriptor.hasFamily(Bytes.toBytes(familyBuilder.getFamilyName()));
                if (familyExists) {
                    throw new HBaseOperationsException(familyBuilder.getFamilyName() + " is exists.");
                } else {
                    tableDescriptor.addFamily(getHColumnDescriptor(familyBuilder));
                }
            }
            admin.modifyTable(TableName.valueOf(tableName), tableDescriptor);

            return true;
        });
    }

    @Override
    public boolean renameTable(String oriTableName, String targetTableName, boolean overwrite) {
        checkTableNotExists(oriTableName);

        if (StrUtil.isBlank(targetTableName)) {
            throw new HBaseOperationsException("target table name is not empty.");
        }
        return this.execute(admin -> {
            if (admin.tableExists(TableName.valueOf(targetTableName))) {
                throw new HBaseOperationsException("target table " + targetTableName + " is exists.");
            }
            TableName oldTableName = TableName.valueOf(oriTableName);
            if (admin.isTableEnabled(oldTableName)) {
                admin.disableTable(oldTableName);
            }
            final String tempSnapShotName = oriTableName.replace(":", "_") + "_SNAPSHOT_RENAME_" + UUID.randomUUID().toString().replaceAll("-", "");
            admin.snapshot(tempSnapShotName, oldTableName);
            admin.cloneSnapshot(tempSnapShotName, TableName.valueOf(targetTableName));
            admin.deleteSnapshot(tempSnapShotName);
            if (overwrite) {
                admin.deleteTable(oldTableName);
            }
            return true;
        });
    }

    @Override
    public List<SnapshotModel> listSnapshots() {
        return this.execute(admin -> admin.listSnapshots().stream().map(snapshotDescription -> new SnapshotModel(snapshotDescription.getTable(), snapshotDescription.getName(),
                snapshotDescription.getCreationTime())).collect(Collectors.toList()));
    }

    @Override
    public List<SnapshotModel> listSnapshots(String tableName) {
        checkTableNotExists(tableName);

        return this.execute(admin -> admin.listSnapshots().stream().filter(snapshotDescription -> snapshotDescription.getTable().equals(tableName))
                .map(snapshotDescription -> new SnapshotModel(snapshotDescription.getTable(), snapshotDescription.getName(), snapshotDescription.getCreationTime())).collect(Collectors.toList()));
    }

    @Override
    public boolean createSnapshot(String tableName, String snapshotName) {
        checkTableNotExists(tableName);

        return this.execute(admin -> {
            admin.snapshot(snapshotName, TableName.valueOf(tableName));
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

    private boolean alterTableReplicationScope(String tableName, int replicationScope, String... families) {
        checkTableNotExists(tableName);

        if (families == null || families.length == 0) {
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


    private void checkTableNotExists(String tableName) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseOperationsException("table name is not empty.");
        }

        boolean tableIsCreated = this.tableIsExists(tableName);
        if (!tableIsCreated) {
            throw new HBaseOperationsException(tableName + " is not exists.");
        }
    }

    private HTableDescriptor getHTableDescriptor(HTableModel tableModel) {
        if (StrUtil.isBlank(tableModel.getTableName())) {
            throw new HBaseOperationsException("table name is not empty.");
        }

        boolean tableIsCreated = this.tableIsExists(tableModel.getTableName());
        if (tableIsCreated) {
            throw new HBaseOperationsException(tableModel.getTableName() + " is exists.");
        }
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableModel.getTableName()));
        tableModel.gethFamilies().forEach(familyBuilder -> tableDescriptor.addFamily(getHColumnDescriptor(familyBuilder)));
        return tableDescriptor;
    }

    private HColumnDescriptor getHColumnDescriptor(HFamilyBuilder familyBuilder) {
        HColumnDescriptor hd = new HColumnDescriptor(Bytes.toBytes(familyBuilder.getFamilyName()));
        hd.setMaxVersions(familyBuilder.getMaxVersions());
        hd.setMinVersions(familyBuilder.getMinVersions());
        hd.setTimeToLive(familyBuilder.getTimeToLive());
        if (StrUtil.isNotBlank(familyBuilder.getCompressionType())) {
            hd.setCompressionType(Compression.Algorithm.valueOf(familyBuilder.getCompressionType()));
        }
        return hd;
    }
}
