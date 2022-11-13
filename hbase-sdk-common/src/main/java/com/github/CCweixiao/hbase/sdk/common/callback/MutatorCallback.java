package com.github.CCweixiao.hbase.sdk.common.callback;

/**
 * <p>A functional interface for defining bulk data operations.</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface MutatorCallback<M> {
    /**
     * do mutator action.
     *
     * @param mutator mutator
     * @throws Throwable throw error
     */
    void doInMutator(M mutator) throws Throwable;
}

