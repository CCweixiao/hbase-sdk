package com.github.CCweixiao.hbase.sdk.schema;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseFamilyNotUniqueException;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author leojie 2023/5/19 20:35
 */
public abstract class BaseHTableDesc {
    private String tableName;
    private long maxFileSize;
    private boolean readOnly;
    private long memStoreFlushSize;
    private boolean compactionEnabled;

    private String regionSplitPolicyClassName;
    private Map<String, String> configuration;
    private List<BaseColumnFamilyDesc> columnFamilyDescList;

    public BaseHTableDesc() {
    }

    public BaseHTableDesc(Builder<?> builder) {
        this.tableName = builder.tableName;
        this.maxFileSize = builder.maxFileSize;
        this.readOnly = builder.readOnly;
        this.memStoreFlushSize = builder.memStoreFlushSize;
        this.compactionEnabled = builder.compactionEnabled;
        this.configuration = builder.configuration;
        this.columnFamilyDescList = builder.columnFamilyDescList;
        this.regionSplitPolicyClassName = builder.regionSplitPolicyClassName;
    }

    public  abstract static class Builder<HTD extends BaseHTableDesc> {
        private String tableName;
        private long maxFileSize;
        private boolean readOnly;
        private long memStoreFlushSize;
        private boolean compactionEnabled;
        private String regionSplitPolicyClassName;
        private Map<String, String> configuration;
        private List<BaseColumnFamilyDesc> columnFamilyDescList;

        public Builder<HTD> tableName(String tableName) {
            this.tableName = tableName;
            this.maxFileSize = 10737418240L;
            this.readOnly = false;
            this.memStoreFlushSize = 134217728L;
            this.compactionEnabled = true;
            this.regionSplitPolicyClassName = null;
            this.columnFamilyDescList = new ArrayList<>();
            this.configuration = new HashMap<>();
            return this;
        }

        public Builder<HTD> tableName(String namespaceName, String tableName) {
            this.tableName = namespaceName.concat(HMHBaseConstants.TABLE_NAME_SPLIT_CHAR).concat(tableName);
            this.maxFileSize = 10737418240L;
            this.readOnly = false;
            this.memStoreFlushSize = 134217728L;
            this.compactionEnabled = true;
            this.regionSplitPolicyClassName = null;
            this.columnFamilyDescList = new ArrayList<>();
            this.configuration = new HashMap<>();
            return this;
        }

        public Builder<HTD> maxFileSize(long maxFileSize) {
            this.maxFileSize = maxFileSize;
            return this;
        }

        public Builder<HTD> readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder<HTD> memStoreFlushSize(long memStoreFlushSize) {
            this.memStoreFlushSize = memStoreFlushSize;
            return this;
        }

        public Builder<HTD> compactionEnabled(boolean compactionEnabled) {
            this.compactionEnabled = compactionEnabled;
            return this;
        }

        public Builder<HTD> regionSplitPolicyClassName(String regionSplitPolicyClassName) {
            this.regionSplitPolicyClassName = regionSplitPolicyClassName;
            return this;
        }

        public Builder<HTD> configuration(Map<String, String> configuration) {
            this.configuration = configuration;
            return this;
        }

        public Builder<HTD> configuration(String key, String value) {
            if (this.configuration == null) {
                this.configuration = new HashMap<>();
            }
            if (StringUtil.isBlank(key) || StringUtil.isBlank(value)) {
                return this;
            }
            this.configuration.put(key, value);
            return this;
        }

        public Builder<HTD> columnFamilyDescList(List<BaseColumnFamilyDesc> columnFamilyDescList) {
            this.columnFamilyDescList = columnFamilyDescList;
            return this;
        }

        public Builder<HTD> addFamilyDesc(BaseColumnFamilyDesc columnFamilyDesc) {
            if (this.columnFamilyDescList == null) {
                this.columnFamilyDescList = new ArrayList<>();
            }
            this.columnFamilyDescList.add(columnFamilyDesc);
            return this;
        }

        public abstract HTD build();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public long getMemStoreFlushSize() {
        return memStoreFlushSize;
    }

    public void setMemStoreFlushSize(long memStoreFlushSize) {
        this.memStoreFlushSize = memStoreFlushSize;
    }

    public boolean isCompactionEnabled() {
        return compactionEnabled;
    }

    public void setCompactionEnabled(boolean compactionEnabled) {
        this.compactionEnabled = compactionEnabled;
    }

    public String getRegionSplitPolicyClassName() {
        return regionSplitPolicyClassName;
    }

    public void setRegionSplitPolicyClassName(String regionSplitPolicyClassName) {
        this.regionSplitPolicyClassName = regionSplitPolicyClassName;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public List<BaseColumnFamilyDesc> getColumnFamilyDescList() {
        return columnFamilyDescList;
    }

    public void setColumnFamilyDescList(List<BaseColumnFamilyDesc> columnFamilyDescList) {
        this.columnFamilyDescList = columnFamilyDescList;
    }

    public void addColumnFamily(BaseColumnFamilyDesc columnFamilyDesc) {
        if (this.columnFamilyDescList == null) {
            this.columnFamilyDescList = new ArrayList<>();
        }
        this.columnFamilyDescList.add(columnFamilyDesc);
    }

    public String getNamespaceName() {
        return HMHBaseConstants.getNamespaceName(tableName);
    }

    public String getTableNameWithNamespace() {
        String tabName = this.getTableName();
        if (StringUtil.isBlank(tabName)) {
            throw new IllegalArgumentException("The table name is not empty.");
        }
        String namespaceName = getNamespaceName();
        if (!tabName.contains(HMHBaseConstants.TABLE_NAME_SPLIT_CHAR)) {
            return namespaceName.concat(HMHBaseConstants.TABLE_NAME_SPLIT_CHAR).concat(tabName);
        } else {
            return tabName;
        }
    }

    public boolean columnFamilyExists(BaseColumnFamilyDesc columnFamilyDesc) {
        if (columnFamilyIsEmpty()) {
            return false;
        }
        for (BaseColumnFamilyDesc familyDesc : this.columnFamilyDescList) {
            if (familyDesc.getFamilyName().equals(columnFamilyDesc.getFamilyName())) {
                return true;
            }
        }
        return false;
    }

    private boolean columnFamilyIsEmpty() {
        return this.columnFamilyDescList == null || this.columnFamilyDescList.isEmpty();
    }

    public void checkHasSameColumnFamilyOrNot() {
        if (columnFamilyIsEmpty()) {
            return;
        }

        final Map<String, Long> familyCountMap = this.columnFamilyDescList.stream()
                .collect(Collectors.groupingBy(BaseColumnFamilyDesc::getFamilyName, Collectors.counting()));
        familyCountMap.forEach((familyName, count) -> {
            if (count > 1) {
                throw new HBaseFamilyNotUniqueException(
                        String.format("The family %s in the same table should be unique.", familyName));
            }
        });
    }

    public BaseHTableDesc addConfiguration(Map<String, String> configuration) {
        if (configuration == null || configuration.isEmpty()) {
            return this;
        }
        configuration.forEach((k, v) -> {
            if (StringUtil.isNotBlank(k) && StringUtil.isNotBlank(v)) {
                this.configuration.put(k, v);
            }
        });
        return this;
    }

    public BaseHTableDesc addConfiguration(String key, String value) {
        if (StringUtil.isBlank(key) || StringUtil.isBlank(value)) {
            return this;
        }
        if (configuration == null || configuration.isEmpty()) {
            return this;
        }
        this.configuration.put(key, value);
        return this;
    }
}
