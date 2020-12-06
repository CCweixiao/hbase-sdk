package com.github.CCwexiao.dsl.client;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * @author leojie 2020/11/28 8:22 下午
 */
public class HBaseCellResult {
    private RowKey rowKey;
    private Object rowKeyValue;
    private String familyStr;
    private String qualifierStr;
    private Object value;
    private Date tsDate;

    public Object getRowKeyValue() {
        return rowKeyValue;
    }

    public void setRowKeyValue(Object rowKeyValue) {
        this.rowKeyValue = rowKeyValue;
    }

    public RowKey getRowKey() {
        return rowKey;
    }

    public void setRowKey(RowKey rowKey) {
        this.rowKey = rowKey;
    }

    public void setFamilyStr(String familyStr) {
        this.familyStr = familyStr;
    }

    public void setQualifierStr(String qualifierStr) {
        this.qualifierStr = qualifierStr;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setTsDate(Date tsDate) {
        this.tsDate = tsDate;
    }

    public String getFamilyStr() {
        return familyStr;
    }

    public String getQualifierStr() {
        return qualifierStr;
    }

    public Object getValue() {
        return value;
    }

    public Date getTsDate() {
        return tsDate;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
