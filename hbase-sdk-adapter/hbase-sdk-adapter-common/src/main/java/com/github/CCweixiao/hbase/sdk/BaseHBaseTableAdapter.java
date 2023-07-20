package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.adapter.IHBaseTableDeleteAdapter;
import com.github.CCweixiao.hbase.sdk.adapter.IHBaseTableGetAdapter;
import com.github.CCweixiao.hbase.sdk.adapter.IHBaseTablePutAdapter;
import com.github.CCweixiao.hbase.sdk.adapter.IHBaseTableScanAdapter;
import com.github.CCweixiao.hbase.sdk.common.IHBaseTableOpAdapter;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import com.github.CCweixiao.hbase.sdk.common.reflect.HBaseTableMeta;
import com.github.CCweixiao.hbase.sdk.common.reflect.ReflectFactory;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.yetus.audience.InterfaceAudience;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <p>the abstract class of HBaseTemplate,</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
@InterfaceAudience.Private
public abstract class BaseHBaseTableAdapter extends AbstractHBaseBaseAdapter implements IHBaseTableOpAdapter, IHBaseTableGetAdapter, IHBaseTablePutAdapter, IHBaseTableDeleteAdapter, IHBaseTableScanAdapter {
    public BaseHBaseTableAdapter(Configuration configuration) {
        super(configuration);
    }

