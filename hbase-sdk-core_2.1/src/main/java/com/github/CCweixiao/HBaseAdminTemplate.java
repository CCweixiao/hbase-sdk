package com.github.CCweixiao;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.hbtop.HBaseMetricOperations;
import com.github.CCweixiao.hbtop.Record;
import com.github.CCweixiao.hbtop.RecordFilter;
import com.github.CCweixiao.hbtop.Summary;
import com.github.CCweixiao.hbtop.field.Field;
import com.github.CCweixiao.hbtop.field.FieldValue;
import com.github.CCweixiao.hbtop.field.FieldValueType;
import com.github.CCweixiao.hbtop.mode.Mode;
import com.github.CCweixiao.model.*;
import com.github.CCweixiao.util.*;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/9/25 11:11 下午
 */
public class HBaseAdminTemplate extends AbstractHBaseAdminTemplate implements HBaseMetricOperations {
    public static final Logger LOG = LoggerFactory.getLogger(HBaseAdminTemplate.class);
    public static final Pattern REGION_COMPILE = Pattern.compile("\\.([\\w]+)\\.");

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
    public List<HTableDesc> listTableDesc() {
        return listTableDesc("", false);
    }

    @Override
    public List<HTableDesc> listTableDesc(boolean includeSysTables) {
        return listTableDesc("", includeSysTables);
    }

    @Override
    public List<HTableDesc> listTableDesc(String regex, boolean includeSysTables) {
        return this.execute(admin -> {
            List<TableDescriptor> tableDescriptors;
            if (StrUtil.isBlank(regex)) {
                tableDescriptors = admin.listTableDescriptors(null, includeSysTables);
            } else {
                tableDescriptors = admin.listTableDescriptors(Pattern.compile(regex), includeSysTables);
            }
            if (tableDescriptors == null || tableDescriptors.isEmpty()) {
                return new ArrayList<>();
            }

            return parseHTableDescriptorsToHTableDescList(tableDescriptors);
        });
    }

