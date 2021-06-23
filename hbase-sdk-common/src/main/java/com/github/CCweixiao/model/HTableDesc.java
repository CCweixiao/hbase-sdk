package com.github.CCweixiao.model;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.util.StrUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author leojie 2021/6/23 9:48 下午
 */
public class HTableDesc {
    public static final long DEFAULT_MAX_FILE_SIZE = 10737418240L;
    public static final boolean DEFAULT_READ_ONLY = false;
    public static final long DEFAULT_MEM_STORE_FLUSH_SIZE = 134217728L;
    public static final boolean DEFAULT_COMPACTION_ENABLED = true;

    private final String namespaceName;
    private final String tableName;
    private final long maxFileSize;
    private final boolean readOnly;
    private final long memStoreFlushSize;
    private final boolean compactionEnabled;
    private final Map<String, String> tableProps;
    private final List<ColumnFamilyDesc> columnFamilyDescList;
    private final boolean metaTable;

    public HTableDesc(Builder builder) {
        this.namespaceName = builder.namespaceName;
        this.tableName = builder.tableName;
        this.maxFileSize = builder.maxFileSize;
        this.readOnly = builder.readOnly;
        this.memStoreFlushSize = builder.memStoreFlushSize;
        this.compactionEnabled = builder.compactionEnabled;
        this.tableProps = builder.tableProps;
        this.columnFamilyDescList = builder.columnFamilyDescList;
        this.metaTable = builder.metaTable;
    }

    public static class Builder {
        private String namespaceName;
        private String tableName;
        private long maxFileSize;
        private boolean readOnly;
        private long memStoreFlushSize;
        private boolean compactionEnabled;
        private Map<String, String> tableProps;
        private List<ColumnFamilyDesc> columnFamilyDescList;
        private boolean metaTable;


        public Builder namespaceName(String namespaceName) {
            this.namespaceName = namespaceName;
            return this;
        }

        public Builder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public Builder defaultTableDesc(String tableName) {
            this.namespaceName = HMHBaseConstant.getNamespaceName(tableName);
            this.tableName = HMHBaseConstant.getFullTableName(tableName);
            this.maxFileSize = DEFAULT_MAX_FILE_SIZE;
            this.readOnly = DEFAULT_READ_ONLY;
            this.memStoreFlushSize = DEFAULT_MEM_STORE_FLUSH_SIZE;
            this.compactionEnabled = DEFAULT_COMPACTION_ENABLED;
            return this;
        }

        public Builder defaultTableDescWithFamily(String namespaceName, String tableName) {
            this.namespaceName = namespaceName;
            this.tableName = HMHBaseConstant.getFullTableName(namespaceName, tableName);
            this.maxFileSize = DEFAULT_MAX_FILE_SIZE;
            this.readOnly = DEFAULT_READ_ONLY;
            this.memStoreFlushSize = DEFAULT_MEM_STORE_FLUSH_SIZE;
            this.compactionEnabled = DEFAULT_COMPACTION_ENABLED;
            return this;
        }

        public Builder maxFileSize(long maxFileSize) {
            this.maxFileSize = maxFileSize;
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder memStoreFlushSize(long memStoreFlushSize) {
            this.memStoreFlushSize = memStoreFlushSize;
            return this;
        }

        public Builder compactionEnabled(boolean compactionEnabled) {
            this.compactionEnabled = compactionEnabled;
            return this;
        }

        public Builder tableProps(Map<String, String> tableProps) {
            this.tableProps = tableProps;
            return this;
        }

        public Builder addTableProp(String key, String value) {
            if (this.tableProps == null) {
                this.tableProps = new HashMap<>();
            }
            if (StrUtil.isBlank(key)) {
                return this;
            }
            if (value == null) {
                value = "";
            }
            this.tableProps.put(key, value);
            return this;
        }

        public Builder columnFamilyDescList(List<ColumnFamilyDesc> columnFamilyDescList) {
            this.columnFamilyDescList = columnFamilyDescList;
            return this;
        }

        public Builder addColumnFamilyDesc(ColumnFamilyDesc columnFamilyDesc) {
            if (this.columnFamilyDescList == null) {
                this.columnFamilyDescList = new ArrayList<>(2);
            }
            this.columnFamilyDescList.add(columnFamilyDesc);
            return this;
        }

        public Builder metaTable(boolean metaTable) {
            this.metaTable = metaTable;
            return this;
        }

        public HTableDesc build() {
            return new HTableDesc(this);
        }
    }

    public String getNamespaceName() {
        return namespaceName;
    }

    public boolean isMetaTable() {
        return metaTable;
    }

    public String getTableName() {
        return this.tableName;
    }

    public long getMaxFileSize() {
        return this.maxFileSize;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public long getMemStoreFlushSize() {
        return this.memStoreFlushSize;
    }

    public boolean isCompactionEnabled() {
        return this.compactionEnabled;
    }

    public Map<String, String> getTableProps() {
        return this.tableProps;
    }

    public List<ColumnFamilyDesc> getColumnFamilyDescList() {
        return this.columnFamilyDescList;
    }

    public String getFullTableName() {
        if (StrUtil.isBlank(getTableName())) {
            throw new HBaseOperationsException("The table name is not empty.");
        }
        if (StrUtil.isNotBlank(getNamespaceName())) {
            if (!getTableName().contains(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR)) {
                return getNamespaceName().concat(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR).concat(getTableName());
            } else {
                return getTableName();
            }
        } else {
            if (!getTableName().contains(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR)) {
                return HMHBaseConstant.DEFAULT_NAMESPACE_NAME.concat(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR).concat(getTableName());
            } else {
                return getTableName();
            }
        }
    }

    public boolean hasColumnFamily(ColumnFamilyDesc columnFamilyDesc) {
        if (this.columnFamilyDescList == null || this.columnFamilyDescList.isEmpty()) {
            return false;
        }
        return this.columnFamilyDescList.stream()
                .map(ColumnFamilyDesc::getFamilyName).collect(Collectors.toList())
                .contains(columnFamilyDesc.getFamilyName());
    }

    public boolean columnFamilyIsEmpty() {
        return this.columnFamilyDescList == null || this.columnFamilyDescList.isEmpty();
    }

    public void checkHasSameColumnFamily() {
        if (this.columnFamilyIsEmpty()) {
            return;
        }

        final Map<String, Long> familyCountMap = this.columnFamilyDescList.stream()
                .collect(Collectors.groupingBy(ColumnFamilyDesc::getFamilyName, Collectors.counting()));
        familyCountMap.forEach((familyName, count) -> {
            if (count > 1) {
                throw new HBaseOperationsException("同一张表中的列簇名[" + familyName + "]应该是唯一的");
            }
        });
    }

    @Override
    public String toString() {
        return "HTableDesc{" +
                "namespaceName='" + namespaceName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", maxFileSize=" + maxFileSize +
                ", readOnly=" + readOnly +
                ", memStoreFlushSize=" + memStoreFlushSize +
                ", compactionEnabled=" + compactionEnabled +
                ", tableProps=" + tableProps +
                ", columnFamilyDescList=" + columnFamilyDescList +
                ", metaTable=" + metaTable +
                '}';
    }
}
