package com.github.CCweixiao.hbase.sdk;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author leojie 2023/6/30 18:49
 */
public class HBaseHedgedReadExecutor {
    private static volatile ThreadPoolExecutor HEDGED_READ_THREAD_POOL;

    private HBaseHedgedReadExecutor() {}

    public static ThreadPoolExecutor getHBaseHedgedReadExecutor() {
        if (HEDGED_READ_THREAD_POOL == null) {
            synchronized (HBaseHedgedReadExecutor.class) {
                if (HEDGED_READ_THREAD_POOL == null) {

                }
            }
        }
    }
}
