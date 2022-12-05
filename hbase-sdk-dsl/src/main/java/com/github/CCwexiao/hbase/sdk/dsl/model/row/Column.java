package com.github.CCwexiao.hbase.sdk.dsl.model.row;

import java.lang.reflect.Type;

/**
 * @author leojie 2022/12/5 23:03
 */
public class Column {
    private String family;
    private String qualifier;
    private Type type;
    private Object value;

    public Column() {
    }

    public Column(String family, String qualifier, Type type, Object value) {
        this.family = family;
        this.qualifier = qualifier;
        this.type = type;
        this.value = value;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
