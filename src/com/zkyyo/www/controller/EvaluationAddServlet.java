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
        String evaluatorId = request.getParameter("evaluatorId");
        String beEvaluatedId = request.getParameter("beEvaluatedId");
        String stars = request.getParameter("stars");
        String comment = request.getParameter("comment");

        List<String> errors = new ArrayList<>(); //错误列表
        //检查是否遗漏参数 !未解决!
        if (evaluatorId == null || beEvaluatedId == null || stars == null || comment == null) {
            errors.add("信息不完整");
        }

        EmployeeService employeeService = EmployeeService.getInstance();
        EvaluationService evaluationService = EvaluationService.getInstance();
        //校验评价人是否为当前登录的用户
        if (evaluatorId != null && Integer.valueOf(evaluatorId) != userId) {
            //评价人不是当前用户, 重定向为功能页面
            response.sendRedirect("functions.jsp");
        }
        //校验被评价的员工号是否符合格式
        if (beEvaluatedId != null && employeeService.isValidId(beEvaluatedId)) {
            //校验被评价员工是否存在
            if (!employeeService.isIdExisted(Integer.valueOf(beEvaluatedId))) {
                errors.add("被评价员工不存在");
            }
        }
        //校验评价等级
        if (stars != null && !evaluationService.isValidStars(stars)) {
            errors.add("评价等级无效");
        }

        String page = ERROR_VIEW;
        if (!errors.isEmpty()) { //输入有误
            request.setAttribute("errors", errors);
            request.setAttribute("message", "评价失败");
        } else { //输入正确
            EvaluationPo newEval = evaluationService.addEvaluation(evaluatorId, beEvaluatedId, stars, comment);
            if (newEval == null) { //添加失败
                errors.add("数据库发生错误,无法添加评价");
                request.setAttribute("errors", errors);
                request.setAttribute("message", "评价失败");
            } else { //添加成功
                request.setAttribute("message", "评价成功");
                LogUtil.add(loginId, newEval); //记录日志
                page = SUCCESS_VIEW;
            }
        }
        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
