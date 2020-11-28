package com.github.CCweixiao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/11/28 7:47 上午
 */
public class HBaseApiTest {
    private Connection connection;

    @Before
    public void createConn() {
        Configuration conf = HBaseConfiguration.create();
        conf.set(HConstants.ZOOKEEPER_QUORUM, "localhost");
        try {
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addData() {
        try (Table table = connection.getTable(TableName.valueOf("LEO_USER"))) {
            long row = 2020101012120303L;
            Put put = new Put(Bytes.toBytes(row));
            put.addColumn(Bytes.toBytes("g"), Bytes.toBytes("name"), Bytes.toBytes("leo"));
            put.addColumn(Bytes.toBytes("g"), Bytes.toBytes("age"), Bytes.toBytes(18));
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getData() {
        try (Table table = connection.getTable(TableName.valueOf("LEO_USER"))) {
            long row = 2020101012120303L;
            //String row = "2020101012120303";
            Get get = new Get(Bytes.toBytes(row));
            final Result result = table.get(get);
            List<Cell> cs = result.listCells();

            if (cs == null || cs.isEmpty()) {
                System.out.println("not found!");
                return;
            }

            for (Cell cell : cs) {
                System.out.println(Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println(Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println(Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
                System.out.println("######################################");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
