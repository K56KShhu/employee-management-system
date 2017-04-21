package com.zkyyo.www.controller;

import com.zkyyo.www.po.EvaluationPo;
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
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String way = request.getParameter("way"); //查询方式
        String info = request.getParameter("info"); //查询内容
        String order = request.getParameter("order"); //排序依据
        String reverse = request.getParameter("reverse"); //升序降序
        List<EvaluationPo> result = null;
        EvaluationService evaluationService = EvaluationService.getInstance();

        //获得查询列表
        if (way != null && way.length() > 0) {
            if (info != null) {
                info = info.trim();
                if (info.length() > 0) { //用户有输入内容
                    //根据评价关键词查询
                    if (way.equals("by_key_words")) {
                        result = evaluationService.findEvaluationsByKeyWords(info);
                    }
                    //查询所有
                    if (way.equals("all")) {
                        result = evaluationService.findEvaluations();
                    }
                } else { //用户无输入内容
                    //查询所有
                    result = evaluationService.findEvaluationsByKeyWords(info);
                }
            }
        }

        //排序
        if (result != null) {
            //升序
            if (reverse.equals("false")) {
                switch (order) {
                    case "stars":
                        result = evaluationService.sort(result, EvaluationService.ORDER_BY_STARS, false);
                        break;
                    default:
                        break;
                }
            }
            //倒序
            if (reverse.equals("true")) {
                switch (order) {
                    case "stars":
                        result = evaluationService.sort(result, EvaluationService.ORDER_BY_STARS, true);
                        break;
                    default:
                        break;
                }
            }
        }

        request.setAttribute("result", result);
        request.getRequestDispatcher(EVALUATION_VIEW).forward(request, response);
    }
}
