package com.zkyyo.www.controller;

import com.zkyyo.www.po.EvaluationPo;
import com.zkyyo.www.service.EmployeeService;
import com.zkyyo.www.service.EvaluationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/evaluation_add.do")
public class EvaluationAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
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

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("message", "评价失败");
        } else {
            boolean isAdded = evaluationService.addEvaluation(evaluatorId, beEvaluatedId, stars, comment);
            if (isAdded) {
                request.setAttribute("message", "评价成功");
            } else {
                errors.add("数据库发生错误,无法添加评价");
                request.setAttribute("errors", errors);
                request.setAttribute("message", "评价失败");
            }
        }

        request.getRequestDispatcher("evaluation_add.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
