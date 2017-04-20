package com.zkyyo.www.controller;

import com.zkyyo.www.po.EvaluationPo;
import com.zkyyo.www.service.EvaluationService;
import com.zkyyo.www.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/evaluation_delete.do")
public class EvaluationDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String evalId = request.getParameter("evalId");
        Integer loginId = (Integer) request.getSession().getAttribute("login");

        EvaluationService evaluationService = EvaluationService.getInstance();
        if (evalId != null) {
            if (evaluationService.isValidId(evalId)) {
                if (evaluationService.isExisted(Integer.valueOf(evalId))) {
                    EvaluationPo deletedEval = evaluationService.deleteEvaluation(Integer.valueOf(evalId));
                    if (deletedEval == null) {
                        request.setAttribute("message", "评价删除失败");
                    } else {
                        request.setAttribute("message", "评价删除成功");
                        LogUtil.delete(loginId, deletedEval);
                    }
                }
            }
        }
        request.getRequestDispatcher("operation_message.jsp").forward(request, response);
    }
}
