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
    private String familyStr;
    private String qualifierStr;
    private Object valueObject;
    private Date tsDate;

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

    public void setValueObject(Object valueObject) {
        this.valueObject = valueObject;
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

    public Object getValueObject() {
        return valueObject;
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
