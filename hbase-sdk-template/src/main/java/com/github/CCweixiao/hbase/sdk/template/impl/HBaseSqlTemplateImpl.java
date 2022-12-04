package com.github.CCweixiao.hbase.sdk.template.impl;

import com.github.CCweixiao.hbase.sdk.HBaseSqlTemplate;
import com.github.CCweixiao.hbase.sdk.common.model.HBaseCellResult;
import com.github.CCweixiao.hbase.sdk.template.IHBaseSqlTemplate;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2022/11/27 17:14
 */
public class HBaseSqlTemplateImpl implements IHBaseSqlTemplate {

    private final HBaseSqlTemplate sqlOperations;
    public HBaseSqlTemplateImpl() {
        this.sqlOperations = new HBaseSqlTemplate();
    }

    @Override
    public List<List<HBaseCellResult>> select(String hsql, Map<String, Object> params) {
        return sqlOperations.select(hsql, params);
    }

    @Override
    public List<List<HBaseCellResult>> select(String hsql) {
        return sqlOperations.select(hsql);
    }

    @Override
    public void insert(String hql) {
        sqlOperations.insert(hql);
    }

    @Override
    public void delete(String hql) {
        sqlOperations.delete(hql);
    }
}
