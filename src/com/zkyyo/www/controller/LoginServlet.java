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
        String employeeId = request.getParameter("employeeId"); //请求登录的员工号
        String password = request.getParameter("password"); //用户输入的密码
        String page = ERROR_VIEW;

        EmployeeService employeeService = EmployeeService.getInstance();
        //检测员工号是否有效
        if (employeeService.isValidId(employeeId)) {
            int id = Integer.valueOf(employeeId);
            //密码验证
            if (employeeService.checkLogin(id, password)) {
                request.getSession().setAttribute("login", id);
                page = SUCCESS_VIEW;
            }
        }
        response.sendRedirect(page);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
