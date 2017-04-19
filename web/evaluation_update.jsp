<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改评价</title>
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
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
<script type="text/javascript">
    alert("<%= message %>");
</script>
<%
    }
%>


<h1>修改评价</h1>
<form method="post" action="/evaluation_update.do">
    <table border="1">
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

