package com.zkyyo.www.controller;

import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.po.EvaluationPo;
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
        name = "EvaluationFindServlet",
        urlPatterns = {"/evaluation_find.do"},
        initParams = {
                @WebInitParam(name = "EVALUATION_VIEW", value = "evaluations.jsp"),
        }
)
public class EvaluationFindServlet extends HttpServlet {
    private String EVALUATION_VIEW;

    public void init() throws ServletException {
        EVALUATION_VIEW = getServletConfig().getInitParameter("EVALUATION_VIEW");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String way = request.getParameter("way");
        String info = request.getParameter("info").trim();
        List<EvaluationPo> result = null;
        EvaluationService evaluationService = EvaluationService.getInstance();

        if (way.length() > 0 && info.length() > 0) {
            if (way.equals("all")) {
                result = evaluationService.findEvaluations();
            }
            if (way.equals("by_key_words")) {
                result = evaluationService.findEvaluationsByKeyWords(info);
            }
        } else {
            result = evaluationService.findEvaluations();
        }
        request.setAttribute("result", result);
        request.getRequestDispatcher(EVALUATION_VIEW).forward(request, response);
    }
}
