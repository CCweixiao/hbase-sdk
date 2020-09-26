package com.github.CCweixiao.model;

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
    private String tableName = null;
    private String namespaceName;

    private boolean disabled;
    private boolean metaTable;

    private Map<String, String> tableProps;
    private List<FamilyDesc> familyDescList;

    /**
     * 预分区开始的key
     */
    private String startKey;
    /**
     * 预分区结束的key
     */
    private String endKey;
    /**
     * 分区数
     */
    private Integer preSplitRegions;
    /**
     * 以指定分区key的方式分区
     */
    private String preSplitKeys;

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

    public String getStartKey() {
        return startKey;
    }

    public void setStartKey(String startKey) {
        this.startKey = startKey;
    }

    public String getEndKey() {
        return endKey;
    }

    public void setEndKey(String endKey) {
        this.endKey = endKey;
    }

    public Integer getPreSplitRegions() {
        return preSplitRegions;
    }

    public void setPreSplitRegions(Integer preSplitRegions) {
        this.preSplitRegions = preSplitRegions;
    }

    public String getPreSplitKeys() {
        return preSplitKeys;
    }

    public void setPreSplitKeys(String preSplitKeys) {
        this.preSplitKeys = preSplitKeys;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public TableDesc addProp(final String key, final String value) {
        if (tableProps == null) {
            tableProps = new HashMap<>();
        }
        if (!tableProps.containsKey(key)) {
            this.tableProps.put(key, value);
        }
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
            throw new HBaseOperationsException("列簇" + familyDesc.getFamilyName() + "已经存在不能被添加");
        }
        if(this.familyDescList==null){
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

}