    @Override
    public List<HTableDesc> listTableDescByNamespace(String namespaceName) {
        return this.execute(admin -> {
            final List<TableDescriptor> tableDescriptors = admin.listTableDescriptorsByNamespace(Bytes.toBytes(namespaceName));
            if (tableDescriptors == null || tableDescriptors.isEmpty()) {
                return new ArrayList<>();
            }
            return parseHTableDescriptorsToHTableDescList(tableDescriptors);
        });
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
    public List<ColumnFamilyDesc> listFamilyDesc(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);
            TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));
            final ColumnFamilyDescriptor[] families = tableDescriptor.getColumnFamilies();
            return parseColumnFamilyDescriptorToColumnFamilyDescList(families);
        });
    }

    @Override
    public HTableDesc getTableDesc(String tableName) {
        return this.execute(admin -> {
            TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));
            return parseHTableDescriptorToHTableDesc(tableDescriptor);
        });
    }

    @Override
    public boolean createTable(HTableDesc tableDesc) {
        String tableName = tableDesc.getFullTableName();
        return this.execute(admin -> {
            tableIsExistsError(admin, tableName);
            TableDescriptor tableDescriptor = parseHTableDescToTableDescriptor(tableDesc);
            admin.createTable(tableDescriptor);
            return true;
        });
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync) {
        String tableName = tableDesc.getFullTableName();

        return this.execute(admin -> {
            tableIsExistsError(admin, tableName);
            TableDescriptor tableDescriptor = parseHTableDescToTableDescriptor(tableDesc);
            boolean preSplit = (StrUtil.isNotBlank(startKey) && StrUtil.isNotBlank(endKey) && numRegions > 0);

            if (preSplit) {
                final byte[] startKeyBytes = Bytes.toBytes(startKey);
                final byte[] endKeyBytes = Bytes.toBytes(endKey);

                if (numRegions < 3) {
                    throw new HBaseOperationsException("请至少指定3个分区");
                }

                if (Bytes.compareTo(startKeyBytes, endKeyBytes) >= 0) {
                    throw new HBaseOperationsException("预分区开始的key必须要小于预分区结束的key");
                }

                if (numRegions == 3) {
                    if (isAsync) {
                        admin.createTableAsync(tableDescriptor, new byte[][]{startKeyBytes, endKeyBytes});
                    } else {
                        admin.createTable(tableDescriptor, new byte[][]{startKeyBytes, endKeyBytes});
                    }
                    return true;
                }
                byte[][] splitKeys = Bytes.split(startKeyBytes, endKeyBytes, numRegions - 3);

                if (splitKeys == null || splitKeys.length != numRegions - 1) {
                    throw new HBaseOperationsException("无法将预分区的起止键范围分割成足够的region");
                }

                if (isAsync) {
                    admin.createTableAsync(tableDescriptor, splitKeys);
                } else {
                    admin.createTable(tableDescriptor, splitKeys);
                }

            } else {
                admin.createTable(tableDescriptor);
            }
            return true;
        });
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, String[] splitKeys, boolean isAsync) {
        String tableName = tableDesc.getFullTableName();
        return this.execute(admin -> {
            tableIsExistsError(admin, tableName);
            TableDescriptor tableDescriptor = parseHTableDescToTableDescriptor(tableDesc);
            boolean preSplit = splitKeys != null && splitKeys.length > 0;

            if (preSplit) {
                if (isAsync) {
                    admin.createTableAsync(tableDescriptor, SplitKeyUtil.getSplitKeyBytes(splitKeys));
                } else {
                    admin.createTable(tableDescriptor, SplitKeyUtil.getSplitKeyBytes(splitKeys));
                }
            } else {
                admin.createTable(tableDescriptor);
            }
            return true;
        });
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, SplitGoEnum splitGoEnum, int numRegions, boolean isAsync) {
        String tableName = tableDesc.getFullTableName();
        return this.execute(admin -> {
            tableIsExistsError(admin, tableName);
            TableDescriptor tableDescriptor = parseHTableDescToTableDescriptor(tableDesc);
            boolean preSplit = splitGoEnum != null && numRegions > 0;

            if (preSplit) {
                byte[][] splits;
                if (splitGoEnum == SplitGoEnum.HEX_STRING_SPLIT) {
                    splits = new RegionSplitter.HexStringSplit().split(numRegions);
                } else if (splitGoEnum == SplitGoEnum.DECIMAL_STRING_SPLIT) {
                    splits = new RegionSplitter.DecimalStringSplit().split(numRegions);
                } else if (splitGoEnum == SplitGoEnum.UNIFORM_SPLIT) {
                    splits = new RegionSplitter.UniformSplit().split(numRegions);
                } else {
                    throw new HBaseOperationsException("暂不支持的预分区策略:" + splitGoEnum.getSplitGo());
                }
                if (isAsync) {
                    admin.createTableAsync(tableDescriptor, splits);
                } else {
                    admin.createTable(tableDescriptor, splits);
                }
            } else {
                admin.createTable(tableDescriptor);
            }
            return true;
        });
    }

    @Override
    public boolean modifyTableProps(final String tableName, Map<String, String> props, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);
            final TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableDescriptor);

            if (props != null && !props.isEmpty()) {
                props.forEach(tableDescriptorBuilder::setValue);
                if (isAsync) {
                    admin.modifyTableAsync(tableDescriptorBuilder.build());
                } else {
                    admin.modifyTable(tableDescriptorBuilder.build());
                }
                return true;
            }
            return true;
        });
    }

    @Override
    public boolean modifyTablePropsAsync(final String tableName, Map<String, String> props) {
        return modifyTableProps(tableName, props, true);
    }

    @Override
    public boolean renameTable(String oldTableName, String newTableName, boolean deleteOldTable, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, oldTableName);
            tableIsExistsError(admin, newTableName);
            if (admin.isTableEnabled(TableName.valueOf(oldTableName))) {
                throw new HBaseOperationsException("表[" + oldTableName + "]重命名之前请先禁用");
            }
            final String tempSnapShotName = oldTableName.replace(":", "_") +
                    "_SNAPSHOT_RENAME_" + UUID.randomUUID().toString().replaceAll("-", "");
            if (isAsync) {
                //TODO 完善这里的异步逻辑
                SnapshotDescription snapshotDescription = new SnapshotDescription(tempSnapShotName, TableName.valueOf(oldTableName));
                admin.snapshotAsync(snapshotDescription);
                admin.cloneSnapshotAsync(tempSnapShotName, TableName.valueOf(newTableName));
                admin.deleteSnapshot(tempSnapShotName);
            } else {
                admin.snapshot(tempSnapShotName, TableName.valueOf(oldTableName));
                admin.cloneSnapshot(tempSnapShotName, TableName.valueOf(newTableName));
                admin.deleteSnapshot(tempSnapShotName);
            }

            if (deleteOldTable) {
                admin.deleteTable(TableName.valueOf(oldTableName));
            } else {
                admin.enableTableAsync(TableName.valueOf(oldTableName));
            }
            return true;
        });
    }

    @Override
    public boolean deleteTable(String tableName, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);
            tableIsNotDisableError(admin, tableName);

            if (isAsync) {
                admin.deleteTableAsync(TableName.valueOf(tableName));
            } else {
                admin.deleteTable(TableName.valueOf(tableName));
            }
            return true;
        });
    }

    @Override
    public boolean deleteTableAsync(String tableName) {
        return deleteTable(tableName, true);
    }

    @Override
    public boolean truncateTable(String tableName, boolean preserveSplits, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);
            tableIsNotDisableError(admin, tableName);
            if (isAsync) {
                admin.truncateTableAsync(TableName.valueOf(tableName), preserveSplits);
            } else {
                admin.truncateTable(TableName.valueOf(tableName), preserveSplits);
            }
            return true;
        });
    }

    @Override
    public boolean truncateTableAsync(String tableName, boolean preserveSplits) {
        return truncateTable(tableName, preserveSplits, true);
    }

    @Override
    public boolean enableTable(String tableName, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);

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
            tableIsNotExistsError(admin, tableName);

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
            tableIsNotExistsError(admin, tableName);
            return admin.isTableEnabled(TableName.valueOf(tableName));
        });
    }

    @Override
    public boolean isTableDisabled(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);
            return admin.isTableDisabled(TableName.valueOf(tableName));
        });
    }

    @Override
    public boolean isTableAvailable(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);
            return admin.isTableAvailable(TableName.valueOf(tableName));
        });
    }

    @Override
    public boolean addFamily(String tableName, ColumnFamilyDesc familyDesc, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);
            final TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));

            if (tableDescriptor.hasColumnFamily(Bytes.toBytes(familyDesc.getFamilyName()))) {
                throw new HBaseOperationsException("列簇[" + familyDesc.getFamilyName() + "]在表[" + tableName + "]中已经存在");
            }
            ColumnFamilyDescriptor columnDescriptor = parseColumnFamilyDescToColumnFamilyDescriptor(familyDesc);
            if (isAsync) {
                admin.addColumnFamilyAsync(TableName.valueOf(tableName), columnDescriptor);
            } else {
                admin.addColumnFamily(TableName.valueOf(tableName), columnDescriptor);
            }
            return true;
        });
    }

    @Override
    public boolean addFamilyAsync(String tableName, ColumnFamilyDesc familyDesc) {
        return addFamily(tableName, familyDesc, true);
    }

    @Override
    public boolean deleteFamily(String tableName, String familyName, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);

            final TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));
            if (!tableDescriptor.hasColumnFamily(Bytes.toBytes(familyName))) {
                throw new HBaseOperationsException("列簇[" + familyName + "]在表[" + tableName + "]中不存在");
            }
            if (isAsync) {
                admin.deleteColumnFamilyAsync(TableName.valueOf(tableName), Bytes.toBytes(familyName));
            } else {
                admin.deleteColumnFamily(TableName.valueOf(tableName), Bytes.toBytes(familyName));
            }
            return true;
        });
    }

    @Override
    public boolean deleteFamilyAsync(String tableName, String familyName) {
        return deleteFamily(tableName, familyName, true);
    }

    @Override
    public boolean modifyFamily(String tableName, ColumnFamilyDesc familyDesc, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);
            final TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));

            if (!tableDescriptor.hasColumnFamily(Bytes.toBytes(familyDesc.getFamilyName()))) {
                throw new HBaseOperationsException("待修改列簇[" + familyDesc.getFamilyName() + "]不存在");
            }

            ColumnFamilyDescriptor columnDescriptor = tableDescriptor.getColumnFamily(Bytes.toBytes(familyDesc.getFamilyName()));
            ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(columnDescriptor);
            boolean change = false;

            if (familyDesc.getMinVersions() != null && columnDescriptor.getMinVersions() != familyDesc.getMinVersions()) {
                columnFamilyDescriptorBuilder.setMinVersions(familyDesc.getMinVersions());
                change = true;
            }
            if (familyDesc.getVersions() != null && columnDescriptor.getMaxVersions() != familyDesc.getVersions()) {
                columnFamilyDescriptorBuilder.setMaxVersions(familyDesc.getVersions());
                change = true;
            }
            if (familyDesc.getTimeToLive() != null && columnDescriptor.getTimeToLive() != familyDesc.getTimeToLive()) {
                columnFamilyDescriptorBuilder.setTimeToLive(familyDesc.getTimeToLive());
                change = true;
            }
            if (familyDesc.getCompressionType() != null && !columnDescriptor.getCompressionType().getName().equalsIgnoreCase(familyDesc.getCompressionType())) {
                columnFamilyDescriptorBuilder.setCompressionType(Compression.Algorithm.valueOf(familyDesc.getCompressionType()));
                change = true;
            }
            if (familyDesc.getReplicationScope() != null && columnDescriptor.getScope() != familyDesc.getReplicationScope()) {
                columnFamilyDescriptorBuilder.setScope(familyDesc.getReplicationScope());
                change = true;
            }
            if (familyDesc.getBlockSize() != null && columnDescriptor.getBlocksize() != familyDesc.getBlockSize()) {
                columnFamilyDescriptorBuilder.setBlocksize(familyDesc.getBlockSize());
                change = true;
            }
            if (familyDesc.isBlockCache() != null && columnDescriptor.isBlockCacheEnabled() != familyDesc.isBlockCache()) {
                columnFamilyDescriptorBuilder.setBlockCacheEnabled(familyDesc.isBlockCache());
                change = true;
            }
            if (familyDesc.isInMemory() != null && columnDescriptor.isInMemory() != familyDesc.isInMemory()) {
                columnFamilyDescriptorBuilder.setInMemory(familyDesc.isInMemory());
                change = true;
            }
            if (change) {
                if (isAsync) {
                    admin.modifyColumnFamilyAsync(TableName.valueOf(tableName), columnFamilyDescriptorBuilder.build());
                } else {
                    admin.modifyColumnFamily(TableName.valueOf(tableName), columnFamilyDescriptorBuilder.build());
                }
            }
            return true;
        });
    }

    @Override
    public boolean modifyFamilyAsync(String tableName, ColumnFamilyDesc familyDesc) {
        return modifyFamily(tableName, familyDesc, true);
    }

    @Override
    public boolean enableReplicationScope(String tableName, List<String> families, boolean isAsync) {
        return modifyTableReplicationScope(tableName, HMHBaseConstant.ENABLE_REPLICATION_SCOPE, families, isAsync);
    }

    @Override
    public boolean enableReplicationScopeAsync(String tableName, List<String> families) {
        return enableReplicationScope(tableName, families, true);
    }

    @Override
    public boolean disableReplicationScope(String tableName, List<String> families, boolean isAsync) {
        return modifyTableReplicationScope(tableName, HMHBaseConstant.DISABLE_REPLICATION_SCOPE, families, isAsync);
    }

    @Override
    public boolean disableReplicationScopeAsync(String tableName, List<String> families) {
        return disableReplicationScope(tableName, families, true);
    }

    @Override
    public boolean flush(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);
            admin.flush(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean compact(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);
            admin.compact(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean majorCompact(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);
            admin.majorCompact(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean createNamespace(NamespaceDesc namespaceDesc, boolean isAsync) {
        if (namespaceIsExists(namespaceDesc.getNamespaceName())) {
            throw new HBaseOperationsException("命名空间[" + namespaceDesc.getNamespaceName() + "]已经存在");
        }

        return this.execute(admin -> {
            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespaceDesc.getNamespaceName()).build();
            final Map<String, String> namespaceProps = namespaceDesc.getNamespaceProps();
            if (namespaceProps != null && !namespaceProps.isEmpty()) {
                namespaceProps.forEach(namespaceDescriptor::setConfiguration);
            }
            if (isAsync) {
                admin.createNamespaceAsync(namespaceDescriptor);
            } else {
                admin.createNamespace(namespaceDescriptor);
            }
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
    public boolean deleteNamespace(String namespaceName, boolean isAsync) {
        return this.execute(admin -> {
            final List<TableDescriptor> tableDescriptors = admin.listTableDescriptorsByNamespace(Bytes.toBytes(namespaceName));
            if (tableDescriptors != null && !tableDescriptors.isEmpty()) {
                throw new HBaseOperationsException("命名空间[" + namespaceName + "]下存在表，不能被删除");
            }
            if (isAsync) {
                admin.deleteNamespaceAsync(namespaceName);
            } else {
                admin.deleteNamespace(namespaceName);
            }
            return true;
        });
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
            tableIsNotExistsError(admin, tableName);
            return admin.getLastMajorCompactionTimestamp(TableName.valueOf(tableName));
        });
    }

    @Override
    public long getLastMajorCompactionTimestampForRegion(String regionName) {
        return this.execute(admin -> admin.getLastMajorCompactionTimestampForRegion(Bytes.toBytes(regionName)));
    }

    @Override
    public List<SnapshotDesc> listSnapshots() {
        return this.execute(admin -> {
            final List<SnapshotDescription> listSnapshots = admin.listSnapshots();
            if (listSnapshots == null || listSnapshots.isEmpty()) {
                return new ArrayList<>();
            } else {
                return listSnapshots.stream().map(snapshotDescription -> {
                    SnapshotDesc snapshotDesc = new SnapshotDesc();
                    snapshotDesc.setSnapshotName(snapshotDescription.getName());
                    snapshotDesc.setTableName(snapshotDescription.getTableName().getNameAsString());
                    snapshotDesc.setCreateTime(snapshotDescription.getCreationTime());
                    return snapshotDesc;
                }).collect(Collectors.toList());
            }
        });
    }

    @Override
    public boolean snapshot(SnapshotDesc snapshotDesc, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsError(admin, snapshotDesc.getTableName());
            if (isAsync) {
                SnapshotDescription snapshotDescription = new SnapshotDescription(snapshotDesc.getSnapshotName(),
                        TableName.valueOf(snapshotDesc.getTableName()));
                admin.snapshotAsync(snapshotDescription);
            } else {
                admin.snapshot(snapshotDesc.getSnapshotName(), TableName.valueOf(snapshotDesc.getTableName()));
            }
            return true;
        });
    }

    @Override
    public boolean snapshotAsync(SnapshotDesc snapshotDesc) {
        return snapshot(snapshotDesc, true);
    }

    @Override
    public boolean restoreSnapshot(String snapshotName, boolean isAsync) {
        return this.execute(admin -> {
            if (isAsync) {
                admin.restoreSnapshotAsync(snapshotName);
            } else {
                admin.restoreSnapshot(snapshotName);
            }
            return true;
        });
    }

    @Override
    public boolean restoreSnapshotAsync(String snapshotName) {
        return restoreSnapshot(snapshotName, true);
    }

    @Override
    public boolean cloneSnapshot(String snapshotName, String tableName, boolean isAsync) {
        return this.execute(admin -> {
            tableIsExistsError(admin, tableName);
            if (isAsync) {
                admin.cloneSnapshotAsync(snapshotName, TableName.valueOf(tableName));
            } else {
                admin.cloneSnapshot(snapshotName, TableName.valueOf(tableName));
            }
            return true;
        });
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

    @Override
    public boolean mergeRegions(final byte[] firstRegion, final byte[] secondRegion, boolean force) {
        return this.execute(admin -> {
            admin.mergeRegionsAsync(firstRegion, secondRegion, force);
            return true;
        });
    }

    @Override
    public boolean mergeMultipleRegions(byte[][] regions, boolean force) {
        return this.execute(admin -> {
            int mergeRegionsNum = regions.length;
            if (mergeRegionsNum % 2 != 0) {
                mergeRegionsNum = mergeRegionsNum - 1;
            }
            for (int i = 0, j = 1; i < mergeRegionsNum - 1; i += 2, j += 2) {
                byte[] firstByteRegionName = regions[i];
                byte[] secondByteRegionName = regions[j];
                admin.mergeRegionsAsync(firstByteRegionName, secondByteRegionName, force);
            }
            return true;
        });
    }

    @Override
    public boolean mergeTableSmallRegions(String tableName, int limitRegionsNum, int limitRegionSize) {
        return this.execute(admin -> {
            final ClusterMetrics clusterStatus = admin.getClusterMetrics();
            final Map<ServerName, ServerMetrics> serverStatus = clusterStatus.getLiveServerMetrics();
            List<String> biggerRegions = new ArrayList<>();
            serverStatus.forEach(((serverName, serverMetrics) -> {
                serverMetrics.getRegionMetrics().forEach((region, regionMetric) -> {
                    final String regionStrName = regionMetric.getNameAsString();
                    if (regionStrName.startsWith(tableName)) {
                        Matcher regionNameMatcher = REGION_COMPILE.matcher(regionStrName);
                        String regionEncodedName = "";
                        if (regionNameMatcher.find()) {
                            regionEncodedName = regionNameMatcher.group(1);
                        }
                        if (StrUtil.isBlank(regionEncodedName)) {
                            throw new HBaseOperationsException("无法获取region[" + regionStrName + "]的加密名称");
                        }
                        final double regionStoreFileSize = regionMetric.getStoreFileSize().get();
                        if (regionStoreFileSize > limitRegionSize) {
                            biggerRegions.add(regionEncodedName);
                        }
                    }
                });
            }));
            List<RegionInfo> mergedRegions = new ArrayList<>();
            admin.getRegions(TableName.valueOf(tableName)).forEach(regionInfo -> {
                if (biggerRegions.contains(regionInfo.getEncodedName())) {
                    // 把大region设置成null，使之不会参与合并
                    mergedRegions.add(null);
                } else {
                    mergedRegions.add(regionInfo);
                }
            });
            List<RegionInfo> subMergedRegions;
            int mergedRegionsSize = mergedRegions.size();
            if (mergedRegionsSize > limitRegionsNum) {
                subMergedRegions = mergedRegions.subList(0, limitRegionsNum);
            } else {
                subMergedRegions = mergedRegions;
            }
            // 保证region相邻
            for (int i = 0, j = 1; i < subMergedRegions.size() - 1; i += 2, j += 2) {
                RegionInfo firstRegionInfo = subMergedRegions.get(i);
                RegionInfo secondRegionInfo = subMergedRegions.get(j);
                if (firstRegionInfo == null) {
                    continue;
                }
                if (secondRegionInfo == null) {
                    continue;
                }
                if (firstRegionInfo.isOffline() || firstRegionInfo.isSplit()) {
                    continue;
                }
                if (secondRegionInfo.isOffline() || secondRegionInfo.isSplit()) {
                    continue;
                }
                if (!RegionInfo.areAdjacent(firstRegionInfo, secondRegionInfo)) {
                    continue;
                }
                admin.mergeRegionsAsync(firstRegionInfo.getEncodedNameAsBytes(), secondRegionInfo.getEncodedNameAsBytes(), false);
                LOG.info("表:" + tableName + ", Region:" + firstRegionInfo.getEncodedName() + ", " + secondRegionInfo.getEncodedName() + " 异步合并指令运行成功");
            }
            return true;
        });
    }

    /**
     * 修改表和列簇的REPLICATION_SCOPE属性
     *
     * @param tableName        表名
     * @param replicationScope 0 或 1，1 表示禁用replication
     * @param families         列簇
     * @param isAsync          是否异步
     * @return 修改REPLICATION_SCOPE的熟悉是否成功
     */
    private boolean modifyTableReplicationScope(String tableName, int replicationScope, List<String> families, boolean isAsync) {
        if (families == null || families.isEmpty()) {
            throw new HBaseOperationsException("请传入一个或多个待修改的列簇");
        }

        return this.execute(admin -> {
            tableIsNotExistsError(admin, tableName);
            TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));
            for (String family : families) {
                if (!tableDescriptor.hasColumnFamily(Bytes.toBytes(family))) {
                    throw new HBaseOperationsException("待修改列簇[" + family + "]不存在");
                }
            }
            for (String family : families) {
                final ColumnFamilyDescriptor columnFamily = tableDescriptor.getColumnFamily(Bytes.toBytes(family));
                ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(columnFamily);
                final int currentScope = columnFamily.getScope();
                if (currentScope != replicationScope) {
                    columnFamilyDescriptorBuilder.setScope(replicationScope);
                    if (isAsync) {
                        admin.modifyColumnFamilyAsync(TableName.valueOf(tableName), columnFamilyDescriptorBuilder.build());
                    } else {
                        admin.modifyColumnFamily(TableName.valueOf(tableName), columnFamilyDescriptorBuilder.build());
                    }
                }
            }
            return true;
        });
    }

    @Override
    public Summary refreshSummary() {
        return this.execute(admin -> {
            final ClusterMetrics clusterMetrics = admin.getClusterMetrics();

            String currentTime = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());

            String version = clusterMetrics.getHBaseVersion();
            String clusterId = clusterMetrics.getClusterId();
            int liveServers = clusterMetrics.getLiveServerMetrics().size();
            int deadServers = clusterMetrics.getDeadServerNames().size();
            int regionCount = clusterMetrics.getRegionCount();
            int ritCount = clusterMetrics.getRegionStatesInTransition().size();
            int namespaceCount = admin.listNamespaceDescriptors().length;
            int tableCount = admin.listTableNames().length;
            int snapshotCount = admin.listSnapshots().size();

            double averageLoad = clusterMetrics.getAverageLoad();

            long aggregateRequestPerSecond = clusterMetrics.getLiveServerMetrics().values().stream()
                    .mapToLong(ServerMetrics::getRequestCountPerSecond).sum();

            Summary clusterSummary = new Summary(currentTime, version, clusterId, liveServers + deadServers,
                    liveServers, deadServers, namespaceCount, tableCount, snapshotCount, regionCount, ritCount, averageLoad, aggregateRequestPerSecond);
            List<String> liveServerNames = new ArrayList<>(liveServers);

            clusterMetrics.getLiveServerMetrics().forEach(((serverName, serverMetrics) -> liveServerNames.add(serverName.getServerName())));
            List<String> deadServerNames = clusterMetrics.getDeadServerNames().stream().map(ServerName::getServerName).collect(Collectors.toList());
            clusterSummary.setLiveServerNames(liveServerNames);
            clusterSummary.setDeadServerNames(deadServerNames);

            return clusterSummary;
        });
    }

    @Override
    public List<Record> refreshRecords(Mode currentMode, List<RecordFilter> filters, Field currentSortField, boolean ascendingSort) {
        return this.execute(admin -> {
            final ClusterMetrics clusterMetrics = admin.getClusterMetrics();
            List<Record> records = currentMode.getRecords(clusterMetrics);

            return filterAndSortRecords(records, filters, currentSortField, ascendingSort);

        });
    }


    @Override
    public List<HBaseTableRecord> refreshTableRecords(Field currentSortField, boolean ascendingSort) {
        return this.execute(admin -> {
            List<RecordFilter> recordFilters = new ArrayList<>();
            RecordFilter recordFilter = RecordFilter.newBuilder(Field.NAMESPACE, false)
                    .notEqual(new FieldValue(HMHBaseConstant.DEFAULT_SYS_TABLE_NAMESPACE, FieldValueType.STRING));
            recordFilters.add(recordFilter);
            final ClusterMetrics clusterMetrics = admin.getClusterMetrics();
            List<Record> records = Mode.TABLE.getRecords(clusterMetrics);
            records = filterAndSortRecords(records, recordFilters, currentSortField, ascendingSort);
            return records.stream().map(this::convertRecordToHBaseTableRecord).collect(Collectors.toList());
        });
    }

    private HBaseTableRecord convertRecordToHBaseTableRecord(Record record) {
        HBaseTableRecord tableRecord = new HBaseTableRecord();
        tableRecord.setNamespaceName(record.get(Field.NAMESPACE).asString());
        tableRecord.setTableName(record.get(Field.TABLE).asString());
        tableRecord.setStoreFileSizeTag(record.get(Field.STORE_FILE_SIZE).asString());
        tableRecord.setStoreFileSize(record.get(Field.STORE_FILE_SIZE).asSize().get(Size.Unit.GIGABYTE));
        tableRecord.setUncompressedStoreFileSizeTag(record.get(Field.UNCOMPRESSED_STORE_FILE_SIZE).asString());
        tableRecord.setUncompressedStoreFileSize(record.get(Field.UNCOMPRESSED_STORE_FILE_SIZE).asSize().get(Size.Unit.GIGABYTE));
        tableRecord.setNumStoreFiles(record.get(Field.NUM_STORE_FILES).asInt());
        tableRecord.setMemStoreSizeTag(record.get(Field.MEM_STORE_SIZE).asString());
        tableRecord.setMemStoreSize(record.get(Field.MEM_STORE_SIZE).asSize().get(Size.Unit.MEGABYTE));
        tableRecord.setRegionCount(record.get(Field.REGION_COUNT).asInt());
        return tableRecord;
    }

    @Override
    public HBaseTableRecord refreshTableRecord(String fullTableName) {
        return this.execute(admin -> {
            List<RecordFilter> recordFilters = createTableRecordFilters(fullTableName);
            final ClusterMetrics clusterMetrics = admin.getClusterMetrics();
            List<Record> records = Mode.TABLE.getRecords(clusterMetrics);
            if (records.isEmpty()) {
                return null;
            }
            records = filterAndSortRecords(records, recordFilters, null, true);
            Record record = records.get(0);
            return convertRecordToHBaseTableRecord(record);
        });
    }

    @Override
    public List<HBaseRegionRecord> refreshRegionRecords(String tableName, Field currentSortField, boolean ascendingSort) {
        return this.execute(admin -> {


            final ClusterMetrics clusterMetrics = admin.getClusterMetrics();
            List<Record> records = Mode.REGION.getRecords(clusterMetrics);
            List<RecordFilter> recordFilters = createTableRecordFilters(tableName);
            records = filterAndSortRecords(records, recordFilters, currentSortField, ascendingSort);

            List<HBaseRegionRecord> regionRecords = new ArrayList<>(records.size());

            for (int i = 0, j = 1; i < records.size(); i += 1, j += 1) {
                Record firstRegionRecord = records.get(i);
                String endKey;
                if (j > records.size() - 1) {
                    endKey = "";
                } else {
                    Record secondRegionRecord = records.get(j);
                    endKey = secondRegionRecord.getOrDefault(Field.START_KEY, new FieldValue("", FieldValueType.STRING)).asString();
                }
                HBaseRegionRecord regionRecord = new HBaseRegionRecord();

                regionRecord.setNamespaceName(firstRegionRecord.get(Field.NAMESPACE).asString());
                regionRecord.setTableName(firstRegionRecord.get(Field.TABLE).asString());
                regionRecord.setRegionName(firstRegionRecord.get(Field.REGION_NAME).asString());
                regionRecord.setEncodedRegionName(firstRegionRecord.get(Field.REGION).asString());

                regionRecord.setStartCode(DateUtil.parseTimestampToTimeStr(Long.parseLong(firstRegionRecord.get(Field.START_CODE).asString())));
                regionRecord.setRegionServer(firstRegionRecord.get(Field.REGION_SERVER).asString());

                regionRecord.setStoreFileSizeTag(firstRegionRecord.get(Field.STORE_FILE_SIZE).asString());
                regionRecord.setStoreFileSize(firstRegionRecord.get(Field.STORE_FILE_SIZE).asSize().get(Size.Unit.GIGABYTE));
                regionRecord.setUncompressedStoreFileSizeTag(firstRegionRecord.get(Field.UNCOMPRESSED_STORE_FILE_SIZE).asString());
                regionRecord.setUncompressedStoreFileSize(firstRegionRecord.get(Field.UNCOMPRESSED_STORE_FILE_SIZE).asSize().get(Size.Unit.GIGABYTE));
                regionRecord.setNumStoreFiles(firstRegionRecord.get(Field.NUM_STORE_FILES).asInt());
                regionRecord.setMemStoreSizeTag(firstRegionRecord.get(Field.MEM_STORE_SIZE).asString());
                regionRecord.setMemStoreSize(firstRegionRecord.get(Field.MEM_STORE_SIZE).asSize().get(Size.Unit.MEGABYTE));

                regionRecord.setLocality(firstRegionRecord.get(Field.LOCALITY).asFloat());
                regionRecord.setStartKey(firstRegionRecord.get(Field.START_KEY).asString());
                regionRecord.setEndKey(endKey);
                regionRecords.add(regionRecord);

            }
            return regionRecords;
        });

    }

    private List<Record> filterAndSortRecords(List<Record> records, List<RecordFilter> recordFilters,
                                              Field currentSortField, boolean ascendingSort) {
        // Filter and sort
        List<Record> sortAndFilterRecords;
        if (currentSortField != null) {
            sortAndFilterRecords = records.stream()
                    .filter(r -> recordFilters.stream().allMatch(f -> f.execute(r)))
                    .sorted((recordLeft, recordRight) -> {
                        FieldValue left = recordLeft.get(currentSortField);
                        FieldValue right = recordRight.get(currentSortField);
                        return (ascendingSort ? 1 : -1) * left.compareTo(right);
                    }).collect(Collectors.toList());
        } else {
            sortAndFilterRecords = records.stream()
                    .filter(r -> recordFilters.stream().allMatch(f -> f.execute(r)))
                    .collect(Collectors.toList());
        }

        return Collections.unmodifiableList(sortAndFilterRecords);
    }

    private List<RecordFilter> createTableRecordFilters(String tableName) {
        List<RecordFilter> recordFilters = new ArrayList<>();

        RecordFilter namespaceFilter = RecordFilter.newBuilder(Field.NAMESPACE, false)
                .equal(new FieldValue(HMHBaseConstant.getNamespaceName(tableName), FieldValueType.STRING));
        RecordFilter tableFilter = RecordFilter.newBuilder(Field.TABLE, false)
                .equal(new FieldValue(HMHBaseConstant.getTableName(tableName), FieldValueType.STRING));
        recordFilters.add(namespaceFilter);
        recordFilters.add(tableFilter);
        return recordFilters;
    }
}
