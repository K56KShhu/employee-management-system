<%@ page import="com.zkyyo.www.po.EmployeePo" %>
<%@ page import="com.zkyyo.www.po.EvaluationPo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.zkyyo.www.po.DepartmentPo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>员工详细信息</title>
</head>
<body>
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
<h1>员工详细信息</h1>
<table border="1">
    <tr>
        <td>员工号</td>
        <td><%= employee.getUserId() %>
        </td>
    </tr>
    <tr>
        <td>员工名</td>
        <td><%= employee.getUserName() %>
        </td>
    </tr>
    <tr>
        <td>部门名</td>
        <td><%= department.getDeptName() %>
        </td>
    </tr>
    <tr>
        <td>部门号</td>
        <td><%= employee.getDeptId() %>
        </td>
    </tr>
    <tr>
        <td>薪水</td>
        <td><%= employee.getSalary() %>
        </td>
    </tr>
    <tr>
        <td>手机号</td>
        <td><%= employee.getMobile() %>
        </td>
    </tr>
    <tr>
        <td>邮箱</td>
        <td><%= employee.getEmail() %>
        </td>
    </tr>
    <tr>
        <td>就职日期</td>
        <td><%= employee.getEmployDate() %>
        </td>
    </tr>
    <%
        if (!sendedEvals.isEmpty()) {
    %>
    <tr>
        <td>发出的评价</td>
        <td>
            <table border="1">
                <tr>
                    <td>评价等级</td>
                    <td>评价内容</td>
                    <td>被评价id</td>
                </tr>
                <%
                    for (EvaluationPo e : sendedEvals) {
                %>
                <tr>
                    <td><%= e.getStarLevel() %>
                    </td>
                    <td><%= e.getComment() %>
                    </td>
                    <td><%= e.getBeEvaluatedId() %>
                    </td>
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
        <td>收到的评价</td>
        <td>
            <table border="1">
                <tr>
                    <td>评价等级</td>
                    <td>评价内容</td>
                    <td>评价者</td>
                </tr>
                <%
                    for (EvaluationPo e : receivedEvals) {
                %>
                <tr>
                    <td><%= e.getStarLevel() %>
                    </td>
                    <td><%= e.getComment() %>
                    </td>
                    <td><%= e.getEvaluatorId() %>
                    </td>
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
    }
    else {
%>
<h1 align="center">该员工不存在, 可能离开我们了吧......</h1>
<%
    }
%>
</body>
</html>
