package com.github.CCweixiao.hbase.sdk.dsl.antlr.data;

import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import java.util.List;

/**
 * @author leojie 2020/11/28 10:59 上午
 */
public class RowKeyRange {
    private RowKey<?> eqRow;
    private RowKey<?> start;
    private boolean includeStart;
    private RowKey<?> stop;
    private boolean includeStop;
    private List<RowKey<?>> inSomeKeys;
    private RowKey<?> rowPrefix;
    private boolean matchGet;
    private boolean matchGetRows;
    private boolean matchScanByStart;
    private boolean matchScanByStartAndEnd;
    private boolean matchScanByRowPrefix;

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

    public boolean isIncludeStart() {
        return includeStart;
    }

    public void setIncludeStart(boolean includeStart) {
        this.includeStart = includeStart;
    }

    public RowKey<?> getStop() {
        return stop;
    }

    public void setStop(RowKey<?> stop) {
        this.stop = stop;
    }

    public boolean isIncludeStop() {
        return includeStop;
    }

    public void setIncludeStop(boolean includeStop) {
        this.includeStop = includeStop;
    }

    public List<RowKey<?>> getInSomeKeys() {
        return inSomeKeys;
    }

    public void setInSomeKeys(List<RowKey<?>> inSomeKeys) {
        this.inSomeKeys = inSomeKeys;
    }

    public RowKey<?> getRowPrefix() {
        return rowPrefix;
    }
    public void setRowPrefix(RowKey<?> rowPrefix) {
        this.rowPrefix = rowPrefix;
    }

    public boolean isMatchGet() {
        return matchGet;
    }

    public void setMatchGet(boolean matchGet) {
        this.matchGet = matchGet;
    }

    public boolean isMatchGetRows() {
        return matchGetRows;
    }

    public void setMatchGetRows(boolean matchGetRows) {
        this.matchGetRows = matchGetRows;
    }

    public boolean isMatchScanByStart() {
        return matchScanByStart;
    }

    public void setMatchScanByStart(boolean matchScanByStart) {
        this.matchScanByStart = matchScanByStart;
    }

    public boolean isMatchScanByStartAndEnd() {
        return matchScanByStartAndEnd;
    }

    public void setMatchScanByStartAndEnd(boolean matchScanByStartAndEnd) {
        this.matchScanByStartAndEnd = matchScanByStartAndEnd;
    }

    public boolean isMatchScanByRowPrefix() {
        return matchScanByRowPrefix;
    }

    public void setMatchScanByRowPrefix(boolean matchScanByRowPrefix) {
        this.matchScanByRowPrefix = matchScanByRowPrefix;
    }
}
