package com.github.CCweixiao.hbase.sdk.common.model.row;

import java.awt.Font;
import java.util.*;


/**
 * @author leojie
 */
public class DataSetFormatter {
    private static final Font FONT = new Font("宋体", Font.PLAIN, 16);
    private String columnInterval = "|";
    private final AlignType ALIGN_TYPE = AlignType.Left;
    private Integer maxValueLength = 70;
    private Map<String, Integer> maxFieldValueLengthMap = null;
    List<String> columnNameList = null;
    List<List<String>> columnValueList = null;
    private boolean isException = false;
    private Integer printTableRow = 30;

    public DataSetFormatter(List<String> columnNameList, List<List<String>> columnValueList) {
        this(columnNameList, columnValueList, null);
    }

    public DataSetFormatter(List<String> columnNameList, List<List<String>> columnValueList,
                            Map<String, Integer> maxFieldValueLengthMap) {
        if (null == columnNameList || columnNameList.isEmpty()) {
            Exception e = new Exception("The column name list must not be null.");
            e.printStackTrace();
            this.isException = true;
            return;
        }
        this.columnNameList = columnNameList;
        this.columnValueList = columnValueList;
        this.maxFieldValueLengthMap = maxFieldValueLengthMap;
    }

    public String printTable() {
        if (null == this.maxFieldValueLengthMap || this.columnNameList.size() != this.maxFieldValueLengthMap.size()) {
            this.setMaxFieldValueLengthMap(this.columnNameList, this.columnValueList, null);
        }
        if (isException) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Integer tableLength = 0;
        for (int i = 0; i < columnNameList.size(); i++) {
            if (i == 0) {
                sb.append(columnInterval + " " + makeValueAlign(columnNameList.get(i),
                        maxFieldValueLengthMap.get(columnNameList.get(i))));
            } else {
                sb.append(" " + columnInterval + " " + makeValueAlign(columnNameList.get(i),
                        maxFieldValueLengthMap.get(columnNameList.get(i))));
            }
        }
        sb.append(columnInterval + "\n");
        tableLength = getStrPixelsLength(sb.toString());
        sb.insert(0, getRepairStr('-', tableLength) + "\n");
        sb.append(getRepairStr('-', tableLength) + "\n");
        if (null != columnValueList && columnValueList.size() > 0) {
            StringBuilder sbb = new StringBuilder();
            for (int x = 0; x < columnValueList.size(); x++) {
                List<String> ls = columnValueList.get(x);
                for (int i = 0; i < ls.size(); i++) {
                    if (i == 0) {
                        sbb.append(columnInterval + " " + makeValueAlign(ls.get(i),
                                maxFieldValueLengthMap.get(columnNameList.get(i))));
                    } else {
                        sbb.append(" " + columnInterval + " " + makeValueAlign(ls.get(i),
                                maxFieldValueLengthMap.get(columnNameList.get(i))));
                    }
                }
                sbb.append(columnInterval + "\n");
                if (x > this.printTableRow) {
                    String info = "print " + printTableRow + " rows; total " + columnValueList.size() + " row num.";
                    sbb.append(columnInterval + " " + makeValueLeft(info, tableLength) + columnInterval + "\n");
                    break;
                }
            }
            sb.append(sbb.append(getRepairStr('-', tableLength)));
        } else {
            StringBuilder sbb = new StringBuilder();
            for (int i = 0; i < columnNameList.size(); i++) {
                if (i == 0) {
                    sbb.append(columnInterval + " " + makeValueAlign("-",
                            maxFieldValueLengthMap.get(columnNameList.get(i))));
                } else {
                    sbb.append(" " + columnInterval + " " + makeValueAlign("-",
                            maxFieldValueLengthMap.get(columnNameList.get(i))));
                }
            }
            sbb.append(columnInterval + "\n");
            sb.append(sbb.append(getRepairStr('-', tableLength)));
        }
        return sb.toString();
    }

    public static Integer getStrPixelsLength(String str) {
        return sun.font.FontDesignMetrics.getMetrics(FONT).stringWidth(str) / 8;
    }

    private String makeValueCenter(String columnValue, Integer repairStrLength) {
        StringBuilder sb = new StringBuilder();
        if (repairStrLength % 2 == 1) {
            sb.append(getRepairStr(' ', repairStrLength / 2));
        } else {
            sb.append(getRepairStr(' ', repairStrLength / 2));
        }
        sb.append(columnValue);
        if (repairStrLength % 2 == 1) {
            sb.append(getRepairStr(' ', repairStrLength / 2 + 1));
        } else {
            sb.append(getRepairStr(' ', repairStrLength / 2));
        }
        return sb.toString();
    }

