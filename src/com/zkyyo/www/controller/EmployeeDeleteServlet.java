package com.zkyyo.www.controller;

import com.zkyyo.www.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/employee_delete.do")
public class EmployeeDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.valueOf(request.getParameter("userId"));

        EmployeeService employeeService = EmployeeService.getInstance();
        boolean isDeleted = employeeService.deleteEmployee(userId);
        if (isDeleted) {
            request.setAttribute("status", "ok");
        } else {
            request.setAttribute("status", "fail");
        }

        request.getRequestDispatcher("employees.jsp").forward(request, response);
    }
}
