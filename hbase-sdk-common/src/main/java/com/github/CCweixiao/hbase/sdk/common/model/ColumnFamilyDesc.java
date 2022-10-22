package com.github.CCweixiao.hbase.sdk.common.model;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;

/**
 * @author leojie 2020/9/9 10:25 下午
 */
public class ColumnFamilyDesc {

    private final String familyName;
    private final Integer replicationScope;
    private final Integer versions;
    private final Integer minVersions;
    private final String compressionType;
    private final Integer timeToLive;
    private final Integer blockSize;
    private final Boolean blockCache;
    private final Boolean inMemory;

    public ColumnFamilyDesc(Builder builder) {
        this.familyName = builder.familyName;
        this.replicationScope = builder.replicationScope;
        this.versions = builder.versions;
        this.minVersions = builder.minVersions;
        this.compressionType = builder.compressionType;
        this.timeToLive = builder.timeToLive;
        this.blockSize = builder.blockSize;
        this.blockCache = builder.blockCache;
        this.inMemory = builder.inMemory;
    }

    public static class Builder {
        private String familyName;
        private Integer replicationScope;
        private Integer versions;
        private Integer minVersions;
        private String compressionType;
        private Integer timeToLive;
        private Integer blockSize;
        private Boolean blockCache;
        private Boolean inMemory;

        public Builder familyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Builder defaultColumnFamilyDesc(String familyName) {
            this.familyName = familyName;
            this.replicationScope = null;
            this.versions = null;
            this.minVersions = null;
            this.compressionType = null;
            this.timeToLive = null;
            this.blockSize = null;
            this.blockCache = null;
            this.inMemory = null;
            return this;
        }

        public Builder replicationScope(Integer replicationScope) {
            this.replicationScope = replicationScope;
            return this;
        }


        public Builder versions(Integer versions) {
            this.versions = versions;
            return this;
        }

        public Builder minVersions(Integer minVersions) {
            this.minVersions = minVersions;
            return this;
        }

        public Builder compressionType(String compressionType) {
            this.compressionType = compressionType;
            return this;
        }

        public Builder timeToLive(Integer timeToLive) {
            this.timeToLive = timeToLive;
            return this;
        }

        public Builder blockSize(Integer blockSize) {
            this.blockSize = blockSize;
            return this;
        }

        public Builder blockCache(Boolean blockCache) {
            this.blockCache = blockCache;
            return this;
        }

        public Builder inMemory(Boolean inMemory) {
            this.inMemory = inMemory;
            return this;
        }

        public ColumnFamilyDesc build() {
            return new ColumnFamilyDesc(this);
        }
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public Integer getReplicationScope() {
        return this.replicationScope;
    }

    public Integer getVersions() {
        return this.versions;
    }

    public Integer getMinVersions() {
        return this.minVersions;
    }

    public String getCompressionType() {
        return this.compressionType;
    }

    public Integer getTimeToLive() {
        return this.timeToLive;
    }

    public String getTTL() {
        return humanReadableTTL(this.timeToLive);
    }

    private static String humanReadableTTL(long interval) {
        StringBuilder sb = new StringBuilder();
        if (interval == HMHBaseConstants.DEFAULT_TTL) {
            sb.append("FOREVER");
            return sb.toString();
        } else if (interval < 60L) {
            sb.append(interval);
            sb.append(" 秒");
            return sb.toString();
        } else {
            int days = (int) (interval / 86400L);
            int hours = (int) (interval - (86400L * days)) / 3600;
            int minutes = (int) (interval - (86400L * days) - (long) (3600 * hours)) / 60;
            int seconds = (int) (interval - (86400L * days) - (long) (3600 * hours) - (long) (60 * minutes));
            sb.append(interval);
            sb.append(" 秒 (");
            if (days > 0) {
                sb.append(days);
                sb.append(" 天");
            }

            if (hours > 0) {
                sb.append(days > 0 ? " " : "");
                sb.append(hours);
                sb.append(" 小时");
            }

            if (minutes > 0) {
                sb.append(days + hours > 0 ? " " : "");
                sb.append(minutes);
                sb.append(" 分钟");
            }

            if (seconds > 0) {
                sb.append(days + hours + minutes > 0 ? " " : "");
                sb.append(seconds);
                sb.append(" 秒");
            }

            sb.append(")");
            return sb.toString();
        }
    }

    public Integer getBlockSize() {
        return this.blockSize;
    }

    public Boolean isBlockCache() {
        return this.blockCache;
    }

    public Boolean isInMemory() {
        return this.inMemory;
    }

    @Override
    public String toString() {
        return String.format("{NAME => '%s', VERSIONS => '%s', TTL => '%s', MIN_VERSIONS => '%s', REPLICATION_SCOPE => '%s', " +
                        "IN_MEMORY => '%s', COMPRESSION => '%s', BLOCK_CACHE => '%s', BLOCKSIZE => '%s'}",
                getFamilyName(), getVersions(), getTTL(), getMinVersions(), getReplicationScope(), isInMemory(),
                getCompressionType(), isBlockCache(), getBlockSize());
    }
}
