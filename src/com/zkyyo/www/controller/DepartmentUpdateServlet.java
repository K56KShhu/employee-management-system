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

@WebServlet(
        name = "DepartmentUpdateServlet",
        urlPatterns = {"/department_update.do"},
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW", value = "department_update.jsp"),
                @WebInitParam(name = "ERROR_VIEW", value = "department_update.jsp"),
        }
)
public class DepartmentUpdateServlet extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;

    public void init() throws ServletException {
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer loginId = (Integer) request.getSession().getAttribute("login"); //登陆用户号
        String name = request.getParameter("name"); //部门名
        String departmentId = request.getParameter("deptId"); //部门号
        String buildDate = request.getParameter("buildDate"); //建立日期
        String desc = request.getParameter("description"); //描述

        List<String> errors = new ArrayList<>(); //获取错误信息
        DepartmentService departmentService = DepartmentService.getInstance();
        DepartmentPo dept = new DepartmentPo();

        //检查是否遗漏参数 !未解决!
        if (name == null || departmentId == null || buildDate == null || desc == null) {
            errors.add("未填写相关信息");
        } else {
            dept.setDeptId(Integer.valueOf(departmentId));
        }

        //是否修改部门名
        if (name != null && name.length() > 0) {
            //检查部门名是否可用
            if (!departmentService.isAvailableName(name)) {
                errors.add("部门名输入为空或已被注册");
            } else {
                dept.setName(name);
            }
        }
        //是否修改创建日期
        if (buildDate != null && buildDate.length() > 0) {
            //检查日期是否有效
            if (!departmentService.isValidDate(buildDate)) {
                errors.add("创建日期(yyyy-MM-dd)输入有误");
            } else {
                dept.setBuildDate(java.sql.Date.valueOf(buildDate));
            }
        }
        //是否修改描述
        if (desc != null && desc.length() > 0) {
            dept.setDescription(desc);
        }

        String page = ERROR_VIEW;
        //检查是否含有错误信息
        if (!errors.isEmpty()) { //输入错误
            request.setAttribute("errors", errors);
        } else { //输入正确
            DepartmentPo initialDept = departmentService.findDepartment(Integer.valueOf(departmentId));
            DepartmentPo updatedDept = departmentService.updateDepartment(dept);
            if (updatedDept == null) { //添加失败
                errors.add("数据库更新错误");
                request.setAttribute("errors", errors);
            } else { //添加成功
                request.setAttribute("status", "ok");
                LogUtil.update(loginId, initialDept, updatedDept); //记录日志
                page = SUCCESS_VIEW;
            }
        }
        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
