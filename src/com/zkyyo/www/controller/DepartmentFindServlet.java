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
        name = "DepartmentFindServlet",
        urlPatterns = {"/department_find.do"},
        initParams = {
                @WebInitParam(name = "DEPARTMENTS_VIEW", value = "departments.jsp"),
        }
)
public class DepartmentFindServlet extends HttpServlet {
    private String DEPARTMENTS_VIEW;

    public void init() throws ServletException {
        DEPARTMENTS_VIEW = getServletConfig().getInitParameter("DEPARTMENTS_VIEW");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String way = request.getParameter("way");
        String info = request.getParameter("info");
        List<DepartmentPo> result = null;
        DepartmentService departmentService = DepartmentService.getInstance();
//        EmployeeService employeeService = (EmployeeService) getServletContext().getAttribute("employeeService");
        info = info.trim();

        if (way.length() > 0 && info.length() > 0) {
            if (way.equals("by_dept_id")) {
                result = departmentService.fuzzyFindDepartmentByDeptId(Integer.valueOf(info));
            }
            if (way.equals("by_dept_name")) {
                result = departmentService.fuzzyFindDepartmentByDeptName(info);
            }
            if (way.equals("all")) {
                result = departmentService.findDepartments();
            }
        } else {
            result = departmentService.findDepartments();
        }
        request.setAttribute("result", result);
        request.getRequestDispatcher(DEPARTMENTS_VIEW).forward(request, response);
    }
}
