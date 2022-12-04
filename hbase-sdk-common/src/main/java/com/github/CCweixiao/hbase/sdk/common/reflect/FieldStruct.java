package com.github.CCweixiao.hbase.sdk.common.reflect;

import com.github.CCweixiao.hbase.sdk.common.type.TypeHandler;

/**
 * @author leojie 2022/11/20 16:49
 */
public class FieldStruct {
    private boolean isRowKey;
    private String family;
    private String qualifier;

    private String familyAndQualifier;
    private int setterMethodIndex;
    private String setterMethodName;
    private int getterMethodIndex;
    private String getterMethodName;

    private Class<?> type;

    private TypeHandler<?> typeHandler;

    public FieldStruct() {
    }

    public boolean isRowKey() {
        return isRowKey;
    }

    public void setRowKey(boolean rowKey) {
        isRowKey = rowKey;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getFamilyAndQualifier() {
        return familyAndQualifier;
    }

    public void setFamilyAndQualifier(String familyAndQualifier) {
        this.familyAndQualifier = familyAndQualifier;
    }

    public int getSetterMethodIndex() {
        return setterMethodIndex;
    }

    public void setSetterMethodIndex(int setterMethodIndex) {
        this.setterMethodIndex = setterMethodIndex;
    }

    public String getSetterMethodName() {
        return setterMethodName;
    }

    public void setSetterMethodName(String setterMethodName) {
        this.setterMethodName = setterMethodName;
    }

    public int getGetterMethodIndex() {
        return getterMethodIndex;
    }

    public void setGetterMethodIndex(int getterMethodIndex) {
        this.getterMethodIndex = getterMethodIndex;
    }

    public String getGetterMethodName() {
        return getterMethodName;
    }

    public void setGetterMethodName(String getterMethodName) {
        this.getterMethodName = getterMethodName;
    }

    public TypeHandler<?> getTypeHandler() {
        return typeHandler;
    }

    public void setTypeHandler(TypeHandler<?> typeHandler) {
        this.typeHandler = typeHandler;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
