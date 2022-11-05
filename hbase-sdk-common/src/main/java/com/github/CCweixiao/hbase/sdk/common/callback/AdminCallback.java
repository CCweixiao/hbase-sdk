package com.github.CCweixiao.hbase.sdk.common.callback;

/**
 * <p>管理员类型的操作回调.</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface AdminCallback<T, A> {
    /**
     * 管理员类型的操作回调
     *
     * @param admin Admin的对象
     * @return 操作结果
     * @throws Throwable 异常抛出
     */
    T doInAdmin(A admin) throws Throwable;
}
