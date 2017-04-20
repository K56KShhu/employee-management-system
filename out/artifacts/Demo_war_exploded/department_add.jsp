<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>创建部门</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>
<div style="text-align: right">
    <a href="${pageContext.request.contextPath}/functions.jsp">首页</a>
    <a href="${pageContext.request.contextPath}/logout.do">注销&nbsp;</a>
</div>
<%
    Integer userId = (Integer) request.getSession().getAttribute("login");
    if (userId == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    List<String> errors = (List<String>) request.getAttribute("errors");
    if (errors != null) {
        out.println("<h3>创建部门失败,详细信息如下</h3>");
        for (String error : errors) {
            out.println("<p style='font-family:arial;color:red;font-size:20px;'>" + error + "</p>");
        }
    }
    String status = (String) request.getAttribute("status");
    if (status != null) {
        out.println("<h3>创建部门成功<h3>");
    }
%>
<h2>创建部门</h2>
<hr/>

<form method="post" action="${pageContext.request.contextPath}/department_add.do">
    <table border="1" align="center">
        <tr>
            <th align="right">部门号:</th>
            <td><input type="text" name="departmentId" size="30" maxlength="10"></td>
        </tr>
        <tr>
            <th align="right">部门名:</th>
            <td><input type="text" name="name" size="30" maxlength="20"></td>
        </tr>

        <tr>
            <th align="right">创建日期:</th>
            <td><input type="text" name="buildDate" size="30" maxlength="20"
                       value="<%= new java.sql.Date(System.currentTimeMillis()) %>"></td>
        </tr>
        <tr>
            <th align="right">描述(300字以内):</th>
            <td><textarea name="description" rows="10" cols="30" maxlength="300"></textarea></td>
        </tr>
    </table>
    <input type="submit" value="提交">
</form>
</body>
</html>
