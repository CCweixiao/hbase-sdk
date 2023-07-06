package com.github.CCweixiao.hbase.sdk.common.retry;

import com.github.CCweixiao.hbase.sdk.common.exception.BaseRetryTemplateHandleException;

/**
 * @author leojie 2023/7/5 16:57
 */
public class BaseRetryTemplate {
    private final int maxRetryNum;
    private final int retryInterval;

    private BaseRetryTemplate(Builder builder) {
        this.maxRetryNum = builder.maxRetryNum;
        this.retryInterval = builder.retryInterval;
    }

    public static class Builder {
        private int maxRetryNum;
        private int retryInterval;

        public Builder maxRetryNum(int maxRetryNum) {
            this.maxRetryNum = maxRetryNum;
            return this;
        }

        public Builder retryInterval(int retryInterval) {
            this.retryInterval = retryInterval;
            return this;
        }

        public BaseRetryTemplate build() {
            return new BaseRetryTemplate(this);
        }
    }

    public int getMaxRetryNum() {
        return maxRetryNum;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public <T> T execute(RetryExecutor<T> executor) {
        T execRes = null;
        try {
            execRes = executor.execute();
        } catch (Exception e) {
            for (int i = 0; i < this.getMaxRetryNum(); i++) {
                try {
                    execRes = executor.execute();
                } catch (Exception ex) {
                    if (i == (this.getMaxRetryNum() - 1)) {
                        throw new BaseRetryTemplateHandleException(ex);
                    }
                    shortSpin(this.getRetryInterval());
                }
            }
        }
        return execRes;
    }

    private void shortSpin(int interval) {
        if (interval <= 0) {
            return;
        }
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
        }
    }
}
