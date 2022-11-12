package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLBaseVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLContextUtil;
import com.github.CCwexiao.hbase.sdk.dsl.manual.TimeStampRange;

/**
 * @author leojie 2020/11/28 10:24 上午
 */
public class TimeStampRangeVisitor extends HBaseSQLBaseVisitor<TimeStampRange> {

    public TimeStampRangeVisitor() {
        super();
    }

    @Override
    public TimeStampRange visitTsrange_start(HBaseSQLParser.Tsrange_startContext startContext) {
        TimeStampRange timeStampRange = new TimeStampRange();
        timeStampRange.setStart(HBaseSQLContextUtil.parseTimeStamp(startContext.tsexp()));
        timeStampRange.setEnd(Long.MAX_VALUE);
        return timeStampRange;
    }

    @Override
    public TimeStampRange visitTsrange_end(HBaseSQLParser.Tsrange_endContext endContext) {
        TimeStampRange timeStampRange = new TimeStampRange();
        timeStampRange.setStart(0L);
        timeStampRange.setEnd(HBaseSQLContextUtil.parseTimeStamp(endContext.tsexp()));
        return timeStampRange;
    }

    @Override
    public TimeStampRange visitTsrange_startAndEnd(HBaseSQLParser.Tsrange_startAndEndContext startAndEndContext) {
        TimeStampRange timeStampRange = new TimeStampRange();
        timeStampRange.setStart(HBaseSQLContextUtil.parseTimeStamp(startAndEndContext.tsexp(0)));
        timeStampRange.setEnd(HBaseSQLContextUtil.parseTimeStamp(startAndEndContext.tsexp(1)));
        return timeStampRange;
    }
}
