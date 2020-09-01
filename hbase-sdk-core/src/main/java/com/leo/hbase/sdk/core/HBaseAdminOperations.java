package com.leo.hbase.sdk.core;

import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.protobuf.generated.HBaseProtos;
import org.apache.hadoop.hbase.util.Pair;

import java.util.List;

/**
 * <p>该接口用于定义管理员操作的API，被{@link HBaseTemplate}实现。</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface HBaseAdminOperations {

    /**
     * HBase表是否存在
     *
     * @param tableName HBase表名
     * @return HBase表是否存在
     */
    boolean tableExists(String tableName);

    /**
     * 获取所有的HBase表及其描述
     *
     * @return 所有的HBase表及其描述
     */
    HTableDescriptor[] listTables();


    /**
     * 正则查询HBase表及其描述
     *
     * @param regex            查询正则
     * @param includeSysTables 是否包含系统表
     * @return 筛选出的HBase表及其描述
     */
    HTableDescriptor[] listTables(String regex, boolean includeSysTables);

    /**
     * 获取所有表名
     *
     * @return 表名数组
     */
    TableName[] listTableNames();

    /**
     * 正则筛选表名
     *
     * @param regex            正则表达式
     * @param includeSysTables 是否包含系统表
     * @return 表名数组
     */
    TableName[] listTableNames(final String regex, final boolean includeSysTables);

    /**
     * 获取某一张表的描述信息
     *
     * @param tableName 表名
     * @return 表描述
     */
    HTableDescriptor getTableDescriptor(final String tableName);

    /**
     * 创建表
     *
     * @param desc 表的描述信息
     * @return 表是否被创建成功
     */
    boolean createTable(final HTableDescriptor desc);

    /**
     * 创建表
     *
     * @param desc       表的描述信息
     * @param startKey   预分区开始key
     * @param endKey     预分区结束key
     * @param numRegions region数
     * @return 表是否被创建成功
     */
    boolean createTable(final HTableDescriptor desc, byte[] startKey, byte[] endKey, int numRegions);

    /**
     * 创建表
     *
     * @param desc      表的描述信息
     * @param splitKeys 指定的预分区key列表
     * @return 表是否被创建成功
     */
    boolean createTable(final HTableDescriptor desc, byte[][] splitKeys);

    /**
     * 异步方式创建表
     *
     * @param desc      表的描述信息
     * @param splitKeys 指定的预分区key列表
     * @return 表是否被创建成功
     */
    boolean createTableAsync(final HTableDescriptor desc, final byte[][] splitKeys);

    /**
     * 修改表
     *
     * @param tableName       表名
     * @param tableDescriptor 表描述
     * @return 表是否被修改成功
     */
    boolean modifyTable(final String tableName, final HTableDescriptor tableDescriptor);

    /**
     * 修改表名
     * @param oldTableName 旧表名
     * @param newTableName 新表名
     * @param deleteOldTable 是否删除旧表
     * @return 修改表名结果
     */
    boolean renameTable(String oldTableName, String newTableName, boolean deleteOldTable);

    /**
     * 删除表
     *
     * @param tableName 表名
     * @return 表是否被删除成功
     */
    boolean deleteTable(final String tableName);

    /**
     * 正则删除表
     *
     * @param regex 正则表达式
     * @return 不能被删除表的描述信息
     */
    HTableDescriptor[] deleteTables(String regex);

    /**
     * 清空表
     *
     * @param tableName      表名
     * @param preserveSplits 是否保留预分区
     * @return 表是否被成功清空
     */
    boolean truncateTable(final String tableName, final boolean preserveSplits);


    /**
     * 启用表
     *
     * @param tableName 表名
     * @return 启用表是否成功
     */
    boolean enableTable(String tableName);

    /**
     * 异步方式启用表
     *
     * @param tableName 表名
     * @return 启用表是否成功
     */
    boolean enableTableAsync(String tableName);

    /**
     * 禁用表
     *
     * @param tableName 表名
     * @return 禁用表是否成功
     */
    boolean disableTable(String tableName);

    /**
     * 异步方式禁用表
     *
     * @param tableName 表名
     * @return 禁用表是否成功
     */
    boolean disableTableAsync(String tableName);

    /**
     * 表是否被启用
     *
     * @param tableName 表名
     * @return 表是否被启用
     */
    boolean isTableEnabled(String tableName);

    /**
     * 表是否被禁用
     *
     * @param tableName 表名
     * @return 表是否被禁用
     */
    boolean isTableDisabled(String tableName);

    /**
     * 表是否可用，即该表所有的region是否可用
     *
     * @param tableName 表名
     * @return 表是否可用
     */
    boolean isTableAvailable(String tableName);

    /**
     * 针对异步操作，获取某张表的修改命令的执行状态，Pair.getFirst()表示哪些region已经被更新了，Pair.getSecond()表示总的region数
     *
     * @param tableName 表名
     * @return 异步命令的执行状态
     */
    Pair<Integer, Integer> getAlterStatus(final String tableName);

    /**
     * 为某张表新增一个列簇
     *
     * @param tableName 表名
     * @param column    列簇名
     * @return 新增列簇是否成功
     */
    boolean addColumn(final String tableName, final HColumnDescriptor column);

    /**
     * 删除一个列簇
     *
     * @param tableName  表名
     * @param columnName 列名
     * @return 删除列簇是否成功
     */
    boolean deleteColumn(final String tableName, final String columnName);

    /**
     * 修改一个列簇
     *
     * @param tableName  表名
     * @param descriptor 列簇描述
     * @return 修改列簇是否成功
     */
    boolean modifyColumn(final String tableName, final HColumnDescriptor descriptor);

    /**
     * 启用replication
     * @param tableName 表名
     * @param families 列簇名
     * @return 启用replication是否成功
     */
    boolean enableReplicationScope(String tableName, List<String> families);

    /**
     * 禁用replication
     * @param tableName 表名
     * @param families 列簇名
     * @return 禁用replication是否成功
     */
    boolean disableReplicationScope(String tableName, List<String> families);

    /**
     * 刷新表，异步操作
     *
     * @param tableName 表名
     * @return 刷新表命令是否执行成功
     */
    boolean flush(final String tableName);

    /**
     * compact操作
     *
     * @param tableName 表名
     * @return compact命令是否执行成功
     */
    boolean compact(final String tableName);

    /**
     * major compact操作
     *
     * @param tableName 表名
     * @return major compact命令是否执行成功
     */
    boolean majorCompact(final String tableName);

    /**
     * 获取集群所有节点的状态
     *
     * @return 集群所有节点的状态
     */
    ClusterStatus getClusterStatus();


    /**
     * 创建一个命名空间
     *
     * @param descriptor 该新命名空间的描述
     * @return namespace是否创建成功
     */
    boolean createNamespace(final NamespaceDescriptor descriptor);

    /**
     * 修改命名空间的描述信息
     *
     * @param descriptor 修改后命名空间的描述
     * @return namespace是否修改成功
     */
    boolean modifyNamespace(final NamespaceDescriptor descriptor);

    /**
     * 删除命名空间
     *
     * @param name 命名空间名称
     * @return namespace是否删除成功
     */
    boolean deleteNamespace(final String name);

    /**
     * 获取一个命名空间的描述
     *
     * @param name 命名空间名称
     * @return 该命名空间的描述
     */
    NamespaceDescriptor getNamespaceDescriptor(final String name);

    /**
     * 获取所有命名空间的描述
     *
     * @return 所有命名空间的描述
     */
    NamespaceDescriptor[] listNamespaceDescriptors();

    /**
     * 获取HBase所有的命名空间名称
     *
     * @return 所有的命名空间名称
     */
    List<String> listNamespaces();


    /**
     * 获取某一命名空间下的所有表描述
     *
     * @param name 命名空间名称
     * @return 所有表描述信息
     */
    HTableDescriptor[] listTableDescriptorsByNamespace(final String name);

    /**
     * 获取某一命名空间下的所有表名
     *
     * @param name 命名空间名称
     * @return 所有表名
     */
    List<String> listTableNamesByNamespace(final String name);


    /**
     * 获取某一张表的所有region信息
     *
     * @param tableName 表名
     * @return 该表的所有region信息
     */
    List<HRegionInfo> getTableRegions(final String tableName);

    /**
     * 获取某张表最后一次的major compact时间戳，如果是0则最新的HFile无法被找到
     *
     * @param tableName 表名
     * @return 时间戳
     */
    long getLastMajorCompactionTimestamp(final String tableName);

    /**
     * 获取某一个region最后major compaction的时间戳
     *
     * @param regionName 表名
     * @return 时间戳
     */
    long getLastMajorCompactionTimestampForRegion(final String regionName);

    /**
     * 查询所有快照
     *
     * @return 所有快照
     */
    List<HBaseProtos.SnapshotDescription> listSnapshots();

    /**
     * 正则查询所有快照
     *
     * @param regex 正则表达式
     * @return 所有快照
     */
    List<HBaseProtos.SnapshotDescription> listSnapshots(String regex);

    /**
     * 创建快照
     *
     * @param snapshotName 快照名称
     * @param tableName    表名
     * @return 创建快照是否成功
     */
    boolean snapshot(final String snapshotName, final String tableName);

    /**
     * 恢复快照
     *
     * @param snapshotName 快照名称
     * @return 恢复快照是否成功
     */
    boolean restoreSnapshot(final String snapshotName);

    /**
     * 克隆快照
     *
     * @param snapshotName 快照名称
     * @param tableName    表名
     * @return 克隆快照是否成功
     */
    boolean cloneSnapshot(final String snapshotName, final String tableName);

    /**
     * 删除快照
     *
     * @param snapshotName 快照名称
     * @return 删除快照是否成功
     */
    boolean deleteSnapshot(final String snapshotName);

    /**
     * 根据正则批量删除快照
     *
     * @param regex 正则
     * @return 删除快照是否成功
     */
    boolean deleteSnapshots(final String regex);


}
