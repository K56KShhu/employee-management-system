package com.zkyyo.www.controller;

import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/employee_update.do")
public class EmployeeUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmedPaswword = request.getParameter("confirmedPassword");
        String departmendId = request.getParameter("departmentId");
        String salary = request.getParameter("salary");
        String date = request.getParameter("date");

        List<String> errors = new ArrayList<>();
        EmployeeService employeeService = EmployeeService.getInstance();
        EmployeePo updatedEp = new EmployeePo();
        updatedEp.setUserId(Integer.valueOf(userId));
        if (name.trim().length() > 0) {
            if (!employeeService.isValidName(name)) {
                errors.add("姓名输入有误");
            } else {
                updatedEp.setUserName(name);
            }
        }
        if (mobile.trim().length() > 0) {
            if (!employeeService.isValidMobile(mobile)) {
                errors.add("手机号输入有误");
            } else {
                updatedEp.setMobile(mobile);
            }
        }
        if (email.trim().length() > 0) {
            if (!employeeService.isValidEmail(email)) {
                errors.add("邮箱输入有误");
            } else {
                updatedEp.setEmail(email);
            }
        }
        if (password.trim().length() > 0) {
            if (!employeeService.isValidPassword(password, confirmedPaswword)) {
                errors.add("第二次密码输入有误");
            } else {
                updatedEp.setPassword(password);
            }
        }
        if (departmendId.trim().length() > 0) {
            updatedEp.setDeptId(Integer.valueOf(departmendId));
        }
        if (salary.trim().length() > 0) {
            if (!employeeService.isValidSalary(salary)) {
                errors.add("薪水输入有误");
            } else {
                updatedEp.setSalary(Double.valueOf(salary));
            }
        }
        if (date.trim().length() > 0) {
            if (!employeeService.isValidDate(date)) {
                errors.add("就职日期输入有误");
            } else {
                updatedEp.setEmployDate(java.sql.Date.valueOf(date));
            }
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        } else {
            boolean isUpdated = employeeService.updateEmployee(updatedEp);
            if (isUpdated) {
                request.setAttribute("status", "ok");
            } else {
                errors.add("数据库更新错误");
                request.setAttribute("errors", errors);
            }
        }

        request.getRequestDispatcher("employee_update.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
