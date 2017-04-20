package com.zkyyo.www.controller;

import com.zkyyo.www.po.DepartmentPo;
import com.zkyyo.www.service.DepartmentService;
import com.zkyyo.www.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/department_delete.do")
public class DepartmentDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer loginId = (Integer) request.getSession().getAttribute("login");
        int deptId = Integer.valueOf(request.getParameter("deptId"));

        DepartmentService employeeService = DepartmentService.getInstance();
        DepartmentPo deletedDept = employeeService.findDepartment(deptId);
        boolean isDeleted = employeeService.deleteDepartment(deptId);
        if (isDeleted) {
            request.setAttribute("message", "部门解散成功");
            LogUtil.delete(loginId, deletedDept);
        } else {
            request.setAttribute("message", "部门解散失败");
        }

        request.getRequestDispatcher("operation_message.jsp").forward(request, response);
    }
}
