package com.zkyyo.www.controller;

import com.zkyyo.www.po.DepartmentPo;
import com.zkyyo.www.service.DepartmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "DepartmentFindServlet",
        urlPatterns = {"/department_find.do"},
        initParams = {
                @WebInitParam(name = "DEPARTMENTS_VIEW", value = "departments.jsp"),
        }
)
public class DepartmentFindServlet extends HttpServlet {
    private String DEPARTMENTS_VIEW;

    public void init() throws ServletException {
        DEPARTMENTS_VIEW = getServletConfig().getInitParameter("DEPARTMENTS_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String way = request.getParameter("way"); //查询方式
        String info = request.getParameter("info"); //查询信息
        String order = request.getParameter("order"); //排序依据
        String reverse = request.getParameter("reverse"); //升序降序
        List<DepartmentPo> result = null;
        DepartmentService departmentService = DepartmentService.getInstance();

        //获得部门列表
        if (way != null && way.length() > 0) {
            if (info != null) {
                info = info.trim();
                //检查用户是否输入内容
                if (info.length() > 0) { //用户输入内容
                    //通过部门号查询
                    if (way.equals("by_dept_id") && departmentService.isValidId(info)) {
                        result = departmentService.fuzzyFindDepartmentByDeptId(Integer.valueOf(info));
                    }
                    //通过部门名查询
                    if (way.equals("by_dept_name")) {
                        result = departmentService.fuzzyFindDepartmentByDeptName(info);
                    }
                    //查询所有部门
                    if (way.equals("all")) {
                        result = departmentService.findDepartments();
                    }
                } else { //用户未输入内筒
                    result = departmentService.findDepartments();
                }
            }
        }

        //排序
        if (result != null) {
            //升序
            if (reverse.equals("false")) {
                switch (order) {
                    //通过部门号排序
                    case "id":
                        result = departmentService.sort(result, DepartmentService.ORDER_BY_ID, false);
                        break;
                    //通过部门人数排序
                    case "pop":
                        result = departmentService.sort(result, DepartmentService.ORDER_BY_POPULATION, false);
                        break;
                    //通过建立日期排序
                    case "date":
                        result = departmentService.sort(result, DepartmentService.ORDER_BY_DATE, false);
                        break;
                    default:
                        break;
                }
            }
            //倒序
            if (reverse.equals("true")) {
                switch (order) {
                    //通过部门号排序
                    case "id":
                        result = departmentService.sort(result, DepartmentService.ORDER_BY_ID, true);
                        break;
                    //通过部门人数排序
                    case "pop":
                        result = departmentService.sort(result, DepartmentService.ORDER_BY_POPULATION, true);
                        break;
                    //通过建立日期排序
                    case "date":
                        result = departmentService.sort(result, DepartmentService.ORDER_BY_DATE, true);
                        break;
                    default:
                        break;
                }
            }
        }
        request.setAttribute("result", result);
        request.getRequestDispatcher(DEPARTMENTS_VIEW).forward(request, response);
    }
}
