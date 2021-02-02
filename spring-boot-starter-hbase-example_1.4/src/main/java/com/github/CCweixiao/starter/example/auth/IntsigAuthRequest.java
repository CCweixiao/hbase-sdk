package com.github.CCweixiao.starter.example.auth;

import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthDefaultRequest;

/**
 * @author leojie 2021/1/19 9:43 下午
 */
public class IntsigAuthRequest  extends AuthDefaultRequest {
    public IntsigAuthRequest(AuthConfig config){
        super(config,IntsigAuthSource.INTSIG);
    }

    public IntsigAuthRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, IntsigAuthSource.INTSIG, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        doPostAuthorizationCode(authCallback.getCode());

        return null;
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        return null;
    }

    @Override
    public AuthResponse revoke(AuthToken authToken) {
        return null;
    }

    @Override
    public AuthResponse refresh(AuthToken authToken) {
        return null;
    }
}
