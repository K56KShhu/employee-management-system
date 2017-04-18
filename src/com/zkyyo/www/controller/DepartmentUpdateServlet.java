package com.zkyyo.www.controller;

import com.zkyyo.www.po.DepartmentPo;
import com.zkyyo.www.service.DepartmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/department_update.do")
public class DepartmentUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name").trim();
        String departmentId = request.getParameter("departmentId").trim();
        String buildDate = request.getParameter("buildDate").trim();
        String desc = request.getParameter("description").trim();

        List<String> errors = new ArrayList<>();
        DepartmentService departmentService = DepartmentService.getInstance();
        DepartmentPo updateDept = new DepartmentPo();
        updateDept.setDeptId(Integer.valueOf(departmentId));

        //部门名
        if (name.length() > 0) {
            if (!departmentService.isValidName(name)) {
                errors.add("部门名输入有误或已被注册");
            } else {
                updateDept.setDeptName(name);
            }
        }
        //创建日期
        if (buildDate.length() > 0) {
            if (!departmentService.isValidDate(buildDate)) {
                errors.add(buildDate);
            } else {
                updateDept.setBuildDate(java.sql.Date.valueOf(buildDate));
            }
        }
        //描述
        if (desc.length() > 0) {
            updateDept.setDeptDesc(desc);
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        } else {
            boolean isUpdated = departmentService.updateDepartment(updateDept);
            if (isUpdated) {
                request.setAttribute("status", "ok");
            } else {
                errors.add("数据库更新错误");
                request.setAttribute("errors", errors);
            }
        }

        request.getRequestDispatcher("department_update.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
