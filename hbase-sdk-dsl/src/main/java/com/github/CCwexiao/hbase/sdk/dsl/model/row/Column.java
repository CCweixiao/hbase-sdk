package com.github.CCwexiao.hbase.sdk.dsl.model.row;

/**
 * @author leojie 2022/12/5 23:03
 */
public class Column {
    private String family;
    private String qualifier;
    private Object value;

    private Long timestamp;

    public Column() {
    }

    public Column(String family, String qualifier, Object value, Long timestamp) {
        this.family = family;
        this.qualifier = qualifier;
        this.value = value;
        this.timestamp = timestamp;
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
