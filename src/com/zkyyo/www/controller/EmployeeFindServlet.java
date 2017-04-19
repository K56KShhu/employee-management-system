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
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String way = request.getParameter("way");
        String info = request.getParameter("info");
        String order = request.getParameter("order");
        String reverse = request.getParameter("reverse");
        List<EmployeePo> result = null;
        EmployeeService employeeService = EmployeeService.getInstance();
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
            if (way.equals("all")) {
                result = employeeService.findEmployees();
            }
        } else {
            result = employeeService.findEmployees();
        }

        if (result != null) {
            if (reverse.equals("false")) {
                switch (order) {
                    case "user_id":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_USER_ID, false);
                        break;
                    case "dept_id":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_DEPT_ID, false);
                        break;
                    case "salary":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_SALARY, false);
                        break;
                    case "date":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_DATE, false);
                        break;
                    default:
                        break;
                }
            }
            if (reverse.equals("true")) {
                switch (order) {
                    case "user_id":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_USER_ID, true);
                        break;
                    case "dept_id":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_DEPT_ID, true);
                        break;
                    case "salary":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_SALARY, true);
                        break;
                    case "date":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_DATE, true);
                        break;
                    default:
                        break;
                }
            }

            request.setAttribute("result", result);
            request.getRequestDispatcher(EMPLOYEE_VIEW).forward(request, response);
        }
    }
}
