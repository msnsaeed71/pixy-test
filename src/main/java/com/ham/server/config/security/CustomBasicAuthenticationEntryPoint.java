package com.ham.server.config.security;

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(SecurityConfiguration.REALM);
        super.afterPropertiesSet();
    }
}
