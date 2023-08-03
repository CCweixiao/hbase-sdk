package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.adapter.BaseHBaseTableAdapter;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseQueryParamsException;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowParam;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowsParam;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
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

    @Override
    public Get buildGet(GetRowParam getRowParam) {
        Get get = new Get(Bytes.toBytes(getRowParam.getRowKey()));
        if (getRowParam.onlyFamily()) {
            get.addFamily(Bytes.toBytes(getRowParam.getFamily()));
        }
        if (getRowParam.familyWithQualifiers()) {
            getRowParam.getQualifiers().forEach(colName -> {
                if (StringUtil.isNotBlank(colName)) {
                    get.addColumn(Bytes.toBytes(getRowParam.getFamily()), Bytes.toBytes(colName));
                }
            });
        }
        try {
            get.setMaxVersions(getRowParam.getVersions());
            if (getRowParam.getTimeRange() != null) {
                get.setTimeRange(getRowParam.getTimeRange().getMinTimestamp(),
                        getRowParam.getTimeRange().getMaxTimestamp());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return get;
    }

    @Override
    public List<Get> buildGets(GetRowsParam getRowsParam) {
        if (getRowsParam == null || getRowsParam.getRowKeyList() == null || getRowsParam.getRowKeyList().isEmpty()) {
            return new ArrayList<>(0);
        }
        List<Get> gets = new ArrayList<>(getRowsParam.getRowKeyList().size());
        for (String rowKey : getRowsParam.getRowKeyList()) {
            Get get = new Get(Bytes.toBytes(rowKey));
            if (getRowsParam.onlyFamily()) {
                get.addFamily(Bytes.toBytes(getRowsParam.getFamily()));
            }
            if (getRowsParam.familyWithQualifiers()) {
                getRowsParam.getQualifiers().forEach(colName -> {
                    if (StringUtil.isNotBlank(colName)) {
                        get.addColumn(Bytes.toBytes(getRowsParam.getFamily()), Bytes.toBytes(colName));
                    }
                });
            }
            try {
                get.setMaxVersions(getRowsParam.getVersions());
                if (getRowsParam.getTimeRange() != null) {
                    get.setTimeRange(getRowsParam.getTimeRange().getMinTimestamp(),
                            getRowsParam.getTimeRange().getMaxTimestamp());
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
            gets.add(get);
        }

        return gets;
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
            scan.setStartRow(Bytes.toBytes(scanParams.getStartRow()));
        }

        if (scanParams.endRowIsSet()) {
            scan.setStopRow(Bytes.toBytes(scanParams.getStopRow()));
        }

        if (StringUtil.isNotBlank(scanParams.getRowPrefix())) {
            PrefixFilter filter = new PrefixFilter(Bytes.toBytes(scanParams.getRowPrefix()));
            scan.setFilter(filter);
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
        } else {
            scan.setCaching(getClientScannerCaching());
        }

        if (scanParams.getBatch() > 0) {
            if (!(scan.hasFilter() && scan.getFilter().hasFilterRow())) {
                scan.setBatch(scanParams.getBatch());
            }
        }

        if (scanParams.getMaxResultSize() > 0) {
            scan.setMaxResultSize(scanParams.getMaxResultSize());
        }

        return scan;
    }


}