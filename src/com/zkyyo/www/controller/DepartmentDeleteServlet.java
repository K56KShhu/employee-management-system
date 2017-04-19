package com.zkyyo.www.controller;

import com.zkyyo.www.service.DepartmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/department_delete.do")
public class DepartmentDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int deptId = Integer.valueOf(request.getParameter("deptId"));


        DepartmentService employeeService = DepartmentService.getInstance();
        boolean isDeleted = employeeService.deleteDepartment(deptId);
        if (isDeleted) {
            request.setAttribute("message", "部门解散成功");
        } else {
            request.setAttribute("message", "部门解散失败");
        }

        request.getRequestDispatcher("operation_message.jsp").forward(request, response);
    }
}
