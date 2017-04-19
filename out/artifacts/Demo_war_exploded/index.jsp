<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>

<h2 style="text-align: center">员工登录</h2>
<form method="post" action="login.do">
    <table align="center">
        <tr>
            <td style="text-align: right">员工号:</td>
            <td><input type="text" name="employeeId"></td>
        </tr>
        <tr>
            <td style="text-align: right">密码:</td>
            <td><input type="password" name="password"></td>
        </tr>
    </table><br/>
    <div style="text-align: center">
        <input type="submit" value="登录">
    </div>
</form>

</body>
</html>
