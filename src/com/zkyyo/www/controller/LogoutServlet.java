package com.zkyyo.www.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "LogoutServlet",
        urlPatterns = {"/logout.do"},
        initParams = {
                @WebInitParam(name = "LOGIN_VIEW", value = "index.jsp")
        }
)
public class LogoutServlet extends HttpServlet {
    private String LOGIN_VIEW;

    public void init() throws ServletException {
        LOGIN_VIEW = getServletConfig().getInitParameter("LOGIN_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(LOGIN_VIEW);
    }
}
