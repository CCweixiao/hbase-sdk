package com.leo.hbase.sdk.core;

import com.leo.hbase.sdk.core.model.HFamilyBuilder;
import com.leo.hbase.sdk.core.model.HTableModel;
import com.leo.hbase.sdk.core.model.SnapshotModel;
import org.apache.hadoop.hbase.HTableDescriptor;

import java.util.List;

/**
 * <p>the interface is used to define operation of admin，被{@link HBaseTemplate}实现。</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface HBaseAdminOperations {

    /**
     * get all namespace name list.
     *
     * @return namespace name list
     */
    List<String> listNamespaces();

    /**
     * get all table name list.
     *
     * @return table name list
     */
    List<String> listTableNames();


    /**
     * create namespace
     *
     * @param namespace the name of namespace.
     * @return created successfully or not.
     */
    boolean createNamespace(String namespace);

    /**
     * drop namespace
     * @param namespace namespace
     * @return dropped successfully or not.
     */
    boolean deleteNamespace(String namespace);

    /**
     * table is exists or not
     *
     * @param tableName the name of HBase table.
     * @return table is exists or not
     */
    boolean tableIsExists(String tableName);

    /**
     * create a simple table.
     *
     * @param hTableModel data model of create table.
     * @return table is created successfully or not.
     */
    boolean createTable(HTableModel hTableModel);

    /**
     * create table with pre-split
     *
     * @param hTableModel data model of create table.
     * @param splitKeys   keys of pre-split
     * @return table is created successfully or not.
     */
    boolean createTable(HTableModel hTableModel, String[] splitKeys);

    /**
     * create table with start key, end key and a number of regions.
     *
     * @param hTableModel data model of create table.
     * @param startKey    start key
     * @param endKey      end key
     * @param numRegions  a number of regions
     * @return table is created successfully or not.
     */
    boolean createTable(HTableModel hTableModel, String startKey, String endKey, int numRegions);

    /**
     * get table descriptor
     *
     * @param tableName the name of HBase table.
     * @return table descriptor
     */
    String getTableDescriptor(String tableName);

    /**
     * the table is disabled or not.
     *
     * @param tableName the name of HBase table.
     * @return the table is disabled or not
     */
    boolean tableIsDisabled(String tableName);

    /**
     * disable table
     *
     * @param tableName the name of HBase table.
     * @return table is disabled successfully or not.
     */
    boolean disableTable(String tableName);

    /**
     * disable table async
     *
     * @param tableName the name of HBase table.
     * @return table is disabled successfully or not.
     */
    boolean disableTableAsync(String tableName);

    /**
     * table is enabled or not.
     *
     * @param tableName the name of HBase table.
     * @return table is enabled or not.
     */
    boolean tableIsEnabled(String tableName);

    /**
     * enable table
     *
     * @param tableName the name of HBase table.
     * @return table is enabled successfully or not.
     */
    boolean enableTable(String tableName);

    /**
     * enable table async.
     *
     * @param tableName the name of HBase table.
     * @return table is enabled successfully or not.
     */
    boolean enableTableAsync(String tableName);


    /**
     * delete table
     *
     * @param tableName the name of HBase table.
     * @return table is disabled successfully or not.
     */
    boolean deleteTable(String tableName);

    /**
     * alter table replication scope 0 to 1
     *
     * @param tableName the name of HBase table.
     * @param families  family names
     * @return alter table replication scope successfully or not.
     */
    boolean enableReplicationScope(String tableName, String... families);

    /**
     * alter table replication scope 1 to 0
     *
     * @param tableName the name of HBase table.
     * @param families  family names
     * @return alter table replication scope successfully or not.
     */
    boolean disableReplicationScope(String tableName, String... families);

    /**
     * alter table add family
     *
     * @param tableName the name of HBase table.
     * @param families  family names
     * @return add family successfully.
     */
    boolean addFamily(String tableName, HFamilyBuilder... families);

    /**
     * rename table
     *
     * @param oriTableName    The original table name.
     * @param targetTableName The target table name.
     * @param overwrite       is overwrite or not. when true, delete original table
     * @return rename table successfully or not
     */
    boolean renameTable(String oriTableName, String targetTableName, boolean overwrite);

    /**
     * get all snapshot names
     *
     * @return all snapshot names
     */
    List<SnapshotModel> listSnapshots();

    /**
     * get all snapshot names of one table.
     * @param tableName table name
     * @return all snapshots of the table
     */
    List<SnapshotModel> listSnapshots(String tableName);

    /**
     * create snapshot
     *
     * @param tableName    table name
     * @param snapshotName snapshot name
     * @return create successfully or not
     */
    boolean createSnapshot(String tableName, String snapshotName);


    /**
     * delete a snapshot.
     *
     * @param snapshotName snapshot name
     * @return delete snapshot or not
     */
    boolean deleteSnapshot(String snapshotName);




}
