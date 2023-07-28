package com.github.CCweixiao.hbase.sdk.dsl.antlr.data;

import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import java.util.List;

/**
 * @author leojie 2020/11/28 10:59 上午
 */
public class RowKeyRange {
    private RowKey<?> eqRow;
    private RowKey<?> start;
    private RowKey<?> end;
    private List<RowKey<?>> inSomeKeys;

    public RowKey<?> getEqRow() {
        return eqRow;
    }

    public void setEqRow(RowKey<?> eqRow) {
        this.eqRow = eqRow;
    }

    public RowKey<?> getStart() {
        return start;
    }

    public void setStart(RowKey<?> start) {
        this.start = start;
    }

    public RowKey<?> getEnd() {
        return end;
    }

    public void setEnd(RowKey<?> end) {
        this.end = end;
    }

    public List<RowKey<?>> getInSomeKeys() {
        return inSomeKeys;
    }

    public void setInSomeKeys(List<RowKey<?>> inSomeKeys) {
        this.inSomeKeys = inSomeKeys;
    }
}
