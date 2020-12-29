package com.github.CCweixiao.thrift;

/**
 * @author leojie 2020/12/27 11:41 下午
 */
public class HBaseThriftServiceHolder {


    private volatile static HBaseThriftService hBaseThriftService;

    private HBaseThriftServiceHolder() {

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

}
