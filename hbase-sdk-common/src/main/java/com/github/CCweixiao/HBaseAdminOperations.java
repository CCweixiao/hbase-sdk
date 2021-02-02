package com.github.CCweixiao;

import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.NamespaceDesc;
import com.github.CCweixiao.model.SnapshotDesc;
import com.github.CCweixiao.model.TableDesc;
import com.github.CCweixiao.util.SplitGoEnum;

import java.util.List;

/**
 * <p>该接口用于定义管理员操作的API</p>
 *
 * @author leojie 2020/9/25 10:44 下午
 */
public interface HBaseAdminOperations {
    /**
     * 判断HBase表是否存在
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
    List<TableDesc> listTableDesc();

    /**
     * @param includeSysTables 是否包含系统表
     * @return 所有的HBase表及其描述
     */
    List<TableDesc> listTableDesc(boolean includeSysTables);

    /**
     * 正则查询HBase表及其描述
     *
     * @param regex            查询正则
     * @param includeSysTables 是否包含系统表
     * @return 筛选出的HBase表及其描述
     */
    List<TableDesc> listTableDesc(String regex, boolean includeSysTables);

    /**
     * 获取某一命名空间下的所有表信息
     *
     * @param namespaceName 命名空间名称
     * @return 所有表信息
     */
    List<TableDesc> listTableDescByNamespace(final String namespaceName);

    /**
     * 获取所有表名
     *
     * @return 所有表名
     */
    List<String> listTableNames();

    /**
     * 获取所有表名
     *
     * @param includeSysTables 是否包含系统表
     * @return 所有表名
     */
    List<String> listTableNames(boolean includeSysTables);

    /**
     * 获取所有表名
     *
     * @param regex            查询正则
     * @param includeSysTables 是否包含系统表
     * @return 所有表名
     */
    List<String> listTableNames(String regex, boolean includeSysTables);

    /**
     * 获取某一命名空间下的所有表名
     *
     * @param namespaceName 命名空间名称
     * @return 所有表名
     */
    List<String> listTableNamesByNamespace(final String namespaceName);

    /**
     * 获取某张表所有的列簇信息
     *
     * @return 所有的列簇信息
     */
    List<FamilyDesc> listFamilyDesc(String tableName);

    /**
     * 获取某一张表的描述信息
     *
     * @param tableName 表名
     * @return 表描述
     */
    TableDesc getTableDesc(final String tableName);

    /**
     * 创建表，以默认的方式
     *
     * @param tableDesc 表的描述信息
     * @return 表是否被创建成功
     */
    boolean createTable(final TableDesc tableDesc);

    /**
     * 创建表，预分区
     *
     * @param tableDesc  表的描述信息
     * @param startKey   预分区开始的key
     * @param endKey     预分区结束的key
     * @param numRegions 需要的预分区个数
     * @param isAsync    是否是异步的方式
     * @return 表是否被创建成功
     */
    boolean createTable(final TableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync);

    /**
     * 创建表，预分区
     *
     * @param tableDesc 表的描述信息
     * @param splitKeys 需要划分的预分区key
     * @param isAsync   是否是异步的方式
     * @return 表是否被创建成功
     */
    boolean createTable(final TableDesc tableDesc, String[] splitKeys, boolean isAsync);

    /**
     * 创建表，预分区
     *
     * @param tableDesc   表的描述信息
     * @param splitGoEnum 预分区方式
     * @param numRegions  需要的预分区个数
     * @param isAsync     是否是异步的方式
     * @return 表是否被创建成功
     */
    boolean createTable(final TableDesc tableDesc, SplitGoEnum splitGoEnum, int numRegions, boolean isAsync);

    /**
     * 修改表
     *
     * @param tableDesc 表描述
     * @return 表是否被修改成功
     */
    boolean modifyTableProps(final TableDesc tableDesc);

    /**
     * 修改表名
     *
     * @param oldTableName   旧表名
     * @param newTableName   新表名
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
     * 清空表
     *
     * @param tableName      表名
     * @param preserveSplits 是否保留预分区信息
     * @return 表是否被成功清空
     */
    boolean truncateTable(final String tableName, final boolean preserveSplits);

    /**
     * 启用表
     *
     * @param tableName 表名
     * @param isAsync   是否是异步的
     * @return 启用表是否成功
     */
    boolean enableTable(String tableName, boolean isAsync);


    /**
     * 禁用表
     *
     * @param tableName 表名
     * @param isAsync   是否是异步的
     * @return 禁用表是否成功
     */
    boolean disableTable(String tableName, boolean isAsync);


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
     * 为某张表新增一个列簇
     *
     * @param tableName  表名
     * @param familyDesc 列簇定义信息
     * @return 新增列簇是否成功
     */
    boolean addFamily(final String tableName, final FamilyDesc familyDesc);


    /**
     * 删除一个列簇
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @return 删除列簇是否成功
     */
    boolean deleteFamily(final String tableName, final String familyName);

    /**
     * 修改一个列簇
     *
     * @param tableName  表名
     * @param familyDesc 列簇描述
     * @return 修改列簇是否成功
     */
    boolean modifyFamily(final String tableName, final FamilyDesc familyDesc);

    /**
     * 启用replication
     *
     * @param tableName 表名
     * @param families  列簇名
     * @return 启用replication是否成功
     */
    boolean enableReplicationScope(String tableName, List<String> families);


    /**
     * 禁用replication
     *
     * @param tableName 表名
     * @param families  列簇名
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
     * 创建一个命名空间
     *
     * @param namespaceDesc 该命名空间的描述
     * @return namespace是否创建成功
     */
    boolean createNamespace(final NamespaceDesc namespaceDesc);

    /**
     * 判断命名空间是否存在
     *
     * @param namespaceName 命名空间名称
     * @return 是否存在
     */
    boolean namespaceIsExists(final String namespaceName);


    /**
     * 删除命名空间
     *
     * @param namespaceName 命名空间名称
     * @return namespace是否删除成功
     */
    boolean deleteNamespace(final String namespaceName);

    /**
     * 获取一个命名空间的描述
     *
     * @param namespaceName 命名空间的名称
     * @return 该命名空间的描述
     */
    NamespaceDesc getNamespaceDesc(final String namespaceName);

    /**
     * 获取所有命名空间的描述
     *
     * @return 所有命名空间的描述
     */
    List<NamespaceDesc> listNamespaceDesc();

    /**
     * 获取HBase所有的命名空间名称
     *
     * @return 所有的命名空间名称
     */
    List<String> listNamespaceNames();

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
    List<SnapshotDesc> listSnapshots();

    /**
     * 创建快照
     *
     * @param snapshotDesc 快照信息描述
     * @return 创建快照是否成功
     */
    boolean snapshot(SnapshotDesc snapshotDesc);

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
