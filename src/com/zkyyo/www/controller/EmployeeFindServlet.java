package com.zkyyo.www.controller;

import com.zkyyo.www.dao.DepartmentDao;
import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.service.DepartmentService;
import com.zkyyo.www.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "EmployeeFindServlet",
        urlPatterns = {"/employee_find.do"},
        initParams = {
                @WebInitParam(name = "EMPLOYEE_VIEW", value = "employees.jsp"),
        }
)
public class EmployeeFindServlet extends HttpServlet {
    private String EMPLOYEE_VIEW;

    public void init() throws ServletException {
        EMPLOYEE_VIEW = getServletConfig().getInitParameter("EMPLOYEE_VIEW");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String way = request.getParameter("way"); //查询方式
        String info = request.getParameter("info"); //查询内容
        String order = request.getParameter("order"); //排序依据
        String reverse = request.getParameter("reverse"); //升序降序
        List<EmployeePo> result = null;
        EmployeeService employeeService = EmployeeService.getInstance();
        DepartmentService departmentService = DepartmentService.getInstance();

        //获取查询结果列表
        if (way != null && way.length() > 0) {
            if (info != null) {
                info = info.trim();
                //检查用户是否输入内容
                if (info.length() > 0) { //有输入
                    //通过员工号查询
                    if (way.equals("by_user_id") && employeeService.isValidId(info)) {
                        result = employeeService.fuzzyFindEmployeeByUserId(Integer.valueOf(info));
                    }
                    //通过员工名查询
                    if (way.equals("by_user_name") && employeeService.isValidName(info)) {
                        result = employeeService.fuzzyFindEmployeeByUserName(info);
                    }
                    //通过部门号查询
                    if (way.equals("by_dept_id") && departmentService.isValidId(info)) {
                        result = employeeService.findEmployeeByDeptId(Integer.valueOf(info));
                    }
                    //查询所有
                    if (way.equals("all")) {
                        result = employeeService.findEmployees();
                    }
                } else { //无输入
                    //查询所有
                    result = employeeService.findEmployees();
                }
            }
        }

       //排序
        if (result != null) {
            //升序
            if (reverse.equals("false")) {
                switch (order) {
                    //员工号
                    case "user_id":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_USER_ID, false);
                        break;
                    //部门号
                    case "dept_id":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_DEPT_ID, false);
                        break;
                    //薪水
                    case "salary":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_SALARY, false);
                        break;
                    //就职日期
                    case "date":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_DATE, false);
                        break;
                    default:
                        break;
                }
            }
            //倒序
            if (reverse.equals("true")) {
                switch (order) {
                    //员工号
                    case "user_id":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_USER_ID, true);
                        break;
                    //部门号
                    case "dept_id":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_DEPT_ID, true);
                        break;
                    //薪水
                    case "salary":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_SALARY, true);
                        break;
                    case "date":
                        result = employeeService.sort(result, EmployeeService.ORDER_BY_DATE, true);
                        break;
                    //就职日期
                    default:
                        break;
                }
            }
        }
        request.setAttribute("result", result);
        request.getRequestDispatcher(EMPLOYEE_VIEW).forward(request, response);
    }
}
