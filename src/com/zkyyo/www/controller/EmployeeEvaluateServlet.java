package com.zkyyo.www.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/employee_evaluate.do")
public class EmployeeEvaluateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (Integer) request.getSession().getAttribute("login");
        int evaluatorId = Integer.valueOf(request.getParameter("evaluatorId"));
        int beEvaluatedId = Integer.valueOf(request.getParameter("beEvaluatedId"));
        int stars = Integer.valueOf(request.getParameter("stars"));
        String comment = request.getParameter("comment");

        if (userId != evaluatorId) {
            response.sendRedirect("functions.jsp");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
