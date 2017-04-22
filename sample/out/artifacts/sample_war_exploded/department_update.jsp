<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改部门信息</title>
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
        out.println("<h3>修改部门失败, 详细信息如下</h3>");
        for (String error : errors) {
            out.println("<p style='font-family:arial;color:red;font-size:20px;'>" + error + "</p>");
        }
    }
    String status = (String) request.getAttribute("status");
    if (status != null && status.equals("ok")) {
        out.println("<h3>修改部门成功</h3>");
    }
%>
<h2>修改部门信息</h2>
<hr/>

<p>可选择以下任意项进行修改</p>
<form method="post" action="${pageContext.request.contextPath}/department_update.do">
    <table border="1" align="center">
        <tr>
            <td align="right">部门号:</td>
            <td><input type="text" name="deptId" size="30" maxlength="20" readonly="readonly"
                       value="<%= request.getParameter("deptId") %>"></td>
        </tr>
        <tr>
            <td align="right">部门名:</td>
            <td><input type="text" name="name" size="30" maxlength="20"></td>
        </tr>
        <tr>
            <td align="right">创建日期:</td>
            <td><input type="text" name="buildDate" size="30" maxlength="20"></td>
        </tr>
        <tr>
            <td align="right">描述:</td>
            <td><textarea name="description" rows="10" cols="30" maxlength="300"></textarea></td>
        </tr>
    </table>
    <input type="submit" value="提交">
</form>
</body>
</html>
