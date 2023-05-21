package com.github.CCweixiao.hbase.sdk.common.model.data;

import java.io.Serializable;

/**
 * @author leojie 2023/5/9 23:14
 */
public class HBaseColData implements Serializable {
    public static final long serialVersionUID = 1L;
    private String value;
    private long timestamp;

    public HBaseColData(String value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
