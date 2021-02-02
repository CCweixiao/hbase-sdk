package com.github.CCweixiao.model;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.util.StrUtil;

/**
 * @author leojie 2020/9/9 10:25 下午
 */
public class FamilyDesc {
    private final String familyName;
    private final Integer maxVersions;
    private final Integer timeToLive;
    private final String compressionType;
    private final Integer replicationScope;

    public FamilyDesc(Builder builder) {
        this.familyName = builder.familyName;
        this.maxVersions = builder.maxVersions;
        this.timeToLive = builder.timeToLive;
        this.compressionType = builder.compressionType;
        this.replicationScope = builder.replicationScope;
    }

    public static class Builder {
        private String familyName;
        private Integer maxVersions;
        private Integer timeToLive;
        private String compressionType;
        private Integer replicationScope;

        public Builder familyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Builder maxVersions(Integer maxVersions) {
            if (maxVersions <= 0) {
                throw new HBaseOperationsException("最大版本号必须大于0");
            }
            this.maxVersions = maxVersions;
            return this;
        }

        public Builder timeToLive(Integer timeToLive) {
            if (timeToLive <= 0) {
                throw new HBaseOperationsException("ttl必须大于0");
            }
            this.timeToLive = timeToLive;
            return this;
        }

        public Builder compressionType(String compressionType) {
            if(StrUtil.isBlank(compressionType)){
                this.compressionType = HMHBaseConstant.DEFAULT_COMPRESSION_TYPE;
            }else{
                this.compressionType = compressionType;
            }
            return this;
        }

        public Builder replicationScope(Integer replicationScope) {
            this.replicationScope = replicationScope;
            return this;
        }

        public FamilyDesc build() {
            return new FamilyDesc(this);
        }
    }

    public String getFamilyName() {
        return familyName;
    }

    public Integer getMaxVersions() {
        return maxVersions;
    }

    public Integer getTimeToLive() {
        return timeToLive;
    }

    public String getCompressionType() {
        if(StrUtil.isBlank(compressionType)){
            return HMHBaseConstant.DEFAULT_COMPRESSION_TYPE;
        }else{
            return compressionType.toUpperCase();
        }
    }

    public Integer getReplicationScope() {
        return replicationScope;
    }

    @Override
    public String toString() {
        return "FamilyDesc{" +
                "familyName='" + getFamilyName() + '\'' +
                ", maxVersions=" + getMaxVersions() +
                ", timeToLive=" + getTimeToLive() +
                ", compressionType='" + getCompressionType() + '\'' +
                ", replicationScope=" + getReplicationScope() +
                '}';
    }
}
