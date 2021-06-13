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
import com.github.CCweixiao.hbtop.field.Size;
import com.github.CCweixiao.hbtop.mode.Mode;
import com.github.CCweixiao.model.*;
import com.github.CCweixiao.util.*;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.protobuf.generated.HBaseProtos;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.github.CCweixiao.constant.HMHBaseConstant.DISABLE_REPLICATION_SCOPE;
import static com.github.CCweixiao.constant.HMHBaseConstant.ENABLE_REPLICATION_SCOPE;

/**
 * @author leojie 2020/9/25 11:11 下午
 */
public class HBaseAdminTemplate extends AbstractHBaseAdminTemplate implements HBaseMetricOperations {
    private static final Logger LOG = LoggerFactory.getLogger(HBaseAdminTemplate.class);

    public static final Pattern REGION_COMPILE = Pattern.compile("\\.([\\w]+)\\.");

    public HBaseAdminTemplate(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public HBaseAdminTemplate(Configuration configuration) {
        super(configuration);
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
            HTableDescriptor[] tableDescriptors;
            if (StrUtil.isBlank(regex)) {
                tableDescriptors = admin.listTables((Pattern) null, includeSysTables);
            } else {
                tableDescriptors = admin.listTables(regex, includeSysTables);
            }
            if (tableDescriptors == null || tableDescriptors.length == 0) {
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
            final HTableDescriptor[] tableDescriptors = admin.listTableDescriptorsByNamespace(namespaceName);
            if (tableDescriptors == null || tableDescriptors.length == 0) {
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
                tableNames = admin.listTableNames(regex, includeSysTables);
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
            HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            final Collection<HColumnDescriptor> families = tableDescriptor.getFamilies();
            return parseFamilyDescriptorToFamilyDescList(families);
        });
    }

    @Override
    public TableDesc getTableDesc(String tableName) {
        return this.execute(admin -> {
            HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
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
            HTableDescriptor tableDescriptor = parseTableDescToHTableDescriptor(tableDesc);
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
            HTableDescriptor tableDescriptor = parseTableDescToHTableDescriptor(tableDesc);
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
            HTableDescriptor tableDescriptor = parseTableDescToHTableDescriptor(tableDesc);
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
            HTableDescriptor tableDescriptor = parseTableDescToHTableDescriptor(tableDesc);
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
            final HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            final Map<String, String> tableProps = tableDesc.getTableProps();
            if (tableProps != null && !tableProps.isEmpty()) {
                tableProps.forEach(tableDescriptor::setValue);
                admin.modifyTable(TableName.valueOf(tableName), tableDescriptor);
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
            final HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            if (tableDescriptor.hasFamily(Bytes.toBytes(familyDesc.getFamilyName()))) {
                throw new HBaseOperationsException("列簇[" + familyDesc.getFamilyName() + "]在表[" + tableName + "]中已经存在");
            }
            HColumnDescriptor columnDescriptor = parseFamilyDescToHColumnDescriptor(familyDesc);
            admin.addColumn(TableName.valueOf(tableName), columnDescriptor);
            return true;
        });
    }

    @Override
    public boolean deleteFamily(String tableName, String familyName) {
        tableIsNotExistsError(tableName);
        return this.execute(admin -> {
            final HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            if (!tableDescriptor.hasFamily(Bytes.toBytes(familyName))) {
                throw new HBaseOperationsException("列簇[" + familyName + "]在表[" + tableName + "]中不存在");
            }
            admin.deleteColumn(TableName.valueOf(tableName), Bytes.toBytes(familyName));
            return true;
        });
    }

    @Override
    public boolean modifyFamily(String tableName, FamilyDesc familyDesc) {
        tableIsNotExistsError(tableName);

        return this.execute(admin -> {
            final HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));

            if (!tableDescriptor.hasFamily(Bytes.toBytes(familyDesc.getFamilyName()))) {
                throw new HBaseOperationsException("待修改列簇[" + familyDesc.getFamilyName() + "]不存在");
            }
            HColumnDescriptor columnDescriptor = tableDescriptor.getFamily(Bytes.toBytes(familyDesc.getFamilyName()));
            boolean change = false;

            if (columnDescriptor.getMaxVersions() != familyDesc.getMaxVersions()) {
                columnDescriptor.setMaxVersions(familyDesc.getMaxVersions());
                change = true;
            }
            if (columnDescriptor.getTimeToLive() != familyDesc.getTimeToLive()) {
                columnDescriptor.setTimeToLive(familyDesc.getTimeToLive());
                change = true;
            }
            if (!columnDescriptor.getCompressionType().getName().equalsIgnoreCase(familyDesc.getCompressionType())) {
                columnDescriptor.setCompressionType(Compression.Algorithm.valueOf(familyDesc.getCompressionType()));
                change = true;
            }
            if (columnDescriptor.getScope() != familyDesc.getReplicationScope()) {
                columnDescriptor.setScope(familyDesc.getReplicationScope());
                change = true;
            }
            if (change) {
                admin.modifyColumn(TableName.valueOf(tableName), columnDescriptor);
            }
            return true;
        });
    }

