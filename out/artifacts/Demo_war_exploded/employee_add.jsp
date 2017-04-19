<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加员工</title>
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
        out.println("<h3>注册员工失败, 详细信息如下</h3>");
        for (String error : errors) {
            out.println("<p style='font-family:arial;color:red;font-size:20px;'>" + error + "</p>");
        }
    }
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
<script type="text/javascript">
    alert("<%= message %>");
</script>
<%
    }
%>
<h2>添加员工</h2>
<hr/>

<form method="post" action="${pageContext.request.contextPath}/employee_add.do">
    <table border="1" align="center">
        <tr>
            <td align="right">员工名:</td>
            <td><input type="text" name="name" size="30" maxlength="20"></td>
        </tr>
        <tr>
            <td align="right">手机号:</td>
            <td><input type="text" name="mobile" size="30" maxlength="20"></td>
        </tr>
        <tr>
            <td align="right">邮箱:</td>
            <td><input type="text" name="email" size="30" maxlength="50"></td>
        </tr>
        <tr>
            <td align="right">密码:</td>
            <td><input type="password" name="password" size="30" maxlength="30"></td>
        </tr>
        <tr>
            <td align="right">密码确认:</td>
            <td><input type="password" name="confirmedPassword" size="30" maxlength="30"></td>
        </tr>
        <tr>
            <td align="right">部门号:</td>
            <td><input type="text" name="departmentId" size="30" maxlength="20"></td>
        </tr>
        <tr>
            <td align="right">薪水:</td>
            <td><input type="text" name="salary" size="30" maxlength="20"></td>
        </tr>
        <tr>
            <td align="right">就职日期:</td>
            <td><input type="text" name="date" size="30" maxlength="20"
                       value="<%= new java.sql.Date(System.currentTimeMillis()) %>"></td>
        </tr>
    </table><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
