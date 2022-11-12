package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.model.*;
import com.github.CCweixiao.hbase.sdk.hbtop.HBaseMetricOperations;
import com.github.CCweixiao.hbase.sdk.hbtop.Record;
import com.github.CCweixiao.hbase.sdk.hbtop.RecordFilter;
import com.github.CCweixiao.hbase.sdk.hbtop.Summary;
import com.github.CCweixiao.hbase.sdk.hbtop.field.Field;
import com.github.CCweixiao.hbase.sdk.hbtop.field.FieldValue;
import com.github.CCweixiao.hbase.sdk.hbtop.field.FieldValueType;
import com.github.CCweixiao.hbase.sdk.hbtop.field.Size;
import com.github.CCweixiao.hbase.sdk.hbtop.mode.Mode;
import com.github.CCweixiao.hbase.sdk.common.util.DateUtil;
import com.github.CCweixiao.hbase.sdk.common.util.SplitGoEnum;
import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;
import com.github.CCweixiao.hbase.sdk.util.RegionSplitter;
import com.github.CCweixiao.hbase.sdk.util.SplitKeyUtil;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.protobuf.generated.HBaseProtos;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants.DISABLE_REPLICATION_SCOPE;
import static com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants.ENABLE_REPLICATION_SCOPE;

/**
 * @author leojie 2020/9/25 11:11 下午
 */
public class HBaseAdminTemplate extends AbstractHBaseAdminTemplate implements HBaseMetricOperations {
    public static final Pattern REGION_COMPILE = Pattern.compile("\\.([\\w]+)\\.");

    public HBaseAdminTemplate(Configuration configuration) {
        super(configuration);
    }

