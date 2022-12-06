package com.github.CCweixiao.hbase.sdk.service.row;

import com.github.CCwexiao.hbase.sdk.dsl.model.row.DataSet;
import com.github.CCwexiao.hbase.sdk.dsl.model.row.DataSetFormatter;
import com.github.CCwexiao.hbase.sdk.dsl.model.row.Row;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author leojie 2022/12/6 22:34
 */
public class DataSetTest {
    @Test
    public void testDataSet() {

        Row row1 = Row.of("10001")
                .appendColumn("f1", "name", "leo")
                .appendColumn("f1", "age", 12);

        Row row2 = Row.of("10002")
                .appendColumn("f1", "name", "leo1")
                .appendColumn("f1", "age", 14);
        DataSet dataSet = DataSet.of("test:test_sql")
                .appendRow(row1).appendRow(row2);

    }

    @Test
    public void testShow() {
        List<String> cols = Arrays.asList("name", "age", "address");
        List<List<String>> values = Arrays.asList(Arrays.asList("leo", "18", "通过JDBC查询数据库表中的数据"),
                Arrays.asList("leo2", "17", "通过JDBC查询数据库表中的数据通过JDBC查询数据库表中的数据"));
        DataSetFormatter dataSetFormatter = new DataSetFormatter(cols, values);
        System.out.println(dataSetFormatter.printTable());
        System.out.println();
    }
}
