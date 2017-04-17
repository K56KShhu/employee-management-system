<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>创建部门</title>
</head>
<body>

<%
    Integer employeeId = (Integer) request.getSession().getAttribute("login");
    if (employeeId == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    List<String> errors = (List<String>) request.getAttribute("errors");
    if (errors != null) {
        out.println("<h1>创建部门失败,错误信息如下<h1>");
        out.println("<ul>");
        for (String error : errors) {
            out.println("<li>" + error + "</li>");
        }
        out.println("</ul>");
    }
    String status = (String) request.getAttribute("status");
    if (status != null) {
        out.println("<h1>创建部门成功<h1><br/>");
    }
%>

<a href="${pageContext.request.contextPath}/logout.do">注销</a>
<a href="${pageContext.request.contextPath}/functions.jsp">返回</a>

<h1>创建部门</h1>
<form method="post" action="${pageContext.request.contextPath}/employee_add.do">
    <table border="1">
        <tr>
            <td align="right">部门名:</td>
            <td><input type="text" name="name" size="30" maxlength="20"></td>
        </tr>
        <tr>
            <td align="right">部门号:</td>
            <td><input type="text" name="departmentId" size="30" maxlength="20"></td>
        </tr>
        <tr>
            <td align="right">创建日期:</td>
            <td><input type="text" name="buildDate" size="30" maxlength="20" value="<%= new java.sql.Date(System.currentTimeMillis()) %>"></td>
        </tr>
        <tr>
            <td align="right">描述:</td>
            <td><textarea name="description" rows="10" cols="30"></textarea></td>
        </tr>
    </table>
    <input type="submit" value="提交">
</form>
</body>
</html>
