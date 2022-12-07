package com.github.CCwexiao.hbase.sdk.dsl.context;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlTableSchemaMissingException;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import java.util.HashMap;
import java.util.Map;


/**
 * @author leojie 2022/12/4 00:03
 */
public class HBaseSqlContext {
    private volatile static Map<String, HBaseTableSchema> tableSchemaMap;

    private HBaseSqlContext() {
    }

    public static void registerTableSchema(HBaseTableSchema tableSchema) {
        String tableName = tableSchema.getTableName();
        if (tableSchemaMap == null || !tableSchemaMap.containsKey(tableName)) {
            synchronized (HBaseSqlContext.class) {
                if (tableSchemaMap == null || !tableSchemaMap.containsKey(tableName)) {
                    if (tableSchemaMap == null) {
                        tableSchemaMap = new HashMap<>(2);
                    }
                    tableSchemaMap.put(tableName, tableSchema);
                }
            }
        }
    }

    public static HBaseTableSchema getTableSchema(String tableName) {
        if (tableSchemaMap == null || tableName.isEmpty() || !tableSchemaMap.containsKey(tableName)) {
            throw new HBaseSqlTableSchemaMissingException(
                    String.format("The table [%s] has no table schema, please register first.", tableName));
        }
        return tableSchemaMap.get(tableName);
    }
}
