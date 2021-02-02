package com.github.CCweixiao;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.hbtop.HBaseMetricOperations;
import com.github.CCweixiao.hbtop.Record;
import com.github.CCweixiao.hbtop.RecordFilter;
import com.github.CCweixiao.hbtop.Summary;
import com.github.CCweixiao.hbtop.field.Field;
import com.github.CCweixiao.hbtop.field.FieldValue;
import com.github.CCweixiao.hbtop.mode.Mode;
import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.NamespaceDesc;
import com.github.CCweixiao.model.SnapshotDesc;
import com.github.CCweixiao.model.TableDesc;
import com.github.CCweixiao.util.SplitGoEnum;
import com.github.CCweixiao.util.SplitKeyUtil;
import com.github.CCweixiao.util.StrUtil;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.RegionSplitter;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/9/25 11:11 下午
 */
public class HBaseAdminTemplate extends AbstractHBaseAdminTemplate implements HBaseMetricOperations {
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
    public List<TableDesc> listTableDesc() {
        return listTableDesc("", false);
    }

    @Override
    public List<TableDesc> listTableDesc(boolean includeSysTables) {
        return listTableDesc("", includeSysTables);
    }

    @Override
    public List<TableDesc> listTableDesc(String regex, boolean includeSysTables) {
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

            List<TableDesc> tableDescList = parseHTableDescriptorsToTableDescList(tableDescriptors);

            for (TableDesc tableDesc : tableDescList) {
                tableDesc.setLastMajorCompactTimestamp(admin.getLastMajorCompactionTimestamp(TableName.valueOf(tableDesc.getTableName())));
            }
            return tableDescList;
        });
    }

    @Override
    public List<TableDesc> listTableDescByNamespace(String namespaceName) {
        return this.execute(admin -> {
            final List<TableDescriptor> tableDescriptors = admin.listTableDescriptorsByNamespace(Bytes.toBytes(namespaceName));
            if (tableDescriptors == null || tableDescriptors.isEmpty()) {
                return new ArrayList<>();
            }

            final List<TableDesc> tableDescList = parseHTableDescriptorsToTableDescList(tableDescriptors);

            for (TableDesc tableDesc : tableDescList) {
                tableDesc.setLastMajorCompactTimestamp(admin.getLastMajorCompactionTimestamp(TableName.valueOf(tableDesc.getTableName())));
            }
            return tableDescList;
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
        List<TableDesc> tableDescList = listTableDescByNamespace(namespaceName);
        return tableDescList.stream().map(TableDesc::getTableName).collect(Collectors.toList());
    }

    @Override
    public List<FamilyDesc> listFamilyDesc(String tableName) {
        tableIsNotExistsError(tableName);
        return this.execute(admin -> {
            TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));
            final ColumnFamilyDescriptor[] families = tableDescriptor.getColumnFamilies();
            return parseFamilyDescriptorToFamilyDescList(families);
        });
    }

    @Override
    public TableDesc getTableDesc(String tableName) {
        return this.execute(admin -> {
            TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));
            TableDesc tableDesc = parseHTableDescriptorToTableDesc(tableDescriptor);
            tableDesc.setLastMajorCompactTimestamp(admin.getLastMajorCompactionTimestamp(TableName.valueOf(tableName)));
            return tableDesc;
        });
    }

    @Override
    public boolean createTable(TableDesc tableDesc) {
        String tableName = tableDesc.getFullTableName();
        tableIsExistsError(tableName);
        tableDesc.setTableName(tableName);

        return this.execute(admin -> {
            TableDescriptor tableDescriptor = parseTableDescToTableDescriptor(tableDesc);
            admin.createTable(tableDescriptor);
            return true;
        });
    }

    @Override
    public boolean createTable(TableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync) {
        String tableName = tableDesc.getFullTableName();
        tableIsExistsError(tableName);
        tableDesc.setTableName(tableName);

        return this.execute(admin -> {
            TableDescriptor tableDescriptor = parseTableDescToTableDescriptor(tableDesc);
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
    public boolean createTable(TableDesc tableDesc, String[] splitKeys, boolean isAsync) {
        String tableName = tableDesc.getFullTableName();
        tableIsExistsError(tableName);
        tableDesc.setTableName(tableName);

        return this.execute(admin -> {
            TableDescriptor tableDescriptor = parseTableDescToTableDescriptor(tableDesc);
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
    public boolean createTable(TableDesc tableDesc, SplitGoEnum splitGoEnum, int numRegions, boolean isAsync) {
        String tableName = tableDesc.getFullTableName();
        tableIsExistsError(tableName);
        tableDesc.setTableName(tableName);

        return this.execute(admin -> {
            TableDescriptor tableDescriptor = parseTableDescToTableDescriptor(tableDesc);
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
    public boolean modifyTableProps(TableDesc tableDesc) {
        final String tableName = tableDesc.getFullTableName();
        tableIsNotExistsError(tableName);

        return this.execute(admin -> {
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName));
            final Map<String, String> tableProps = tableDesc.getTableProps();
            if (tableProps != null && !tableProps.isEmpty()) {
                tableProps.forEach(tableDescriptorBuilder::setValue);
                admin.modifyTable(tableDescriptorBuilder.build());
                return true;
            }
            return true;
        });
    }

    @Override
    public boolean renameTable(String oldTableName, String newTableName, boolean deleteOldTable) {
        tableIsNotExistsError(oldTableName);
        tableIsExistsError(newTableName);

        if (isTableEnabled(oldTableName)) {
            throw new HBaseOperationsException("表[" + oldTableName + "]重命名之前请先禁用");
        }

        return this.execute(admin -> {
            final String tempSnapShotName = oldTableName.replace(":", "_") +
                    "_SNAPSHOT_RENAME_" + UUID.randomUUID().toString().replaceAll("-", "");
            admin.snapshot(tempSnapShotName, TableName.valueOf(oldTableName));
            admin.cloneSnapshot(tempSnapShotName, TableName.valueOf(newTableName));
            admin.deleteSnapshot(tempSnapShotName);
            if (deleteOldTable) {
                admin.deleteTable(TableName.valueOf(oldTableName));
            } else {
                admin.enableTableAsync(TableName.valueOf(oldTableName));
            }
            return true;
        });
    }

    @Override
    public boolean deleteTable(String tableName) {
        return this.execute(admin -> {
            if (!admin.isTableDisabled(TableName.valueOf(tableName))) {
                throw new HBaseOperationsException("表[" + tableName + "]删除之前请先禁用");
            }

            admin.deleteTable(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean truncateTable(String tableName, boolean preserveSplits) {
        return this.execute(admin -> {
            if (!admin.isTableDisabled(TableName.valueOf(tableName))) {
                throw new HBaseOperationsException("表[" + tableName + "]清空之前请先禁用");
            }
            admin.truncateTable(TableName.valueOf(tableName), preserveSplits);
            return true;
        });
    }

    @Override
    public boolean enableTable(String tableName, boolean isAsync) {
        return this.execute(admin -> {
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
    public boolean disableTable(String tableName, boolean isAsync) {
        return this.execute(admin -> {
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
    public boolean addFamily(String tableName, FamilyDesc familyDesc) {
        tableIsNotExistsError(tableName);
        return this.execute(admin -> {
            final TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));
            if (tableDescriptor.hasColumnFamily(Bytes.toBytes(familyDesc.getFamilyName()))) {
                throw new HBaseOperationsException("列簇[" + familyDesc.getFamilyName() + "]在表[" + tableName + "]中已经存在");
            }
            ColumnFamilyDescriptor columnDescriptor = parseFamilyDescToColumnFamilyDescriptor(familyDesc);
            admin.addColumnFamily(TableName.valueOf(tableName), columnDescriptor);
            return true;
        });
    }

    @Override
    public boolean deleteFamily(String tableName, String familyName) {
        return this.execute(admin -> {
            final TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));
            if (!tableDescriptor.hasColumnFamily(Bytes.toBytes(familyName))) {
                throw new HBaseOperationsException("列簇[" + familyName + "]在表[" + tableName + "]中不存在");
            }
            admin.deleteColumnFamily(TableName.valueOf(tableName), Bytes.toBytes(familyName));
            return true;
        });
    }

    @Override
    public boolean modifyFamily(String tableName, FamilyDesc familyDesc) {
        tableIsNotExistsError(tableName);

        return this.execute(admin -> {
            final TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));

            if (!tableDescriptor.hasColumnFamily(Bytes.toBytes(familyDesc.getFamilyName()))) {
                throw new HBaseOperationsException("待修改列簇[" + familyDesc.getFamilyName() + "]不存在");
            }

            ColumnFamilyDescriptor columnDescriptor = tableDescriptor.getColumnFamily(Bytes.toBytes(familyDesc.getFamilyName()));
            ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(columnDescriptor);
            boolean change = false;

            if (columnDescriptor.getMaxVersions() != familyDesc.getMaxVersions()) {
                columnFamilyDescriptorBuilder.setMaxVersions(familyDesc.getMaxVersions());
                change = true;
            }
            if (columnDescriptor.getTimeToLive() != familyDesc.getTimeToLive()) {
                columnFamilyDescriptorBuilder.setTimeToLive(familyDesc.getTimeToLive());
                change = true;
            }
            if (!columnDescriptor.getCompressionType().getName().equalsIgnoreCase(familyDesc.getCompressionType())) {
                columnFamilyDescriptorBuilder.setCompressionType(Compression.Algorithm.valueOf(familyDesc.getCompressionType()));
                change = true;
            }
            if (columnDescriptor.getScope() != familyDesc.getReplicationScope()) {
                columnFamilyDescriptorBuilder.setScope(familyDesc.getReplicationScope());
                change = true;
            }
            if (change) {
                admin.modifyColumnFamily(TableName.valueOf(tableName), columnFamilyDescriptorBuilder.build());
            }
            return true;
        });
    }

    @Override
    public boolean enableReplicationScope(String tableName, List<String> families) {
        tableIsNotExistsError(tableName);
        return modifyTableReplicationScope(tableName, HMHBaseConstant.ENABLE_REPLICATION_SCOPE, families);
    }

    @Override
    public boolean disableReplicationScope(String tableName, List<String> families) {
        tableIsNotExistsError(tableName);
        return modifyTableReplicationScope(tableName, HMHBaseConstant.DISABLE_REPLICATION_SCOPE, families);
    }

    @Override
    public boolean flush(String tableName) {
        tableIsNotExistsError(tableName);
        return this.execute(admin -> {
            admin.flush(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean compact(String tableName) {
        tableIsNotExistsError(tableName);
        return this.execute(admin -> {
            admin.compact(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean majorCompact(String tableName) {
        tableIsNotExistsError(tableName);
        return this.execute(admin -> {
            admin.majorCompact(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean createNamespace(NamespaceDesc namespaceDesc) {
        if (namespaceIsExists(namespaceDesc.getNamespaceName())) {
            throw new HBaseOperationsException("命名空间[" + namespaceDesc.getNamespaceName() + "]已经存在");
        }

        return this.execute(admin -> {
            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespaceDesc.getNamespaceName()).build();
            final Map<String, String> namespaceProps = namespaceDesc.getNamespaceProps();
            if (namespaceProps != null && !namespaceProps.isEmpty()) {
                namespaceProps.forEach(namespaceDescriptor::setConfiguration);
            }
            admin.createNamespace(namespaceDescriptor);
            return true;
        });
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
    public boolean deleteNamespace(String namespaceName) {
        return this.execute(admin -> {
            final List<TableDescriptor> tableDescriptors = admin.listTableDescriptorsByNamespace(Bytes.toBytes(namespaceName));
            if (tableDescriptors != null && !tableDescriptors.isEmpty()) {
                throw new HBaseOperationsException("命名空间[" + namespaceName + "]下存在表，不能被删除");
            }
            admin.deleteNamespace(namespaceName);
            return true;
        });
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
        tableIsNotExistsError(tableName);
        return this.execute(admin -> admin.getLastMajorCompactionTimestamp(TableName.valueOf(tableName)));
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
    public boolean snapshot(SnapshotDesc snapshotDesc) {
        tableIsNotExistsError(snapshotDesc.getTableName());
        return this.execute(admin -> {
            admin.snapshot(snapshotDesc.getSnapshotName(), TableName.valueOf(snapshotDesc.getTableName()));
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
        tableIsExistsError(tableName);

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
     * 修改表和列簇的REPLICATION_SCOPE属性
     *
     * @param tableName        表名
     * @param replicationScope 0 或 1，1 表示禁用replication
     * @param families         列簇
     * @return 修改REPLICATION_SCOPE的熟悉是否成功
     */
    private boolean modifyTableReplicationScope(String tableName, int replicationScope, List<String> families) {
        tableIsNotExistsError(tableName);

        if (families == null || families.isEmpty()) {
            throw new HBaseOperationsException("请传入一个或多个待修改的列簇");
        }

        return this.execute(admin -> {
            TableDescriptor tableDescriptor = admin.getDescriptor(TableName.valueOf(tableName));
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableDescriptor);

            for (String family : families) {
                final ColumnFamilyDescriptor columnFamily = tableDescriptor.getColumnFamily(Bytes.toBytes(family));
                ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(columnFamily);
                final int currentScope = columnFamily.getScope();
                if (currentScope != replicationScope) {
                    columnFamilyDescriptorBuilder.setScope(replicationScope);
                    tableDescriptorBuilder.modifyColumnFamily(columnFamilyDescriptorBuilder.build());
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

            return new Summary(currentTime, version, clusterId, liveServers + deadServers,
                    liveServers, deadServers, namespaceCount, tableCount, snapshotCount, regionCount, ritCount, averageLoad, aggregateRequestPerSecond);
        });
    }

    @Override
    public List<Record> refreshRecords(Mode currentMode, List<RecordFilter> filters, Field currentSortField, boolean ascendingSort) {
        return this.execute(admin -> {
            final ClusterMetrics clusterMetrics = admin.getClusterMetrics();
            List<Record> records = currentMode.getRecords(clusterMetrics);

            // Filter and sort
            records = records.stream()
                    .filter(r -> filters.stream().allMatch(f -> f.execute(r)))
                    .sorted((recordLeft, recordRight) -> {
                        FieldValue left = recordLeft.get(currentSortField);
                        FieldValue right = recordRight.get(currentSortField);
                        return (ascendingSort ? 1 : -1) * left.compareTo(right);
                    }).collect(Collectors.toList());

            return Collections.unmodifiableList(records);
        });
    }
}
