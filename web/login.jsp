<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆页面</title>
</head>
<body>
<h1>Login page</h1>
<form action="shiro/shiroLogin" method="post">
    username：<input type="text" name="username"/><br/>
    <br/>
    password：<input type="password" name="password"><br/>
    <br/>
    <input type="submit" value="login"/>
</form>
</body>
</html>
