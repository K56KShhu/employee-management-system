package com.zkyyo.www.controller;

import com.zkyyo.www.po.DepartmentPo;
import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.service.DepartmentService;
import com.zkyyo.www.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/department_detail.do")
public class DepartmentDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer deptId = Integer.valueOf(request.getParameter("deptId"));

        DepartmentService departmentService = DepartmentService.getInstance();
        DepartmentPo dept = departmentService.findDepartment(deptId);
        EmployeeService employeeService = EmployeeService.getInstance();
        List<EmployeePo> employees = employeeService.findEmployeeByDeptId(deptId);

        request.setAttribute("department", dept);
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("department_detail.jsp").forward(request, response);
    }
}
