package com.millinch.spring.boot.autoconfigure.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Configuration properties for Shiro.
 *
 * @author John Zhang
 */
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {
    /**
     * Custom Realm
     */
    private Class<?> realm;
    /**
     * URL of login
     */
    private String loginUrl = "/login";
    /**
     * URL of success
     */
    private String successUrl = "/index";
    /**
     * URL of unauthorized
     */
    private String unauthorizedUrl = "/unauthorized";

    private String hashAlgorithmName = "MD5";

    private int hashIterations = 1;

    private boolean storedCredentialsHexEncoded = true;
    /**
     * Filter chain
     */
    private Map<String, String> filterChainDefinitions;

    public Class<?> getRealm() {
        return realm;
    }

    public void setRealm(Class<?> realm) {
        this.realm = realm;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public String getHashAlgorithmName() {
        return hashAlgorithmName;
    }

    public void setHashAlgorithmName(String hashAlgorithmName) {
        this.hashAlgorithmName = hashAlgorithmName;
    }

    public int getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public boolean isStoredCredentialsHexEncoded() {
        return storedCredentialsHexEncoded;
    }

    public void setStoredCredentialsHexEncoded(boolean storedCredentialsHexEncoded) {
        this.storedCredentialsHexEncoded = storedCredentialsHexEncoded;
    }

    public Map<String, String> getFilterChainDefinitions() {
        return filterChainDefinitions;
    }

    public void setFilterChainDefinitions(Map<String, String> filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }
}
