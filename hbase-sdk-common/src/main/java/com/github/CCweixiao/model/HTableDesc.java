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
    private final String tableName;
    private final Long maxFileSize;
    private final Boolean readOnly;
    private final Long memStoreFlushSize;
    private final Boolean compactionEnabled;
    private final Map<String, String> tableProps;
    private final List<ColumnFamilyDesc> columnFamilyDescList;

    public HTableDesc(Builder builder) {
        this.tableName = builder.tableName;
        this.maxFileSize = builder.maxFileSize;
        this.readOnly = builder.readOnly;
        this.memStoreFlushSize = builder.memStoreFlushSize;
        this.compactionEnabled = builder.compactionEnabled;
        this.tableProps = builder.tableProps;
        this.columnFamilyDescList = builder.columnFamilyDescList;
    }

    public static class Builder {
        private String tableName;
        private Long maxFileSize;
        private Boolean readOnly;
        private Long memStoreFlushSize;
        private Boolean compactionEnabled;
        private Map<String, String> tableProps;
        private List<ColumnFamilyDesc> columnFamilyDescList;

        public Builder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public Builder defaultTableDesc(String tableName) {
            this.tableName = tableName;
            this.maxFileSize = null;
            this.readOnly = null;
            this.memStoreFlushSize = null;
            this.compactionEnabled = null;
            return this;
        }

        public Builder defaultTableDescWithNs(String namespaceName, String tableName) {
            this.tableName = namespaceName.concat(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR).concat(tableName);
            this.maxFileSize = null;
            this.readOnly = null;
            this.memStoreFlushSize = null;
            this.compactionEnabled = null;
            return this;
        }

        public Builder maxFileSize(Long maxFileSize) {
            this.maxFileSize = maxFileSize;
            return this;
        }

        public Builder readOnly(Boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder memStoreFlushSize(Long memStoreFlushSize) {
            this.memStoreFlushSize = memStoreFlushSize;
            return this;
        }

        public Builder compactionEnabled(Boolean compactionEnabled) {
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

        public HTableDesc build() {
            return new HTableDesc(this);
        }
    }

    public String getNamespaceName() {
        return HMHBaseConstant.getNamespaceName(tableName);
    }

    public String getTableName() {
        return this.tableName;
    }

    public Long getMaxFileSize() {
        return this.maxFileSize;
    }

    public Boolean isReadOnly() {
        return this.readOnly;
    }

    public Long getMemStoreFlushSize() {
        return this.memStoreFlushSize;
    }

    public Boolean isCompactionEnabled() {
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
                "namespaceName='" + getNamespaceName() + '\'' +
                ", tableName='" + getTableName() + '\'' +
                ", maxFileSize=" + getMaxFileSize() +
                ", readOnly=" + isReadOnly() +
                ", memStoreFlushSize=" + getMaxFileSize() +
                ", compactionEnabled=" + isCompactionEnabled() +
                ", tableProps=" + tableProps +
                ", columnFamilyDescList=" + columnFamilyDescList +
                '}';
    }
}
