package com.github.CCweixiao.starter.example.auth;

import me.zhyd.oauth.config.AuthSource;

/**
 * @author leojie 2021/1/19 9:39 下午
 */
public enum  IntsigAuthSource implements AuthSource {
    /**
     * 自定义
     */
    INTSIG {
        @Override
        public String authorize() {
            return "https://web-sso.intsig.net/login?platform_id=dGfGyeLHg240HsB3c64rLbUCGnuicOx8";
        }

        @Override
        public String accessToken() {
            return "https://webapi-sso-sandbox.intsig.net/auth/verify-access-token";
        }

        @Override
        public String userInfo() {
            return null;
        }
    }

}
