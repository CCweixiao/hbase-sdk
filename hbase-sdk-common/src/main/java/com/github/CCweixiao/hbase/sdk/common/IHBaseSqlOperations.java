package com.github.CCweixiao.hbase.sdk.common;

import com.github.CCweixiao.hbase.sdk.common.model.HBaseCellResult;

import java.util.List;

/**
 * 定义HBase SQL的操作接口
 *
 * @author leojie 2020/11/28 8:29 下午
 */
public interface IHBaseSqlOperations {

    /**
     * 输入hsql 查询数据，返回HBaseCellResult结合
     *
     * @param hsql hsql
     * @return 数据集合
     */
    List<List<HBaseCellResult>> select(String hsql);

    /**
     * 数据插入
     *
     * @param hql HBase SQL
     */
    void insert(String hql);

    /**
     * 数据删除
     *
     * @param hql HBase SQL
     */
    void delete(String hql);
}
