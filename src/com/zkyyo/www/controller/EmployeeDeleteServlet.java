package com.zkyyo.www.controller;

import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.service.EmployeeService;
import com.zkyyo.www.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "EmployeeDeleteServlet",
        urlPatterns = {"/employee_delete.do"},
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW", value = "operation_message.jsp"),
                @WebInitParam(name = "ERROR_VIEW", value = "operation_message.jsp")
        }
)
public class EmployeeDeleteServlet extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;

    public void init() throws ServletException {
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer loginId = (Integer) request.getSession().getAttribute("login");
        int userId = Integer.valueOf(request.getParameter("userId"));

        EmployeeService employeeService = EmployeeService.getInstance();
        EmployeePo deletedEp = employeeService.deleteEmployee(userId);
        String page = ERROR_VIEW;
        if (deletedEp == null) { //删除失败
            request.setAttribute("message", "删除员工失败");
        } else { //删除成功
            request.setAttribute("message", "删除员工成功");
            LogUtil.delete(loginId, deletedEp);
            page = SUCCESS_VIEW;
        }

        request.getRequestDispatcher(page).forward(request, response);
    }
}
