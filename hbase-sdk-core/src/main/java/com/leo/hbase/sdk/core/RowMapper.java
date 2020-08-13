package com.leo.hbase.sdk.core;

import org.apache.hadoop.hbase.client.Result;

/**
 * <p>结果集映射POJO</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/5/13 10:22 上午
 * @since 1.0
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
