<%@ page import="com.zkyyo.www.po.EmployeePo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询员工</title>
</head>
<body>

<%
    Integer userId = (Integer) request.getSession().getAttribute("login");
    if (userId == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    String status = (String) request.getAttribute("status");
    if (status != null) {
        if (status.equals("fail")) {
            out.println("<h1>删除员工失败</h1>");
        } else if (status.equals("ok")) {
            out.println("<h1>删除员工成功</h1>");
        }
    }
%>
<a href="${pageContext.request.contextPath}/logout.do">注销</a>
<a href="${pageContext.request.contextPath}/functions.jsp">返回</a><br/>
<h1>查询员工</h1>
<form method="get" action="${pageContext.request.contextPath}/employee_find.do">
    <select name="way">
        <option value="all">查询所有</option>
        <option value="by_user_id">通过员工号</option>
        <option value="by_user_name">通过员工名</option>
        <option value="by_dept_id">通过部门号</option>
    </select>
    <input type="text" name="info" size="50" maxlength="80">
    <input type="submit" value="搜索一下">
</form>

<table border="1">
    <%
        List<EmployeePo> result = (List<EmployeePo>) request.getAttribute("result");
        if (result != null) {
    %>
    <tr>
        <td>员工号</td>
        <td>姓名</td>
        <td>部门号</td>
        <td>薪水</td>
        <td>就职日期</td>
        <td>手机号</td>
        <td>邮箱</td>
    </tr>
    <%
        for (EmployeePo e : result) {
    %>

    <tr>
        <td><%= e.getUserId() %>
        </td>
        <td><%= e.getUserName() %>
        </td>
        <td><a href="/department_detail.do?&deptId=<%= e.getDeptId() %>"><%= e.getDeptId() %></a></td>
        <td><%= e.getSalary() %>
        </td>
        <td><%= e.getEmployDate() %>
        </td>
        <td><%= e.getMobile() %>
        </td>
        <td><%= e.getEmail() %>
        </td>
        <td><a href="/employee_detail.do?&userId=<%= e.getUserId() %>">详细</a></td>
        <td><a href="/evaluation_add.jsp?&beEvaluatedId=<%= e.getUserId() %>">评价</a></td>
        <td><a href="/employee_update.jsp?&userId=<%= e.getUserId() %>">修改</a></td>
        <td><a href="/employee_delete.do?&userId=<%= e.getUserId() %>">删除</a></td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