    public BaseHBaseTableAdapter(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public BaseHBaseTableAdapter(Properties properties) {
        super(properties);
    }


    @Override
    public void save(String tableName, String rowKey, Map<String, Object> data) {
        this.executeSave(tableName, buildPutCondition(rowKey, data));
    }

    @Override
    public <T> void save(T t) {
        final Class<?> clazz = t.getClass();
        HBaseTableMeta tableMeta = ReflectFactory.getHBaseTableMeta(clazz);
        this.executeSave(tableMeta.getTableName(), new Put(buildPut(t)));
    }

    @Override
    public int saveBatch(String tableName, Map<String, Map<String, Object>> data) {
        if (data == null || data.isEmpty()) {
            return 0;
        }
        List<Mutation> puts = new ArrayList<>(data.size());
        data.forEach((row, d) -> {
            if (d != null && !d.isEmpty()) {
                puts.add(buildPutCondition(row, d));
            }
        });
        this.executeSaveBatch(tableName, puts);
        return puts.size();
    }

    @Override
    public <T> int saveBatch(List<T> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        final Class<?> clazz0 = list.get(0).getClass();
        HBaseTableMeta tableMeta = ReflectFactory.getHBaseTableMeta(clazz0);
        List<Mutation> putList = new ArrayList<>(list.size());
        for (T t : list) {
            putList.add(new Put(buildPut(t)));
        }
        this.executeSaveBatch(tableMeta.getTableName(), putList);
        return list.size();
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, Class<T> clazz) {
        return this.getRow(rowKey, null, null, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, Class<T> clazz) {
        return this.getRow(rowKey, familyName, null, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz) {
        String tableName = ReflectFactory.getHBaseTableMeta(clazz).getTableName();
        return this.execute(tableName, table -> {
            Get get = buildGetCondition(rowKey, familyName, qualifiers, 1);
            Result result = checkGetAndReturnResult(get, table);
            if (result == null) {
                return null;
            }
            return mapperRowToT(result, clazz);
        });
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, RowMapper<T> rowMapper) {
        return this.getRow(tableName, rowKey, null, null, rowMapper);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, RowMapper<T> rowMapper) {
        return this.getRow(tableName, rowKey, familyName, null, rowMapper);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return this.execute(tableName, table -> {
            Get get = buildGetCondition(rowKey, familyName, qualifiers, 1);
            Result result = checkGetAndReturnResult(get, table);
            if (result == null) {
                return null;
            }
            return rowMapper.mapRow(result, 0);
        });
    }

    @Override
    public HBaseRowData getToRowData(String tableName, Get get) {
        return this.execute(tableName, table -> {
            Result result = checkGetAndReturnResult(get, table);
            if (result == null) {
                return null;
            }
            return convertResultToHBaseColData(result);
        }).orElse(HBaseRowData.empty());
    }

    @Override
    public HBaseRowData getToRowData(String tableName, String rowKey) {
        return this.getToRowData(tableName, rowKey, null, null);
    }

    @Override
    public HBaseRowData getToRowData(String tableName, String rowKey, String familyName) {
        return this.getToRowData(tableName, rowKey, familyName, null);
    }

    @Override
    public HBaseRowData getToRowData(String tableName, String rowKey, String familyName,
                                                 List<String> qualifiers) {
        Get get = buildGetCondition(rowKey, familyName, qualifiers, 1);
        return this.getToRowData(tableName, get);
    }

    @Override
    public HBaseRowDataWithMultiVersions getToRowDataWithMultiVersions(String tableName, String rowKey, int versions) {
        return getToRowDataWithMultiVersions(tableName, rowKey, "", null, versions);
    }

    @Override
    public HBaseRowDataWithMultiVersions getToRowDataWithMultiVersions(String tableName, String rowKey, String familyName, int versions) {
        return getToRowDataWithMultiVersions(tableName, rowKey, familyName, null, versions);
    }

    @Override
    public HBaseRowDataWithMultiVersions getToRowDataWithMultiVersions(String tableName, String rowKey, String familyName, List<String> qualifiers, int versions) {
        Get get = buildGetCondition(rowKey, familyName, qualifiers, versions);
        return this.getToRowDataWithMultiVersions(tableName, get, versions);
    }

    @Override
    public HBaseRowDataWithMultiVersions getToRowDataWithMultiVersions(String tableName, Get get, int versions) {
        return this.execute(tableName, table -> {
            Result result = checkGetAndReturnResult(get, table);
            if (result == null) {
                return null;
            }
            return convertResultsToHBaseColDataListWithMultiVersion(result, versions);
        }).orElse(HBaseRowDataWithMultiVersions.empty());
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, Class<T> clazz) {
        return getRows(rowKeys, null, null, clazz);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, Class<T> clazz) {
        return getRows(rowKeys, familyName, null, clazz);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, List<String> qualifiers, Class<T> clazz) {
        String tableName = ReflectFactory.getHBaseTableMeta(clazz).getTableName();
        return this.execute(tableName, table -> {
            List<Get> gets = buildBatchGetCondition(rowKeys, familyName, qualifiers);
            Result[] results = checkBatchGetAndReturnResult(gets, table);
            if (results == null) {
                return null;
            }
            List<T> data = new ArrayList<>();
            for (Result result : results) {
                data.add(mapperRowToT(result, clazz));
            }
            return data;
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, RowMapper<T> rowMapper) {
        return getRows(tableName, rowKeys, null, null, rowMapper);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, RowMapper<T> rowMapper) {
        return getRows(tableName, rowKeys, familyName, null, rowMapper);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        List<Get> gets = buildBatchGetCondition(rowKeys, familyName, qualifiers);
        return this.getRowsToRowData(tableName, gets, rowMapper);
    }

    @Override
    public <T> List<T> getRowsToRowData(String tableName, List<Get> gets, RowMapper<T> rowMapper) {
        return this.execute(tableName, table -> {
            Result[] results = checkBatchGetAndReturnResult(gets, table);
            if (results == null) {
                return null;
            }
            List<T> data = new ArrayList<>(results.length);
            for (Result result : results) {
                data.add(rowMapper.mapRow(result, 0));
            }
            return data;
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public List<HBaseRowData> getRowsToRowData(String tableName, List<Get> gets) {
        return this.getRowsToRowData(tableName, gets, new RowMapper<HBaseRowData>() {
            @Override
            public <R> HBaseRowData mapRow(R r, int rowNum) throws Exception {
                Result result = (Result) r;
                return convertResultToHBaseColData(result);
            }
        });
    }

    @Override
    public List<HBaseRowData> getToRowsData(String tableName, List<String> rowKeys) {
        return null;
    }

    @Override
    public List<HBaseRowData> getToRowsData(String tableName, List<String> rowKeys, String familyName) {
        return null;
    }

    @Override
    public List<HBaseRowData> getToRowsData(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers) {
        return null;
    }

    @Override
    public <T> List<T> scan(ScanParams scanParams, Class<T> clazz) {
        String tableName = ReflectFactory.getHBaseTableMeta(clazz).getTableName();
        return this.execute(tableName, table -> {
            try (ResultScanner scanner = table.getScanner(buildScan(scanParams))) {
                List<T> rs = new ArrayList<>();
                for (Result result : scanner) {
                    rs.add(mapperRowToT(result, clazz));
                }
                return rs;
            }
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public List<HBaseRowData> scan(String tableName, ScanParams scanParams) {
        List<HBaseRowData> rowDataList = new ArrayList<>(4);
        return this.execute(tableName, table -> {
            try (ResultScanner scanner = table.getScanner(buildScan(scanParams))) {
                for (Result result : scanner) {
                    rowDataList.add(convertResultToHBaseColData(result));
                }
                return rowDataList;
            }
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public <T> List<T> scan(String tableName, ScanParams scanParams, RowMapper<T> rowMapper) {
        return this.execute(tableName, table -> {
            try (ResultScanner scanner = table.getScanner(buildScan(scanParams))) {
                List<T> rs = new ArrayList<>();
                int rowNum = 0;
                for (Result result : scanner) {
                    rs.add(rowMapper.mapRow(result, rowNum++));
                }
                return rs;
            }
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public <T> void delete(T t) {
        if (t == null) {
            return;
        }
        final Class<?> clazz = t.getClass();
        HBaseTableMeta tableMeta = ReflectFactory.getHBaseTableMeta(clazz);
        this.executeDelete(tableMeta.getTableName(), new Delete(buildDelete(t)));
    }

    @Override
    public void delete(String tableName, String rowKey) {
        this.delete(tableName, rowKey, null, new ArrayList<>());
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName) {
        this.delete(tableName, rowKey, familyName, new ArrayList<>());
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        if (StringUtil.isBlank(tableName)) {
            throw new IllegalArgumentException("the table name is not empty.");
        }
        if (StringUtil.isBlank(rowKey)) {
            throw new IllegalArgumentException("the row key of the table will be deleted is not empty.");
        }
        Delete delete = buildDeleteCondition(rowKey, familyName, qualifiers);
        this.executeDelete(tableName, delete);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, String... qualifiers) {
        if (qualifiers == null || qualifiers.length == 0) {
            this.delete(tableName, rowKey, familyName);
        } else {
            this.delete(tableName, rowKey, familyName, Arrays.asList(qualifiers));
        }
    }

    @Override
    public <T> void deleteBatch(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        final Class<?> clazz0 = list.get(0).getClass();
        HBaseTableMeta tableMeta = ReflectFactory.getHBaseTableMeta(clazz0);
        List<Mutation> deleteList = new ArrayList<>(list.size());
        for (T t : list) {
            deleteList.add(new Delete(buildDelete(t)));
        }
        this.executeDeleteBatch(tableMeta.getTableName(), deleteList);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys) {
        this.deleteBatch(tableName, rowKeys, null, new ArrayList<>());
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName) {
        this.deleteBatch(tableName, rowKeys, familyName, new ArrayList<>());
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers) {
        if (StringUtil.isBlank(tableName)) {
            throw new IllegalArgumentException("the table name is not empty.");
        }
        if (rowKeys == null || rowKeys.isEmpty()) {
            throw new IllegalArgumentException("the row keys of the table will be deleted is not empty.");
        }
        List<Mutation> mutations = rowKeys.stream().map(rowKey -> buildDeleteCondition(rowKey, familyName, qualifiers)).collect(Collectors.toList());
        this.executeDeleteBatch(tableName, mutations);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, String... qualifiers) {
        if (qualifiers == null || qualifiers.length == 0) {
            this.deleteBatch(tableName, rowKeys, familyName);
        } else {
            this.deleteBatch(tableName, rowKeys, familyName, Arrays.asList(qualifiers));
        }
    }
}
