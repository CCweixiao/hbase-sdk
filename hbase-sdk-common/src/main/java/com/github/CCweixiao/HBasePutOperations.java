package com.github.CCweixiao;

import java.util.List;
import java.util.Map;

/**
 * 定义HBase数据保存相关的操作API
 *
 * @author leojie 2020/12/31 11:12 下午
 */
public interface HBasePutOperations {
    /**
     * 保存单条数据，构造Map类型的数据参数，例如： {"INFO1:NAME": "leo", "INFO2:AGE": 18}
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @param data      需要保存的数据. 例如： {"INFO1:NAME": "leo", "INFO2:AGE": 18}
     */
    void save(String tableName, String rowKey, Map<String, Object> data);

    /**
     * 保存数据，构造一个Java数据实体映射
     *
     * @param t   Java数据实体对象.
     * @param <T> 泛型类型.
     * @return 保存成功的数据对象.
     * @throws Exception 抛出异常
     */
    <T> T save(T t) throws Exception;

    /**
     * 批量保存数据，构造Map类型结构的列表数据参数，例如：
     * {"row1": {"INFO:NAME": "leo1", "INFO:AGE": 18}, "row2": {"INFO:NAME": "leo2", "INFO:AGE": 17}}
     *
     * @param tableName 表名
     * @param data      需要保存的数据. 例如： {"row1": {"INFO:NAME": "leo1"}, "row2": {"INFO:NAME": "leo2"}}
     */
    void saveBatch(String tableName, Map<String, Map<String, Object>> data);

    /**
     * 批量保存数据，构造一个Java数据实体映射列表
     *
     * @param lst Java数据实体对象列表.
     * @param <T> 泛型数据类型.
     * @return 保存成功的数据列表中的第一条数据对象.
     * @throws Exception 抛出异常
     */
    <T> T saveBatch(List<T> lst) throws Exception;


}
