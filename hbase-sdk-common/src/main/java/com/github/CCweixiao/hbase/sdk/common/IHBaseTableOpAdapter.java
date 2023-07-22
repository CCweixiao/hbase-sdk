package com.github.CCweixiao.hbase.sdk.common;

import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowParam;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowsParam;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 定义HBase的数据操作接口
 *
 * @author leojie 2020/9/26 11:04 上午
 */
public interface IHBaseTableOpAdapter {

    /**
     * 保存数据，需构造Map类型的数据参数<br/>
     * 入参data的数据格式举例如下： <br/>
     * {"f1:name": "leo", "f2:age": 18} <br/>
     * <h3>tip:</h3>
     * <p>1. HBase数据都以字节形成存储，存取都需要字段值的类型，才能对值做正确的类型转换，<br/>
     * 因此在此API的封装中，非字符串的基础类型数据，底层会被自动序列化成字符串类型。<br/>
     * 2. 字段名称的命名格式必须是：family:qualifier <br/>
     * 3. 复杂数据类型，如：List/Map/Java对象等，会先被格式化成JSON字符串后再进行存储，默认使用FastJson处理json格式数据</p>
     * <p>如果对数据类型有要求，请使用方法: {@link IHBaseTableOpAdapter#save(Object)} <br/><p/>
     *
     * @param tableName HBase表名
     * @param rowKey    指定row key，row key默认被限定必须使用String类型
     * @param data      需要保存的数据，自行构造Map<String, Object>结构，样例数据如：入参data的数据格式举例
     */
    void save(String tableName, String rowKey, Map<String, Object> data);

    /**
     * 批量保存数据，需构造嵌套Map类型的入参<br/>
     * 例如：<br/>
     * {  <br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;"row_key1": {"f1:name": "leojie1", "f2:age": 18}, <br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;"row_key2": {"f1:name": "leojie2", "f2:age": 17} <br/>
     * } <br/>
     * <h3>tip:</h3>
     * <p>1. HBase数据都以字节形成存储，存取都需要字段值的类型，才能对值做正确的类型转换，<br/>
     * 因此在此API的封装中，非字符串的基础类型数据，底层会被自动序列化成字符串类型。<br/>
     * 2. 字段名称的命名格式必须是：family:qualifier <br/>
     * 3. 复杂数据类型，如：List/Map/Java对象等，会先被格式化成JSON字符串后再进行存储，默认使用FastJson处理json格式数据</p>
     * <p>如果对数据类型有要求，请使用方法: {@link IHBaseTableOpAdapter#saveBatch(List<Object>)} <br/><p/>
     *
     * @param tableName 表名
     * @param data      需要保存的数据. 如样例格式数据
     */
    void saveBatch(String tableName, Map<String, Map<String, Object>> data);

    /**
     * 保存数据，需构造一个Java对象来定义属性与HBase列族、字段的对应关系。<br/>
     * 例如：{@link com.github.CCweixiao.hbase.sdk.common.model.example.CityModel} <br/>
     *
     * @param t 数据实体对象
     * @see <a href="https://github.com/CCweixiao/hbase-manager/wiki/hbase%E2%80%90sdk-ORM%E7%89%B9%E6%80%A7%E4%BD%BF%E7%94%A8">ORM特性使用文档</a>
     */
    <T> void save(T t);

    /**
     * 构造一个Java数据实体对象来绑定属性与HBase列族、字段的对应关系。<br/>
     * 在批量保存数据时，传入一个数据实体对象列表，列表为空时，无报错，也不会做任何操作。<br/>
     * 例如：{@link com.github.CCweixiao.hbase.sdk.common.model.example.CityModel} <br/>
     *
     * @param list 数据实体对象列表
     * @param <T>  泛型类型
     * @see <a href="https://github.com/CCweixiao/hbase-manager/wiki/hbase%E2%80%90sdk-ORM%E7%89%B9%E6%80%A7%E4%BD%BF%E7%94%A8">ORM特性使用文档</a>
     */
    <T> void saveBatch(List<T> list);

    /**
     * 构造一个{@link GetRowParam}参数对象，查询单条数据，不识别查询参数中的versions <br/>
     * 底层通过反射机制，自动把Result中的字段和值绑定到指定java对象的属性和值上
     *
     * @param getRowParam 查询参数
     * @param clazz       数据实体类型，如：CityModel.class
     * @param <T>         泛型类型
     * @return 结果数据，使用Optional来避免空指针异常
     */
    <T> Optional<T> getRow(GetRowParam getRowParam, Class<T> clazz);

