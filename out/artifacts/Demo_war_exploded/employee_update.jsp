<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改员工信息</title>
</head>
<body>
<%
    Integer userId = (Integer) request.getSession().getAttribute("login");
    if (userId == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    List<String> errors = (List<String>) request.getAttribute("errors");
    if (errors != null) {
        out.println("<h1>错误信息如下<h1>");
        out.println("<ul>");
        for (String error : errors) {
            out.println("<li>" + error + "</li>");
        }
        out.println("</ul>");
    }
    String status = (String) request.getAttribute("status");
    if (status != null && status.equals("ok")) {
        out.println("<h1>修改员工成功</h1>");
    }
%>

<a href="${pageContext.request.contextPath}/logout.do">注销</a>
<a href="${pageContext.request.contextPath}/functions.jsp">返回</a>

<h1>修改员工信息</h1>
<p>可选择以下任意项进行修改</p>
<form method="post" action="${pageContext.request.contextPath}/employee_update.do">
    <table border="1">
        <tr>
            <td align="right">员工号:</td>
            <td><input type="text" name="userId" size="30" maxlength="20" readonly="readonly"
                       value="<%= request.getParameter("userId") %>"></td>
        </tr>
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
            <td><input type="text" name="date" size="30" maxlength="20"></td>
        </tr>
    </table>
    <input type="submit" value="提交">
</form>
</body>
</html>