    public HBaseAdminTemplate(Properties properties) {
        super(properties);
    }
    public HBaseAdminTemplate(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    @Override
    public List<HTableDesc> listTableDesc(String regex, boolean includeSysTables) {
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
            List<HTableDesc> tableDescList = new ArrayList<>(tableDescriptors.length);
            for (HTableDescriptor tableDescriptor : tableDescriptors) {
                final HTableDesc tableDesc = convertToTableDesc(tableDescriptor);
                tableDesc.setState(admin.isTableEnabled(tableDescriptor.getTableName()));
                tableDesc.setLastMajorCompaction(admin.getLastMajorCompactionTimestamp(tableDescriptor.getTableName()));
                tableDescList.add(tableDesc);
            }
            return tableDescList;
        });
    }

    @Override
    public List<HTableDesc> listTableDescByNamespace(String namespaceName) {
        return this.execute(admin -> {
            final HTableDescriptor[] tableDescriptors = admin.listTableDescriptorsByNamespace(namespaceName);
            if (tableDescriptors == null || tableDescriptors.length == 0) {
                return new ArrayList<>();
            }
            return convertToTableDescList(Arrays.stream(tableDescriptors).collect(Collectors.toList()));
        });
    }

    @Override
    public List<ColumnFamilyDesc> listFamilyDesc(String tableName) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            final Collection<HColumnDescriptor> families = tableDescriptor.getFamilies();
            return convertToColumnFamilyDescList(Arrays.asList(families.toArray()));
        });
    }

    @Override
    public HTableDesc getTableDesc(String tableName) {
        return this.execute(admin -> {
            HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            final HTableDesc tableDesc = convertToTableDesc(tableDescriptor);
            tableDesc.setState(admin.isTableEnabled(TableName.valueOf(tableName)));
            tableDesc.setLastMajorCompaction(admin.getLastMajorCompactionTimestamp(TableName.valueOf(tableName)));
            return tableDesc;
        });
    }

    @Override
    public boolean createTable(HTableDesc tableDesc) {
        String tableName = tableDesc.getFullTableName();

        return this.execute(admin -> {
            tableIsExistsThrowError(admin, tableName);
            HTableDescriptor tableDescriptor = convertToTableDescriptor(tableDesc);
            admin.createTable(tableDescriptor);
            return true;
        });
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync) {
        String tableName = tableDesc.getFullTableName();

        return this.execute(admin -> {
            tableIsExistsThrowError(admin, tableName);
            HTableDescriptor tableDescriptor = convertToTableDescriptor(tableDesc);
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
            tableIsExistsThrowError(admin, tableName);
            HTableDescriptor tableDescriptor = convertToTableDescriptor(tableDesc);
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
            tableIsExistsThrowError(admin, tableName);
            HTableDescriptor tableDescriptor = convertToTableDescriptor(tableDesc);
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
            tableIsNotExistsThrowError(admin, tableName);
            final HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            if (props != null && !props.isEmpty()) {
                props.forEach(tableDescriptor::setValue);
                if (isAsync) {
                    admin.modifyTable(TableName.valueOf(tableName), tableDescriptor);
                } else {
                    admin.modifyTable(TableName.valueOf(tableName), tableDescriptor);
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

        if (isTableEnabled(oldTableName)) {
            throw new HBaseOperationsException("表[" + oldTableName + "]重命名之前请先禁用");
        }

        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, oldTableName);
            tableIsExistsThrowError(admin, newTableName);
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
    public boolean deleteTable(String tableName, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            tableIsNotDisableThrowError(admin, tableName);

            if (isAsync) {
                admin.deleteTable(TableName.valueOf(tableName));
            } else {
                admin.deleteTable(TableName.valueOf(tableName));
            }
            return true;
        });
    }

    @Override
    public boolean truncateTable(String tableName, boolean preserveSplits, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            tableIsNotDisableThrowError(admin, tableName);
            if (isAsync) {
                admin.truncateTable(TableName.valueOf(tableName), preserveSplits);
            } else {
                admin.truncateTable(TableName.valueOf(tableName), preserveSplits);
            }
            return true;
        });
    }

    @Override
    public boolean addFamily(String tableName, ColumnFamilyDesc familyDesc, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            final HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            if (tableDescriptor.hasFamily(Bytes.toBytes(familyDesc.getFamilyName()))) {
                throw new HBaseOperationsException("列簇[" + familyDesc.getFamilyName() + "]在表[" + tableName + "]中已经存在");
            }
            HColumnDescriptor columnDescriptor = convertToColumnFamilyDescriptor(familyDesc);
            admin.addColumn(TableName.valueOf(tableName), columnDescriptor);
            return true;
        });
    }

    @Override
    public boolean deleteFamily(String tableName, String familyName, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            final HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
            if (!tableDescriptor.hasFamily(Bytes.toBytes(familyName))) {
                throw new HBaseOperationsException("列簇[" + familyName + "]在表[" + tableName + "]中不存在");
            }
            admin.deleteColumn(TableName.valueOf(tableName), Bytes.toBytes(familyName));
            return true;
        });
    }

    @Override
    public boolean modifyFamily(String tableName, ColumnFamilyDesc familyDesc, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
            final HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));

            if (!tableDescriptor.hasFamily(Bytes.toBytes(familyDesc.getFamilyName()))) {
                throw new HBaseOperationsException("待修改列簇[" + familyDesc.getFamilyName() + "]不存在");
            }
            HColumnDescriptor columnDescriptor = tableDescriptor.getFamily(Bytes.toBytes(familyDesc.getFamilyName()));
            boolean change = false;

            if (familyDesc.getMinVersions() != null && columnDescriptor.getMinVersions() != familyDesc.getMinVersions()) {
                columnDescriptor.setMinVersions(familyDesc.getMinVersions());
                change = true;
            }
            if (familyDesc.getVersions() != null && columnDescriptor.getMaxVersions() != familyDesc.getVersions()) {
                columnDescriptor.setMaxVersions(familyDesc.getVersions());
                change = true;
            }
            if (familyDesc.getTimeToLive() != null && columnDescriptor.getTimeToLive() != familyDesc.getTimeToLive()) {
                columnDescriptor.setTimeToLive(familyDesc.getTimeToLive());
                change = true;
            }
            if (familyDesc.getCompressionType() != null && !columnDescriptor.getCompressionType().getName().equalsIgnoreCase(familyDesc.getCompressionType())) {
                columnDescriptor.setCompressionType(Compression.Algorithm.valueOf(familyDesc.getCompressionType()));
                change = true;
            }
            if (familyDesc.getReplicationScope() != null && columnDescriptor.getScope() != familyDesc.getReplicationScope()) {
                columnDescriptor.setScope(familyDesc.getReplicationScope());
                change = true;
            }
            if (familyDesc.getBlockSize() != null && columnDescriptor.getBlocksize() != familyDesc.getBlockSize()) {
                columnDescriptor.setBlocksize(familyDesc.getBlockSize());
                change = true;
            }
            if (familyDesc.isBlockCache() != null && columnDescriptor.isBlockCacheEnabled() != familyDesc.isBlockCache()) {
                columnDescriptor.setBlockCacheEnabled(familyDesc.isBlockCache());
                change = true;
            }
            if (familyDesc.isInMemory() != null && columnDescriptor.isInMemory() != familyDesc.isInMemory()) {
                columnDescriptor.setInMemory(familyDesc.isInMemory());
                change = true;
            }
            if (change) {
                if (isAsync) {
                    admin.modifyColumn(TableName.valueOf(tableName), columnDescriptor);
                } else {
                    admin.modifyColumn(TableName.valueOf(tableName), columnDescriptor);
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
        return modifyTableReplicationScope(tableName, ENABLE_REPLICATION_SCOPE, families);
    }

    @Override
    public boolean enableReplicationScopeAsync(String tableName, List<String> families) {
        return false;
    }

    @Override
    public boolean disableReplicationScope(String tableName, List<String> families, boolean isAsync) {
        return modifyTableReplicationScope(tableName, DISABLE_REPLICATION_SCOPE, families);
    }

    @Override
    public boolean disableReplicationScopeAsync(String tableName, List<String> families) {
        return false;
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
            admin.createNamespace(namespaceDescriptor);
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
            final HTableDescriptor[] tableDescriptors = admin.listTableDescriptorsByNamespace(namespaceName);
            if (tableDescriptors != null && tableDescriptors.length > 0) {
                throw new HBaseOperationsException("命名空间[" + namespaceName + "]下存在表，不能被删除");
            }
            admin.deleteNamespace(namespaceName);
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
            tableIsNotExistsThrowError(admin, tableName);
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
    public boolean snapshot(SnapshotDesc snapshotDesc, boolean isAsync) {
        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, snapshotDesc.getTableName());
            admin.snapshot(snapshotDesc.getSnapshotName(), TableName.valueOf(snapshotDesc.getTableName()));
            return true;
        });
    }

    @Override
    public boolean restoreSnapshot(String snapshotName, boolean isAsync) {
        return this.execute(admin -> {
            admin.restoreSnapshot(snapshotName);
            return true;
        });
    }

    @Override
    public boolean cloneSnapshot(String snapshotName, String tableName, boolean isAsync) {
        return this.execute(admin -> {
            tableIsExistsThrowError(admin, tableName);
            admin.cloneSnapshot(snapshotName, TableName.valueOf(tableName));
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
    @SuppressWarnings("unchecked")
    protected HTableDescriptor convertToTableDescriptor(HTableDesc tableDesc) {
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableDesc.getTableName()));
        if (tableDesc.getMaxFileSize() != null) {
            tableDescriptor.setMaxFileSize(tableDesc.getMaxFileSize());
        }
        if (tableDesc.isReadOnly() != null) {
            tableDescriptor.setReadOnly(tableDesc.isReadOnly());
        }
        if (tableDesc.getMemStoreFlushSize() != null) {
            tableDescriptor.setMemStoreFlushSize(tableDesc.getMemStoreFlushSize());
        }
        if (tableDesc.isCompactionEnabled() != null) {
            tableDescriptor.setCompactionEnabled(tableDesc.isCompactionEnabled());
        }
        if (tableDesc.columnFamilyIsEmpty()) {
            throw new HBaseOperationsException("请为表[" + tableDesc.getTableName() + "]指定一个或多个列簇");
        }
        tableDesc.checkHasSameColumnFamily();
        if (tableDesc.getTableProps() != null && !tableDesc.getTableProps().isEmpty()) {
            tableDesc.getTableProps().forEach(tableDescriptor::setValue);
        }

        for (ColumnFamilyDesc familyDesc : tableDesc.getColumnFamilyDescList()) {
            tableDescriptor.addFamily(convertToColumnFamilyDescriptor(familyDesc));
        }
        return tableDescriptor;
    }

    @Override
    protected <T> List<HTableDesc> convertToTableDescList(List<T> tList) {
        return tList.stream().map(this::convertToTableDesc).collect(Collectors.toList());
    }

    @Override
    protected <T> HTableDesc convertToTableDesc(T t) {
        HTableDescriptor tableDescriptor = (HTableDescriptor) t;
        final Map<ImmutableBytesWritable, ImmutableBytesWritable> values = tableDescriptor.getValues();
        final Map<String, String> props = new HashMap<>(2);

        if (values != null && !values.isEmpty()) {
            values.forEach((key, value) -> props.put(Bytes.toString(key.get()), Bytes.toString(value.get())));
        }

        return new HTableDesc.Builder()
                .tableName(tableDescriptor.getTableName().getNameAsString())
                .maxFileSize(tableDescriptor.getMaxFileSize())
                .readOnly(tableDescriptor.isReadOnly())
                .memStoreFlushSize(tableDescriptor.getMemStoreFlushSize())
                .compactionEnabled(tableDescriptor.isCompactionEnabled())
                .tableProps(props)
                .columnFamilyDescList(convertToColumnFamilyDescList(Arrays.asList(tableDescriptor.getFamilies().toArray())))
                .build();
    }

    @Override
    protected <CF> List<ColumnFamilyDesc> convertToColumnFamilyDescList(List<CF> fList) {
        List<ColumnFamilyDesc> families = new ArrayList<>(fList.size());
        for (CF cf : fList) {
            HColumnDescriptor family = (HColumnDescriptor) cf;
            ColumnFamilyDesc familyDesc = new ColumnFamilyDesc.Builder()
                    .familyName(family.getNameAsString())
                    .versions(family.getMaxVersions())
                    .minVersions(family.getMinVersions())
                    .timeToLive(family.getTimeToLive())
                    .compressionType(family.getCompressionType().getName().toUpperCase())
                    .replicationScope(family.getScope())
                    .blockSize(family.getBlocksize())
                    .blockCache(family.isBlockCacheEnabled())
                    .inMemory(family.isInMemory())
                    .build();
            families.add(familyDesc);
        }
        return families;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected HColumnDescriptor convertToColumnFamilyDescriptor(ColumnFamilyDesc familyDesc) {
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(familyDesc.getFamilyName());
        if (familyDesc.getReplicationScope() != null) {
            columnDescriptor.setScope(familyDesc.getReplicationScope());
        }
        if (familyDesc.getVersions() != null) {
            columnDescriptor.setMaxVersions(familyDesc.getVersions());
        }
        if (familyDesc.getMinVersions() != null) {
            columnDescriptor.setMinVersions(familyDesc.getMinVersions());
        }
        if (familyDesc.getTimeToLive() != null) {
            columnDescriptor.setTimeToLive(familyDesc.getTimeToLive());
        }
        if (familyDesc.getCompressionType() != null) {
            columnDescriptor.setCompressionType(Compression.Algorithm.valueOf(familyDesc.getCompressionType()));
        }
        if (familyDesc.getBlockSize() != null) {
            columnDescriptor.setBlocksize(familyDesc.getBlockSize());
        }
        if (familyDesc.isBlockCache() != null) {
            columnDescriptor.setBlockCacheEnabled(familyDesc.isBlockCache());
        }
        if (familyDesc.isInMemory() != null) {
            columnDescriptor.setInMemory(familyDesc.isInMemory());
        }
        return columnDescriptor;
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
            if (mergeRegionsNum % 2 != 0) {
                mergeRegionsNum = mergeRegionsNum - 1;
            }
            for (int i = 0, j = 1; i < mergeRegionsNum - 1; i += 2, j += 2) {
                byte[] firstByteRegionName = regions[i];
                byte[] secondByteRegionName = regions[j];
                admin.mergeRegions(firstByteRegionName, secondByteRegionName, force);
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

        if (families == null || families.isEmpty()) {
            throw new HBaseOperationsException("请传入一个或多个待修改的列簇");
        }

        return this.execute(admin -> {
            tableIsNotExistsThrowError(admin, tableName);
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
            Summary clusterSummary = new Summary(currentTime, version, clusterId, liveServers + deadServers,
                    liveServers, deadServers, namespaceCount, tableCount, snapshotCount, regionCount, ritCount, averageLoad, aggregateRequestPerSecond);
            List<String> liveServerNames = clusterStatus.getServers().stream().map(ServerName::getServerName).collect(Collectors.toList());
            List<String> deadServerNames = clusterStatus.getDeadServerNames().stream().map(ServerName::getServerName).collect(Collectors.toList());
            clusterSummary.setLiveServerNames(liveServerNames);
            clusterSummary.setDeadServerNames(deadServerNames);
            return clusterSummary;
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
                    .notEqual(new FieldValue(HMHBaseConstants.DEFAULT_SYS_TABLE_NAMESPACE, FieldValueType.STRING));
            recordFilters.add(recordFilter);
            ClusterStatus clusterStatus = admin.getClusterStatus();
            List<Record> records = Mode.TABLE.getRecords(clusterStatus);

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
            ClusterStatus clusterStatus = admin.getClusterStatus();
            List<Record> records = Mode.TABLE.getRecords(clusterStatus);
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
                .equal(new FieldValue(HMHBaseConstants.getNamespaceName(tableName), FieldValueType.STRING));
        RecordFilter tableFilter = RecordFilter.newBuilder(Field.TABLE, false)
                .equal(new FieldValue(HMHBaseConstants.getTableName(tableName), FieldValueType.STRING));
        recordFilters.add(namespaceFilter);
        recordFilters.add(tableFilter);
        return recordFilters;
    }
}
