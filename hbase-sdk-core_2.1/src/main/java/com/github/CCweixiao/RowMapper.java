package com.github.CCweixiao;

import org.apache.hadoop.hbase.client.Result;

/**
 * <p>结果集映射POJO</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface RowMapper<T> {
    /**
     * Result 结果映射实体字段
     *
     * @param result Result object
     * @param rowNum 行数
     * @return 实体对象
     * @throws Exception 异常抛出
     */
    T mapRow(Result result, int rowNum) throws Exception;
}
