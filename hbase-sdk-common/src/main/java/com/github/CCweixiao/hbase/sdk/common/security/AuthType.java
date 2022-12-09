package com.github.CCweixiao.hbase.sdk.common.security;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkUnsupportedAuthTypeException;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

/**
 * @author leojie 2022/10/22 14:52
 */
public enum AuthType {
    /**
     * auth type
     */
    SIMPLE("simple"),
    KERBEROS("kerberos");
    private final String authType;

    AuthType(String authType) {
        this.authType = authType;
    }

    public String getAuthType() {
        return authType;
    }

    public static AuthType findAuthType(String authType) {
        if (StringUtil.isBlank(authType)) {
            return SIMPLE;
        }
        for (AuthType value : AuthType.values()) {
            if (authType.equalsIgnoreCase(value.getAuthType())) {
                return value;
            }
        }
        throw new HBaseSdkUnsupportedAuthTypeException(String.format("Unsupported auth type %s", authType));
    }
}
