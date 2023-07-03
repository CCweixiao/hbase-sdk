package com.github.CCweixiao.hbase.sdk;

import com.google.common.base.Preconditions;
import org.apache.hadoop.util.Daemon;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leojie 2023/6/30 18:49
 */
public class HBaseHedgedReadExecutor {
    private static volatile ThreadPoolExecutor HEDGED_READ_THREAD_POOL;

    private HBaseHedgedReadExecutor() {}

    public static ThreadPoolExecutor getHBaseHedgedReadExecutor(int num) {
        if (HEDGED_READ_THREAD_POOL == null) {
            synchronized (HBaseHedgedReadExecutor.class) {
                if (HEDGED_READ_THREAD_POOL == null) {
                    ThreadPoolExecutor threadPool = getThreadPoolExecutor(1,
                            num, 60, new SynchronousQueue<>(),
                            "HBaseClientHedgedRead-", true);
                    threadPool.allowCoreThreadTimeOut(true);
                    HEDGED_READ_THREAD_POOL = threadPool;
                }
            }
        }
        return HEDGED_READ_THREAD_POOL;
    }

    public static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize,
                                                           int maxPoolSize, long keepAliveTimeSecs, BlockingQueue<Runnable> queue,
                                                           String threadNamePrefix, boolean runRejectedExec) {
        Preconditions.checkArgument(corePoolSize > 0);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                maxPoolSize, keepAliveTimeSecs, TimeUnit.SECONDS,
                queue, new Daemon.DaemonFactory() {
            private final AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = super.newThread(r);
                t.setName(threadNamePrefix + threadIndex.getAndIncrement());
                return t;
            }
        });
        if (runRejectedExec) {
            threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor
                    .CallerRunsPolicy() {
                @Override
                public void rejectedExecution(Runnable runnable,
                                              ThreadPoolExecutor e) {
                    super.rejectedExecution(runnable, e);
                }
            });
        }
        return threadPoolExecutor;
    }
}
