<%@ page import="com.zkyyo.www.po.EmployeePo" %>
<%@ page import="com.zkyyo.www.po.EvaluationPo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.zkyyo.www.po.DepartmentPo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>员工详细信息</title>
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
<%
    EmployeePo employee = (EmployeePo) request.getAttribute("employee");
    DepartmentPo department = (DepartmentPo) request.getAttribute("department");
    List<EvaluationPo> sendedEvals = (List<EvaluationPo>) request.getAttribute("sendedEvals");
    List<EvaluationPo> receivedEvals = (List<EvaluationPo>) request.getAttribute("receivedEvals");
    if (employee != null && department != null && sendedEvals != null && receivedEvals != null) {
%>
<h2>员工详细信息</h2>
<hr/>

<table border="1" align="center">
    <tr>
        <th>员工号</th>
        <td><%= employee.getUserId() %>
        </td>
    </tr>
    <tr>
        <th>员工名</th>
        <td><%= employee.getUserName() %>
        </td>
    </tr>
    <tr>
        <th>部门名</th>
        <td><a href="/department_detail.do?&deptId=<%= employee.getDeptId() %>"
               target="_blank"><%= department.getName() %>
        </a></td>
    </tr>
    <tr>
        <th>部门号</th>
        <td><a href="/department_detail.do?&deptId=<%= employee.getDeptId() %>"
               target="_blank"><%= employee.getDeptId() %>
        </a></td>
    </tr>
    <tr>
        <th>薪水</th>
        <td><%= employee.getSalary() %>
        </td>
    </tr>
    <tr>
        <th>手机号</th>
        <td><%= employee.getMobile() %>
        </td>
    </tr>
    <tr>
        <th>邮箱</th>
        <td><%= employee.getEmail() %>
        </td>
    </tr>
    <tr>
        <th>就职日期</th>
        <td><%= employee.getEmployDate() %>
        </td>
    </tr>
    <%
        if (!sendedEvals.isEmpty()) {
    %>
    <tr>
        <th>发出的评价</th>
        <td>
            <table border="1">
                <tr>
                    <th>评价等级</th>
                    <th>评价内容</th>
                    <th>被评价id</th>
                </tr>
                <%
                    for (EvaluationPo e : sendedEvals) {
                %>
                <tr>
                    <td><%= e.getStarLevel() %>
                    </td>
                    <td><%= e.getComment() %>
                    </td>
                    <td><a href="/employee_detail.do?&userId=<%= e.getBeEvaluatedId() %>"
                           target="_blank"><%= e.getBeEvaluatedId() %>
                    </a></td>
                    <td><a href="evaluation_update.jsp?evalId=<%= e.getEvaluationId() %>" target="_blank">修改</a></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/evaluation_delete.do?evalId=<%= e.getEvaluationId() %>"
                           target="_blank">删除</a></td>
                </tr>
                <%
                    }
                %>
            </table>
        </td>
    </tr>
    <%
        }
        if (!receivedEvals.isEmpty()) {
    %>
    <tr>
        <th>收到的评价</th>
        <td>
            <table border="1">
                <tr>
                    <th>评价等级</th>
                    <th>评价内容</th>
                    <th>评价者</th>
                </tr>
                <%
                    for (EvaluationPo e : receivedEvals) {
                %>
                <tr>
                    <td><%= e.getStarLevel() %>
                    </td>
                    <td><%= e.getComment() %>
                    </td>
                    <td><a href="/employee_detail.do?&userId=<%= e.getEvaluatorId() %>"
                           target="_blank"><%= e.getEvaluatorId() %>
                    </a></td>
                    <td><a href="evaluation_update.jsp?evalId=<%= e.getEvaluationId() %>" target="_blank">修改</a></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/evaluation_delete.do?evalId=<%= e.getEvaluationId() %>"
                           target="_blank">删除</a></td>
                </tr>
                <%
                    }
                %>
            </table>
        </td>
    </tr>
    <%
        }
    %>
</table>
<%
} else {
%>
<h2 style="text-align: center">该员工可能离开我们了, 看来他另有打算啊......</h2>
<hr/>
<%
    }
%>
</body>
</html>
