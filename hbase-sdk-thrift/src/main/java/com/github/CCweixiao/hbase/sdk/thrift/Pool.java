package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseThriftConnectionException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseThriftException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseThriftExhaustedPoolException;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.Closeable;
import java.util.NoSuchElementException;

/**
 * @author leojie 2020/12/27 2:48 下午
 */
public abstract class Pool<T> implements Closeable {
    protected GenericObjectPool<T> internalPool;

    public Pool() {
    }

    public Pool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {
        initPool(poolConfig, factory);
    }

    @Override
    public void close() {
        destroy();
    }

    public boolean isClosed() {
        return this.internalPool.isClosed();
    }

    public void initPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {
        if (this.internalPool != null) {
            try {
                closeInternalPool();
            } catch (Exception e) {
            }
        }
        this.internalPool = new GenericObjectPool<>(factory, poolConfig);
    }

    public T getResource() {
        try {
            return internalPool.borrowObject();
        } catch (NoSuchElementException nse) {
            if (null == nse.getCause()) {
                // 连接池耗尽
                // The exception was caused by an exhausted pool
                throw new HBaseThriftExhaustedPoolException("Could not get a resource since the pool is exhausted", nse);
            }
            // Otherwise, the exception was caused by the implemented activateObject() or ValidateObject()
            throw new HBaseThriftException("Could not get a resource from the pool", nse);
        } catch (Exception e) {
            throw new HBaseThriftConnectionException("Could not get a resource from the pool", e);
        }
    }

    protected void returnResourceObject(final T resource) {
        if (resource == null) {
            return;
        }
        try {
            internalPool.returnObject(resource);
        } catch (Exception e) {
            throw new HBaseThriftException("Could not return the resource to the pool", e);
        }
    }

    protected void returnBrokenResource(final T resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
        }
    }

    protected void returnResource(final T resource) {
        if (resource != null) {
            returnResourceObject(resource);
        }
    }

    public void destroy() {
        closeInternalPool();
    }

    protected void returnBrokenResourceObject(final T resource) {
        try {
            internalPool.invalidateObject(resource);
        } catch (Exception e) {
            throw new HBaseThriftException("Could not return the broken resource to the pool", e);
        }
    }

    protected void closeInternalPool() {
        try {
            internalPool.close();
        } catch (Exception e) {
            throw new HBaseThriftException("Could not destroy the pool", e);
        }
    }

    protected void clearInternalPool() {
        try {
            internalPool.clear();
        } catch (Exception e) {
            throw new HBaseThriftException("Could not clear the pool", e);
        }
    }

    public int getNumActive() {
        if (poolInactive()) {
            return -1;
        }

        return this.internalPool.getNumActive();
    }

    public int getNumIdle() {
        if (poolInactive()) {
            return -1;
        }

        return this.internalPool.getNumIdle();
    }

    public int getNumWaiters() {
        if (poolInactive()) {
            return -1;
        }

        return this.internalPool.getNumWaiters();
    }

    public long getMeanBorrowWaitTimeMillis() {
        if (poolInactive()) {
            return -1;
        }
        return this.internalPool.getMeanBorrowWaitTimeMillis();
    }

    public long getMaxBorrowWaitTimeMillis() {
        if (poolInactive()) {
            return -1;
        }
        return this.internalPool.getMaxBorrowWaitTimeMillis();
    }

    private boolean poolInactive() {
        return this.internalPool == null || this.internalPool.isClosed();
    }

    public void addObjects(int count) {
        try {
            for (int i = 0; i < count; i++) {
                this.internalPool.addObject();
            }
        } catch (Exception e) {
            throw new HBaseThriftException("Error trying to add idle objects", e);
        }
    }

}
