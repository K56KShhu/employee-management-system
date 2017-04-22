<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>功能选择</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>
<div style="text-align: right">
<a href="${pageContext.request.contextPath}/logout.do">注销&nbsp;</a>
</div>
<%
    Integer userId = (Integer) request.getSession().getAttribute("login");
    if (userId == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<h1 style="text-align: center">功能列表</h1>
<hr/>

<h2 style="text-align: center">员工管理</h2>
<a href="${pageContext.request.contextPath}/employee_add.jsp" target="_blank">添加员工</a><br/>
<a href="${pageContext.request.contextPath}/employees.jsp" target="_blank">查询员工</a><br/>

<h2 style="text-align: center">部门管理</h2>
<a href="${pageContext.request.contextPath}/department_add.jsp" target="_blank">创建部门</a><br/>
<a href="${pageContext.request.contextPath}/departments.jsp" target="_blank">查询部门</a><br/>

<h2 style="text-align: center">评价管理</h2>
<a href="${pageContext.request.contextPath}/evaluations.jsp" target="_blank">查询评价</a><br/>

</body>
</html>