    /**
     * 构造一个{@link GetRowParam}参数对象，查询单条数据，不识别查询参数中的versions <br/>
     * 自定义一个字段数据映射器：{@link com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper} <br/>
     * 来实现把Result的字段和值绑定到需要的容器中，比如：数据对象或Map
     *
     * @param tableName 表名
     * @param rowMapper RowMapper，即：数据字段映射器
     * @param <T>       泛型类型
     * @return 查询结果
     * @see <a href="https://github.com/CCweixiao/hbase-manager/wiki/hbase%E2%80%90sdk-ORM%E7%89%B9%E6%80%A7%E4%BD%BF%E7%94%A8">ORM特性使用文档</a>
     */
    <T> Optional<T> getRow(String tableName, GetRowParam getRowParam, RowMapper<T> rowMapper);

    /**
     * 构造一个{@link GetRowParam}参数对象，查询单条数据，不识别查询参数中的versions <br/>
     * 返回一个{@link HBaseRowData}对象，其格式如下：<br/>
     * {<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; rowKey: "row1",
     * &nbsp;&nbsp;&nbsp;&nbsp; "f1:name": {"value": "leo", "timestamp": 1667568422619},<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; "f1:age": {"value": "18", "timestamp": 1667568422619}
     * <br/>}
     *
     * @param tableName   表名
     * @param getRowParam get查询参数
     * @return 查询结果
     */
    HBaseRowData getRow(String tableName, GetRowParam getRowParam);

    /**
     * 构造{@link GetRowParam}条件来查询数据 <br/>
     * 返回一个数据对象列表，结果集的数量根据指定的查询版本数定，注意，不足版本数的字段值为null
     *
     * @param getRowParam get查询参数
     * @param clazz       数据实体类型，如：CityModel.class
     * @param <T>         泛型类型
     * @return 结果数据
     */
    <T> List<T> getWithMultiVersions(GetRowParam getRowParam, Class<T> clazz);

    /**
     * 构造{@link GetRowParam}条件来查询数据 <br/>
     * 自定义一个字段数据映射器：{@link com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper} <br/>
     * 来实现把Result的字段和值绑定到需要的容器中，比如：数据对象或Map <br/>
     * 结果集的数量根据指定的查询版本数定，注意，不足版本数的字段值为null
     *
     * @param tableName 表名
     * @param rowMapper RowMapper，即：数据字段映射器
     * @param <T>       泛型类型
     * @return 查询结果
     * @see <a href="https://github.com/CCweixiao/hbase-manager/wiki/hbase%E2%80%90sdk-ORM%E7%89%B9%E6%80%A7%E4%BD%BF%E7%94%A8">ORM特性使用文档</a>
     */
    <T> List<T> getWithMultiVersions(String tableName, GetRowParam getRowParam, RowMapper<T> rowMapper);


    /**
     * 构造{@link GetRowParam}条件来查询数据， <br/>
     * 返回一个{@link HBaseRowDataWithMultiVersions}对象，其格式如下：<br/>
     * {<br/>
     * HBaseRowDataWithMultiVersions: {<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;"rowKey": "1001", <br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;{<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"f1:id": [{"value": "i2", timestamp: 2}, {"value": "i1", timestamp: 1}],<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"f2:age": [{"value": 17, timestamp: 2}, {"value": 18, timestamp: 1}]<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
     * &nbsp;&nbsp;}<br/>
     * }<br/>
     * tag: 列表中数据，按照时间戳的新旧排序
     *
     * @param tableName   表名
     * @param getRowParam get查询条件列表
     * @return 查询结果集合，参考示例数据
     */
    HBaseRowDataWithMultiVersions getWithMultiVersions(String tableName, GetRowParam getRowParam);

    /**
     * 构造{@link GetRowsParam}查询多条rowKey的数据，默认只返回最新版本的数据 <br/>
     * 查询结果集，自动绑定到对象列表中
     *
     * @param getRowsParam getRows查询参数
     * @param clazz        数据实体类型，如：CityModel.class
     * @param <T>          泛型类型
     * @return 查询结果集
     */
    <T> List<T> getRows(GetRowsParam getRowsParam, Class<T> clazz);

    /**
     * 构造{@link GetRowsParam}查询多条rowKey的数据，默认只返回最新版本的数据 <br/>
     * 自定义一个字段数据映射器：{@link com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper} <br/>
     * 来实现把Result的字段和值绑定到需要的容器中，比如：数据对象或Map <br/>
     *
     * @param tableName     表名
     * @param getRowsParams getRows查询参数
     * @param rowMapper     自定义的RowMapper
     * @param <T>           泛型类型
     * @return 查询结果集
     */
    <T> List<T> getRows(String tableName, GetRowsParam getRowsParams, RowMapper<T> rowMapper);

