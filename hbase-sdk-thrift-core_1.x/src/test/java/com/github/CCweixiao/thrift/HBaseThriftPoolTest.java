package com.github.CCweixiao.thrift;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HBaseThriftPoolTest {
    @Test
    public void testPut(){
        // 声明连接池的配置对象
        HBaseThriftPoolConfig config = new HBaseThriftPoolConfig();
        // 创建HBase Thrift连接池
        HBaseThriftPool hBaseThriftPool = new HBaseThriftPool(config, "localhost", 9090);
        // 从连接池中获取到HBaseThrift对象，HBaseThrift中封装了对HBase的读写操作
        final HBaseThrift hBaseThrift = hBaseThriftPool.getResource();
        Map<String, String> data = new HashMap<>();
        data.put("info:name", "leo");
        data.put("info:age", "18");
        data.put("info:address", "shanghai");
        // 保存数据
        hBaseThrift.save("leo_test", "a10002", data);
        // 关闭hBaseThrift对象，关闭即把该对象放回连接池中
        hBaseThrift.close();
    }
    @Test
    public void testPut2(){
        HBaseThriftService hBaseThriftService = HBaseThriftServiceHolder.getInstance("localhost", 9090);
        Map<String, String> data = new HashMap<>();
        data.put("info:name", "leo");
        data.put("info:age", "18");
        data.put("info:address", "shanghai");
        // 保存数据
        hBaseThriftService.save("leo_test", "a10003", data);
    }
}
