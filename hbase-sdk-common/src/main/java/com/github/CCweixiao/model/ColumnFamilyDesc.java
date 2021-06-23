package com.github.CCweixiao.model;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.util.StrUtil;

/**
 * @author leojie 2020/9/9 10:25 下午
 */
public class ColumnFamilyDesc {
    public static final int REPLICATION_SCOPE = 0;
    public static final int VERSIONS = 1;
    public static final int MIN_VERSIONS = 0;
    public static final String COMPRESSION = HMHBaseConstant.DEFAULT_COMPRESSION_TYPE;
    private static final int TTL = 2147483647;
    private static final int BLOCKSIZE = 65536;
    private static final boolean IN_MEMORY = false;
    private static final boolean BLOCK_CACHE = true;


    private final String familyName;
    private final int replicationScope;
    private final int versions;
    private final int minVersions;
    private final String compressionType;
    private final int timeToLive;
    private final int blockSize;
    private final boolean blockCache;
    private final boolean inMemory;

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
        private int replicationScope;
        private int versions;
        private int minVersions;
        private String compressionType;
        private int timeToLive;
        private int blockSize;
        private boolean blockCache;
        private boolean inMemory;

        public Builder familyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Builder defaultColumnFamilyDesc(String familyName) {
            this.familyName = familyName;
            this.replicationScope = REPLICATION_SCOPE;
            this.versions = VERSIONS;
            this.minVersions = MIN_VERSIONS;
            this.compressionType = COMPRESSION;
            this.timeToLive = TTL;
            this.blockSize = BLOCKSIZE;
            this.blockCache = BLOCK_CACHE;
            this.inMemory = IN_MEMORY;
            return this;
        }

        public Builder replicationScope(int replicationScope) {
            this.replicationScope = replicationScope;
            return this;
        }


        public Builder versions(int versions) {
            this.versions = versions;
            return this;
        }

        public Builder minVersions(int minVersions) {
            this.minVersions = minVersions;
            return this;
        }

        public Builder compressionType(String compressionType) {
            if (StrUtil.isBlank(compressionType)) {
                this.compressionType = COMPRESSION;
            } else {
                this.compressionType = compressionType;
            }
            return this;
        }

        public Builder timeToLive(int timeToLive) {
            if (timeToLive <= 0) {
                throw new HBaseOperationsException("ttl必须大于0");
            }
            this.timeToLive = timeToLive;
            return this;
        }

        public Builder blockSize(int blockSize) {
            this.blockSize = blockSize;
            return this;
        }

        public Builder blockCache(boolean blockCache) {
            this.blockCache = blockCache;
            return this;
        }

        public Builder inMemory(boolean inMemory) {
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

    public int getReplicationScope() {
        return this.replicationScope;
    }

    public int getVersions() {
        return this.versions;
    }

    public int getMinVersions() {
        return this.minVersions;
    }

    public String getCompressionType() {
        return this.compressionType;
    }

    public int getTimeToLive() {
        return this.timeToLive;
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public boolean isBlockCache() {
        return this.blockCache;
    }

    public boolean isInMemory() {
        return this.inMemory;
    }

    @Override
    public String toString() {
        return "ColumnFamilyDesc{" +
                "familyName='" + familyName + '\'' +
                ", replicationScope=" + replicationScope +
                ", versions=" + versions +
                ", minVersions=" + minVersions +
                ", compressionType='" + compressionType + '\'' +
                ", timeToLive=" + timeToLive +
                ", blockSize=" + blockSize +
                ", blockCache=" + blockCache +
                ", inMemory=" + inMemory +
                '}';
    }
}
