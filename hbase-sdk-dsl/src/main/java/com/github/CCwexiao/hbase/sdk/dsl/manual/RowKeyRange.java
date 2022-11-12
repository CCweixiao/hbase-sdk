package com.github.CCwexiao.hbase.sdk.dsl.manual;

import com.github.CCwexiao.hbase.sdk.dsl.client.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkeytextfunc.RowKeyTextFunc;

import java.util.List;

/**
 * @author leojie 2020/11/28 10:59 上午
 */
public class RowKeyRange {
    private RowKey start;
    private RowKey end;
    private List<RowKey> containsSomeKeys;
    private RowKeyTextFunc rowKeyFunc;

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

    public List<RowKey> getContainsSomeKeys() {
        return containsSomeKeys;
    }

    public void setContainsSomeKeys(List<RowKey> containsSomeKeys) {
        this.containsSomeKeys = containsSomeKeys;
    }

    public RowKeyTextFunc getRowKeyFunc() {
        return rowKeyFunc;
    }

    public void setRowKeyFunc(RowKeyTextFunc rowKeyFunc) {
        this.rowKeyFunc = rowKeyFunc;
    }
}
