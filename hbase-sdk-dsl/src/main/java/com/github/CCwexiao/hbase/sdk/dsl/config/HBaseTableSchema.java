package com.github.CCwexiao.hbase.sdk.dsl.config;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;
import com.github.CCwexiao.hbase.sdk.dsl.type.TypeHandlers;
import com.github.CCwexiao.hbase.sdk.dsl.util.Util;

import java.util.*;

/**
 * @author leojie 2020/11/27 10:53 下午
 */
public class HBaseTableSchema {
    /**
     * HBase的表名，不能为空
     */
    private String tableName;

    /**
     * HBase的默认列簇，可以为空
     */
    private String defaultFamily;

    /**
     * qualifier -> family -> HBaseColumnSchema
     */
    private final Map<String, Map<String, HBaseColumnSchema>> columnSchemas = new TreeMap<>();


    public void init(List<HBaseColumnSchema> hBaseColumnSchemas, TypeHandlers typeHandlers) {
        Util.checkEmptyString(tableName);

        if (hBaseColumnSchemas.isEmpty()) {
            throw new HBaseOperationsException("no HBaseColumnSchema.");
        }

        for (HBaseColumnSchema columnSchema : hBaseColumnSchemas) {
            if (StrUtil.isBlank(columnSchema.getFamily())) {
                columnSchema.setFamily(defaultFamily);
            }

            columnSchema.init(typeHandlers);

            Map<String, HBaseColumnSchema> tempMap = columnSchemas.computeIfAbsent(columnSchema.getQualifier(), k -> new TreeMap<>());

            tempMap.put(columnSchema.getFamily(), columnSchema);
        }

    }


    /**
     * 从输入的Cid中，解析字段名
     *
     * @param family    列簇
     * @param qualifier 列标识符
     * @return HBaseColumnSchema
     */
    public HBaseColumnSchema findColumnSchema(String family, String qualifier) {
        Util.checkEmptyString(family);
        Util.checkEmptyString(qualifier);

        HBaseColumnSchema result = columnSchemas.get(qualifier).get(family);

        if (result == null) {
            throw new HBaseOperationsException("no HBaseColumnSchema found.");
        }

        return result;
    }

    /**
     * 根据qualifier获取HBaseColumnSchema，如果传入的qualifier出现在多个列簇中，需要限定所属列簇
     * @param qualifier qualifier
     * @return HBaseColumnSchema
     */
    public HBaseColumnSchema findColumnSchema(String qualifier) {
        Util.checkEmptyString(qualifier);

        final Map<String, HBaseColumnSchema> tempMap = columnSchemas.get(qualifier);
        if (tempMap.size() == 1) {
            for (HBaseColumnSchema t : tempMap.values()) {
                return t;
            }
        }
        throw new HBaseOperationsException("0 or many HBaseColumnSchema with qualifier = " + qualifier);
    }

    /**
     * 获取所有的 HBaseColumnSchema
     *
     * @return All HBaseColumnSchema List
     */
    public List<HBaseColumnSchema> findAllColumnSchemas() {
        List<HBaseColumnSchema> result = new ArrayList<>();

        for (Map<String, HBaseColumnSchema> t : columnSchemas.values()) {
            result.addAll(t.values());
        }
        return result;
    }

    /**
     * 获取所有的列簇列表
     *
     * @return 所有的列簇列表
     */
    public List<String> findAllFamilies() {
        List<HBaseColumnSchema> list = findAllColumnSchemas();
        Set<String> allFamilies = new HashSet<>();
        for (HBaseColumnSchema hBaseColumnSchema : list) {
            allFamilies.add(hBaseColumnSchema.getFamily());
        }
        return new ArrayList<>(allFamilies);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDefaultFamily() {
        return defaultFamily;
    }

    public void setDefaultFamily(String defaultFamily) {
        this.defaultFamily = defaultFamily;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------table--------------------------\n");
        Util.append(sb, "tableName", tableName);
        Util.append(sb, "defaultFamily", defaultFamily);
        for (Map<String, HBaseColumnSchema> tem : columnSchemas.values()) {
            for (HBaseColumnSchema columnSchema : tem.values()) {
                Util.append(sb, "columnSchema", columnSchema);
            }
        }
        sb.append("---------------table--------------------------\n");
        return sb.toString();
    }
}
