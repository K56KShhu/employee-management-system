/*
package com.zkyyo.www.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = "UserFilter",
        urlPatterns = {"/logout.do", "/functions.jsp",
                "/employee_add.jsp", "/employees.jsp",
                "/employee_add.do", "/employee_delete.do", "/employee_find.do", "/employee_update.do"},
        initParams = {@WebInitParam(name = "LOGIN_VIEW", value = "index.jsp")}
)
public class UserFilter implements Filter {
    private String LOGIN_VIEW;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);
        HttpServletRequest hreq = (HttpServletRequest) req;
        if (hreq.getSession().getAttribute("login") != null) {
            chain.doFilter(req, resp);
        } else {
            HttpServletResponse hresp = (HttpServletResponse) resp;
            hresp.sendRedirect(LOGIN_VIEW);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        this.LOGIN_VIEW = config.getInitParameter("LOGIN_VIEW");
    }

}
*/
