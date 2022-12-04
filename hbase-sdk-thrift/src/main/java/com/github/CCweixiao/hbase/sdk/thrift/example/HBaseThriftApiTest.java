package com.github.CCweixiao.hbase.sdk.thrift.example;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import org.apache.hadoop.hbase.thrift.generated.Hbase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leojie 2021/1/6 8:28 下午
 */
public class HBaseThriftApiTest {
    public static void main(String[] args) {
        test2();
    }

    /**
     * 同一连接闲置失效异常
     */
    public static void test1(){
        TSocket socket = new TSocket("myhbase", 9090);
        List<String> allTableNames;
        Hbase.Client hbaseClient;

        TProtocol protocol = new TBinaryProtocol(socket);
        hbaseClient = new Hbase.Client(protocol);

        try {
            socket.open();
            allTableNames = hbaseClient.getTableNames().stream().map(t -> ColumnType.toString(t.array())).collect(Collectors.toList());
            System.out.println(allTableNames);
            Thread.sleep(60000L);
            System.out.println("此处停顿了一分钟......");
            allTableNames = hbaseClient.getTableNames().stream().map(t -> ColumnType.toString(t.array())).collect(Collectors.toList());
            System.out.println(allTableNames);
            Thread.sleep(120000L);
            System.out.println("此处停顿了两分钟......");
            allTableNames = hbaseClient.getTableNames().stream().map(t -> ColumnType.toString(t.array())).collect(Collectors.toList());
            System.out.println(allTableNames);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    /**
     * 连接频繁创建的开销与短连接问题
     */
    public static void test2(){
        int x = 0;
        while (true){
            TSocket socket = new TSocket("myhbase", 9090);

            TProtocol protocol = new TBinaryProtocol(socket, true, true);
            Hbase.Client hbaseClient = new Hbase.Client(protocol);

            try {
                socket.open();
                List<String> allTableNames = hbaseClient.getTableNames().stream().map(t -> ColumnType.toString(t.array())).collect(Collectors.toList());
                System.out.println(allTableNames);
                System.out.println(x);
            } catch (TException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x+=1;
        }
    }

}
