package com.zkyyo.www.controller;

import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.service.EmployeeService;
import com.zkyyo.www.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/employee_delete.do")
public class EmployeeDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer loginId = (Integer) request.getSession().getAttribute("login");
        int userId = Integer.valueOf(request.getParameter("userId"));

        EmployeeService employeeService = EmployeeService.getInstance();
        EmployeePo deletedEp = employeeService.deleteEmployee(userId);
        if (deletedEp == null) {
            request.setAttribute("message", "删除员工失败");
        } else {
            request.setAttribute("message", "删除员工成功");
            LogUtil.delete(loginId, deletedEp);
        }

        request.getRequestDispatcher("operation_message.jsp").forward(request, response);
    }
}
