package com.zkyyo.www.controller;

import com.zkyyo.www.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "LoginServlet",
        urlPatterns={"/login.do"},
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW", value="functions.jsp"),
                @WebInitParam(name = "ERROR_VIEW", value="index.jsp")
        }
)
public class LoginServlet extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;

    public void init() throws ServletException {
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int employeeId = Integer.valueOf(request.getParameter("employeeId"));
        String password = request.getParameter("password");
        String page = ERROR_VIEW;

        EmployeeService employeeService = EmployeeService.getInstance();
        if (employeeService.checkLogin(employeeId, password)) {
            request.getSession().setAttribute("login", employeeId);
            page = SUCCESS_VIEW;
        }
        response.sendRedirect(page);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
