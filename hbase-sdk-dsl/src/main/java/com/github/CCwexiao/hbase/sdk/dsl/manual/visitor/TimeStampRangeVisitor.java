package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;


import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.manual.TimeStampRange;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

/**
 * @author leojie 2020/11/28 10:24 上午
 */
public class TimeStampRangeVisitor extends BaseVisitor<TimeStampRange> {

    public TimeStampRangeVisitor(HBaseTableSchema tableSchema) {
        super(tableSchema);
    }

    @Override
    public TimeStampRange visitTsrange_start(HBaseSQLParser.Tsrange_startContext startContext) {
        TimeStampRange timeStampRange = new TimeStampRange();
        timeStampRange.setStart(parseTimeStamp(startContext.tsExp()));
        timeStampRange.setEnd(Long.MAX_VALUE);
        return timeStampRange;
    }

    @Override
    public TimeStampRange visitTsrange_end(HBaseSQLParser.Tsrange_endContext endContext) {
        TimeStampRange timeStampRange = new TimeStampRange();
        timeStampRange.setStart(0L);
        timeStampRange.setEnd(parseTimeStamp(endContext.tsExp()));
        return timeStampRange;
    }

    @Override
    public TimeStampRange visitTsrange_startAndEnd(HBaseSQLParser.Tsrange_startAndEndContext startAndEndContext) {
        TimeStampRange timeStampRange = new TimeStampRange();
        timeStampRange.setStart(parseTimeStamp(startAndEndContext.tsExp(0)));
        timeStampRange.setEnd(parseTimeStamp(startAndEndContext.tsExp(1)));
        return timeStampRange;
    }

    public TimeStampRange parseTimeStampRange(HBaseSQLParser.TsRangeContext tsRangeContext) {
        MyAssert.checkNotNull(tsRangeContext);
        TimeStampRange timeStampRange = tsRangeContext.accept(this);
        MyAssert.checkNotNull(timeStampRange);
        return timeStampRange;
    }

}
