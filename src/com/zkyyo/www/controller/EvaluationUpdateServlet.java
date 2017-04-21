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
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "EvaluationUpdateServlet",
        urlPatterns = {"/evaluation_update.do"},
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW", value = "evaluation_update.jsp"),
                @WebInitParam(name = "ERROR_VIEW", value = "evaluation_update.jsp")
        }
)
public class EvaluationUpdateServlet extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;

    public void init() throws ServletException {
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer loginId = (Integer) request.getSession().getAttribute("login"); //登录员工号
        String evalId = request.getParameter("evalId"); //评价号
        String stars = request.getParameter("stars"); //评价等级
        String comment = request.getParameter("comment"); //评价内容

        List<String> errors = new ArrayList<>(); //错误列表
        //检查是否遗漏参数 !未解决!
        if (evalId == null || stars == null || comment == null) {
            errors.add("信息不完整");
        }

        EvaluationService evaluationService = EvaluationService.getInstance();
        EvaluationPo eval = new EvaluationPo();
        if (evalId != null) {
            //校验评价号是否有效
            if (!evaluationService.isValidId(evalId)) {
                errors.add("评价索引格式无效");
            } else {
                int id = Integer.valueOf(evalId);
                //检测评价是否存在
                if (!evaluationService.isExisted(id)) {
                    errors.add("评价不存在");
                } else {
                    eval.setEvaluationId(id); //设置评价号
                    //是否进行进行等级评价
                    if (stars != null && stars.length() > 0) {
                        //检测评价等级是否有效
                        if (!evaluationService.isValidStars(stars)) {
                            errors.add("评价等级输入有误"); //不合法
                        } else {
                            eval.setStarLevel(id); //合法
                        }
                    }
                    //是否进行评论
                    if (comment != null && comment.length() > 0) {
                        eval.setComment(comment);
                    }
                }
            }
        }

        String page = ERROR_VIEW;
        if (!errors.isEmpty()) { //输入有误
            request.setAttribute("errors", errors);
            request.setAttribute("message", "修改评价失败");
        } else { //输入正确
            EvaluationPo initialEval = evaluationService.findEvaluation(Integer.valueOf(evalId)); //原始评价对象
            EvaluationPo updatedEval = evaluationService.updateEvaluation(eval); //更新后的评价对象
            if (updatedEval == null) { //添加数据库失败
                errors.add("数据库发生错误,无法修改评价");
                request.setAttribute("errors", errors);
                request.setAttribute("message", "修改评价失败");
            } else { //添加数据库成功
                request.setAttribute("message", "修改评价成功");
                LogUtil.update(loginId, initialEval, updatedEval); //记录日志
                page = SUCCESS_VIEW;
            }
        }

        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
