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

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");

        String page = ERROR_VIEW;
        EmployeeService employeeService = EmployeeService.getInstance();
        DepartmentService departmentService = DepartmentService.getInstance();
        EvaluationService evaluationService = EvaluationService.getInstance();
        if (userId != null) {
            if (employeeService.isValidId(userId)) {
                EmployeePo employee = employeeService.findEmployee(Integer.valueOf(userId));
                DepartmentPo department = null;
                if (employee != null) {
                    department = departmentService.findDepartment(employee.getDeptId());
                }
                List<EvaluationPo> sendedEvals = evaluationService.findSendedEvaluations(Integer.valueOf(userId));
                List<EvaluationPo> receivedEvals = evaluationService.findReceivedEvaluations(Integer.valueOf(userId));
                request.setAttribute("employee", employee);
                request.setAttribute("department", department);
                request.setAttribute("sendedEvals", sendedEvals);
                request.setAttribute("receivedEvals", receivedEvals);
                page = SUCCESS_VIEW;
            }
        }
        request.getRequestDispatcher(page).forward(request, response);
    }
}
