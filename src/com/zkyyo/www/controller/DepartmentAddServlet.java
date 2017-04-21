package com.zkyyo.www.controller;

import com.zkyyo.www.po.DepartmentPo;
import com.zkyyo.www.service.DepartmentService;
import com.zkyyo.www.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类用于处理部门创建
 */
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
        Integer loginId = (Integer) request.getSession().getAttribute("login");
        String name = request.getParameter("name");
        String departmentId = request.getParameter("departmentId");
        String buildDate = request.getParameter("buildDate");
        String desc = request.getParameter("description");

        List<String> errors = new ArrayList<>(); //记录错误内容
        DepartmentService departmentService = DepartmentService.getInstance();
        //检测部门名是否可用
        if (!departmentService.isAvailableName(name)) {
            errors.add("部门名输入为空或已经被注册");
        }
        //校验部门号是否符合格式
        if (!departmentService.isValidId(departmentId)) {
            errors.add("部门号输入有误");
        } else {
            //检测部门号是否可用
            if (!departmentService.isAvailableId(Integer.valueOf(departmentId))) {
                errors.add("部门号已被注册");
            }
        }
        //校验日期是否正确
        if (!departmentService.isValidDate(buildDate)) {
            errors.add("部门建立日期输入有误");
        }

        String page = ERROR_VIEW;
        if (!errors.isEmpty()) { //输入有误
            request.setAttribute("errors", errors);
        } else {
            DepartmentPo newDept = departmentService.addDepartment(name, departmentId, buildDate, desc);
            if (newDept == null) { //添加失败
                errors.add("数据库发生错误,无法创建部门");
                request.setAttribute("errors", errors);
            } else { //添加成功
                request.setAttribute("status", "ok");
                LogUtil.add(loginId, newDept); //记录日志
                page = SUCCESS_VIEW;
            }
        }

        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