    @Override
    public boolean enableReplicationScope(String tableName, List<String> families) {
        tableIsNotExistsError(tableName);
        return modifyTableReplicationScope(tableName, ENABLE_REPLICATION_SCOPE, families);
    }

    @Override
    public boolean disableReplicationScope(String tableName, List<String> families) {
        tableIsNotExistsError(tableName);
        return modifyTableReplicationScope(tableName, DISABLE_REPLICATION_SCOPE, families);
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
            final HTableDescriptor[] tableDescriptors = admin.listTableDescriptorsByNamespace(namespaceName);
            if (tableDescriptors != null && tableDescriptors.length > 0) {
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
            final List<HBaseProtos.SnapshotDescription> listSnapshots = admin.listSnapshots();
            if (listSnapshots == null || listSnapshots.isEmpty()) {
                return new ArrayList<>();
            } else {
                return listSnapshots.stream().map(snapshotDescription -> {
                    SnapshotDesc snapshotDesc = new SnapshotDesc();
                    snapshotDesc.setSnapshotName(snapshotDescription.getName());
                    snapshotDesc.setTableName(snapshotDescription.getTable());
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

    @Override
    public boolean mergeRegions(byte[] firstRegion, byte[] secondRegion, boolean force) {
        return this.execute(admin -> {
            admin.mergeRegions(firstRegion, secondRegion, force);
            return true;
        });
    }

    @Override
    public boolean mergeMultipleRegions(byte[][] regions, boolean force) {
        return this.execute(admin -> {
            int mergeRegionsNum = regions.length;
            if (mergeRegionsNum % 2 != 0){
                mergeRegionsNum = mergeRegionsNum - 1;
            }
            for(int i = 0, j = 1; i < mergeRegionsNum - 1; i += 2, j += 2){
                byte[] firstByteRegionName = regions[i];
                byte[] secondByteRegionName = regions[j];
                admin.mergeRegions(firstByteRegionName,secondByteRegionName, force);
            }
            return true;
        });
    }

    @Override
    public boolean mergeTableSmallRegions(String tableName, int limitRegionsNum, int limitRegionSize) {
        return this.execute(admin -> {
            final ClusterStatus clusterStatus = admin.getClusterStatus();
            List<String> biggerRegions = new ArrayList<>();
            clusterStatus.getServers().forEach((serverName -> {
                clusterStatus.getLoad(serverName).getRegionsLoad().forEach((region, regionLoad) -> {

                    final String regionStrName = regionLoad.getNameAsString();
                    if (regionStrName.startsWith(tableName)) {
                        Matcher regionNameMatcher = REGION_COMPILE.matcher(regionStrName);
                        String regionEncodedName = "";
                        if (regionNameMatcher.find()) {
                            regionEncodedName = regionNameMatcher.group(1);
                        }
                        if (StrUtil.isBlank(regionEncodedName)) {
                            throw new HBaseOperationsException("无法获取region[" + regionStrName + "]的加密名称");
                        }
                        final int regionStoreFileSize = regionLoad.getStorefileSizeMB();
                        if (regionStoreFileSize > limitRegionSize) {
                            biggerRegions.add(regionEncodedName);
                        }
                    }
                });
            }));
            List<HRegionInfo> mergedRegions = new ArrayList<>();
            admin.getTableRegions(TableName.valueOf(tableName)).forEach(regionInfo -> {
                if (biggerRegions.contains(regionInfo.getEncodedName())) {
                    // 把大region设置成null，使之不会参与合并
                    mergedRegions.add(null);
                } else {
                    mergedRegions.add(regionInfo);
                }
            });
            List<HRegionInfo> subMergedRegions;
            int mergedRegionsSize = mergedRegions.size();
            if (mergedRegionsSize > limitRegionsNum) {
                subMergedRegions = mergedRegions.subList(0, limitRegionsNum);
            } else {
                subMergedRegions = mergedRegions;
            }
            // 保证region相邻
            for (int i = 0, j = 1; i < subMergedRegions.size() - 1; i += 2, j += 2) {
                HRegionInfo firstRegionInfo = subMergedRegions.get(i);
                HRegionInfo secondRegionInfo = subMergedRegions.get(j);
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
                if (!HRegionInfo.areAdjacent(firstRegionInfo, secondRegionInfo)) {
                    continue;
                }
                admin.mergeRegions(firstRegionInfo.getEncodedNameAsBytes(), secondRegionInfo.getEncodedNameAsBytes(), false);
                LOG.info("表:{},Region:{},{}异步合并指令运行成功", tableName, firstRegionInfo.getEncodedName(), secondRegionInfo.getEncodedName());
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
     * @return 修改REPLICATION_SCOPE的熟悉是否成功
     */
    private boolean modifyTableReplicationScope(String tableName, int replicationScope, List<String> families) {
        tableIsNotExistsError(tableName);

        if (families == null || families.isEmpty()) {
            throw new HBaseOperationsException("请传入一个或多个待修改的列簇");
        }

        return this.execute(admin -> {
            HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            boolean change = false;
            for (String family : families) {
                final HColumnDescriptor hd = tableDescriptor.getFamily(Bytes.toBytes(family));
                final int currentScope = hd.getScope();
                if (currentScope != replicationScope) {
                    hd.setScope(replicationScope);
                    change = true;
                }
            }
            if (change) {
                admin.modifyTable(TableName.valueOf(tableName), tableDescriptor);
            }
            return true;
        });
    }

    @Override
    public Summary refreshSummary() {
        return this.execute(admin -> {
            ClusterStatus clusterStatus = admin.getClusterStatus();

            String currentTime = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());

            String version = clusterStatus.getHBaseVersion();
            String clusterId = clusterStatus.getClusterId();

            int liveServers = clusterStatus.getServersSize();
            int deadServers = clusterStatus.getDeadServerNames().size();
            int regionCount = clusterStatus.getRegionsCount();
            int ritCount = clusterStatus.getRegionsInTransition().size();
            int namespaceCount = admin.listNamespaceDescriptors().length;
            int tableCount = admin.listTableNames().length;
            int snapshotCount = admin.listSnapshots().size();

            double averageLoad = clusterStatus.getAverageLoad();
            long aggregateRequestPerSecond = 0;
            for (ServerName sn : clusterStatus.getServers()) {
                ServerLoad sl = clusterStatus.getLoad(sn);
                aggregateRequestPerSecond += sl.getNumberOfRequests();
            }
            return new Summary(currentTime, version, clusterId, liveServers + deadServers,
                    liveServers, deadServers, namespaceCount, tableCount, snapshotCount, regionCount, ritCount, averageLoad, aggregateRequestPerSecond);
        });
    }

    @Override
    public List<Record> refreshRecords(Mode currentMode, List<RecordFilter> filters, Field currentSortField, boolean ascendingSort) {
        return this.execute(admin -> {
            ClusterStatus clusterStatus = admin.getClusterStatus();
            List<Record> records = currentMode.getRecords(clusterStatus);
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
            ClusterStatus clusterStatus = admin.getClusterStatus();
            List<Record> records = Mode.TABLE.getRecords(clusterStatus);

            records = filterAndSortRecords(records, recordFilters, currentSortField, ascendingSort);
            return records.stream().map(this::convertRecordToHBaseTableRecord).collect(Collectors.toList());
        });
    }

    private HBaseTableRecord convertRecordToHBaseTableRecord(Record record){
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
            ClusterStatus clusterStatus = admin.getClusterStatus();
            List<Record> records = Mode.TABLE.getRecords(clusterStatus);
            if (records.isEmpty()){
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
            List<RecordFilter> recordFilters = createTableRecordFilters(tableName);

            ClusterStatus clusterStatus = admin.getClusterStatus();
            List<Record> records = Mode.REGION.getRecords(clusterStatus);
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
        if(currentSortField != null){
            sortAndFilterRecords = records.stream()
                    .filter(r -> recordFilters.stream().allMatch(f -> f.execute(r)))
                    .sorted((recordLeft, recordRight) -> {
                        FieldValue left = recordLeft.get(currentSortField);
                        FieldValue right = recordRight.get(currentSortField);
                        return (ascendingSort ? 1 : -1) * left.compareTo(right);
                    }).collect(Collectors.toList());
        }else{
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
