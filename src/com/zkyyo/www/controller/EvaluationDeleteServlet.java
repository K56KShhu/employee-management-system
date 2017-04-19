package com.zkyyo.www.controller;

import com.zkyyo.www.dao.EvaluationDao;
import com.zkyyo.www.service.EvaluationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/evaluation_delete.do")
public class EvaluationDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String evalId = request.getParameter("evalId");

        EvaluationService evaluationService = EvaluationService.getInstance();
        if (evalId != null) {
            if (evaluationService.isValidId(evalId)) {
                if (evaluationService.isExisted(Integer.valueOf(evalId))) {
                    boolean isDeleted = evaluationService.deleteEvaluation(Integer.valueOf(evalId));
                    if (isDeleted) {
                        request.setAttribute("message", "评价删除成功");
                    } else {
                        request.setAttribute("message", "评价删除失败");
                    }
                }
            }
        }
        request.getRequestDispatcher("operation_message.jsp").forward(request, response);
    }
}
