<%@ page import="com.zkyyo.www.po.DepartmentPo" %>
<%@ page import="com.zkyyo.www.po.EmployeePo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>部门详细信息</title>
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
    DepartmentPo department = (DepartmentPo) request.getAttribute("department");
    List<EmployeePo> employees = (List<EmployeePo>) request.getAttribute("employees");
    if (department == null || employees == null) {
        response.sendRedirect("functions.jsp");
        return;
    }
%>
<h2>部门详细信息</h2>
<hr/>

<table border="1" align="center">
    <tr>
        <th>部门号</th>
        <td><%= department.getDeptId() %>
        </td>
    </tr>
    <tr>
        <th>部门名</th>
        <td><%= department.getName() %>
        </td>
    </tr>
    <tr>
        <th>人数</th>
        <td><%= department.getPopulation() %>
        </td>
    </tr>
    <tr>
        <th>建立日期</th>
        <td><%= department.getBuildDate() %>
        </td>
    </tr>
    <tr>
        <th>描述</th>
        <td><%= department.getDescription() %>
        </td>
    </tr>
    <tr>
        <th>组成人员</th>
        <td>
            <table border="1">
                <tr>
                    <th>员工号</th>
                    <th>姓名</th>
                    <th>薪水</th>
                    <th>就职日期</th>
                    <th>手机号</th>
                    <th>邮箱</th>
                </tr>
                <%
                    for (EmployeePo e : employees) {
                %>
                <tr>
                    <td><a href="/employee_detail.do?&userId=<%= e.getUserId() %>" target="_blank"><%= e.getUserId() %>
                    </a></td>
                    </td>
                    <td><%= e.getUserName()%>
                    </td>
                    <td><%= e.getSalary() %>
                    </td>
                    <td><%= e.getEmployDate() %>
                    </td>
                    <td><%= e.getMobile() %>
                    </td>
                    <td><%= e.getEmail() %>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
        </td>
        </td>
    </tr>
</table>
</body>
</html>
