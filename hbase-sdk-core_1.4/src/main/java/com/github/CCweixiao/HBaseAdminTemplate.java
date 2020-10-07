package com.github.CCweixiao;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.NamespaceDesc;
import com.github.CCweixiao.model.SnapshotDesc;
import com.github.CCweixiao.model.TableDesc;
import com.github.CCweixiao.util.SplitGoEnum;
import com.github.CCweixiao.util.StrUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.protobuf.generated.HBaseProtos;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.RegionSplitter;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/9/25 11:11 下午
 */
public class HBaseAdminTemplate extends AbstractHBaseAdminTemplate {
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
    public List<FamilyDesc> listFamilyDesc(String tableName) {
        tableIsNotExistsError(tableName);
        return this.execute(admin -> {
            HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            final Collection<HColumnDescriptor> families = tableDescriptor.getFamilies();
            return parseFamilyDescriptorToFamilyDescList(families);
        });
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
            return parseHTableDescriptorsToTableDescList(tableDescriptors);
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
    public List<TableDesc> listTableDescByNamespace(String namespaceName) {
        return this.execute(admin -> {
            final HTableDescriptor[] tableDescriptors = admin.listTableDescriptorsByNamespace(namespaceName);
            if (tableDescriptors == null || tableDescriptors.length == 0) {
                return new ArrayList<>();
            }
            return parseHTableDescriptorsToTableDescList(tableDescriptors);
        });
    }

    @Override
    public List<String> listTableNamesByNamespace(String namespaceName) {
        List<TableDesc> tableDescList = listTableDescByNamespace(namespaceName);
        return tableDescList.stream().map(TableDesc::getTableName).collect(Collectors.toList());
    }

    @Override
    public TableDesc getTableDesc(String tableName) {
        return this.execute(admin -> {
            HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            return parseHTableDescriptorToTableDesc(tableDescriptor);
        });
    }

    @Override
    public boolean createTable(TableDesc tableDesc) {
        String tableName = HMHBaseConstant.getFullTableName(tableDesc.getNamespaceName(), tableDesc.getTableName());
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
        String tableName = HMHBaseConstant.getFullTableName(tableDesc.getNamespaceName(), tableDesc.getTableName());
        tableIsExistsError(tableName);
        tableDesc.setTableName(tableName);

        return this.execute(admin -> {
            HTableDescriptor tableDescriptor = parseTableDescToHTableDescriptor(tableDesc);
            boolean preSplit = (StrUtil.isNotBlank(startKey) && StrUtil.isNotBlank(endKey) && numRegions > 0);

            if (preSplit) {
                final byte[] startKeyBytes = Bytes.toBytes(startKey);
                final byte[] endKeyBytes = Bytes.toBytes(endKey);

                if (numRegions < 3) {
                    throw new HBaseOperationsException("请至少指定3个分区！");
                }

                if (Bytes.compareTo(startKeyBytes, endKeyBytes) >= 0) {
                    throw new HBaseOperationsException("预分区开始的key必须要小于预分区结束的key！");
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
                    throw new HBaseOperationsException("无法将预分区的起止键范围分割成足够的region！");
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
        String tableName = HMHBaseConstant.getFullTableName(tableDesc.getNamespaceName(), tableDesc.getTableName());
        tableIsExistsError(tableName);
        tableDesc.setTableName(tableName);

        return this.execute(admin -> {
            HTableDescriptor tableDescriptor = parseTableDescToHTableDescriptor(tableDesc);
            boolean preSplit = splitKeys != null && splitKeys.length > 0;

            if (preSplit) {
                if (isAsync) {
                    admin.createTableAsync(tableDescriptor, getSplitKeyBytes(splitKeys));
                } else {
                    admin.createTable(tableDescriptor, getSplitKeyBytes(splitKeys));
                }
            } else {
                admin.createTable(tableDescriptor);
            }
            return true;
        });
    }

    @Override
    public boolean createTable(TableDesc tableDesc, SplitGoEnum splitGoEnum, int numRegions, boolean isAsync) {
        String tableName = HMHBaseConstant.getFullTableName(tableDesc.getNamespaceName(), tableDesc.getTableName());
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
                } else {
                    throw new HBaseOperationsException("暂不支持的一种预分区策略");
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
    public boolean modifyTableProps(TableDesc desc) {
        final String tableName = desc.getTableName();
        tableIsNotExistsError(tableName);
        return this.execute(admin -> {
            final HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            final Map<String, String> tableProps = desc.getTableProps();
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
            throw new HBaseOperationsException("重命名表前请禁用表！");
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
            admin.deleteTable(TableName.valueOf(tableName));
            return true;
        });
    }

    @Override
    public boolean truncateTable(String tableName, boolean preserveSplits) {
        return this.execute(admin -> {
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
        return this.execute(admin -> {
            final HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            if (tableDescriptor.hasFamily(Bytes.toBytes(familyDesc.getFamilyName()))) {
                throw new HBaseOperationsException("列簇" + familyDesc.getFamilyName() + "在表" + tableName + "中已经存在！");
            }
            HColumnDescriptor columnDescriptor = parseFamilyDescToHColumnDescriptor(familyDesc);
            admin.addColumn(TableName.valueOf(tableName), columnDescriptor);
            return true;
        });
    }

    @Override
    public boolean deleteFamily(String tableName, String familyName) {
        return this.execute(admin -> {
            admin.deleteColumn(TableName.valueOf(tableName), Bytes.toBytes(familyName));
            return true;
        });
    }

    @Override
    public boolean modifyFamily(String tableName, FamilyDesc familyDesc) {
        return this.execute(admin -> {
            final HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            if (!tableDescriptor.hasFamily(Bytes.toBytes(familyDesc.getFamilyName()))) {
                throw new HBaseOperationsException("待修改列簇" + familyDesc.getFamilyName() + "不存在！");
            }
            HColumnDescriptor columnDescriptor = parseFamilyDescToHColumnDescriptor(familyDesc);
            admin.modifyColumn(TableName.valueOf(tableName), columnDescriptor);
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
    public boolean createNamespace(NamespaceDesc namespaceDesc) {
        if (namespaceIsExists(namespaceDesc.getNamespaceName())) {
            throw new HBaseOperationsException("命名空间" + namespaceDesc.getNamespaceName() + "已经存在");
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
        final List<String> namespaces = listNamespaces();
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
                throw new HBaseOperationsException("命名空间" + namespaceName + "下存在表，不能被删除");
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
    public List<String> listNamespaces() {
        return listNamespaceDesc().stream().map(NamespaceDesc::getNamespaceName).collect(Collectors.toList());

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

    /**
     * 转换TableDesc得到HTableDescriptor
     *
     * @param tableDesc TableDesc
     * @return HTableDescriptor
     */
    private HTableDescriptor parseTableDescToHTableDescriptor(final TableDesc tableDesc) {
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableDesc.getTableName()));

        final List<FamilyDesc> familyDescList = tableDesc.getFamilyDescList();

        if (familyDescList == null || familyDescList.isEmpty()) {
            throw new HBaseOperationsException("请为表" + tableDesc.getTableName() + "指定一个或多个列簇！");
        }

        final Map<String, Long> familyCountMap = familyDescList.stream().collect(Collectors.groupingBy(FamilyDesc::getFamilyName, Collectors.counting()));
        familyCountMap.forEach((familyName, count) -> {
            if (count > 1) {
                throw new HBaseOperationsException("同一张表中的列簇名" + familyName + "应该是唯一的！");
            }
        });

        if (tableDesc.getTableProps() != null && !tableDesc.getTableProps().isEmpty()) {
            tableDesc.getTableProps().forEach(tableDescriptor::setValue);
        }
        for (FamilyDesc familyDesc : familyDescList) {
            HColumnDescriptor columnDescriptor = parseFamilyDescToHColumnDescriptor(familyDesc);
            tableDescriptor.addFamily(columnDescriptor);
        }
        return tableDescriptor;
    }

    /**
     * HTableDescriptor批量转换为TableDesc
     *
     * @param tableDescriptors HTableDescriptor
     * @return TableDesc
     */
    private List<TableDesc> parseHTableDescriptorsToTableDescList(HTableDescriptor[] tableDescriptors) {
        return Arrays.stream(tableDescriptors).map(this::parseHTableDescriptorToTableDesc).collect(Collectors.toList());
    }

    /**
     * HTableDescriptor 转换为TableDesc
     *
     * @param tableDescriptor 表定义
     * @return TableDesc
     */
    private TableDesc parseHTableDescriptorToTableDesc(HTableDescriptor tableDescriptor) {
        TableDesc tableDesc = new TableDesc();
        String tableName = tableDescriptor.getNameAsString();
        tableDesc.setNamespaceName(HMHBaseConstant.getNamespaceName(tableName));
        tableDesc.setTableName(tableName);
        tableDesc.setDisabled(isTableDisabled(tableName));
        tableDesc.setMetaTable(tableDescriptor.isMetaTable());
        final Map<ImmutableBytesWritable, ImmutableBytesWritable> values = tableDescriptor.getValues();
        if (values != null && !values.isEmpty()) {
            values.forEach((key, value) -> tableDesc.addProp(Bytes.toString(key.get()), Bytes.toString(value.get())));
        }
        tableDesc.setTableDesc(tableDescriptor.toString());
        tableDesc.setFamilyDescList(parseFamilyDescriptorToFamilyDescList(tableDescriptor.getFamilies()));
        return tableDesc;
    }

    /**
     * HColumnDescriptor列表转换为FamilyDesc列表
     *
     * @param families HColumnDescriptor 列表
     * @return FamilyDesc列表
     */
    public List<FamilyDesc> parseFamilyDescriptorToFamilyDescList(Collection<HColumnDescriptor> families) {
        return families.stream().map(family -> new FamilyDesc.Builder()
                .familyName(family.getNameAsString())
                .maxVersions(family.getMaxVersions())
                .timeToLive(family.getTimeToLive())
                .compressionType(family.getCompressionType().getName())
                .replicationScope(family.getScope())
                .build()).collect(Collectors.toList());
    }

    /**
     * FamilyDesc -> HColumnDescriptor
     *
     * @param familyDesc 自定义列簇描述
     * @return HColumnDescriptor
     */
    private HColumnDescriptor parseFamilyDescToHColumnDescriptor(FamilyDesc familyDesc) {
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(familyDesc.getFamilyName());
        columnDescriptor.setMaxVersions(familyDesc.getMaxVersions());
        columnDescriptor.setTimeToLive(familyDesc.getTimeToLive());
        columnDescriptor.setCompressionType(Compression.Algorithm.valueOf(familyDesc.getCompressionType()));
        return columnDescriptor;
    }


    private void tableIsNotExistsError(String tableName) {
        String fullTableName = HMHBaseConstant.getFullTableName(tableName);
        if (!tableExists(fullTableName)) {
            throw new HBaseOperationsException("表" + tableName + "不存在！");
        }
    }

    private void tableIsExistsError(String tableName) {
        String fullTableName = HMHBaseConstant.getFullTableName(tableName);
        if (tableExists(fullTableName)) {
            throw new HBaseOperationsException("表" + tableName + "已经存在！");
        }
    }

    private byte[][] getSplitKeyBytes(String[] splitKeys) {
        final List<byte[]> bytes = Arrays.stream(splitKeys).distinct().map(Bytes::toBytes).collect(Collectors.toList());
        byte[][] splitKeyBytes = new byte[bytes.size()][];
        for (int i = 0; i < bytes.size(); i++) {
            splitKeyBytes[i] = bytes.get(i);
        }
        return splitKeyBytes;
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
        tableIsNotExistsError(tableName);

        if (families == null || families.isEmpty()) {
            throw new HBaseOperationsException("请传入一个或多个待修改的列簇");
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

}
