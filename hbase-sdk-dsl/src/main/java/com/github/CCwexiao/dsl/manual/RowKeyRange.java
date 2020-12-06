package com.github.CCwexiao.dsl.manual;

import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkeytextfunc.RowKeyTextFunc;

/**
 * @author leojie 2020/11/28 10:59 上午
 */
public class RowKeyRange {
    private RowKey start;
    private RowKey end;
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

    public RowKeyTextFunc getRowKeyFunc() {
        return rowKeyFunc;
    }

    public void setRowKeyFunc(RowKeyTextFunc rowKeyFunc) {
        this.rowKeyFunc = rowKeyFunc;
    }
}
