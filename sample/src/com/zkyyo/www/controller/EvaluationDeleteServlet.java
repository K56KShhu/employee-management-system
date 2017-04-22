package com.zkyyo.www.controller;

import com.zkyyo.www.po.EvaluationPo;
import com.zkyyo.www.service.EvaluationService;
import com.zkyyo.www.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "EvaluationDeleteServlet",
        urlPatterns = {"/evaluation_delete.do"},
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW", value = "operation_message.jsp"),
                @WebInitParam(name = "ERROR_VIEW", value = "operation_message.jsp")
        }
)
public class EvaluationDeleteServlet extends HttpServlet {
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
        Integer loginId = (Integer) request.getSession().getAttribute("login"); //登录用户号
        String evalId = request.getParameter("evalId"); //评论号

        String page = ERROR_VIEW;
        EvaluationService evaluationService = EvaluationService.getInstance();
        if (evalId != null) {
            //待删除评价号是否有效
            if (evaluationService.isValidId(evalId)) {
                //待删除评价是否存在
                if (evaluationService.isExisted(Integer.valueOf(evalId))) {
                    EvaluationPo deletedEval = evaluationService.deleteEvaluation(Integer.valueOf(evalId));
                    if (deletedEval == null) { //删除失败
                        request.setAttribute("message", "评价删除失败");
                    } else { //删除成功
                        request.setAttribute("message", "评价删除成功");
                        LogUtil.delete(loginId, deletedEval);
                        page = SUCCESS_VIEW;
                    }
                }
            }
        }
        request.getRequestDispatcher(page).forward(request, response);
    }
}
