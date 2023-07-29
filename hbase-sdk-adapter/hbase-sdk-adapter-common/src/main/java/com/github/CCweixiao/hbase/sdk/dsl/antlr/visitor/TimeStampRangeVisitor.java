package com.github.CCweixiao.hbase.sdk.dsl.antlr.visitor;


import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.data.TimeStampRange;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

/**
 * @author leojie 2020/11/28 10:24 上午
 */
public class TimeStampRangeVisitor extends BaseVisitor<TimeStampRange> {

    public TimeStampRangeVisitor(HBaseTableSchema tableSchema) {
        super(tableSchema);
    }

    @Override
    public TimeStampRange visitTsequal(HBaseSQLParser.TsequalContext tsequalContext) {
        TimeStampRange timeStampRange = new TimeStampRange();
        long ts = this.extractTimeStamp(tsequalContext.tsExp());
        if (ts > 1) {
            timeStampRange.setStart(ts);
            timeStampRange.setEnd(ts + 1);
        }
        return timeStampRange;
    }

    @Override
    public TimeStampRange visitTsrange_start(HBaseSQLParser.Tsrange_startContext startContext) {
        TimeStampRange timeStampRange = new TimeStampRange();
        long startTs = this.extractTimeStamp(startContext.tsExp());
        if (startContext.gtOper().GREATER() != null) {
            startTs += 1;
        }
        timeStampRange.setStart(startTs);
        timeStampRange.setEnd(Long.MAX_VALUE);
        return timeStampRange;
    }

    @Override
    public TimeStampRange visitTsrange_end(HBaseSQLParser.Tsrange_endContext endContext) {
        TimeStampRange timeStampRange = new TimeStampRange();
        timeStampRange.setStart(0L);
        long stopTs = this.extractTimeStamp(endContext.tsExp());
        if (endContext.leOper().LESS() != null) {
            stopTs -= 1;
        }
        timeStampRange.setEnd(stopTs);
        return timeStampRange;
    }

    @Override
    public TimeStampRange visitTsrange_startAndEnd(HBaseSQLParser.Tsrange_startAndEndContext startAndEndContext) {
        TimeStampRange timeStampRange = new TimeStampRange();
        long startTs = this.extractTimeStamp(startAndEndContext.tsExp(0));
        long stopTs = this.extractTimeStamp(startAndEndContext.tsExp(1));
        if (startAndEndContext.gtOper().GREATER() != null) {
            startTs += 1;
        }
        if (startAndEndContext.leOper().LESS() != null) {
            stopTs -= 1;
        }
        if (startTs > stopTs) {
            throw new HBaseSqlAnalysisException(String.format("The start time [%s] cannot be greater" +
                    " than the end time [%s].", startTs, stopTs));
        }
        timeStampRange.setStart(startTs);
        timeStampRange.setEnd(stopTs);
        return timeStampRange;
    }

    public TimeStampRange parseTimeStampRange(HBaseSQLParser.TsRangeContext tsRangeContext) {
        MyAssert.checkNotNull(tsRangeContext);
        return tsRangeContext.accept(this);
    }

}
