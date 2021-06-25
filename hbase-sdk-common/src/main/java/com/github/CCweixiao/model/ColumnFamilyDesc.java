package com.github.CCweixiao.model;

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
        return "ColumnFamilyDesc{" +
                "familyName='" + getFamilyName() + '\'' +
                ", replicationScope=" + getReplicationScope() +
                ", versions=" + getVersions() +
                ", minVersions=" + getMinVersions() +
                ", compressionType='" + getCompressionType() + '\'' +
                ", timeToLive=" + getTimeToLive() +
                ", blockSize=" + getBlockSize() +
                ", blockCache=" + isBlockCache() +
                ", inMemory=" + isInMemory() +
                '}';
    }
}
