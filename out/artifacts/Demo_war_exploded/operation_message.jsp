<%--
  Created by IntelliJ IDEA.
  User: xu
  Date: 4/18/17
  Time: 10:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>删除结果</title>
</head>
<body>
<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
<script type="text/javascript">
    alert("<%= message %>");
</script>
<%
    }
%>
</body>
</html>
