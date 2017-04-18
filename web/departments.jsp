<%@ page import="java.util.List" %>
<%@ page import="com.zkyyo.www.po.DepartmentPo" %>
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
            out.println("<h1>解散部门失败</h1>");
        } else if (status.equals("ok")) {
            out.println("<h1>解散部门成功</h1>");
        }
    }
%>
<a href="${pageContext.request.contextPath}/logout.do">注销</a>
<a href="${pageContext.request.contextPath}/functions.jsp">返回</a><br/>
<h1>查询部门</h1>
<form method="get" action="${pageContext.request.contextPath}/department_find.do">
    <select name="way">
        <option value="all">查询所有</option>
        <option value="by_dept_id">通过部门号</option>
        <option value="by_dept_name">通过部门名</option>
    </select>
    <input type="text" name="info" size="50" maxlength="80">
    <input type="submit" value="搜索一下">
</form>

<table border="1">
    <tr>
        <td>部门号</td>
        <td>部门名</td>
        <td>人数</td>
        <td>建立日期</td>
        <td>描述</td>
    </tr>
    <%
        List<DepartmentPo> result = (List<DepartmentPo>) request.getAttribute("result");
        if (result != null) {
            for (DepartmentPo d : result) {
                out.println("<tr>");
                out.println("<td>" + d.getDeptId() + "</td>");
                out.println("<td>" + d.getDeptName() + "</td>");
                out.println("<td>" + d.getDeptPopulation() + "</td>");
                out.println("<td>" + d.getBuildDate() + "</td>");
                out.println("<td>" + d.getDeptDesc() + "</td>");
                out.println("<td><a href='/department_update.jsp?&deptId=" + d.getDeptId() + "'>修改</a>");
                out.println("<td><a href='/department_delete.do?&deptId=" + d.getDeptId() + "'>删除</a>");
                out.println("</tr>");
            }
        }
    %>
</table>

</body>
</html>
