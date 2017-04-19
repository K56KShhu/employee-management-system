package com.zkyyo.www.controller;

import com.zkyyo.www.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "EmployeeAddServlet",
        urlPatterns = {"/employee_add.do"},
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW", value = "employee_add.jsp"),
                @WebInitParam(name = "ERROR_VIEW", value = "employee_add.jsp")
        }
)
public class EmployeeAddServlet extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;

    public void init() throws ServletException {
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name").trim();
        String mobile = request.getParameter("mobile").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password");
        String confirmedPassword = request.getParameter("confirmedPassword");
        String departmentId = request.getParameter("departmentId").trim();
        String salary = request.getParameter("salary").trim();
        String date = request.getParameter("date").trim();

        List<String> errors = new ArrayList<>();
        EmployeeService employeeService = EmployeeService.getInstance();
        if (!employeeService.isValidName(name)) {
            errors.add("姓名输入有误");
        }
        if (!employeeService.isValidMobile(mobile)) {
            errors.add("手机号输入有误");
        }
        if (!employeeService.isValidEmail(email)) {
            errors.add("邮箱输入有误");
        }
        if (!employeeService.isValidPassword(password, confirmedPassword)) {
            errors.add("第二次验证密码输入有误");
        }
        if (!employeeService.isValidSalary(salary)) {
            errors.add("薪水输入有误");
        }
        if (!employeeService.isValidDate(date)) {
            errors.add("就职日期输入有误");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("message", "注册失败");
        } else {
            Integer newUserId = employeeService.addEmployee(name, mobile, email, password, departmentId, salary, date);
            if (newUserId == null) {
                errors.add("数据库发生错误,无法添加新员工");
                request.setAttribute("errors", errors);
                request.setAttribute("message", "注册失败");
            } else {
                request.setAttribute("message", "注册成功, 新员工号为: " + newUserId);
            }
        }

        request.getRequestDispatcher("employee_add.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
