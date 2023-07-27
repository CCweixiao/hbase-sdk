package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * @author leojie 2020/11/28 9:49 下午
 */
public class InsertValueVisitor extends BaseVisitor<Object> {
    public InsertValueVisitor(HBaseColumn column) {
        super(column);
    }

    @Override
    public Object visitInsertValue_Null(HBaseSQLParser.InsertValue_NullContext ctx) {
        if (column.isNullable()) {
            return null;
        }
        throw new NullPointerException(String.format("The value to be inserted of column [%s]" +
                        " is not allowed to be empty", column.getColumnName()));
    }

    @Override
    public Object visitInsertValue_NotNull(HBaseSQLParser.InsertValue_NotNullContext ctx) {
        HBaseSQLParser.ConstantContext constant = ctx.constant();
        TerminalNode valNode = constant.STRING();
        if (valNode == null) {
            if (column.isNullable()) {
                return null;
            }
            throw new NullPointerException(String.format("The value to be inserted of column [%s]" +
                    " is not allowed to be empty", column.getColumnName()));
        }
        String val = valNode.getText();
        if (StringUtil.isBlank(val)) {
            // 空字符串不存储
            return null;
        }
        return column.getColumnType().getTypeHandler().extractMatchTtypeValue(val);
    }

    public Object parseInsertConstantValue(HBaseColumn column, HBaseSQLParser.InsertValueContext insertValueContext) {
        MyAssert.checkNotNull(column);
        return insertValueContext.accept(this);
    }

}
