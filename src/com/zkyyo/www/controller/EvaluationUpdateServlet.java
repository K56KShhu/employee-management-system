package com.zkyyo.www.controller;

import com.zkyyo.www.po.EvaluationPo;
import com.zkyyo.www.service.EvaluationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/evaluation_update.do")
public class EvaluationUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String evalId = request.getParameter("evalId").trim();
        String stars = request.getParameter("stars").trim();
        String comment = request.getParameter("comment").trim();

        List<String> errors = new ArrayList<>();
        EvaluationService evaluationService = EvaluationService.getInstance();
        EvaluationPo eval = new EvaluationPo();
        if (!evaluationService.isValidId(evalId)) {
            errors.add("评价索引格式无效");
        } else {
            if (!evaluationService.isExisted(Integer.valueOf(evalId))) {
                errors.add("评价不存在");
            } else {
                eval.setEvaluationId(Integer.valueOf(evalId));
                if (stars.length() > 0) {
                    if (!evaluationService.isValidStars(stars)) {
                        errors.add("评价等级输入有误");
                    } else {
                        eval.setStarLevel(Integer.valueOf(stars));
                    }
                }
                if (comment.length() > 0) {
                    eval.setComment(comment);
                }
            }
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("message", "修改评价失败");
        } else {
            boolean isUpdated = evaluationService.updateEvaluation(eval);
            if (isUpdated) {
                request.setAttribute("message", "修改评价成功");
            } else {
                errors.add("数据库发生错误,无法修改评价");
                request.setAttribute("errors", errors);
                request.setAttribute("message", "修改评价失败");
            }
        }

        request.getRequestDispatcher("evaluation_update.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}