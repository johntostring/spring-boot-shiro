package com.millinch.spring.boot.autoconfigure.shiro;

import org.apache.shiro.realm.Realm;
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
    private Class<? extends Realm> realmClass = null;

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

    /**
     * 密码重试次数上限
     */
    private int retryMax = 5;

    private boolean storedCredentialsHexEncoded = true;

    /**
     * Filter chain
     */
    private Map<String, String> filterChainDefinitions;

    private final Ehcache ehcache = new Ehcache();

    public Class<? extends Realm> getRealmClass() {
        return realmClass;
    }

    public void setRealmClass(Class<? extends Realm> realmClass) {
        this.realmClass = realmClass;
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

    public int getRetryMax() {
        return retryMax;
    }

    public void setRetryMax(int retryMax) {
        this.retryMax = retryMax;
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

    public Ehcache getEhcache() {
        return ehcache;
    }

    public static class Ehcache {

        private String cacheManagerConfigFile = "classpath:org/apache/shiro/cache/ehcache/ehcache.xml";

        public String getCacheManagerConfigFile() {
            return cacheManagerConfigFile;
        }

        public void setCacheManagerConfigFile(String cacheManagerConfigFile) {
            this.cacheManagerConfigFile = cacheManagerConfigFile;
        }
    }
}
