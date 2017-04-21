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
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "EmployeeUpdateServlet",
        urlPatterns = {"/employee_update.do"},
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW", value = "employee_update.jsp"),
                @WebInitParam(name = "ERROR_VIEW", value = "employee_update.jsp")
        }
)
public class EmployeeUpdateServlet extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;

    public void init() throws ServletException {
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer loginId = (Integer) request.getSession().getAttribute("login"); //登录用户名
        String userId = request.getParameter("userId"); //员工号
        String name = request.getParameter("name"); //员工名
        String mobile = request.getParameter("mobile"); //手机号
        String email = request.getParameter("email"); //邮箱号
        String password = request.getParameter("password"); //第一次密码
        String confirmedPsw = request.getParameter("confirmedPsw"); //第二次密码
        String deptId = request.getParameter("deptId"); //部门号
        String salary = request.getParameter("salary"); //薪水
        String date = request.getParameter("date"); //就职日期

        List<String> errors = new ArrayList<>(); //获取错误信息
        EmployeePo ep = new EmployeePo();
        //检查是否遗漏参数 !未解决!
        if (userId == null || name == null || mobile == null || email == null || password == null
                || confirmedPsw == null || deptId == null || salary == null || date == null) {
            errors.add("信息不完整");
        } else {
            ep.setUserId(Integer.valueOf(userId));
        }

        EmployeeService employeeService = EmployeeService.getInstance();
        //是否修改用户名
        if (name != null && name.length() > 0) {
            //校验用户名
            if (!employeeService.isValidName(name)) {
                errors.add("姓名输入有误");
            } else {
                ep.setUserName(name);
            }
        }
        //是否修改手机号
        if (mobile != null && mobile.length() > 0) {
            //校验手机号
            if (!employeeService.isValidMobile(mobile)) {
                errors.add("手机号输入有误");
            } else {
                ep.setMobile(mobile);
            }
        }
        //是否修改邮箱
        if (email != null && email.length() > 0) {
            //校验邮箱
            if (!employeeService.isValidEmail(email)) {
                errors.add("邮箱输入有误");
            } else {
                ep.setEmail(email);
            }
        }
        //是否修改密码
        if (password != null && password.length() > 0) {
            //校验二次密码
            if (!employeeService.isValidPassword(password, confirmedPsw)) {
                errors.add("第二次密码输入有误");
            } else {
                ep.setPassword(password);
            }
        }
        //是否修改员工号
        if (deptId != null && deptId.length() > 0) {
            //校验员工号
            ep.setDeptId(Integer.valueOf(deptId));
        }
        //是否修改薪水
        if (salary != null && salary.length() > 0) {
            //校验薪水
            if (!employeeService.isValidSalary(salary)) {
                errors.add("薪水输入有误");
            } else {
                ep.setSalary(Double.valueOf(salary));
            }
        }
        //是否修改就职日期
        if (date != null && date.length() > 0) {
            //校验日期
            if (!employeeService.isValidDate(date)) {
                errors.add("就职日期(yyyy-MM-dd)输入有误");
            } else {
                ep.setEmployDate(java.sql.Date.valueOf(date));
            }
        }

        String page = ERROR_VIEW;
        if (!errors.isEmpty()) { //输入有误
            request.setAttribute("errors", errors);
        } else { //输入正确
            if (userId != null) {
                EmployeePo initialEp = employeeService.findEmployee(Integer.valueOf(userId));
                EmployeePo updatedEp = employeeService.updateEmployee(ep);
                if (updatedEp == null) { //添加失败
                    errors.add("数据库更新错误");
                    request.setAttribute("errors", errors);
                } else { //添加成功
                    request.setAttribute("status", "ok");
                    LogUtil.update(loginId, initialEp, updatedEp); //记录日志
                    page = SUCCESS_VIEW;
                }
            }
        }
        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
