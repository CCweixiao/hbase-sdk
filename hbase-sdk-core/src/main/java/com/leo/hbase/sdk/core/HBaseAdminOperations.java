package com.leo.hbase.sdk.core;

import org.apache.hadoop.hbase.HTableDescriptor;

import java.util.List;

/**
 * <p>the interface is used to define operation of admin，被{@link HBaseTemplate}实现。</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/5/13 5:28 下午
 * @since 1.0
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
     * create table.
     *
     * @param tableDescriptor the descriptor of the table of you want to creating.
     * @param splits          splits info of the table.
     * @return created successfully or not.
     */
    boolean createTable(HTableDescriptor tableDescriptor, byte[][] splits);


}
