package com.github.CCweixiao.thrift;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * <p>HBase thrift 连接池的默认配置</p>
 * @author leojie 2020/12/27 2:51 下午
 */
public class HBaseThriftPoolConfig extends GenericObjectPoolConfig {
    public HBaseThriftPoolConfig() {

        // 连接池中的最大连接数，默认8，根据服务端可以容纳的最大连接数和当前并发数进行合理设置
        setMaxTotal(1);
        // 连接池中确保的最少空闲连接数
        setMinIdle(1);
        // 连接池中允许的最大空闲连接数
        setMaxIdle(1);

        // 连接池用尽后，调用者是否等待，为true时，maxWaitMillis才生效
        setBlockWhenExhausted(true);
        // 连接池用尽后，调用者的最大等待时间，毫秒，默认-1，表示永不超时
        setMaxWaitMillis(6000);

        // 每次从资源池中拿/归还连接是否校验连接的有效性，默认false，避免每次使用或归还连接与服务端进行一次连接开销
        setTestOnBorrow(false);
        setTestOnReturn(false);
        // 开启JMX监控
        setJmxEnabled(true);

        // 是否开启空闲连接检测，默认false，建议true
        setTestWhileIdle(true);
        // 空闲连接的检测周期，毫秒，默认-1不进行检测，此处周期设置为3分钟
        setTimeBetweenEvictionRunsMillis(60 * 1000);
        // 空闲连接检测时，每次检测资源的个数，设置为-1，就是对所有连接进行检测
        setNumTestsPerEvictionRun(-1);

        // 连接池中连接的最小空闲时间，默认180000毫秒，30分钟，此处设置为6分钟
        setMinEvictableIdleTimeMillis(60 * 1000);


        //硬闲置  3秒没有占用设置为闲置, 检测线程直接剔除闲置，保持的最小空闲数，会被剔除且重新生成 硬闲置设置之后，软闲置设置无效
        //setMinEvictableIdleTimeMillis(3000);
        //软闲置  3秒没有占用设置为闲置, 当空闲连接>最小空闲数，才执行剔除闲置连接，否则维持最小空闲数，即使闲置了也不会剔除
        //setSoftMinEvictableIdleTimeMillis(3000);

    }
}

