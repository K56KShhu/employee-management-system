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
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.valueOf(request.getParameter("userId"));
        String way = (String) request.getSession().getAttribute("way");
        String info = (String) request.getSession().getAttribute("info");

        EmployeeService employeeService = EmployeeService.getInstance();
        boolean isDeleted = employeeService.deleteEmployee(userId);
        if (isDeleted) {
            request.setAttribute("message", "删除员工成功");
        } else {
            request.setAttribute("message", "删除员工失败");
        }

        request.getRequestDispatcher("operation_message.jsp").forward(request, response);
    }
}
