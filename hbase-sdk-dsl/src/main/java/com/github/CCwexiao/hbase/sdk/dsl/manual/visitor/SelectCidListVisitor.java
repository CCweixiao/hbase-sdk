package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLBaseVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseColumnSchema;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseTableConfig;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLContextUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author leojie 2020/11/27 11:28 下午
 */
public class SelectCidListVisitor extends HBaseSQLBaseVisitor<List<HBaseColumnSchema>> {
    private final HBaseTableConfig hbaseTableConfig;

    public SelectCidListVisitor(HBaseTableConfig hbaseTableConfig) {
        super();
        this.hbaseTableConfig = hbaseTableConfig;
    }

    @Override
    public List<HBaseColumnSchema> visitCidList_CidList(HBaseSQLParser.CidList_CidListContext ctx) {
        return HBaseSQLContextUtil.parseHBaseColumnSchemaList(hbaseTableConfig, ctx.cidList());
    }

    @Override
    public List<HBaseColumnSchema> visitCidList_Star(HBaseSQLParser.CidList_StarContext ctx) {
        return hbaseTableConfig.gethBaseTableSchema().findAllColumnSchemas();
    }

    @Override
    public List<HBaseColumnSchema> visitCidList_Regx(HBaseSQLParser.CidList_RegxContext ctx) {
        String regx = ctx.TEXT().getText();
        ObjUtil.checkEmptyString(regx);

        List<HBaseColumnSchema> list = hbaseTableConfig.gethBaseTableSchema().findAllColumnSchemas();
        Pattern p = Pattern.compile(regx);

        for (int i = list.size() - 1; i >= 0; i--) {
            HBaseColumnSchema hBaseColumnSchema = list.get(i);
            String s = hBaseColumnSchema.getFamily()
                    + HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR
                    + hBaseColumnSchema.getQualifier();
            Matcher matcher = p.matcher(s);
            if (!matcher.matches()) {
                list.remove(i);
            }
        }

        return list;
    }
}
