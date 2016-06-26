package com.millinch.spring.boot.autoconfigure.shiro;

import org.apache.shiro.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Load filter chain definitions vi JDBC query.
 *
 * @author John Zhang
 */
public class JdbcPermissionDefinitionsLoader implements FactoryBean<Map<String, String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcPermissionDefinitionsLoader.class);

    public static final String PERMISSION_STRING = "perms[\"%s\"]";

    private DataSource dataSource;

    private String sql;

    public JdbcPermissionDefinitionsLoader(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, String> getObject() throws Exception {
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = null;
        Map<String, String> filters = new LinkedHashMap<>();

        try {
            rs = ps.executeQuery();

            while (rs.next()) {

                String url = rs.getString(1);
                String permission = rs.getString(2);

                filters.put(url, String.format(PERMISSION_STRING, permission));
                LOGGER.debug("Load filter chain via JDBC: {} -> {}", url, permission);
            }
        } finally {
            JdbcUtils.closeResultSet(rs);
        }
        return filters;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public Class<?> getObjectType() {
        return this.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
