package com.github.CCweixiao.thrift;

/**
 * @author leojie 2020/12/27 11:41 下午
 */
public class HBaseThriftServiceHolder {


    private static HBaseThriftService hBaseThriftService;

    private HBaseThriftServiceHolder() {

    }


    public static HBaseThriftService getInstance(String host, int port) {
        if (hBaseThriftService == null) {
            synchronized (HBaseThriftServiceHolder.class) {
                if (hBaseThriftService == null) {
                    hBaseThriftService = new HBaseThriftService(host, port);
                }
            }
        }
        return hBaseThriftService;
    }

    public static HBaseThriftService getInstance(String host, int port, int poolSize) {
        if (hBaseThriftService == null) {
            synchronized (HBaseThriftServiceHolder.class) {
                if (hBaseThriftService == null) {
                    hBaseThriftService = new HBaseThriftService(host, port, poolSize);
                }
            }
        }
        return hBaseThriftService;
    }

}
