package com.zkyyo.www.controller;

import com.zkyyo.www.po.DepartmentPo;
import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.po.EvaluationPo;
import com.zkyyo.www.service.DepartmentService;
import com.zkyyo.www.service.EmployeeService;
import com.zkyyo.www.service.EvaluationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        urlPatterns = "/employee_detail.do",
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW", value = "employee_detail.jsp"),
                @WebInitParam(name = "ERROR_VIEW", value = "functions.jsp")
        }
)
public class EmployeeDetailServlet extends HttpServlet {
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
        String userId = request.getParameter("userId");
        EmployeeService employeeService = EmployeeService.getInstance();
        DepartmentService departmentService = DepartmentService.getInstance();
        EvaluationService evaluationService = EvaluationService.getInstance();
        EmployeePo employee = null;
        DepartmentPo department = null;
        List<EvaluationPo> sendedEvals = null;
        List<EvaluationPo> receivedEvals = null;
        String page = ERROR_VIEW;
        if (userId != null) {
            //检查用户号是否有效
            if (employeeService.isValidId(userId)) {
                int id = Integer.valueOf(userId);
                boolean isExisted = employeeService.isIdExisted(id);
                //验证员工是否存在
                if (isExisted) { //存在
                    //获取员工信息
                    employee = employeeService.findEmployee(id);
                    //获取部门信息
                    department = departmentService.findDepartment(employee.getDeptId());
                    //获取发送的评价
                    sendedEvals = evaluationService.findSendedEvaluations(id);
                    //获取接收的评价
                    receivedEvals = evaluationService.findReceivedEvaluations(id);
                    page = SUCCESS_VIEW;
                }
            }
        }

        request.setAttribute("employee", employee);
        request.setAttribute("department", department);
        request.setAttribute("sendedEvals", sendedEvals);
        request.setAttribute("receivedEvals", receivedEvals);
        request.getRequestDispatcher(page).forward(request, response);
    }
}
