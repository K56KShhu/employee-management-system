<%@ page import="com.zkyyo.www.po.EmployeePo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询员工</title>
</head>
<body>

<%
    Integer employeeId = (Integer) request.getSession().getAttribute("login");
    if (employeeId == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    String status = (String) request.getAttribute("status");
    if (status != null) {
        if (status.equals("fail")) {
            out.println("<h1>删除员工失败</h1>");
        } else {
            out.println("<h1>删除员工成功</h1>");
        }
    }
%>
<h>查询员工</h>
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
        List<EmployeePo> result = (List<EmployeePo>) request.getAttribute("result");
        if (result != null) {
            for (EmployeePo e : result) {
                out.println("<tr>");
                out.println("<td>" + e.getUserId() + "</td>");
                out.println("<td>" + e.getUserName() + "</td>");
                out.println("<td>" + e.getDeptId() + "</td>");
                out.println("<td>" + e.getSalary() + "</td>");
                out.println("<td>" + e.getEmployDate() + "</td>");
                out.println("<td>" + e.getMobile() + "</td>");
                out.println("<td>" + e.getEmail() + "</td>");
                out.println("<td><a href='/employee_update.jsp?&userId=" + e.getUserId() + "'>修改</a>");
                out.println("<td><a href='/employee_delete.do?&userId=" + e.getUserId() + "'>删除</a>");
                out.println("</tr>");
            }
        }
    %>
</table>

</body>
</html>
