<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改评价</title>
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
        out.println("<h3>修改信息失败, 详细信息如下</h3>");
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
<h2>修改评价</h2>
<hr/>
<form method="post" action="/evaluation_update.do">
    <table border="1" align="center">
        <tr>
            <td>评价内容(300字以内):</td>
            <td><textarea name="comment" rows="10" cols="30" maxlength="300"></textarea></td>
        </tr>
    </table>
    评价等级(1-10):
    <input type="hidden" name="evalId" value=<%= request.getParameter("evalId") %>>
    <input type="number" name="stars" min="1" max="10"><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>