    private String makeValueLeft(String columnValue, Integer repairStrLength) {
        StringBuilder sb = new StringBuilder();
        sb.append(columnValue);
        sb.append(getRepairStr(' ', repairStrLength));
        return sb.toString();
    }

    private String makeValueRight(String columnValue, Integer repairStrLength) {
        StringBuilder sb = new StringBuilder();
        sb.append(getRepairStr(' ', repairStrLength));
        sb.append(columnValue);
        return sb.toString();
    }

    private static String getRepairStr(char str, Integer pixelsLength) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pixelsLength; i++) {
            sb.append(str);
        }
        return sb.toString();

    }

    private static String subStrByPixels(String str, Integer pixelsLength) {
        if (getStrPixelsLength(str) > pixelsLength) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length() && getStrPixelsLength(sb.toString()) < pixelsLength - 4; i++) {
                sb.append(str.charAt(i));
            }
        }
        return str;
    }

    private String makeValueAlign(String columnValue, Integer columnPixelsLength) {
        if (null == columnValue) {
            columnValue = "";
        }
        if (null == columnPixelsLength) {
            return columnValue;
        }
        if (getStrPixelsLength(columnValue) > columnPixelsLength) {
            return subStrByPixels(columnValue, columnPixelsLength);
        }
        Integer repairStrLength = (columnPixelsLength - getStrPixelsLength(columnValue));

        switch (ALIGN_TYPE) {
            case Center: {
                return makeValueCenter(columnValue, repairStrLength);
            }
            case Left: {
                return makeValueLeft(columnValue, repairStrLength);
            }
            case Right: {
                return makeValueRight(columnValue, repairStrLength);
            }
            default: {
                return "";
            }
        }
    }

    public void setColumnInterval(String columnInterval) {
        this.columnInterval = columnInterval;
    }

    public void setMaxValueLength(Integer maxValueLength) {
        this.maxValueLength = maxValueLength;
    }

    private void setMaxFieldValueLengthMap(List<String> columnNameListTmp, List<List<String>> columnValueListTmp, Map<String, Integer> maxFieldValueLengthMapTmp) {
        if (columnValueListTmp == null) {
            columnValueListTmp = new ArrayList<>();
        }
        if (null != maxFieldValueLengthMapTmp && columnNameListTmp.size() == maxFieldValueLengthMapTmp.size()) {
            this.maxFieldValueLengthMap = maxFieldValueLengthMapTmp;
        } else {
            this.maxFieldValueLengthMap = new HashMap<>(2);
            if (columnValueListTmp.size() > 0) {
                for (List<String> ls : columnValueListTmp) {
                    if (ls.size() != columnNameList.size()) {
                        Exception e = new Exception("The number of columns with data and the number" +
                                " of column names are not equal.");
                        e.printStackTrace();
                        this.isException = true;
                        return;
                    }
                    for (int i = 0; i < ls.size(); i++) {
                        Integer valueLength = getStrPixelsLength(ls.get(i));
                        if (!this.maxFieldValueLengthMap.containsKey(columnNameList.get(i))) {
                            this.maxFieldValueLengthMap.put(columnNameList.get(i), valueLength);
                        }
                        if (valueLength >= this.maxFieldValueLengthMap.get(columnNameList.get(i))) {
                            if (valueLength <= this.maxValueLength) {
                                this.maxFieldValueLengthMap.put(columnNameList.get(i), valueLength);
                            } else {
                                this.maxFieldValueLengthMap.put(columnNameList.get(i), maxValueLength);
                            }
                        }
                    }
                }
            }
            for (String s : columnNameList) {
                if (!this.maxFieldValueLengthMap.containsKey(s)) {
                    this.maxFieldValueLengthMap.put(s, s.length());
                }
                if (this.maxFieldValueLengthMap.get(s) < s.length()) {
                    this.maxFieldValueLengthMap.put(s, s.length());
                }
            }
        }
    }

    public void setPrintTableRow(Integer printTableRow) {
        this.printTableRow = printTableRow;
    }

    public enum AlignType {
        /**
         * align type
         */
        Left,
        Center,
        Right
    }

    public static void main(String[] args) {
        List<String> cols = Arrays.asList("name", "age", "name", "age", "name", "age");
        List<List<String>> values = Arrays.asList(Arrays.asList("leo1", "18", "leo2", "18", "leo3", "18"),
                Arrays.asList("leo3", "17", "leo4", "18", "leo5", "18"));
        DataSetFormatter dataSetFormatter = new DataSetFormatter(cols, values);
        System.out.println(dataSetFormatter.printTable());
        System.out.println();
    }
}
