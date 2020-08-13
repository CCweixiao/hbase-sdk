package com.leo.hbase.sdk.core;

import org.apache.hadoop.hbase.client.BufferedMutator;

/**
 * <p>用于批量的数据操作。</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/5/13 10:21 上午
 * @since 1.0
 */
public interface MutatorCallback {
    /**
     * use mutator api to update put and delete
     *
     * @param mutator 写入缓冲区对象
     * @throws Throwable 异常抛出
     */
    void doInMutator(BufferedMutator mutator) throws Throwable;
}
