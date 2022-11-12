package com.github.CCweixiao.hbase.sdk.common.security;

/**
 * @author leojie 2022/10/22 14:52
 */
public enum AuthType {
    /**
     * auth type
     */
    SIMPLE("simple"),
    KERBEROS("kerberos");
    private String authType;

    AuthType(String authType) {
        this.authType = authType;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }
}