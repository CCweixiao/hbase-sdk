package com.github.CCweixiao.hbase.sdk.adapter;

import com.github.CCweixiao.hbase.sdk.common.IHBaseTableOpAdapter;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowParam;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowsParam;
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
import org.apache.hadoop.hbase.client.Scan;
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
        this.executeSave(tableName, buildPut(rowKey, data));
    }

    @Override
    public <T> void save(T t) {
        final Class<?> clazz = t.getClass();
        HBaseTableMeta tableMeta = ReflectFactory.getHBaseTableMeta(clazz);
        this.executeSave(tableMeta.getTableName(), new Put(buildPut(t)));
    }

    @Override
    public void saveBatch(String tableName, Map<String, Map<String, Object>> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        List<Mutation> puts = new ArrayList<>(data.size());
        data.forEach((row, d) -> {
            if (d != null && !d.isEmpty()) {
                puts.add(buildPut(row, d));
            }
        });
        this.executeSaveBatch(tableName, puts);
    }

    @Override
    public <T> void saveBatch(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        final Class<?> clazz = list.get(0).getClass();
        HBaseTableMeta tableMeta = ReflectFactory.getHBaseTableMeta(clazz);
        List<Mutation> putList = new ArrayList<>(list.size());
        for (T t : list) {
            putList.add(new Put(buildPut(t)));
        }
        this.executeSaveBatch(tableMeta.getTableName(), putList);
    }

    @Override
    public <T> Optional<T> getRow(GetRowParam getRowParam, Class<T> clazz) {
        Get get = this.buildGet(getRowParam);
        return this.get(get, clazz);
    }

    @Override
    public <T> Optional<T> get(Get get, Class<T> clazz) {
        String tableName = ReflectFactory.getHBaseTableMeta(clazz).getTableName();
        return this.execute(tableName, table -> {
            Result result = checkGetAndReturnResult(get, table);
            if (result == null) {
                return null;
            }
            return mapperRowToT(result, clazz);
        });
    }

    @Override
    public <T> Optional<T> getRow(String tableName, GetRowParam getRowParam, RowMapper<T> rowMapper) {
        Get get = buildGet(getRowParam);
        return this.get(tableName, get, rowMapper);
    }

    @Override
    public <T> Optional<T> get(String tableName, Get get, RowMapper<T> rowMapper) {
        return this.execute(tableName, table -> {
            Result result = checkGetAndReturnResult(get, table);
            if (result == null) {
                return null;
            }
            return rowMapper.mapRow(result, 0);
        });
    }

    @Override
    public HBaseRowData getRow(String tableName, GetRowParam getRowParam) {
        Get get = buildGet(getRowParam);
        return this.get(tableName, get);
    }

    @Override
    public HBaseRowData get(String tableName, Get get) {
        return this.execute(tableName, table -> {
            Result result = checkGetAndReturnResult(get, table);
            if (result == null) {
                return null;
            }
            return convertResultToHBaseColData(result);
        }).orElse(HBaseRowData.empty());
    }

    @Override
    public <T> List<T> getWithMultiVersions(Get get, int versions, Class<T> clazz) {
        String tableName = ReflectFactory.getHBaseTableMeta(clazz).getTableName();
        return this.execute(tableName, table -> {
            Result result = checkGetAndReturnResult(get, table);
            if (result == null) {
                return null;
            }
            return mapperRowToList(result, versions, clazz);
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public <T> List<T> getWithMultiVersions(GetRowParam getRowParam, Class<T> clazz) {
        Get get = this.buildGet(getRowParam);
        int versions = getRowParam.getVersions();
        return this.getWithMultiVersions(get, versions, clazz);
    }

    @Override
    public <T> List<T> getWithMultiVersions(String tableName, Get get, int versions, RowMapper<T> rowMapper) {
        return this.execute(tableName, table -> {
            Result result = checkGetAndReturnResult(get, table);
            if (result == null) {
                return null;
            }
            return rowMapper.mapRowWithVersions(result, 0);
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public <T> List<T> getWithMultiVersions(String tableName, GetRowParam getRowParam, RowMapper<T> rowMapper) {
        Get get = this.buildGet(getRowParam);
        int versions = getRowParam.getVersions();
        return this.getWithMultiVersions(tableName, get, versions, rowMapper);
    }

    @Override
    public HBaseRowDataWithMultiVersions getWithMultiVersions(String tableName, GetRowParam getRowParam) {
        Get get = buildGet(getRowParam);
        return this.getWithMultiVersions(tableName, get, getRowParam.getVersions());
    }

    @Override
    public HBaseRowDataWithMultiVersions getWithMultiVersions(String tableName, Get get, int versions) {
        return this.execute(tableName, table -> {
            Result result = checkGetAndReturnResult(get, table);
            if (result == null) {
                return null;
            }
            return convertResultsToHBaseColDataListWithMultiVersion(result, versions);
        }).orElse(HBaseRowDataWithMultiVersions.empty());
    }

    @Override
    public <T> List<T> getRows(GetRowsParam getRowsParam, Class<T> clazz) {
        String tableName = ReflectFactory.getHBaseTableMeta(clazz).getTableName();
        List<Get> gets = this.buildGets(getRowsParam);
        return this.gets(tableName, gets, clazz);
    }

    @Override
    public <T> List<T> gets(String tableName, List<Get> gets, Class<T> clazz) {
        return this.execute(tableName, table -> {
            Result[] results = checkBatchGetAndReturnResult(gets, table);
            if (results == null) {
                return null;
            }
            List<T> data = new ArrayList<>(results.length);
            for (Result result : results) {
                data.add(mapperRowToT(result, clazz));
            }
            return data;
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public <T> List<T> getRows(String tableName, GetRowsParam getRowsParam, RowMapper<T> rowMapper) {
        List<Get> gets = this.buildGets(getRowsParam);
        return this.gets(tableName, gets, rowMapper);
    }

    @Override
    public <T> List<T> gets(String tableName, List<Get> gets, RowMapper<T> rowMapper) {
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
    public List<HBaseRowData> getRows(String tableName, GetRowsParam getRowsParam) {
        List<Get> gets = this.buildGets(getRowsParam);
        return this.gets(tableName, gets);
    }

    @Override
    public List<HBaseRowData> gets(String tableName, List<Get> gets) {
        return this.gets(tableName, gets, new RowMapper<HBaseRowData>() {
            @Override
            public <R> HBaseRowData mapRow(R r, int rowNum) throws Exception {
                Result result = (Result) r;
                return convertResultToHBaseColData(result);
            }
        });
    }

    @Override
    public <T> List<T> scan(ScanParams scanParams, Class<T> clazz) {
        Scan scan = buildScan(scanParams);
        return this.scan(scan, clazz);
    }

    @Override
    public <T> List<T> scan(Scan scan, Class<T> clazz) {
        String tableName = ReflectFactory.getHBaseTableMeta(clazz).getTableName();
        return this.execute(tableName, table -> {
            try (ResultScanner scanner = table.getScanner(scan)) {
                List<T> rs = new ArrayList<>();
                for (Result result : scanner) {
                    rs.add(mapperRowToT(result, clazz));
                }
                return rs;
            }
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public <T> List<T> scan(String tableName, ScanParams scanParams, RowMapper<T> rowMapper) {
        Scan scan = buildScan(scanParams);
        return this.scan(tableName, scan, rowMapper);
    }

    @Override
    public <T> List<T> scan(String tableName, Scan scan, RowMapper<T> rowMapper) {
        return this.execute(tableName, table -> {
            try (ResultScanner scanner = table.getScanner(scan)) {
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
    public List<HBaseRowData> scan(String tableName, ScanParams scanParams) {
        Scan scan = buildScan(scanParams);
        return this.scan(tableName, scan);
    }

    @Override
    public List<HBaseRowData> scan(String tableName, Scan scan) {
        List<HBaseRowData> rowDataList = new ArrayList<>();
        return this.execute(tableName, table -> {
            try (ResultScanner scanner = table.getScanner(scan)) {
                for (Result result : scanner) {
                    rowDataList.add(convertResultToHBaseColData(result));
                }
                return rowDataList;
            }
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public List<HBaseRowDataWithMultiVersions> scanWithMultiVersions(String tableName, ScanParams scanParams) {
        Scan scan = buildScan(scanParams);
        int versions = scanParams.getVersions();
        return this.scanWithMultiVersions(tableName, scan, versions);
    }

    @Override
    public List<HBaseRowDataWithMultiVersions> scanWithMultiVersions(String tableName, Scan scan, int versions) {
        List<HBaseRowDataWithMultiVersions> rowDataListWithMultiVersions = new ArrayList<>();
        return this.execute(tableName, table -> {
            try (ResultScanner scanner = table.getScanner(scan)) {
                for (Result result : scanner) {
                    rowDataListWithMultiVersions.add(convertResultsToHBaseColDataListWithMultiVersion(result, versions));
                }
                return rowDataListWithMultiVersions;
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
