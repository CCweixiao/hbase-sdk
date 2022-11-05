package com.github.CCweixiao.hbase.sdk.common.mapper;

/**
 * <p>结果集映射POJO</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface RowMapper<T> {
    /**
     * Result 结果映射实体字段
     *
     * @param r 查询结果的Result对象
     * @param rowNum 行数
     * @return 实体对象
     * @throws Exception 异常抛出
     */
    <R> T mapRow(R r, int rowNum) throws Exception;
}
