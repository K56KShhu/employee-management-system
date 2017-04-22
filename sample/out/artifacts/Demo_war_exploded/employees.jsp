<%@ page import="com.zkyyo.www.po.EmployeePo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询员工</title>
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
%>
<h2>查询员工</h2>
<hr/>

<form method="get" action="${pageContext.request.contextPath}/employee_find.do">
    <select name="way">
        <option value="all">查询所有</option>
        <option value="by_user_id">通过员工号</option>
        <option value="by_user_name">通过员工名</option>
        <option value="by_dept_id">通过部门号</option>
    </select>
    <input type="text" name="info" size="50" maxlength="80">
    <input type="submit" value="搜索一下"><br/>
    排序依据
    <input type="radio" name="order" value="default" checked>默认
    <input type="radio" name="order" value="user_id">用户号
    <input type="radio" name="order" value="dept_id">部门号
    <input type="radio" name="order" value="salary">薪水
    <input type="radio" name="order" value="date">就职日期<br/>
    排序方式
    <input type="radio" name="reverse" value="false" checked>升序
    <input type="radio" name="reverse" value="true">倒序

</form>

<table border="1" align="center">
    <%
        List<EmployeePo> result = (List<EmployeePo>) request.getAttribute("result");
        if (result != null) {
    %>
    <tr>
        <th>员工号</th>
        <th>姓名</th>
        <th>部门号</th>
        <th>薪水</th>
        <th>就职日期</th>
        <th>手机号</th>
        <th>邮箱</th>
    </tr>
    <%
        for (EmployeePo e : result) {
    %>
    <tr>
        <td><%= e.getUserId() %>
        </td>
        <td><%= e.getUserName() %>
        </td>
        <td><a href="/department_detail.do?&deptId=<%= e.getDeptId() %>"><%= e.getDeptId() %>
        </a></td>
        <td><%= e.getSalary() %>
        </td>
        <td><%= e.getEmployDate() %>
        </td>
        <td><%= e.getMobile() %>
        </td>
        <td><%= e.getEmail() %>
        </td>
        <td><a href="/employee_detail.do?&userId=<%= e.getUserId() %>" target="_blank">详细</a></td>
        <td><a href="/evaluation_add.jsp?&beEvaluatedId=<%= e.getUserId() %>" target="_blank">评价</a></td>
        <td><a href="/employee_update.jsp?&userId=<%= e.getUserId() %>" target="_blank">修改</a></td>
        <%
            if (userId != e.getUserId()) {
        %>
        <td><a href="/employee_delete.do?&userId=<%= e.getUserId() %>" target="_blank">删除</a></td>
        <%
            }
        %>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
