package com.github.CCwexiao.dsl.client;

/**
 * @author leojie 2020/11/28 10:58 上午
 */
public interface RowKey {
    /**
     * 转换rowKey到字节数组
     *
     * @return rowKey的字节数组
     */
    byte[] toBytes();
}
