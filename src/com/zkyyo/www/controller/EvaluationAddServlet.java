package com.zkyyo.www.controller;

import com.zkyyo.www.po.EvaluationPo;
import com.zkyyo.www.service.EmployeeService;
import com.zkyyo.www.service.EvaluationService;
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
        name = "EvaluationAddServlet",
        urlPatterns = {"/evaluation_add.do"},
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW", value = "evaluation_add.jsp"),
                @WebInitParam(name = "ERROR_VIEW", value = "evaluation_add.jsp")
        }
)
public class EvaluationAddServlet extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;

    public void init() throws ServletException {
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer loginId = (Integer) request.getSession().getAttribute("login");
        int userId = (Integer) request.getSession().getAttribute("login");
        String evaluatorId = request.getParameter("evaluatorId").trim();
        String beEvaluatedId = request.getParameter("beEvaluatedId").trim();
        String stars = request.getParameter("stars").trim();
        String comment = request.getParameter("comment").trim();

        List<String> errors = new ArrayList<>();
        EmployeeService employeeService = EmployeeService.getInstance();
        EvaluationService evaluationService = EvaluationService.getInstance();
        if (Integer.valueOf(evaluatorId) != userId) {
            response.sendRedirect("functions.jsp");
        }
        if (employeeService.isValidId(beEvaluatedId)) {
            if (!employeeService.isUserExisted(Integer.valueOf(beEvaluatedId))) {
                errors.add("被评价员工不存在");
            }
        }
        if (!evaluationService.isValidStars(stars)) {
            errors.add("评价等级无效");
        }

        String page = ERROR_VIEW;
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("message", "评价失败");
        } else {
            EvaluationPo newEval = evaluationService.addEvaluation(evaluatorId, beEvaluatedId, stars, comment);
            if (newEval == null) {
                errors.add("数据库发生错误,无法添加评价");
                request.setAttribute("errors", errors);
                request.setAttribute("message", "评价失败");
            } else {
                request.setAttribute("message", "评价成功");
                LogUtil.add(loginId, newEval);
                page = SUCCESS_VIEW;
            }
        }

        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
