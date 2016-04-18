package com.millinch.spring.boot.autoconfigure.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This guy is lazy, nothing left.
 *
 * @author 张劲航
 */
public class FormSignInFilter extends FormAuthenticationFilter {

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

    @Override
    protected void redirectToLogin(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String contentType = request.getContentType();
            if (contentType != null && contentType.contains("application/json")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.print("{\"error\":\"登录超时，请重新登录。\"}");
            } else {
                super.redirectToLogin(servletRequest, servletResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.redirectToLogin(servletRequest, servletResponse);
        }
    }
}
