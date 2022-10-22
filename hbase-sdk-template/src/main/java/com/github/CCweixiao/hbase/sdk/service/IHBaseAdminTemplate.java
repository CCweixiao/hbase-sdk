package com.github.CCweixiao.hbase.sdk.service;

import java.util.List;

/**
 * @author leojie 2022/10/22 18:57
 */
public interface IHBaseAdminTemplate {
    /**
     * list of hbase tables
     * @return hbase tables
     */
    List<String> listTableNames();
}
