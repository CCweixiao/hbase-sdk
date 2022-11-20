package com.github.CCweixiao.hbase.sdk.common.reflect;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.util.List;

/**
 * @author leojie 2022/11/20 11:07
 */
public class HBaseTableMeta {
    private String tableName;
    private MethodAccess methodAccess;
    private FieldAccess fieldAccess;
    private List<FieldStruct> fieldStructList;

    public HBaseTableMeta() {
    }

    public MethodAccess getMethodAccess() {
        return methodAccess;
    }

    public void setMethodAccess(MethodAccess methodAccess) {
        this.methodAccess = methodAccess;
    }

    public FieldAccess getFieldAccess() {
        return fieldAccess;
    }

    public void setFieldAccess(FieldAccess fieldAccess) {
        this.fieldAccess = fieldAccess;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<FieldStruct> getFieldStructList() {
        return fieldStructList;
    }

    public void setFieldStructList(List<FieldStruct> fieldStructList) {
        this.fieldStructList = fieldStructList;
    }
}
