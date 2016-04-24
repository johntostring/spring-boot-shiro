package com.millinch.spring.boot.autoconfigure.shiro;

import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * This guy is lazy, nothing left.
 *
 * @author John Zhang
 */
@ConfigurationProperties(prefix = "shiro.session")
public class ShiroSessionProperties {

    private long globalSessionTimeout = 30 * 60 * 1000L;

    private boolean deleteInvalidSessions = true;

    private long validationInterval = 60 * 60 * 1000L;

    private boolean validationSchedulerEnabled = true;

    private String activeSessionsCacheName = "shiro-activeSessionCache";

    private Class<? extends SessionIdGenerator> idGenerator = JavaUuidSessionIdGenerator.class;

    public long getGlobalSessionTimeout() {
        return globalSessionTimeout;
    }

    public void setGlobalSessionTimeout(long globalSessionTimeout) {
        this.globalSessionTimeout = globalSessionTimeout;
    }

    public boolean isDeleteInvalidSessions() {
        return deleteInvalidSessions;
    }

    public void setDeleteInvalidSessions(boolean deleteInvalidSessions) {
        this.deleteInvalidSessions = deleteInvalidSessions;
    }

    public long getValidationInterval() {
        return validationInterval;
    }

    public void setValidationInterval(long validationInterval) {
        this.validationInterval = validationInterval;
    }

    public boolean isValidationSchedulerEnabled() {
        return validationSchedulerEnabled;
    }

    public void setValidationSchedulerEnabled(boolean validationSchedulerEnabled) {
        this.validationSchedulerEnabled = validationSchedulerEnabled;
    }

    public String getActiveSessionsCacheName() {
        return activeSessionsCacheName;
    }

    public void setActiveSessionsCacheName(String activeSessionsCacheName) {
        this.activeSessionsCacheName = activeSessionsCacheName;
    }

    public Class<? extends SessionIdGenerator> getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(Class<? extends SessionIdGenerator> idGenerator) {
        this.idGenerator = idGenerator;
    }
}
