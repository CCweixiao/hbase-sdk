package com.leo.hbase.sdk.core.model;

import com.leo.hbase.sdk.core.exception.HBaseOperationsException;
import com.leo.hbase.sdk.core.util.StrUtil;

/**
 * HBase创表时列簇的数据模型定义类
 *
 * @author leojie 2020/8/15 10:42 上午
 */
public class HFamilyBuilder {
    public static final int DEFAULT_TTL = 2147483647;
    /**
     * 列簇名
     */
    private final String familyName;
    /**
     * 最大版本数
     */
    private final int maxVersions;
    /**
     * 最小版本数，默认为0，如果大于0，必须设置TTL（数据保存时间）
     */
    private final int minVersions;
    /**
     * ttl
     */
    private final int timeToLive;

    /**
     * 压缩类型，支持LZ4、LZO、GZIP、SNAPPY压缩，LZO、SNAPPY需要集群安装，
     * LZ4需要调用hadoop的lib，GZIP压缩率高但速度更慢
     */
    private final String compressionType;

    private HFamilyBuilder(Builder builder) {
        this.familyName = builder.familyName;
        this.maxVersions = builder.maxVersions;
        this.minVersions = builder.minVersions;
        this.timeToLive = builder.timeToLive;
        this.compressionType = builder.compressionType;

    }

    public static class Builder {
        private String familyName;
        private int maxVersions;
        private int minVersions;
        private int timeToLive;
        private String compressionType;

        public Builder familyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Builder maxVersions(int maxVersions) {
            this.maxVersions = maxVersions;
            return this;
        }

        public Builder minVersions(int minVersions) {
            this.minVersions = minVersions;
            return this;
        }

        public Builder timeToLive(int timeToLive) {
            this.timeToLive = timeToLive;
            return this;
        }

        public Builder compressionType(String compressionType) {
            this.compressionType = compressionType;
            return this;
        }

        public HFamilyBuilder build() {
            return new HFamilyBuilder(this);
        }
    }

    public String getFamilyName() {
        if(StrUtil.isBlank(familyName)){
            throw new HBaseOperationsException("family name is not empty.");
        }
        return familyName;
    }

    public int getMaxVersions() {
        if (this.maxVersions <= 0) {
            return 1;
        }
        return maxVersions;
    }

    public int getMinVersions() {
        if (this.minVersions <= 0) {
            return 0;
        }
        if (this.timeToLive >= DEFAULT_TTL) {
            throw new HBaseOperationsException("if min version is more than 1, please set a value of ttl.");
        }
        return minVersions;
    }

    public int getTimeToLive() {
        if (this.timeToLive <= 0) {
            return DEFAULT_TTL;
        }
        return timeToLive;
    }

    public String getCompressionType() {
        return compressionType;
    }
}
