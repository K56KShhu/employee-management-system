package com.zkyyo.www.controller;

import com.zkyyo.www.po.DepartmentPo;
import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.service.DepartmentService;
import com.zkyyo.www.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "DepartmentDetailServlet",
        urlPatterns = {"/department_detail.do"},
        initParams = {
                @WebInitParam(name = "EMPLOYEE_VIEW", value = "department_detail.jsp")
        }
)
public class DepartmentDetailServlet extends HttpServlet {
    private String EMPLOYEE_VIEW;

    public void init() throws ServletException {
        EMPLOYEE_VIEW = getServletConfig().getInitParameter("EMPLOYEE_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer deptId = Integer.valueOf(request.getParameter("deptId"));

        DepartmentService departmentService = DepartmentService.getInstance();
        DepartmentPo dept = departmentService.findDepartment(deptId); //获得部门信息
        EmployeeService employeeService = EmployeeService.getInstance();
        List<EmployeePo> employees = employeeService.findEmployeeByDeptId(deptId); //获得该部门所有员工的信息

        request.setAttribute("department", dept);
        request.setAttribute("employees", employees);
        String page = EMPLOYEE_VIEW;
        request.getRequestDispatcher(page).forward(request, response);
    }
}
