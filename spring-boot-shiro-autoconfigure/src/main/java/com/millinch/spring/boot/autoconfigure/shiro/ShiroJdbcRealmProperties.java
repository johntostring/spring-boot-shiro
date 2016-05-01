package com.millinch.spring.boot.autoconfigure.shiro;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * This guy is lazy, nothing left.
 *
 * @author John Zhang
 */
@ConfigurationProperties(prefix = "shiro.realm.jdbc")
public class ShiroJdbcRealmProperties {

    private boolean enabled;

    private JdbcRealm.SaltStyle salt;

    /**
     * select password from users where username = ?
     */
    private String authenticationQuery;

    /**
     * select role_name from user_roles where username = ?
     */
    private String userRolesQuery;

    /**
     * select permission from roles_permissions where role_name = ?
     */
    private String permissionsQuery;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAuthenticationQuery() {
        return authenticationQuery;
    }

    public void setAuthenticationQuery(String authenticationQuery) {
        this.authenticationQuery = authenticationQuery;
    }

    public String getUserRolesQuery() {
        return userRolesQuery;
    }

    public void setUserRolesQuery(String userRolesQuery) {
        this.userRolesQuery = userRolesQuery;
    }

    public String getPermissionsQuery() {
        return permissionsQuery;
    }

    public void setPermissionsQuery(String permissionsQuery) {
        this.permissionsQuery = permissionsQuery;
    }

    public JdbcRealm.SaltStyle getSalt() {
        return salt;
    }

    public void setSalt(JdbcRealm.SaltStyle salt) {
        this.salt = salt;
    }


}
