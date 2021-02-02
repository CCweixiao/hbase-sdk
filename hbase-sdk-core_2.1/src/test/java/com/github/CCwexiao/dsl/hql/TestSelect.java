package com.github.CCwexiao.dsl.hql;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.hql.HBaseSQLExtendContextUtil;
import com.github.CCweixiao.hql.config.DefaultHBaseSQLRuntimeSetting;
import com.github.CCweixiao.hql.config.DefaultHBaseTableConfig;
import com.github.CCwexiao.dsl.auto.HBaseSQLParser;
import com.github.CCwexiao.dsl.client.QueryExtInfo;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.config.HBaseColumnSchema;
import com.github.CCwexiao.dsl.config.HBaseSQLRuntimeSetting;
import com.github.CCwexiao.dsl.config.HBaseTableConfig;
import com.github.CCwexiao.dsl.config.HBaseTableSchema;
import com.github.CCwexiao.dsl.manual.HBaseSQLContextUtil;
import com.github.CCwexiao.dsl.manual.RowKeyRange;
import com.github.CCwexiao.dsl.util.TreeUtil;
import com.github.CCwexiao.dsl.util.Util;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2020/11/28 7:25 下午
 */
public class TestSelect {
    HBaseSQLRuntimeSetting runtimeSetting = new DefaultHBaseSQLRuntimeSetting();


    @Test
    public void testSelect1() {

        String sql = "select ( info:id , g:name , f:age , info:address ) from test:user where startKey is intkey ( '123' ) , endKey is intkey ( '1234' ) ( id less '12' and name equal 'leo' or age less '12' )  maxversion is 2  startTS is '123' , endTS is '321' limit 10 ";

        HBaseSQLParser.ProgContext progContext = TreeUtil.parseProgContext(sql);

        HBaseSQLParser.SelecthqlcContext context = HBaseSQLContextUtil.parseSelecthqlcContext(progContext);

        String tableName = TreeUtil.parseTableName(progContext);

        //check 表名是否存在

        //cidList
        List<HBaseColumnSchema> allHBaseColumnSchemas = createHBaseColumnSchemaList();

        HBaseTableSchema hBaseTableSchema = new HBaseTableSchema();
        HBaseTableConfig hBaseTableConfig = new DefaultHBaseTableConfig(hBaseTableSchema, allHBaseColumnSchemas);

        HBaseSQLParser.SelectCidListContext selectCidListContext = context.selectCidList();

        List<HBaseColumnSchema> queryHbaseColumnSchemaList = HBaseSQLContextUtil
                .parseHBaseColumnSchemaList(hBaseTableConfig, selectCidListContext);
        Util.check(!queryHbaseColumnSchemaList.isEmpty());

        //filter

        Filter filter = HBaseSQLExtendContextUtil.parseFilter(context.wherec(), hBaseTableConfig, runtimeSetting);

        RowKeyRange rowKeyRange = HBaseSQLContextUtil.parseRowKeyRange(context.rowKeyRange(), runtimeSetting);

        RowKey startRowKey = rowKeyRange.getStart();
        RowKey endRowKey = rowKeyRange.getEnd();

        Util.checkRowKey(startRowKey);
        Util.checkRowKey(endRowKey);

        // queryInfo

        QueryExtInfo queryExtInfo = HBaseSQLContextUtil.parseQueryExtInfo(context);

        Scan scan = constructScan(startRowKey, endRowKey, filter, queryExtInfo);

        long startIndex = 0L;
        long length = Long.MAX_VALUE;

        if (queryExtInfo.isMaxVersionSet()) {
            scan.setMaxVersions(queryExtInfo.getMaxVersions());
        }

        if (queryExtInfo.isTimeRangeSet()) {
            try {
                scan.setTimeRange(queryExtInfo.getMinStamp(),
                        queryExtInfo.getMaxStamp());
            } catch (IOException e) {
                throw new HBaseOperationsException("should never happen.", e);
            }
        }

        if (queryExtInfo.isLimitSet()) {
            startIndex = queryExtInfo.getStartIndex();
            length = queryExtInfo.getLength();
        }

        for (HBaseColumnSchema hbaseColumnSchema : queryHbaseColumnSchemaList) {
            scan.addColumn(Bytes.toBytes(hbaseColumnSchema.getFamily()),
                    Bytes.toBytes(hbaseColumnSchema.getQualifier()));
        }



    }

    public List<HBaseColumnSchema> createHBaseColumnSchemaList() {
        List<HBaseColumnSchema> hBaseColumnSchemas = new ArrayList<>();

        HBaseColumnSchema hBaseColumnSchema = new HBaseColumnSchema();
        hBaseColumnSchema.setFamily("info");
        hBaseColumnSchema.setQualifier("name");
        hBaseColumnSchema.setTypeName("string");

        hBaseColumnSchemas.add(hBaseColumnSchema);

        return hBaseColumnSchemas;
    }

    private Scan constructScan(RowKey startRowKey, RowKey endRowKey,
                                  Filter filter, QueryExtInfo queryExtInfo) {
        Util.checkRowKey(startRowKey);
        Util.checkRowKey(endRowKey);

        Scan scan = new Scan();
        scan.withStartRow(startRowKey.toBytes());
        scan.withStartRow(endRowKey.toBytes());

        int cachingSize = runtimeSetting.getScanCachingSize();

        if (runtimeSetting.isIntelligentScanSize()) {
            if (queryExtInfo != null && queryExtInfo.isLimitSet()) {
                long limitScanSize = queryExtInfo.getStartIndex()
                        + queryExtInfo.getLength();
                if (limitScanSize > Integer.MAX_VALUE) {
                    cachingSize = Integer.MAX_VALUE;
                } else {
                    cachingSize = (int) limitScanSize;
                }
            }
        }

        scan.setCaching(cachingSize);

        scan.setFilter(filter);

        return scan;
    }
}
