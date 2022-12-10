package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.util.HBaseThriftProtocol;

/**
 * @author leojie 2020/12/27 11:41 下午
 */
public class HBaseThriftTemplateFactory {


    private volatile static HBaseThriftTemplate thriftTemplate;

    private HBaseThriftTemplateFactory() {

    }

    public static HBaseThriftTemplate getInstance() {
        if (null == thriftTemplate) {
            synchronized (HBaseThriftTemplateFactory.class) {
                if (null == thriftTemplate) {
                    thriftTemplate = new HBaseThriftTemplate(HBaseThriftProtocol.DEFAULT_HOST, HBaseThriftProtocol.DEFAULT_PORT);
                }
            }
        }
        return thriftTemplate;
    }

    public static HBaseThriftTemplate getInstance(String host, int port) {
        if (null == thriftTemplate) {
            synchronized (HBaseThriftTemplateFactory.class) {
                if (null == thriftTemplate) {
                    thriftTemplate = new HBaseThriftTemplate(host, port);
                }
            }
        }
        return thriftTemplate;
    }

    public static HBaseThriftTemplate getInstance(String host, int port, int poolSize) {
        if (null == thriftTemplate) {
            synchronized (HBaseThriftTemplateFactory.class) {
                if (null == thriftTemplate) {
                    thriftTemplate = new HBaseThriftTemplate(host, port, poolSize);
                }
            }
        }
        return thriftTemplate;
    }

    public static HBaseThriftTemplate getInstance(String host, int port, HBaseThriftPoolConfig config) {
        if (null == thriftTemplate) {
            synchronized (HBaseThriftTemplateFactory.class) {
                if (null == thriftTemplate) {
                    thriftTemplate = new HBaseThriftTemplate(host, port, config);
                }
            }
        }
        return thriftTemplate;
    }

}
