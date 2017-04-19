<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>删除结果</title>
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
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
<h2><%= message %></h2>
<hr />
<script type="text/javascript">
    alert("<%= message %>");
</script>
<%
    }
%>
</body>
</html>
