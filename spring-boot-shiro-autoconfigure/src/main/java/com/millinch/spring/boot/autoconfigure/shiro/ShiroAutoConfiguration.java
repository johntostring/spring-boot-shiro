package com.millinch.spring.boot.autoconfigure.shiro;

import com.millinch.spring.boot.autoconfigure.shiro.annotation.EnableShiroWebSupport;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import javax.servlet.Filter;
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
@EnableConfigurationProperties({ShiroProperties.class, ShiroSignInProperties.class})
public class ShiroAutoConfiguration {

    @Autowired
    private ShiroProperties properties;

    @Autowired
    private ShiroSignInProperties signInProperties;

    @Autowired(required = false)
    private CacheManager cacheManager;

    @Bean(name = "realm")
    @DependsOn("lifecycleBeanPostProcessor")
    @ConditionalOnMissingBean
    public Realm realm() {
        Class<?> realmClass = properties.getRealm();
        Realm realm = (Realm) BeanUtils.instantiate(realmClass);
        if (realm instanceof AuthenticatingRealm) {
            ((AuthenticatingRealm) realm).setCredentialsMatcher(credentialsMatcher());
        }
        return realm;
    }

    @Bean(name = "credentialsMatcher")
    @ConditionalOnMissingBean
    public CredentialsMatcher credentialsMatcher() {
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

    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultSecurityManager securityManager, Realm realm) {
        securityManager.setRealm(realm);

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
    public FilterRegistrationBean filterRegistrationBean(DefaultSecurityManager securityManager, Realm realm) throws Exception {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        //该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setFilter((Filter) getShiroFilterFactoryBean(securityManager, realm).getObject());
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

}
