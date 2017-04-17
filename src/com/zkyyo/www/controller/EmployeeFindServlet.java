package com.zkyyo.www.controller;

import com.zkyyo.www.po.EmployeePo;
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
        name = "EmployeeFindServlet",
        urlPatterns = {"/employee_find.do"},
        initParams = {
                @WebInitParam(name = "EMPLOYEE_VIEW", value = "employees.jsp"),
        }
)
public class EmployeeFindServlet extends HttpServlet {
    private String EMPLOYEE_VIEW;

    public void init() throws ServletException {
        EMPLOYEE_VIEW = getServletConfig().getInitParameter("EMPLOYEE_VIEW");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String way = request.getParameter("way");
        String info = request.getParameter("info");
        List<EmployeePo> result = null;
//        EmployeeService employeeService = EmployeeService.getInstance();
        EmployeeService employeeService = (EmployeeService) getServletContext().getAttribute("employeeService");
        info = info.trim();

        if (way.length() > 0 && info.length() > 0) {
            if (way.equals("by_user_id")) {
                result = employeeService.fuzzyFindEmployeeByUserId(Integer.valueOf(info));
            }
            if (way.equals("by_user_name")) {
                result = employeeService.fuzzyFindEmployeeByUserName(info);
            }
            if (way.equals("by_dept_id")) {
                result = employeeService.findEmployeeByDeptId(Integer.valueOf(info));
            }

        } else {
            result = employeeService.findEmployees();
        }
        request.setAttribute("result", result);
        request.getRequestDispatcher(EMPLOYEE_VIEW).forward(request, response);
    }
}
