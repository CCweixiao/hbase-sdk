package com.github.CCweixiao.hbase.sdk.adapter_14;

import org.apache.hadoop.hbase.client.BufferedMutator;

/**
 * <p>用于批量的数据操作。</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface MutatorCallback {
    /**
     * 批量操作类型的处理回调
     *
     * @param mutator 写入缓冲区对象
     * @throws Throwable 异常抛出
     */
    void doInMutator(BufferedMutator mutator) throws Throwable;
}
