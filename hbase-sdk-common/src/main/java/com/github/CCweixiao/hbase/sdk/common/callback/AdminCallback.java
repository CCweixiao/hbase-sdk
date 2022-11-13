package com.github.CCweixiao.hbase.sdk.common.callback;

/**
 * <p>A functional interface for defining administrator actions.</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface AdminCallback<T, A> {
    /**
     * do action in admin
     *
     * @param admin admin
     * @return result
     * @throws Throwable throw error
     */
    T doInAdmin(A admin) throws Throwable;
}
