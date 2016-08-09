package com.millinch.springboot.shiro.sample.web;

import javax.servlet.*;
import java.io.IOException;

/**
 * This guy is lazy, nothing left.
 *
 * @author John Zhang
 */
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("My Filter executed!!");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
