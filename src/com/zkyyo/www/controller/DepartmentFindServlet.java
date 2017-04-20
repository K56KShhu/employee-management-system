package com.zkyyo.www.controller;

import com.zkyyo.www.po.DepartmentPo;
import com.zkyyo.www.service.DepartmentService;

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
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String way = request.getParameter("way");
        String info = request.getParameter("info");
        String order = request.getParameter("order");
        String reverse = request.getParameter("reverse");
        List<DepartmentPo> result = null;
        DepartmentService departmentService = DepartmentService.getInstance();

        if (way != null && way.length() > 0) {
            if (info != null) {
                info = info.trim();
                if (info.length() > 0) {
                    if (way.equals("by_dept_id") && departmentService.isValidId(info)) {
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
            }
        }

        if (result != null) {
            if (reverse.equals("false")) {
                switch (order) {
                    case "id":
                        result = departmentService.sort(result, DepartmentService.ORDER_BY_ID, false);
                        break;
                    case "pop":
                        result = departmentService.sort(result, DepartmentService.ORDER_BY_POPULATION, false);
                        break;
                    case "date":
                        result = departmentService.sort(result, DepartmentService.ORDER_BY_DATE, false);
                        break;
                    default:
                        break;
                }
            }
            if (reverse.equals("true")) {
                switch (order) {
                    case "id":
                        result = departmentService.sort(result, DepartmentService.ORDER_BY_ID, true);
                        break;
                    case "pop":
                        result = departmentService.sort(result, DepartmentService.ORDER_BY_POPULATION, true);
                        break;
                    case "date":
                        result = departmentService.sort(result, DepartmentService.ORDER_BY_DATE, true);
                        break;
                    default:
                        break;
                }
            }
        }
        request.setAttribute("result", result);
        request.getRequestDispatcher(DEPARTMENTS_VIEW).forward(request, response);
    }
}
