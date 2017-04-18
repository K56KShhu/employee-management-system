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
        String name = request.getParameter("name").trim();
        String departmentId = request.getParameter("departmentId").trim();
        String buildDate = request.getParameter("buildDate").trim();
        String desc = request.getParameter("description").trim();

        List<String> errors = new ArrayList<>();
        DepartmentService departmentService = DepartmentService.getInstance();
        if (!departmentService.isValidName(name)) {
            errors.add("部门名输入有误或已经被注册");
        }
        if (!departmentService.isValidId(departmentId)) {
            errors.add("部门号输入有误或已经被注册");
        }
        if (!departmentService.isValidDate(buildDate)) {
            errors.add("部门建立日期输入有误");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        } else {
            Integer newDeptId = departmentService.addDepartment(name, departmentId, buildDate, desc);
            if (newDeptId == null) {
                errors.add("数据库发生错误,无法创建部门");
                request.setAttribute("errors", errors);
            } else {
                request.setAttribute("status", "ok");
            }
        }

        request.getRequestDispatcher("department_add.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
