package com.millinch.spring.boot.autoconfigure.shiro;

import com.millinch.spring.boot.autoconfigure.shiro.annotation.EnableShiroWebSupport;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.CipherService;
import org.apache.shiro.io.Serializer;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.quartz.Scheduler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import javax.servlet.Filter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This guy is lazy, nothing left.
 *
 * @author John Zhang
 */
@Configuration
@EnableShiroWebSupport
@ConditionalOnWebApplication
@Import(ShiroConfiguration.class)
@EnableConfigurationProperties({
        ShiroProperties.class, ShiroSignInProperties.class,
        ShiroCookieProperties.class, ShiroSessionProperties.class
})
public class ShiroAutoConfiguration {

    @Autowired
    private ShiroProperties properties;

    @Autowired
    private ShiroSignInProperties signInProperties;

    @Autowired
    private ShiroCookieProperties shiroCookieProperties;

    @Autowired
    private ShiroSessionProperties shiroSessionProperties;

    @Autowired(required = false)
    private CipherService cipherService;

    @Autowired(required = false)
    private Serializer<PrincipalCollection> serializer;

    @Autowired(required = false)
    private Collection<SessionListener> listeners;

    @Bean(name = "realm")
    @ConditionalOnMissingBean
    @DependsOn(value = {"lifecycleBeanPostProcessor", "credentialsMatcher"})
    public Realm realm(CredentialsMatcher credentialsMatcher) {
        Class<?> realmClass = properties.getRealm();
        Realm realm = (Realm) BeanUtils.instantiate(realmClass);
        if (realm instanceof AuthenticatingRealm) {
            ((AuthenticatingRealm) realm).setCredentialsMatcher(credentialsMatcher);
        }
        return realm;
    }

    @Bean(name = "cacheManager")
    @ConditionalOnClass(name = {"org.apache.shiro.cache.ehcache.EhCacheManager"})
    @ConditionalOnMissingBean(name = "cacheManager")
    public CacheManager ehcacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ShiroProperties.Ehcache ehcache = properties.getEhcache();
        if (ehcache.getCacheManagerConfigFile() != null) {
            ehCacheManager.setCacheManagerConfigFile(ehcache.getCacheManagerConfigFile());
        }
        return ehCacheManager;
    }

    @Bean
    @ConditionalOnMissingBean(Cookie.class)
    public Cookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName(signInProperties.getRememberMeParam());
        simpleCookie.setMaxAge(shiroCookieProperties.getMaxAge());
        simpleCookie.setValue(shiroCookieProperties.getValue());
        simpleCookie.setVersion(shiroCookieProperties.getVersion());
        simpleCookie.setHttpOnly(shiroCookieProperties.isHttpOnly());
        simpleCookie.setSecure(shiroCookieProperties.isSecure());
        return simpleCookie;
    }

    @Bean
    @ConditionalOnMissingBean(RememberMeManager.class)
    public RememberMeManager rememberMeManager(Cookie cookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(cookie);
        cookieRememberMeManager.setCipherService(cipherService);
        if (shiroCookieProperties.getCipherKey() != null) {
            cookieRememberMeManager.setCipherKey(shiroCookieProperties.getCipherKey().getBytes());
        } else {
            if (shiroCookieProperties.getEncryptionCipherKey() != null) {
                cookieRememberMeManager.setEncryptionCipherKey(shiroCookieProperties.getEncryptionCipherKey().getBytes());
            }
            if (shiroCookieProperties.getDecryptionCipherKey() != null) {
                cookieRememberMeManager.setDecryptionCipherKey(shiroCookieProperties.getDecryptionCipherKey().getBytes());
            }
        }
        cookieRememberMeManager.setSerializer(serializer);
        return cookieRememberMeManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public SessionDAO sessionDAO(CacheManager cacheManager) {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName(shiroSessionProperties.getActiveSessionsCacheName());
        Class<? extends SessionIdGenerator> idGenerator = shiroSessionProperties.getIdGenerator();
        if (idGenerator != null) {
            SessionIdGenerator sessionIdGenerator = BeanUtils.instantiate(idGenerator);
            sessionDAO.setSessionIdGenerator(sessionIdGenerator);
        }
        sessionDAO.setCacheManager(cacheManager);
        return sessionDAO;
    }

    @Bean(name = "sessionValidationScheduler")
    @ConditionalOnClass(name = {"org.quartz.Scheduler"})
    @ConditionalOnMissingBean(SessionValidationScheduler.class)
    public SessionValidationScheduler quartzSessionValidationScheduler() {
        QuartzSessionValidationScheduler quartzSessionValidationScheduler = new QuartzSessionValidationScheduler();
        return quartzSessionValidationScheduler;
    }

    @Bean(name = "sessionValidationScheduler")
    @ConditionalOnMissingBean(SessionValidationScheduler.class)
    public SessionValidationScheduler sessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler validationScheduler = new ExecutorServiceSessionValidationScheduler();
        return validationScheduler;
    }

    @Bean
    @DependsOn(value = {"cacheManager", "sessionDAO", "sessionValidationScheduler"})
    public WebSessionManager sessionManager(CacheManager cacheManager, SessionDAO sessionDAO,
                                            SessionValidationScheduler sessionValidationScheduler) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(cacheManager);
        sessionManager.setGlobalSessionTimeout(shiroSessionProperties.getGlobalSessionTimeout());
        sessionManager.setDeleteInvalidSessions(shiroSessionProperties.isDeleteInvalidSessions());
        sessionManager.setSessionValidationInterval(shiroSessionProperties.getValidationInterval());
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
        sessionManager.setSessionValidationSchedulerEnabled(shiroSessionProperties.isValidationSchedulerEnabled());
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionListeners(listeners);
        return sessionManager;
    }

    @Bean(name = "credentialsMatcher")
    @ConditionalOnMissingBean
    @DependsOn("cacheManager")
    public CredentialsMatcher credentialsMatcher(CacheManager cacheManager) {
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
        credentialsMatcher.setHashAlgorithmName(properties.getHashAlgorithmName());
        credentialsMatcher.setHashIterations(properties.getHashIterations());
        credentialsMatcher.setStoredCredentialsHexEncoded(properties.isStoredCredentialsHexEncoded());
        return credentialsMatcher;
    }

    public FormSignInFilter formSignInFilter() {
        FormSignInFilter filter = new FormSignInFilter();
        filter.setLoginUrl(properties.getLoginUrl());
        filter.setSuccessUrl(properties.getSuccessUrl());
        filter.setUsernameParam(signInProperties.getUserParam());
        filter.setPasswordParam(signInProperties.getPasswordParam());
        filter.setRememberMeParam(signInProperties.getRememberMeParam());
        return filter;
    }

    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl(properties.getLoginUrl());
        shiroFilter.setSuccessUrl(properties.getSuccessUrl());
        shiroFilter.setUnauthorizedUrl(properties.getUnauthorizedUrl());

        Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>();
        filterMap.put("authc", formSignInFilter());

        shiroFilter.setFilters(filterMap);
        shiroFilter.setFilterChainDefinitionMap(properties.getFilterChainDefinitions());
        return shiroFilter;
    }

    @Bean(name = "shiroFilter")
    @DependsOn("securityManager")
    @ConditionalOnMissingBean
    public FilterRegistrationBean filterRegistrationBean(SecurityManager securityManager) throws Exception {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        //该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setFilter((Filter) getShiroFilterFactoryBean(securityManager).getObject());
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

}
