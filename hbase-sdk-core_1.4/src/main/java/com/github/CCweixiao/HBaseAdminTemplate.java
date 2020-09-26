package com.github.CCweixiao;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.NamespaceDesc;
import com.github.CCweixiao.model.SnapshotDesc;
import com.github.CCweixiao.model.TableDesc;
import com.github.CCweixiao.util.StrUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.protobuf.generated.HBaseProtos;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/9/25 11:11 下午
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
    public List<TableDesc> listTables() {
        return listTables("", false);
    }

    @Override
    public List<TableDesc> listTables(boolean includeSysTables) {
        return listTables("", includeSysTables);
    }

    @Override
    public List<TableDesc> listTables(String regex, boolean includeSysTables) {
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
    public boolean createTable(TableDesc desc, boolean isAsync) {
        String tableName = desc.getTableName();
        tableIsExistsError(tableName);

        final List<FamilyDesc> familyDescList = desc.getFamilyDescList();
        if (familyDescList == null || familyDescList.isEmpty()) {
            throw new HBaseOperationsException("请为表" + tableName + "指定一个或多个列簇！");
        }

        final Map<String, Long> familyCountMap = familyDescList.stream().collect(Collectors.groupingBy(FamilyDesc::getFamilyName, Collectors.counting()));
        familyCountMap.forEach((familyName, count) -> {
            if (count > 1) {
                throw new HBaseOperationsException("同一张表中的列簇名" + familyName + "应该是唯一的！");
            }
        });

        return this.execute(admin -> {
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(desc.getTableName()));
            if (desc.getTableProps() != null && !desc.getTableProps().isEmpty()) {
                desc.getTableProps().forEach(tableDescriptor::setConfiguration);
            }
            for (FamilyDesc familyDesc : familyDescList) {
                HColumnDescriptor columnDescriptor = parseFamilyDescToHColumnDescriptor(familyDesc);
                tableDescriptor.addFamily(columnDescriptor);
            }
            String startKey = desc.getStartKey();
            String endKey = desc.getEndKey();
            Integer numRegions = desc.getPreSplitRegions();

            boolean preSplit1 = StrUtil.isNotBlank(desc.getPreSplitKeys());
            boolean preSplit2 = (StrUtil.isNotBlank(startKey) && StrUtil.isNotBlank(endKey) && numRegions > 0);

            if (preSplit1) {
                if (preSplit2) {
                    throw new HBaseOperationsException("只能同时指定一种预分区的方式！");
                } else {
                    String[] splitKeys = desc.getPreSplitKeys().split(",");
                    if (isAsync) {
                        admin.createTableAsync(tableDescriptor, getSplitKeyBytes(splitKeys));
                    } else {
                        admin.createTable(tableDescriptor, getSplitKeyBytes(splitKeys));
                    }
                    return true;
                }
            } else {
                if (preSplit2) {
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
            }
        });
    }


    @Override
    public boolean modifyTable(String tableName, TableDesc desc) {
        throw new HBaseOperationsException("暂不支持修改表！");
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
            admin.getTableDescriptor(TableName.valueOf(tableName)).getFamilies().forEach(hColumnDescriptor -> {
                if (hColumnDescriptor.getNameAsString().equals(familyDesc.getFamilyName())) {
                    throw new HBaseOperationsException("列簇" + familyDesc.getFamilyName() + "在表" + tableName + "中已经存在！");
                }
            });
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
            final List<String> families = admin.getTableDescriptor(TableName.valueOf(tableName)).getFamilies().stream().map(HColumnDescriptor::getNameAsString).collect(Collectors.toList());
            if (!families.contains(familyDesc.getFamilyName())) {
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
            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespaceDesc.getNamespaceName())
                    .addConfiguration(namespaceDesc.getNamespaceProps()).build();
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
    public List<NamespaceDesc> listNamespaceDescriptors() {
        return this.execute(admin -> Arrays.stream(admin.listNamespaceDescriptors()).map(namespaceDescriptor -> {
            NamespaceDesc namespaceDesc = new NamespaceDesc();
            namespaceDesc.setNamespaceName(namespaceDescriptor.getName());
            namespaceDesc.setNamespaceProps(namespaceDescriptor.getConfiguration());
            return namespaceDesc;
        }).collect(Collectors.toList()));
    }

    @Override
    public List<String> listNamespaces() {
        return listNamespaceDescriptors().stream().map(NamespaceDesc::getNamespaceName).collect(Collectors.toList());

    }

    @Override
    public List<TableDesc> listTableDescriptorsByNamespace(String namespaceName) {
        return this.execute(admin -> {
            final HTableDescriptor[] tableDescriptors = admin.listTableDescriptorsByNamespace(namespaceName);
            if (tableDescriptors == null || tableDescriptors.length == 0) {
                return new ArrayList<>();
            } else {
                return Arrays.stream(tableDescriptors).map(this::parseHTableDescriptorToTableDesc).collect(Collectors.toList());
            }
        });
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

    private List<TableDesc> parseHTableDescriptorsToTableDescList(HTableDescriptor[] tableDescriptors) {
        return Arrays.stream(tableDescriptors).map(this::parseHTableDescriptorToTableDesc).collect(Collectors.toList());
    }

    private TableDesc parseHTableDescriptorToTableDesc(HTableDescriptor tableDescriptor) {
        TableDesc tableDesc = new TableDesc();
        String tableName = tableDescriptor.getNameAsString();
        tableDesc.setNamespaceName(HMHBaseConstant.getNamespaceName(tableName));
        tableDesc.setTableName(tableName);
        tableDesc.setDisabled(isTableDisabled(tableName));
        tableDesc.setMetaTable(tableDescriptor.isMetaTable());
        tableDesc.setTableProps(tableDescriptor.getConfiguration());
        tableDesc.setTableDesc(tableDescriptor.toString());
        return tableDesc;
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

    private void checkTableNameIsNotEmpty(String tableName) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseOperationsException("表名不能为空！");
        }
    }

    private void tableIsNotExistsError(String tableName) {
        checkTableNameIsNotEmpty(tableName);

        if (!tableExists(tableName)) {
            throw new HBaseOperationsException("表" + tableName + "不存在！");
        }
    }

    private void tableIsExistsError(String tableName) {
        checkTableNameIsNotEmpty(tableName);
        if (tableExists(tableName)) {
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
