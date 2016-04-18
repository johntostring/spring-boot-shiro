package com.millinch.spring.boot.autoconfigure.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Shiro FormAuthenticationFilter
 *
 * @author John Zhang
 */
@ConfigurationProperties(prefix = "shiro.sign-in")
public class ShiroSignInProperties {
    /**
     * Request parameter's name of user
     */
    private String userParam = "username";

    /**
     * Request parameter's name of password
     */
    private String passwordParam = "password";

    private String rememberMeParam = "rememberMe";

    public String getUserParam() {
        return userParam;
    }

    public void setUserParam(String userParam) {
        this.userParam = userParam;
    }

    public String getPasswordParam() {
        return passwordParam;
    }

    public void setPasswordParam(String passwordParam) {
        this.passwordParam = passwordParam;
    }

    public String getRememberMeParam() {
        return rememberMeParam;
    }

    public void setRememberMeParam(String rememberMeParam) {
        this.rememberMeParam = rememberMeParam;
    }
}
