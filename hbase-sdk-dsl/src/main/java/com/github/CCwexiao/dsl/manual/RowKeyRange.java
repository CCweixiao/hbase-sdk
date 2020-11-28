package com.github.CCwexiao.dsl.manual;

import com.github.CCwexiao.dsl.client.RowKey;

/**
 * @author leojie 2020/11/28 10:59 上午
 */
public class RowKeyRange {
    private RowKey start;
    private RowKey end;

    public RowKey getStart() {
        return start;
    }

    public void setStart(RowKey start) {
        this.start = start;
    }

    public RowKey getEnd() {
        return end;
    }

    public void setEnd(RowKey end) {
        this.end = end;
    }
}
