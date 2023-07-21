package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.adapter.BaseHBaseTableAdapter;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseQueryParamsException;
import com.github.CCweixiao.hbase.sdk.common.query.GetParams;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.yetus.audience.InterfaceAudience;

import java.io.IOException;
import java.util.*;


/**
 * @author leo.jie (weixiao.me@aliyun.com)
 */
@InterfaceAudience.Private
public class HBaseTableAdapter extends BaseHBaseTableAdapter {
    public HBaseTableAdapter(Configuration configuration) {
        super(configuration);
    }

    public HBaseTableAdapter(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public HBaseTableAdapter(Properties properties) {
        super(properties);
    }

    @Override
    public Get buildGet(GetParams getParams) {
        Get get = new Get(Bytes.toBytes(getParams.getRowKey()));
        if (getParams.onlyFamily()) {
            get.addFamily(Bytes.toBytes(getParams.getFamilyName()));
        }
        if (getParams.familyWithQualifiers()) {
            getParams.getColumnNames().forEach(colName -> {
                if (StringUtil.isNotBlank(colName)) {
                    get.addColumn(Bytes.toBytes(getParams.getFamilyName()), Bytes.toBytes(colName));
                }
            });
        }
        try {
            get.readVersions(getParams.getVersions());
            if (getParams.getTimeRange() != null) {
                get.setTimeRange(getParams.getTimeRange().getMinTimestamp(), getParams.getTimeRange().getMaxTimestamp());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return get;
    }

    @Override
    public Scan buildScan(ScanParams scanParams) {
        Scan scan = new Scan();
        if (scanParams.onlyFamily()) {
            scan.addFamily(Bytes.toBytes(scanParams.getFamilyName()));
        }
        if (scanParams.familyWithQualifiers()) {
            scanParams.getColumnNames().forEach(colName -> {
                if (StringUtil.isNotBlank(colName)) {
                    scan.addColumn(Bytes.toBytes(scanParams.getFamilyName()), Bytes.toBytes(colName));
                }
            });
        }

        if (scanParams.startRowIsSet()) {
            scan.withStartRow(Bytes.toBytes(scanParams.getStartRow()), scanParams.isInclusiveStartRow());
        }

        if (scanParams.endRowIsSet()) {
            scan.withStopRow(Bytes.toBytes(scanParams.getStopRow()), scanParams.isInclusiveStopRow());
        }

        if (scanParams.getFilter() != null && scanParams.getFilter().customFilter() instanceof Filter) {
            scan.setFilter((Filter) scanParams.getFilter().customFilter());
        }

        if (scanParams.timeRangeIsSet()) {
            try {
                scan.setTimeRange(scanParams.getMinTimestamp(), scanParams.getMaxTimestamp());
            } catch (IOException e) {
                throw new HBaseQueryParamsException(e);
            }
        }

        if (scanParams.timestampIsSet()) {
            try {
                scan.setTimeStamp(scanParams.getTimestamp());
            } catch (IOException e) {
                throw new HBaseQueryParamsException(e);
            }
        }

        if (scanParams.getVersions() > 0) {
            scan.setMaxVersions(scanParams.getVersions());
        }

        if (scanParams.isCacheBlocks()) {
            scan.setCacheBlocks(scanParams.isCacheBlocks());
        }

        if (scanParams.isReversed()) {
            scan.setReversed(scan.isReversed());
        }

        if (scanParams.getCaching() > 0) {
            scan.setCaching(scanParams.getCaching());
        }

        if (scanParams.getBatch() > 0) {
            if (!(scan.hasFilter() && scan.getFilter().hasFilterRow())) {
                scan.setBatch(scanParams.getBatch());
            }
        }

        if (scanParams.getMaxResultSize() > 0) {
            scan.setMaxResultSize(scanParams.getMaxResultSize());
        }

        if (scanParams.getLimit() > 0) {
            scan.setLimit(scanParams.getLimit());
        }

        return scan;
    }


}
