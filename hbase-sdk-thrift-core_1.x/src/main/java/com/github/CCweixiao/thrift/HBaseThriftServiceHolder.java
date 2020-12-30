package com.github.CCweixiao.thrift;

import com.github.CCweixiao.util.HBaseThriftProtocol;

/**
 * @author leojie 2020/12/27 11:41 下午
 */
public class HBaseThriftServiceHolder {


    private volatile static HBaseThriftService hBaseThriftService;

    private HBaseThriftServiceHolder() {

    }

    public static HBaseThriftService getInstance() {
        if (null == hBaseThriftService) {
            synchronized (HBaseThriftServiceHolder.class) {
                if (null == hBaseThriftService) {
                    hBaseThriftService = new HBaseThriftService(HBaseThriftProtocol.DEFAULT_HOST, HBaseThriftProtocol.DEFAULT_PORT);
                }
            }
        }
        return hBaseThriftService;
    }

    public static HBaseThriftService getInstance(String host, int port) {
        if (null == hBaseThriftService) {
            synchronized (HBaseThriftServiceHolder.class) {
                if (null == hBaseThriftService) {
                    hBaseThriftService = new HBaseThriftService(host, port);
                }
            }
        }
        return hBaseThriftService;
    }

    public static HBaseThriftService getInstance(String host, int port, int poolSize) {
        if (null == hBaseThriftService) {
            synchronized (HBaseThriftServiceHolder.class) {
                if (null == hBaseThriftService) {
                    hBaseThriftService = new HBaseThriftService(host, port, poolSize);
                }
            }
        }
        return hBaseThriftService;
    }

    public static HBaseThriftService getInstance(String host, int port, HBaseThriftPoolConfig config) {
        if (null == hBaseThriftService) {
            synchronized (HBaseThriftServiceHolder.class) {
                if (null == hBaseThriftService) {
                    hBaseThriftService = new HBaseThriftService(host, port, config);
                }
            }
        }
        return hBaseThriftService;
    }

}
