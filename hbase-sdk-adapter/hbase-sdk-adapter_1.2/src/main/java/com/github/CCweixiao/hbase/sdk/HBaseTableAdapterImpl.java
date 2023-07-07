package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseQueryParamsException;
import com.github.CCweixiao.hbase.sdk.common.query.ScanQueryParamsBuilder;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.yetus.audience.InterfaceAudience;

import java.io.IOException;
import java.util.*;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 */
@InterfaceAudience.Private
public class HBaseTableAdapterImpl extends AbstractHBaseTableAdapter {
    public HBaseTableAdapterImpl(Configuration configuration) {
        super(configuration);
    }

    public HBaseTableAdapterImpl(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public HBaseTableAdapterImpl(Properties properties) {
        super(properties);
    }

    @Override
    protected Scan buildScanCondition(ScanQueryParamsBuilder scanQueryParams) {
        Scan scan = new Scan();
        if (familyNameOnly(scanQueryParams.getFamilyName(), scanQueryParams.getColumnNames())) {
            scan.addFamily(Bytes.toBytes(scanQueryParams.getFamilyName()));
        }
        if (familyAndColumnNames(scanQueryParams.getFamilyName(), scanQueryParams.getColumnNames())) {
            scanQueryParams.getColumnNames().forEach(colName -> {
                if (StringUtil.isNotBlank(colName)) {
                    scan.addColumn(Bytes.toBytes(scanQueryParams.getFamilyName()), Bytes.toBytes(colName));
                }
            });
        }

        if (StringUtil.isNotBlank(scanQueryParams.getStartRow())) {
            scan.setStartRow(Bytes.toBytes(scanQueryParams.getStartRow()));
        }

        if (StringUtil.isNotBlank(scanQueryParams.getStopRow())) {
            scan.setStopRow(Bytes.toBytes(scanQueryParams.getStopRow()));
        }

        if (scanQueryParams.getFilter() != null && scanQueryParams.getFilter().customFilter() instanceof Filter) {
            scan.setFilter((Filter) scanQueryParams.getFilter().customFilter());
        }

        if (scanQueryParams.getMinTimestamp() > 0 && scanQueryParams.getMaxTimestamp() > 0) {
            try {
                scan.setTimeRange(scanQueryParams.getMinTimestamp(), scanQueryParams.getMaxTimestamp());
            } catch (IOException e) {
                throw new HBaseQueryParamsException(e);
            }
        }

        if (scanQueryParams.getTimestamp() > 0) {
            try {
                scan.setTimeStamp(scanQueryParams.getTimestamp());
            } catch (IOException e) {
                throw new HBaseOperationsException(e);
            }
        }

        if (scanQueryParams.getReadVersions() > 0) {
            scan.setMaxVersions(scanQueryParams.getReadVersions());
        }

        if (scanQueryParams.isCacheBlocks()) {
            scan.setCacheBlocks(scanQueryParams.isCacheBlocks());
        }

        if (scanQueryParams.isReversed()) {
            scan.setReversed(scan.isReversed());
        }

        if (scanQueryParams.getCaching() > 0) {
            scan.setCaching(scanQueryParams.getCaching());
        }

        if (scanQueryParams.getBatch() > 0) {
            if (!(scan.hasFilter() && scan.getFilter().hasFilterRow())) {
                scan.setBatch(scanQueryParams.getBatch());
            }
        }

        if (scanQueryParams.getMaxResultSize() > 0) {
            scan.setMaxResultSize(scanQueryParams.getMaxResultSize());
        }

        return scan;
    }


}