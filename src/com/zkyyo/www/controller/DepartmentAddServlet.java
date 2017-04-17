package com.zkyyo.www.controller;

import com.zkyyo.www.service.DepartmentService;
import com.zkyyo.www.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "DepartmentAddServlet",
        urlPatterns = {"/department_add.do"},
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW", value = "department_add.jsp"),
                @WebInitParam(name = "ERROR_VIEW", value = "department_add.jsp")
        }
)
public class DepartmentAddServlet extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;

    public void init() throws ServletException {
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String departmentId = request.getParameter("departmentId");
        String buildDate = request.getParameter("buildDate");
        String description = request.getParameter("description");

        List<String> errors = new ArrayList<>();
        DepartmentService departmentService = DepartmentService.getInstance();
        /*
        if (!departmentService.isValidId(departmentId)) {
            errors.add("部门号输入有误或已经被注册");
        }
        if (!departmentService.isValidDate(buildDate)) {
            errors.add("部门建立日期输入有误");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        } else {
            Integer newId = departmentId.addDepartment(name, departmentId, buildDate, description);
            if (newId == null) {
                errors.add("数据库发生错误,无法创建部门");
                request.setAttribute("errors", errors);
            } else {
                request.setAttribute("status", "ok");
            }
        }

        request.getRequestDispatcher("department_add.jsp").forward(request, response);
        */
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
