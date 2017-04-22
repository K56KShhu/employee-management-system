package com.zkyyo.www.controller;

import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.service.DepartmentService;
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
        name = "EmployeeAddServlet",
        urlPatterns = {"/employee_add.do"},
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW", value = "employee_add.jsp"),
                @WebInitParam(name = "ERROR_VIEW", value = "employee_add.jsp")
        }
)
public class EmployeeAddServlet extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;

    public void init() throws ServletException {
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer loginId = (Integer) request.getSession().getAttribute("login"); //登录用户号
        String name = request.getParameter("name"); //员工名
        String mobile = request.getParameter("mobile"); //手机号
        String email = request.getParameter("email"); //邮箱
        String password = request.getParameter("password"); //密码
        String confirmedPsw = request.getParameter("confirmedPsw"); //二次验证密码
        String deptId = request.getParameter("deptId"); //部门号
        String salary = request.getParameter("salary"); //薪水
        String date = request.getParameter("date"); //就职日期

        List<String> errors = new ArrayList<>(); //获取错误信息
        //检查是否遗漏参数 !未解决!
        if (name == null || mobile == null || email == null || password == null
                || confirmedPsw == null || deptId == null || salary == null || date == null) {
            errors.add("信息不完整");
        }

        EmployeeService employeeService = EmployeeService.getInstance();
        DepartmentService departmentService = DepartmentService.getInstance();
        //校验姓名
        if (!employeeService.isValidName(name)) {
            errors.add("姓名输入有误");
        }
        //校验手机号
        if (!employeeService.isValidMobile(mobile)) {
            errors.add("手机号输入有误");
        }
        //校验邮箱
        if (!employeeService.isValidEmail(email)) {
            errors.add("邮箱输入有误");
        }
        //校验二次密码
        if (!employeeService.isValidPassword(password, confirmedPsw)) {
            errors.add("第二次验证密码输入有误");
        }
        //校验薪水
        if (!employeeService.isValidSalary(salary)) {
            errors.add("薪水输入有误");
        }
        //校验日期
        if (!employeeService.isValidDate(date)) {
            errors.add("就职日期(yyyy-MM-dd)输入有误");
        }
        if (deptId != null && departmentService.isAvailableId(Integer.valueOf(deptId))) {
            errors.add("部门不存在");
        }

        String page = ERROR_VIEW;
        //检查是否含有错误信息
        if (!errors.isEmpty()) { //输入错误
            request.setAttribute("errors", errors);
            request.setAttribute("message", "注册失败");
        } else { //输入成功
            EmployeePo newEp = employeeService.addEmployee(name, mobile, email, password, deptId, salary, date);
            if (newEp == null) { //添加失败
                errors.add("数据库发生错误,无法添加新员工");
                request.setAttribute("errors", errors);
                request.setAttribute("message", "注册失败");
            } else { //添加成功
                request.setAttribute("message", "注册成功, 新员工号为: " + newEp.getUserId());
                LogUtil.add(loginId, newEp); //记录日志
                page = SUCCESS_VIEW;
            }
        }
        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
