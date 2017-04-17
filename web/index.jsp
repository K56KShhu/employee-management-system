<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>

<h1>员工登录</h1>
<form method="post" action="login.do">
    <tr>
        <td>员工号: </td>
        <td><input type="text" name="employeeId"></td>
    </tr>
    <tr>
        <td>密码:</td>
        <td><input type="password" name="password"></td>
    </tr>
    <tr>
        <td><input type="submit" value="登录"></td>
    </tr>
</form>

</body>
</html>
