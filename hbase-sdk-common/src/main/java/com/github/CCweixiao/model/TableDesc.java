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
 * @author leojie 2020/9/9 10:44 下午
 */
public class TableDesc {
    private String namespaceName;
    private String tableName = null;

    private boolean disabled;
    private boolean metaTable;

    private Map<String, String> tableProps;
    private List<FamilyDesc> familyDescList;
    private long lastMajorCompactTimestamp;

    /**
     * 表描述信息
     */
    private String tableDesc;

    public String getNamespaceName() {
        return namespaceName;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Map<String, String> getTableProps() {
        return tableProps;
    }

    public void setTableProps(Map<String, String> tableProps) {
        this.tableProps = tableProps;
    }

    public List<FamilyDesc> getFamilyDescList() {
        return familyDescList;
    }

    public void setFamilyDescList(List<FamilyDesc> familyDescList) {
        this.familyDescList = familyDescList;
    }

    public boolean isMetaTable() {
        return metaTable;
    }

    public void setMetaTable(boolean metaTable) {
        this.metaTable = metaTable;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public long getLastMajorCompactTimestamp() {
        return lastMajorCompactTimestamp;
    }

    public void setLastMajorCompactTimestamp(long lastMajorCompactTimestamp) {
        this.lastMajorCompactTimestamp = lastMajorCompactTimestamp;
    }

    public TableDesc addProp(final String key, String value) {
        if (tableProps == null) {
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

    public String getProp(final String key) {
        if (StrUtil.isBlank(key)) {
            return "";
        }
        return this.tableProps.getOrDefault(key, "");
    }

    public TableDesc addFamilyDesc(final FamilyDesc familyDesc) {
        if (StrUtil.isBlank(familyDesc.getFamilyName())) {
            throw new HBaseOperationsException("列簇名不能为空");
        }
        if (hasFamily(familyDesc.getFamilyName())) {
            throw new HBaseOperationsException("列簇[" + familyDesc.getFamilyName() + "]已经存在不能被添加");
        }
        if (this.familyDescList == null) {
            this.familyDescList = new ArrayList<>();
        }
        this.familyDescList.add(familyDesc);
        return this;
    }

    public boolean hasFamily(final String familyName) {
        if (familyDescList == null || familyDescList.isEmpty()) {
            return false;
        }
        final List<String> familyNames = familyDescList.stream().map(FamilyDesc::getFamilyName).collect(Collectors.toList());
        return familyNames.contains(familyName);
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

    @Override
    public String toString() {
        return "TableDesc{" +
                "tableName='" + getTableName() + '\'' +
                ", namespaceName='" + getNamespaceName() + '\'' +
                ", disabled=" + isDisabled() +
                ", metaTable=" + isMetaTable() +
                ", tableProps=" + getTableProps() +
                ", familyDescList=" + getFamilyDescList() +
                ", tableDesc='" + getTableDesc() + '\'' +
                ", lastMajorCompactTimestamp=" + getLastMajorCompactTimestamp() + '\'' +
                '}';
    }
}
