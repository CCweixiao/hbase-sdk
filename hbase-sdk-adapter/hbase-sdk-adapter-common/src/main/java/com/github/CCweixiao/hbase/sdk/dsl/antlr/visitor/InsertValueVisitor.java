package com.github.CCweixiao.hbase.sdk.dsl.antlr.visitor;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.data.InsertRowData;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.manual.visitor.BaseVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/11/28 9:49 下午
 */
public class InsertValueVisitor extends BaseVisitor<List<InsertRowData>> {
    private final List<String> insertCols;
    private long timestamp;
    public InsertValueVisitor(HBaseTableSchema tableSchema, List<String> insertCols) {
        super(tableSchema);
        this.insertCols = insertCols;
    }

    @Override
    public List<InsertRowData> visitMultiValueList(HBaseSQLParser.MultiValueListContext ctx) {
        int findRowIndex = -1;
        for (int i = 0; i < insertCols.size(); i++) {
            if (insertCols.get(i).equals(this.findRow().getColumnName())) {
                findRowIndex = i;
            }
        }

        if (findRowIndex < 0) {
            throw new HBaseSqlAnalysisException("The specified insertion list field does not contain rowKey.");
        }
        Map<String, HBaseColumn> allColumnMap = getAllColumns();
        List<HBaseSQLParser.ValueListContext> valueListContexts = ctx.valueList();
        List<InsertRowData> rowDataList = new ArrayList<>(valueListContexts.size());

        for (HBaseSQLParser.ValueListContext valueListContext : valueListContexts) {
            List<HBaseSQLParser.ValueContext> valueContexts = valueListContext.value();
            if (valueContexts.size() != this.insertCols.size()) {
                throw new HBaseSqlAnalysisException("Insert field list and value list numbers don't match.");
            }
            HBaseSQLParser.ValueContext rowValueContext = valueContexts.get(findRowIndex);
            String rowValue = parseValueFromValueContext(rowValueContext);
            if (StringUtil.isBlank(rowValue)) {
                throw new HBaseSqlAnalysisException("Unable to parse the rowKey value from the list of data to be inserted.");
            }
            String rowValueMatchType = this.findRow().getColumnType().getTypeHandler().extractMatchTtypeValue(rowValue);
            InsertRowData.Builder rowDataBuilder = InsertRowData.of(this.findRow().getColumnType().getTypeHandler().toBytes(rowValueMatchType));

            for (int i = 0; i < valueContexts.size(); i++) {
                if (i == findRowIndex) {
                    continue;
                }
                HBaseSQLParser.ValueContext colValueContext = valueContexts.get(i);
                String colName = this.insertCols.get(i);
                HBaseColumn hBaseColumn = allColumnMap.get(colName);
                if (hBaseColumn == null) {
                    throw new HBaseSqlAnalysisException("Invalid column name.");
                }
                String colValueOri = parseValueFromValueContext(colValueContext);
                String colValueMatchType;
                if (StringUtil.isBlank(colValueOri)) {
                    if (colValueOri == null && !hBaseColumn.isNullable()) {
                        throw new HBaseSqlAnalysisException(String.format("The value of column %s cannot be empty.", colName));
                    }
                    rowDataBuilder.addColData(hBaseColumn.getFamilyNameBytes(), hBaseColumn.getColumnNameBytes(), null, this.timestamp);
                } else {
                    colValueMatchType = hBaseColumn.getColumnType().getTypeHandler().extractMatchTtypeValue(colValueOri);
                    byte[] valueBytes = hBaseColumn.getColumnType().getTypeHandler().toBytes(colValueMatchType);
                    rowDataBuilder.addColData(hBaseColumn.getFamilyNameBytes(), hBaseColumn.getColumnNameBytes(), valueBytes, this.timestamp);
                }
            }
            rowDataList.add(rowDataBuilder.build());
        }
        return rowDataList;
    }


    public List<InsertRowData> parseInsertConstantValue(HBaseSQLParser.InsertStatementContext insertStatementContext) {
        return insertStatementContext.accept(this);
    }

    public long parseTimestamp(HBaseSQLParser.InsertStatementContext insertStatementContext) {
        HBaseSQLParser.TsExpContext tsExpContext = insertStatementContext.tsExp();
        if (tsExpContext != null) {
            String tsStr = tsExpContext.timestamp().ID().getText();
            try {
                return Long.parseLong(tsStr);
            } catch (NumberFormatException e) {
                throw new HBaseSqlAnalysisException("Illegal 13-digit long timestamp.");
            }
        }
        return -1;
    }



}
