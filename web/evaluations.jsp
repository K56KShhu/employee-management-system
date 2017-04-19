<%@ page import="java.util.List" %>
<%@ page import="com.zkyyo.www.po.EvaluationPo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询评价</title>
</head>
<body>

<%
    Integer userId = (Integer) request.getSession().getAttribute("login");
    if (userId == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<a href="${pageContext.request.contextPath}/logout.do">注销</a>
<a href="${pageContext.request.contextPath}/functions.jsp">返回</a><br/>
<h1>查询评价</h1>
<form method="get" action="${pageContext.request.contextPath}/evaluation_find.do">
    <select name="way">
        <option value="all">查询所有</option>
        <option value="by_key_words">通过评论关键字(空格分隔)</option>
    </select>
    <input type="text" name="info" size="50" maxlength="80">
    <input type="submit" value="搜索一下"><br/>
    排序依据
    <input type="radio" name="order" value="default" checked>默认
    <input type="radio" name="order" value="stars">评价等级<br/>
    排序方式
    <input type="radio" name="reverse" value="false" checked>升序
    <input type="radio" name="reverse" value="true">倒序
</form>

<%
    List<EvaluationPo> result = (List<EvaluationPo>) request.getAttribute("result");
    if (result != null) {
%>
<table border="1">
    <tr>
        <td>被评价者</td>
        <td>评价等级</td>
        <td>评价内容</td>
        <td>评价者</td>
    </tr>
    <%
        for (EvaluationPo e : result) {
    %>
    <tr>
        <td><a href="/employee_detail.do?&userId=<%= e.getBeEvaluatedId() %>"
               target="_blank"><%= e.getBeEvaluatedId() %>
        </a></td>
        <td><%= e.getStarLevel() %>
        </td>
        <td><%= e.getComment() %>
        </td>
        <td><a href="/employee_detail.do?&userId=<%= e.getEvaluatorId() %>"
               target="_blank"><%= e.getEvaluatorId() %>
        </a></td>
        <td><a href="evaluation_update.jsp?evalId=<%= e.getEvaluationId() %>" target="_blank">修改</a></td>
        <td>
            <a href="${pageContext.request.contextPath}/evaluation_delete.do?evalId=<%= e.getEvaluationId() %>"
               target="_blank">删除</a></td>
    </tr>
    <%
        }
    %>
</table>
<%
    }
%>
</body>
</html>