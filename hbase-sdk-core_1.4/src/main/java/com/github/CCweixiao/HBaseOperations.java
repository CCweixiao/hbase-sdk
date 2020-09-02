package com.github.CCweixiao;

/**
 * <p>the interface is used to init some job，被{@link HBaseTemplate}实现。</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface HBaseOperations {
    /**
     * handle with admin operation.
     *
     * @param action admin action
     * @param <T>    return type class
     * @return return result
     */
    <T> T execute(AdminCallback<T> action);

    /**
     * handle with table operation.
     *
     * @param tableName the name of table.
     * @param action    table action callback
     * @param <T>       return type class
     * @return return result
     */
    <T> T execute(String tableName, TableCallback<T> action);

    /**
     * handle with mutator operation.
     *
     * @param tableName the name of table.
     * @param action    mutator operation.
     */
    void execute(String tableName, MutatorCallback action);


}
