<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>功能选择</title>
</head>
<body>

<%
    Integer userId = (Integer) request.getSession().getAttribute("login");
    if (userId == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<a href="${pageContext.request.contextPath}/logout.do" >注销</a>
<h1>功能列表</h1>
<h2>员工管理</h2>
<ul>
    <li><a href="${pageContext.request.contextPath}/employee_add.jsp" target="_blank">添加员工</a></li>
    <li><a href="${pageContext.request.contextPath}/employees.jsp" target="_blank">查询员工</a></li>
</ul>
<br/>
<h2>部门管理</h2>
<ul>
    <li><a href="${pageContext.request.contextPath}/department_add.jsp" target="_blank">创建部门</a></li>
    <li><a href="${pageContext.request.contextPath}/departments.jsp" target="_blank">查询部门</a></li>
</ul>
<h2>评价管理</h2>

</body>
</html>