    /**
     * 构造{@link GetRowsParam}查询多条rowKey的数据，默认只返回最新版本的数据 <br/>
     * 返回{@link HBaseRowData}对象列表，其格式如下：<br/>
     * [{<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; rowKey: "row1", <br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; "f1:name": {"value": "leo", "timestamp": 1667568422619},<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp; "f1:age": {"value": "18", "timestamp": 1667568422619}
     * <br/>}]
     *
     * @param tableName    表名
     * @param getRowsParam getRows查询参数
     * @return 查询结果集
     */
    List<HBaseRowData> getRows(String tableName, GetRowsParam getRowsParam);

    /**
     * 构造{@link com.github.CCweixiao.hbase.sdk.common.query.ScanParams}条件来扫描多条数据，默认只返回最新版本的数据 <br/>
     * 把查询结果Result的字段和值，自动绑定到指定的数据实体对象属性上， <br/>
     *
     * @param scanParams scan查询参数
     * @param clazz      数据实体类型
     * @param <T>        泛型类型
     * @return 结果数据集合
     */
    <T> List<T> scan(ScanParams scanParams, Class<T> clazz);

    /**
     * 构造{@link com.github.CCweixiao.hbase.sdk.common.query.ScanParams}条件来扫描多条数据，默认只返回最新版本的数据，<br/>
     * 并把查询结果Result的字段和值绑定到自定义的{@link com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper}字段转换器上
     *
     * @param tableName  表名
     * @param scanParams scan查询参数
     * @param rowMapper  自定义的RowMapper
     * @param <T>        泛型类型
     * @return 查询结果集
     */
    <T> List<T> scan(String tableName, ScanParams scanParams, RowMapper<T> rowMapper);

    /**
     * 构造{@link com.github.CCweixiao.hbase.sdk.common.query.ScanParams}条件来扫描多条数据，默认只返回最新版本的数据，<br/>
     * 并把查询结果Result的字段和值，转换为{@link com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData}列表
     *
     * @param tableName  表名
     * @param scanParams scan查询参数
     * @return 查询结果集
     */
    List<HBaseRowData> scan(String tableName, ScanParams scanParams);

    /**
     * 构造{@link com.github.CCweixiao.hbase.sdk.common.query.ScanParams}条件来扫描多条数据，默认只返回最新版本的数据，<br/>
     * 并把查询结果Result的字段和值，转换为{@link com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions}列表，
     *
     * @param tableName  表名
     * @param scanParams scan查询参数
     * @return 查询结果集
     */
    List<HBaseRowDataWithMultiVersions> scanWithMultiVersions(String tableName, ScanParams scanParams);

    /**
     * 传入数据实体类型，删除数据
     *
     * @param t   数据模型类
     * @param <T> 泛型类型
     */
    <T> void delete(T t);

    /**
     * 传入RowKey，删除数据
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     */
    void delete(String tableName, String rowKey);

    /**
     * 传入RowKey，删除指定列簇的数据
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     */
    void delete(String tableName, String rowKey, String familyName);

    /**
     * 传入RowKey，删除指定列簇，指定字段列表的数据
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 多个字段名
     */
    void delete(String tableName, String rowKey, String familyName, List<String> qualifiers);

    /**
     * 传入RowKey，删除指定列簇，指定字段列表的数据
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 多个字段名
     */
    void delete(String tableName, String rowKey, String familyName, String... qualifiers);

    /**
     * 传入数据实体类列表，批量删除数据
     *
     * @param list 数据模型类列表
     * @param <T>  泛型类型
     */
    <T> void deleteBatch(List<T> list);

    /**
     * 传入rowKey列表，批量删除数据
     *
     * @param tableName 表名
     * @param rowKeys   rowKey列表
     */
    void deleteBatch(String tableName, List<String> rowKeys);

    /**
     * 传入rowKey列表，批量删除指定列簇的所有数据
     *
     * @param tableName  表名
     * @param rowKeys    rowKey列表
     * @param familyName 列簇名
     */
    void deleteBatch(String tableName, List<String> rowKeys, String familyName);

    /**
     * 传入rowKey列表，批量删除指定列簇，以及该列簇下指定字段的所有数据
     *
     * @param tableName  表名
     * @param rowKeys    rowKey列表
     * @param familyName 列簇名
     * @param qualifiers 多个字段名
     */
    void deleteBatch(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers);

    /**
     * 传入rowKey列表，批量删除指定列簇，以及该列簇下指定字段的所有数据
     *
     * @param tableName  表名
     * @param rowKeys    rowKey列表
     * @param familyName 列簇名
     * @param qualifiers 多个字段名
     */
    void deleteBatch(String tableName, List<String> rowKeys, String familyName, String... qualifiers);

}
