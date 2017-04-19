<%@ page import="com.zkyyo.www.po.DepartmentPo" %>
<%@ page import="com.zkyyo.www.po.EmployeePo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>部门详细信息</title>
</head>
<body>
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
<h1>部门详细信息</h1>
<table border="1">
    <tr>
        <td>部门号</td>
        <td><%= department.getDeptId() %>
        </td>
    </tr>
    <tr>
        <td>部门名</td>
        <td><%= department.getDeptName() %>
        </td>
    </tr>
    <tr>
        <td>人数</td>
        <td><%= department.getDeptPopulation() %>
        </td>
    </tr>
    <tr>
        <td>建立日期</td>
        <td><%= department.getBuildDate() %>
        </td>
    </tr>
    <tr>
        <td>描述</td>
        <td><%= department.getDeptDesc() %>
        </td>
    </tr>
    <tr>
        <td>组成人员</td>
        <td>
            <table border="1">
                <tr>
                    <td>员工号</td>
                    <td>姓名</td>
                    <td>薪水</td>
                    <td>就职日期</td>
                    <td>手机号</td>
                    <td>邮箱</td>
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
