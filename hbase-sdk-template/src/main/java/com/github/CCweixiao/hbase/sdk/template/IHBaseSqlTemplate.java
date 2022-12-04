package com.github.CCweixiao.hbase.sdk.template;

import com.github.CCweixiao.hbase.sdk.common.model.HBaseCellResult;

import java.util.List;
import java.util.Map;

/**
 * @author leojie 2022/11/27 17:14
 */
public interface IHBaseSqlTemplate {

    List<List<HBaseCellResult>> select(String hsql, Map<String, Object> params);

    List<List<HBaseCellResult>> select(String hsql);

    void insert(String hql);

    void delete(String hql);
}
